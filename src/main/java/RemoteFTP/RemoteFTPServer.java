package RemoteFTP;

import TaskHandler.RunnableTask;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Stack;

public class RemoteFTPServer {
    private FTPClient ftpClient;
    private Stack<String> directories;
    private List<RemoteFile> files;
    private Calendar selectedDate;

    public RemoteFTPServer(FTPClient ftpClient, Calendar selectedDate) {
        this.ftpClient = ftpClient;
        this.directories = new Stack<>();
        this.files = new ArrayList<>();
        this.selectedDate = selectedDate;
    }

    public List<RunnableTask> getFilesAsTasks(File destinyDirectory) throws IOException {
        System.out.println("GET FILES AS TASKS");
        scanFiles();
        List<RunnableTask> tasks = new ArrayList<>();
        for (RemoteFile file : files) {
            RunnableTask task = new RunnableTask(ftpClient, file, destinyDirectory);
            tasks.add(task);
        }
        return tasks;
    }

    private void scanFiles() throws IOException {
        System.out.println("SCAN FILES");
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
        return entry.isFile();
    }
}
