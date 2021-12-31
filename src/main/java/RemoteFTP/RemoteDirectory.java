//package RemoteFTP;
//
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Calendar;
//
//public class RemoteDirectory {
//    private FTPClient ftpClient;
//    private String path;
//    private ArrayList<RemoteFile> remoteFiles;
//
//    public RemoteDirectory(FTPClient ftpClient, String path, ArrayList<RemoteFile> remoteFiles) {
//        this.ftpClient = ftpClient;
//        this.path = path;
//        this.remoteFiles = remoteFiles;
//    }
//
//    public void scan(Calendar selectedDate) throws IOException {
////        TODO - selectedDate funciona??
//
//        FTPFile[] files = ftpClient.listFiles(path);
//        for (FTPFile file : files) {
//            if (selectionCriteria()) {
//                RemoteFile remoteFile = new RemoteFile(file, path);
//                remoteFiles.add(remoteFile);
//            }
//        }
//
//
//    }
//
//    private boolean selectionCriteria() {
//        return true;
//    }
//
//}
