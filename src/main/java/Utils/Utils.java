package Utils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static File createNewDir(String baseDir, Date currDay) {
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

}
