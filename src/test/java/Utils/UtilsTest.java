package Utils;

import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled("Disabled until ME APETECER, BOY")
class UtilsTest {

    @Test
    void createNewDirSuccess() {
        String baseDirTest = "C:\\Users\\Antonio\\Desktop\\Freddy Locks\\teste";
        LocalDate currDayTest = LocalDate.of(2022, 1, 16);
        File newDirTest = Utils.createNewDir(baseDirTest, currDayTest);
        Path pathTest = Paths.get(newDirTest.getAbsolutePath());
        Assertions.assertAll("new directory",
                () -> assertTrue(Files.exists(pathTest)),
                () -> assertTrue(newDirTest.isDirectory()),
                () -> assertFalse(newDirTest.isFile())
        );
    }

    @Test
    void createNewDirFailure() {

    }

    @Test
    void getDirectorySuccess() {
        String pathTest = "C:\\Users\\Antonio\\Desktop\\Freddy Locks\\teste";
        File fileTest = new File(pathTest);
        Assumptions.assumeTrue(fileTest.exists());
        File file = Utils.getDirectory(pathTest);

        Assertions.assertNotNull(file);
        Assertions.assertEquals(file, fileTest);
    }

    @Test
    void getDirectoryFailure() {
        Path pathFileTest = Paths.get("C:\\Users\\Antonio\\Desktop\\Freddy Locks\\testeERRO");
        Assertions.assertFalse(Files.exists(pathFileTest));
    }

    @Test
    void yesterdaySuccess() {
        LocalDate yesterdayTest = LocalDate.of(2022, 1, 15);
        Assertions.assertEquals(Utils.yesterday(), yesterdayTest);
    }

    @Test
    void yesterdayFailure() {
        LocalDate yesterdayTest = LocalDate.of(2000, 1, 1);
        Assertions.assertNotEquals(Utils.yesterday(), yesterdayTest);
    }

    @Test
    void calendarToLocalDateSuccess() {
        Calendar cal = Calendar.getInstance();
        LocalDate todayTest = LocalDate.of(2022, 1, 16);
        LocalDate today = Utils.calendarToLocalDate(cal);
        Assertions.assertEquals(todayTest, today);
    }

    @Test
    void calendarToLocalDateFailure() {
        Calendar cal = Calendar.getInstance();
        LocalDate todayTest = LocalDate.of(2000, 1, 1);
        LocalDate today = Utils.calendarToLocalDate(cal);
        Assertions.assertNotEquals(todayTest, today);
    }

    @Test
    void dateToLocalDateSuccess() {
        Date date = new Date();
        LocalDate localDateTest = LocalDate.of(2022, 1, 16);
        LocalDate localDate = Utils.dateToLocalDate(date);
        Assertions.assertEquals(localDate, localDateTest);
    }

    @Test
    void dateToLocalDateFailure() {
        Date date = new Date();
        LocalDate localDateTest = LocalDate.of(2000, 1, 1);
        LocalDate localDate = Utils.dateToLocalDate(date);
        Assertions.assertNotEquals(localDate, localDateTest);
    }

    @Test
    void sumOneDaySuccess() {
        LocalDate dayTest = LocalDate.of(2022, 1, 16);
        LocalDate dayAfterTest = LocalDate.of(2022, 1, 17);
        LocalDate dayAfter = Utils.sumOneDay(dayTest);
        Assertions.assertEquals(dayAfter, dayAfterTest);
    }

    @Test
    void sumOneDayFailure() {
        LocalDate dayTest = LocalDate.of(2022, 1, 16);
        LocalDate dayAfter = Utils.sumOneDay(dayTest);
        Assertions.assertNotEquals(dayAfter, dayTest);
    }

    @Test
    void getCurrentDateTimeSuccess() {
        Date currentTest = new Date(System.currentTimeMillis());
        Date current = Utils.getCurrentDateTime();

        int dayTest = currentTest.getDate();
        int day = current.getDate();
        int monthTest = currentTest.getMonth();
        int month = current.getMonth();
        int yearTest = currentTest.getYear();
        int year = current.getYear();
        int hourTest = currentTest.getHours();
        int hour = current.getHours();
        int minutesTest = currentTest.getMinutes();
        int minutes = current.getMinutes();
        int secondsTest = currentTest.getSeconds();
        int seconds = current.getSeconds();

        Assertions.assertAll("",
                () -> assertEquals(day, dayTest),
                () -> assertEquals(month, monthTest),
                () -> assertEquals(year, yearTest),
                () -> assertEquals(hour, hourTest),
                () -> assertEquals(minutes, minutesTest),
                () -> assertEquals(seconds, secondsTest)
        );
    }

    @Test
    void getCurrentDateTimeSuccess2() {
        Date currentTest = new Date(System.currentTimeMillis());
        Date current = Utils.getCurrentDateTime();

        Instant instantTest = currentTest.toInstant().truncatedTo(ChronoUnit.SECONDS);
        Instant instant = current.toInstant().truncatedTo(ChronoUnit.SECONDS);

        Assertions.assertEquals(instant, instantTest);
    }

    @Test
    void getCurrentDateTimeFailure() {
        Date currentTest = new Date();
        Calendar calTest = Calendar.getInstance();
        calTest.setTime(currentTest);
        calTest.add(Calendar.DATE, 1);
        currentTest = calTest.getTime();
        Date current = Utils.getCurrentDateTime();

        Instant instantTest = currentTest.toInstant().truncatedTo(ChronoUnit.SECONDS);
        Instant instant = current.toInstant().truncatedTo(ChronoUnit.SECONDS);

        Assertions.assertNotEquals(instant, instantTest);
    }

    @Test
    void docToRunnableTaskSuccess() {

    }

    @Test
    void docToRunnableTaskFailure() {

    }
}