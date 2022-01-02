package Connections;

import Utils.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MongoConnector {
    private MongoDatabase database;
    private MongoCollection<Document> finalizedDays;
    private MongoCollection<Document> failedDownloads;

    public MongoConnector(String uri, String databaseName, String finalizedDays, String failedDownloads) {
        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase(databaseName);
        this.finalizedDays = database.getCollection(finalizedDays);
        this.failedDownloads = database.getCollection(failedDownloads);
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

    public synchronized void writeFailedDownload(String filename, String absolute_name, LocalDate consumption_date, Date failed_datetime) {
        Document doc = new Document();
        doc.put("filename", filename);
        doc.put("absolute_name", absolute_name);
        doc.put("consumption_date", consumption_date);
        doc.put("failed_datetime", failed_datetime);
        failedDownloads.insertOne(doc);
    }

    public synchronized void removeFromFailedDownloads(Document doc) {

    }

    public MongoCursor<Document> getFinalizedDaysAsCursor() {
        return finalizedDays.find().iterator();
    }

    public MongoCursor<Document> getFailedDownloadsAsCursor() {
        return failedDownloads.find().iterator();
    }

    public static void main(String[] args) {
        String url = "mongodb+srv://AntonioRodrigo:PalavraPasse1@rinchoa.txroh.mongodb.net/FTPControl?retryWrites=true&w=majority";
        String database = "FTPControl";
        String finalizedDays = "finalizedDays";
        String failedDownloads = "failedDownloads";
        MongoConnector mongo = new MongoConnector(url, database, finalizedDays, failedDownloads);

        /*
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2021);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 10);
        LocalDate date = Utils.calendarToLocalDate(cal);
        System.out.println(date);

        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date1 = new Date(System.currentTimeMillis());
        System.out.println(formatter1.format(date1));

        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date2 = new Date(System.currentTimeMillis());
        System.out.println(formatter2.format(date2));

        Document doc = new Document();
        doc.put("consumption_date", date);
        doc.put("start_datetime", date1);
        doc.put("end_datetime", date2);
        doc.put("total_records", 0);

        mongo.writeFinalizedDay(doc);
         */

        System.out.println(mongo.getLastDay());
    }

}