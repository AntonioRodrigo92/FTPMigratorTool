package Utils;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class Utils {

//    TODO - ALTERAR PARA CRIAR DiR COM O TEMPO
    public static File createNewDir(String baseDir, LocalDate currDay) {
        File outputDir = new java.io.File("C:\\Users\\Antonio\\Desktop\\Freddy Locks\\teste");
        if (! outputDir.exists()) {
            outputDir.mkdirs();
        }
        return outputDir;
    }

    public static Calendar yesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal;
    }

    public static Calendar sumOneDay (Calendar day) {
        return null;
    }

    public static LocalDate calendarToLocalDate(Calendar cal) {
        return LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate();
    }

}
