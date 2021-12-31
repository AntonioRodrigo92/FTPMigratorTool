package TaskHandler;
import RemoteFTP.RemoteFile;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

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
//            OutputStream output = new FileOutputStream(destinyDirectory.getName() + "/" + remoteFile.getFileName());
//            ftpClient.retrieveFile(remoteFile.getAbsolutePath(), output);

//            System.err.println("SOURCE: " + remoteFile.getAbsolutePath() + "/" + remoteFile.getFileName());
//            System.err.println("OUTPUT: " + destinyDirectory.getAbsolutePath() + "\\" + remoteFile.getFileName());

//            TODO - NAO ACABADO!!!!
            OutputStream output = new FileOutputStream(destinyDirectory.getAbsolutePath() + "\\" + remoteFile.getFileName());
            ftpClient.retrieveFile(remoteFile.getAbsolutePath() + "/" + remoteFile.getFileName(), output);

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

