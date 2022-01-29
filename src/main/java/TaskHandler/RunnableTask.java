package TaskHandler;
import Connections.MongoConnector;
import RemoteFTP.RemoteFile;
import Utils.Utils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class RunnableTask implements Runnable {
    private FTPClient ftpClient;
    private MongoConnector mongo;
    private RemoteFile remoteFile;
    private File destinyDirectory;
    private final Logger LOG = LogManager.getLogger();


    public RunnableTask(FTPClient ftpClient, MongoConnector mongo, RemoteFile remoteFile, File destinyDirectory) {
        this.ftpClient = ftpClient;
        this.mongo = mongo;
        this.remoteFile = remoteFile;
        this.destinyDirectory = destinyDirectory;
    }

    @Override
    public void run() {
        try {
//            TODO - NAO ACABADO: protocolo nao permite concorrencia... :(
            downloadFile();
            mongo.removeFromFailedDownloads(remoteFile.getFileName(), remoteFile.getAbsolutePath());
        }
        catch (Exception e) {
            LOG.error("RunnableTask - run(): GeneralException");
            LOG.error(e);
            mongo.writeFailedDownload(remoteFile.getFileName(), remoteFile.getAbsolutePath(), destinyDirectory.getAbsolutePath() + "/" + remoteFile.getFileName(), Utils.getCurrentDateTime());
        }

    }

    private void downloadFile() throws IOException {
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        try (FileOutputStream fos = new FileOutputStream(destinyDirectory.getAbsolutePath() + "/" + remoteFile.getFileName())) {
            ftpClient.retrieveFile(remoteFile.getAbsolutePath() + "/" + remoteFile.getFileName(), fos);
        }
        catch (IOException e) {
            LOG.error("RunnableTask - downloadFile(): IOException");
            LOG.error(e);
        }
    }

}

