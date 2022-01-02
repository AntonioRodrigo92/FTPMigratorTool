package Connections;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoWriteException;
import com.mongodb.client.*;
import org.bson.Document;
import java.sql.Timestamp;
import java.util.Calendar;

public class MongoConnector {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoConnector(String uri, String databaseName, String collectionName) {
        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase(databaseName);
        collection = database.getCollection(collectionName);
    }

    public MongoCursor<Document> getCollectionAsCursor() {
        return collection.find().iterator();
    }

}