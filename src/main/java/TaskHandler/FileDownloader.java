//package TaskHandler;
//
//import RemoteFTP.RemoteFile;
//import org.apache.commons.net.ftp.FTPClient;
//
//import java.io.*;
//
//public class FileDownloader {
//    private FTPClient ftpClient;
//
//    public FileDownloader(FTPClient ftpClient) {
//        this.ftpClient = ftpClient;
//    }
//
//    public synchronized OutputStream getOutputStream(File destinyDirectory, RemoteFile remoteFile) throws IOException {
//        File localFile = new File(destinyDirectory.getAbsolutePath() + "\\" + remoteFile.getFileName());
//        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localFile));
//
//        return outputStream;
//    }
//
//    public void downloadFile(RemoteFile remoteFile, OutputStream outputStream) throws IOException {
//        InputStream inputStream = ftpClient.retrieveFileStream(remoteFile.getAbsolutePath() + "/" + remoteFile.getFileName());
//        byte[] bytesArray = new byte[4096];
//        int bytesRead = -1;
//        while ((bytesRead = inputStream.read(bytesArray)) != -1) {
//            outputStream.write(bytesArray, 0, bytesRead);
//        }
//
//        boolean success = ftpClient.completePendingCommand();
//        if (success) {
//            System.out.println("File has been downloaded successfully.");
//        }
//        outputStream.close();
//        inputStream.close();;
//    }
//
//}
