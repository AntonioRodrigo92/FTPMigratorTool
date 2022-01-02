package TaskHandler;
import RemoteFTP.RemoteFile;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

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
//            TODO - NAO ACABADO: protocolo nao permite concorrencia... :(

            File localFile = new File(destinyDirectory.getAbsolutePath() + "\\" + remoteFile.getFileName());
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localFile));
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile.getAbsolutePath() + "/" + remoteFile.getFileName());
            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                outputStream.write(bytesArray, 0, bytesRead);
            }
            boolean success = ftpClient.completePendingCommand();
            if (success) {
                System.out.println("File has been downloaded successfully.");
            }
            outputStream.close();
            inputStream.close();


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

