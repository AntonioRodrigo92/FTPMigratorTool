package Connections;

import Utils.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import java.time.LocalDate;
import java.util.Date;

public class MongoConnector {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> finalizedDays;
    private MongoCollection<Document> failedDownloads;
    private final Logger LOG = LogManager.getLogger();

    public MongoConnector(String uri, String databaseName, String finalizedDays, String failedDownloads) {
        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase(databaseName);
        this.finalizedDays = database.getCollection(finalizedDays);
        this.failedDownloads = database.getCollection(failedDownloads);

        LOG.info("Connected do Mongo Database successfully!");
    }

    public LocalDate getLastDay() {
        FindIterable<Document> cursor = finalizedDays.find().sort(
                new BasicDBObject("consumption_date", -1)
        ).limit(1);
        Document lastDay = cursor.iterator().next();
        Date date = (Date) lastDay.get("consumption_date");
        return Utils.dateToLocalDate(date);
    }

    public void writeFinalizedDay(LocalDate consumption_date, Date start_datetime, Date end_datetime, int total_records) {
        Document doc = new Document();
        doc.put("consumption_date", consumption_date);
        doc.put("start_datetime", start_datetime);
        doc.put("end_datetime", end_datetime);
        doc.put("total_records", total_records);
        finalizedDays.insertOne(doc);
    }

    public FindIterable<Document> getFailedTasks() {
        return failedDownloads.find();
    }

    public synchronized void writeFailedDownload(String filename, String absolute_path, String destiny_dir, Date failed_datetime) {
        Document doc = new Document();
        doc.put("filename", filename);
        doc.put("absolute_path", absolute_path);
        doc.put("destiny_dir", destiny_dir);
        doc.put("failed_datetime", failed_datetime);
        failedDownloads.insertOne(doc);
    }

    public synchronized void removeFromFailedDownloads(String filename, String absolute_path) {
        BasicDBObject query = new BasicDBObject();
        query.append("filename", filename);
        query.append("absolute_path", absolute_path);
        failedDownloads.deleteMany(query);
    }

    public void closeConnection() {
        mongoClient.close();
        LOG.info("Mongo connection closed!");
    }

}