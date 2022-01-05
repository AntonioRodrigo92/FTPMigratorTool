package Utils;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static File createNewDir(String baseDir, LocalDate currDay) {
        File outputDir = new java.io.File(baseDir + "/" + currDay.toString());
        if (! outputDir.exists()) {
            outputDir.mkdirs();
        }
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

}
