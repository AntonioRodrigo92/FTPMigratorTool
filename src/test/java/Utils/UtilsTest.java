package Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
    }

    @Test
    void sumOneDayFailure() {
    }

    @Test
    void getCurrentDateTimeSuccess() {
    }

    @Test
    void getCurrentDateTimeFailure() {
    }

    @Test
    void docToTunnableTaskSuccess() {
    }

    @Test
    void docToTunnableTaskFailure() {
    }
}