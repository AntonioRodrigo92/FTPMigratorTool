package Utils;

import TaskHandler.RunnableTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void createNewDir() {
    }

    @Test
    void getDirectory() {
    }

    @Test
    void yesterdaySuccess() {
        LocalDate yesterdayTest = LocalDate.of(2022, 1, 14);
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
        LocalDate todayTest = LocalDate.of(2022, 1, 15);
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
        LocalDate localDateTest = LocalDate.of(2022, 1, 15);
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
        LocalDate dayTest = LocalDate.of(2022, 1, 15);
        LocalDate dayAfterTest = LocalDate.of(2022, 1, 16);
        LocalDate dayAfter = Utils.sumOneDay(dayTest);
        Assertions.assertEquals(dayAfter, dayAfterTest);
    }

    @Test
    void sumOneDayFailure() {
        LocalDate dayTest = LocalDate.of(2022, 1, 15);
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

        Assertions.assertEquals(day, dayTest);
        Assertions.assertEquals(month, monthTest);
        Assertions.assertEquals(year, yearTest);
        Assertions.assertEquals(hour, hourTest);
        Assertions.assertEquals(minutes, minutesTest);
        Assertions.assertEquals(seconds, secondsTest);
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