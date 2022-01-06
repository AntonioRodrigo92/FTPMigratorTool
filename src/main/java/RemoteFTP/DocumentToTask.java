package RemoteFTP;

import Connections.MongoConnector;
import TaskHandler.RunnableTask;
import Utils.UserInput;
import Utils.Utils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.bson.Document;

import java.io.File;

public class DocumentToTask {

    public RemoteFile DocToRemoteFile (Document doc) {
        String filename = (String) doc.get("filename");
        String path = (String) doc.get("absolute_path");
        FTPFile file = new FTPFile();
        file.setName(filename);
        RemoteFile remoteFile = new RemoteFile(file, path);
        return remoteFile;
    }

    public RunnableTask remoteFileToTask (FTPClient ftpClient, MongoConnector mongo, RemoteFile file, String destinyDirectory) {
        File destDir = Utils.getDirectory(destinyDirectory);
        RunnableTask task = new RunnableTask(ftpClient, mongo, file, destDir);
        return task;
    }

    public static void main(String[] args) {



    }
}
