package RemoteFTP;

import Connections.MongoConnector;
import TaskHandler.RunnableTask;
import Utils.Utils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class RemoteFTPServer {
    private FTPClient ftpClient;
    private MongoConnector mongo;
    private Stack<String> directories;
    private List<RemoteFile> files;
    private LocalDate selectedDate;


    public RemoteFTPServer(FTPClient ftpClient, MongoConnector mongo, LocalDate selectedDate) {
        this.ftpClient = ftpClient;
        this.mongo = mongo;
        this.directories = new Stack<>();
        this.files = new ArrayList<>();
        this.selectedDate = selectedDate;
    }

    public List<RunnableTask> getFilesAsTasks(File destinyDirectory) throws IOException {
        scanFiles();
        List<RunnableTask> tasks = new ArrayList<>();
        for (RemoteFile file : files) {
            RunnableTask task = new RunnableTask(ftpClient, mongo, file, destinyDirectory);
            tasks.add(task);
        }
        return tasks;
    }

    private void scanFiles() throws IOException {
        directories.push("");
        while (! directories.empty()) {
            String dirPath = directories.pop();
            FTPFile[] entries = ftpClient.listFiles(dirPath);
            for (FTPFile entry : entries) {
                if (fileSelectionCriteria(entry)) {
                    RemoteFile remoteFile = new RemoteFile(entry, dirPath);
                    files.add(remoteFile);
                }
                if (directorySelectionCriteria(entry)) {
                    String dir = dirPath + "/" + entry.getName();
                    directories.push(dir);
                }
            }
        }
    }

    private boolean directorySelectionCriteria(FTPFile entry) {
        return entry.isDirectory();
    }

    private boolean fileSelectionCriteria(FTPFile entry) {
        LocalDate date = Utils.calendarToLocalDate(entry.getTimestamp());
        return date.equals(selectedDate) && entry.isFile() && entry.getSize() > 0 && entry.getName().endsWith(".mp4");
    }
}
