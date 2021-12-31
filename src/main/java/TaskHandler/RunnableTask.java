package TaskHandler;
import RemoteFTP.RemoteFile;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class RunnableTask implements Runnable {
    private FTPClient ftpClient;
//    private MongoConnector mongo;
    private RemoteFile remoteFile;
    private File destinyDirectory;

    public RunnableTask(FTPClient ftpClient, /*MongoConnector mongo,*/ RemoteFile remoteFile, File destinyDirectory) {
        this.ftpClient = ftpClient;
//        this.mongo = mongo;
        this.remoteFile = remoteFile;
        this.destinyDirectory = destinyDirectory;
    }

    @Override
    public void run() {
        try {
//            TODO - NAO ACABADO!!!!
            System.out.println("running task");
            FileOutputStream output = new FileOutputStream(destinyDirectory.getAbsolutePath() + "\\" + remoteFile.getFileName());
//            OutputStream output = new BufferedOutputStream(new FileOutputStream(destinyDirectory.getAbsolutePath() + "\\" + remoteFile.getFileName()));
            ftpClient.retrieveFile(remoteFile.getAbsolutePath() + "/" + remoteFile.getFileName(), output);
            output.flush();
            output.close();


            System.out.println("task ended");

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

