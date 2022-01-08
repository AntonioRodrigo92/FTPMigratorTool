package Utils;

import Connections.MongoConnector;
import RemoteFTP.RemoteFile;
import TaskHandler.RunnableTask;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.bson.Document;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static File createNewDir(String baseDir, LocalDate currDay) {
        File outputDir = new java.io.File(baseDir + "\\" + currDay.toString());
        if (! outputDir.exists()) {
            outputDir.mkdirs();
        }
        return outputDir;
    }

    public static File getDirectory(String path) {
        File outputDir = new java.io.File(path);
        return outputDir;
    }

    public static LocalDate yesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return calendarToLocalDate(cal);
    }


    public static LocalDate calendarToLocalDate(Calendar cal) {
        return LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate();
    }

    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static LocalDate sumOneDay (LocalDate day) {
        return day.plusDays(1);
    }

    public static Date getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        return new Date(System.currentTimeMillis());
    }

    public static RunnableTask docToTunnableTask (FTPClient ftpClient, MongoConnector mongo, Document doc) {
        String filename = (String) doc.get("filename");
        String path = (String) doc.get("absolute_path");
        String destDir = Utils.getJustDir((String) doc.get("destiny_dir"));

        FTPFile file = new FTPFile();
        file.setName(filename);
        RemoteFile remoteFile = new RemoteFile(file, path);
        File destinyDir = Utils.getDirectory(destDir);
        RunnableTask task = new RunnableTask(ftpClient, mongo, remoteFile, destinyDir);
        return task;
    }

    private static String getJustDir (String destDirPlusFile) {
        String[] splits = destDirPlusFile.split("\\\\");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < splits.length - 1; i++) {
            sb.append(splits[i] + "\\");
        }
        return sb.toString();
    }

}
