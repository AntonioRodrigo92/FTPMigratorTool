package Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UserInput {
    private String baseDirectory;
    private String ftpServer;
    private int ftpPort;
    private String ftpUser;
    private String ftpPass;
    private String mongoURI;
    private String mongoDatabase;
    private String mongoCollectionDays;
    private String mongoCollectionFailures;
    private final Logger LOG = LogManager.getLogger();

    public UserInput(String path) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            LOG.error("UserInput: IOException");
            LOG.error(e);
        }
        baseDirectory = properties.getProperty("BASE_DIRECTORY");
        ftpServer = properties.getProperty("SERVER");
        ftpPort = Integer.parseInt(properties.getProperty("PORT"));
        ftpUser = properties.getProperty("USER");
        ftpPass = properties.getProperty("PASSWORD");
        mongoURI = properties.getProperty("MONGO_URI");
        mongoDatabase = properties.getProperty("MONGO_DATABASE");
        mongoCollectionDays = properties.getProperty("MONGO_COLLECTION_DAYS");
        mongoCollectionFailures = properties.getProperty("MONGO_COLLECTION_FAILURES");
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public String getFtpServer() {
        return ftpServer;
    }

    public int getFtpPort() {
        return ftpPort;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public String getFtpPass() {
        return ftpPass;
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
