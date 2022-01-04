package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UserInput {
    private String baseDirectory;
    private String server;
    private int port;
    private String user;
    private String pass;
    private String mongoURI;
    private String mongoDatabase;
    private String mongoCollectionDays;
    private String mongoCollectionFailures;

    public UserInput(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(path));
        baseDirectory = properties.getProperty("BASE_DIRECTORY");
        server = properties.getProperty("SERVER");
        port = Integer.parseInt(properties.getProperty("PORT"));
        user = properties.getProperty("USER");
        pass = properties.getProperty("PASSWORD");
        mongoURI = properties.getProperty("MONGO_URI");
        mongoDatabase = properties.getProperty("MONGO_DATABASE");
        mongoCollectionDays = properties.getProperty("MONGO_COLLECTION_DAYS");
        mongoCollectionFailures = properties.getProperty("MONGO_COLLECTION_FAILURES");
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getMongoURI() {
        return mongoURI;
    }

    public String getMongoDatabase() {
        return mongoDatabase;
    }

    public String getMongoCollectionDays() {
        return mongoCollectionDays;
    }

    public String getMongoCollectionFailures() {
        return mongoCollectionFailures;
    }
}
