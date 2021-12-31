package TaskHandler;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

public class RunnableTask implements Runnable {
    private FTPClient ftpClient;
//    private MongoConnector mongo;
    private String filePath;
    private String destinyDirectory;

    public RunnableTask(FTPClient ftpClient, /*MongoConnector mongo,*/ String filePath, String destinyDirectory) {
        this.ftpClient = ftpClient;
//        this.mongo = mongo;
        this.filePath = filePath;
        this.destinyDirectory = destinyDirectory;
    }

    @Override
    public void run() {
        try {
            OutputStream output = new FileOutputStream(destinyDirectory + "/" + getFileName(filePath));
            ftpClient.retrieveFile(filePath, output);

//            mongo.eraseFailedTask();
        }
        catch (Exception e) {
//            mongo.writeFailedTask(filePath, destinyDirectory);
        }

    }

    private String getFileName (String filePath) {
        String[] pathAsVector = filePath.split("/");
        return pathAsVector[pathAsVector.length - 1];
    }
}

