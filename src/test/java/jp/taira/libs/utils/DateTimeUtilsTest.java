package jp.taira.libs.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DateTimeUtilsTest {

    @Test
    public void constractorTest() {
        assertThrows(IllegalAccessError.class, () -> {
            try {
                Constructor<?> constructor = DateTimeUtils.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        });
    }

    @Test
    public void isDateFormatTest() throws Exception {
        assertTrue(DateTimeUtils.isDateFormat("2000/1/2"));
        assertTrue(DateTimeUtils.isDateFormat("2000/11/22"));
        assertFalse(DateTimeUtils.isDateFormat("yyyy/mm/dd"));

        assertTrue(DateTimeUtils.isDateFormat("2000/11/22 0:0:0", DateTimeUtils.DATETIME_FORMAT_FULL));
        assertTrue(DateTimeUtils.isDateFormat("2000/11/22 00:00:00", DateTimeUtils.DATETIME_FORMAT_FULL));
        assertTrue(DateTimeUtils.isDateFormat("2000/11/22 1:2:3", DateTimeUtils.DATETIME_FORMAT_FULL));
        assertTrue(DateTimeUtils.isDateFormat("2000/11/22 12:34:56", DateTimeUtils.DATETIME_FORMAT_FULL));
        assertTrue(DateTimeUtils.isDateFormat("2000/11/22 23:59:59", DateTimeUtils.DATETIME_FORMAT_FULL));
        assertFalse(DateTimeUtils.isDateFormat("2000/11/22 24:00:00", DateTimeUtils.DATETIME_FORMAT_FULL));
        assertFalse(DateTimeUtils.isDateFormat("yyyy/mm/dd hh:mm:ss", DateTimeUtils.DATETIME_FORMAT_FULL));

        assertFalse(DateTimeUtils.isDateFormat("2000/11/22T0:0:0", DateTimeFormatter.ISO_DATE_TIME));
        assertTrue(DateTimeUtils.isDateFormat("2000/11/22T00:00:00", DateTimeFormatter.ISO_DATE_TIME));
        assertFalse(DateTimeUtils.isDateFormat("2000/11/22T1:2:3", DateTimeFormatter.ISO_DATE_TIME));
        assertTrue(DateTimeUtils.isDateFormat("2000/11/22T12:34:56", DateTimeFormatter.ISO_DATE_TIME));
        assertTrue(DateTimeUtils.isDateFormat("2000/11/22T23:59:59", DateTimeFormatter.ISO_DATE_TIME));
        assertFalse(DateTimeUtils.isDateFormat("2000/11/22T24:00:00", DateTimeFormatter.ISO_DATE_TIME));
    }

    @Test
    public void isDateTimeFormatTest() throws Exception {
        assertFalse(DateTimeUtils.isDateTimeFormat("2000/1/2"));
        assertFalse(DateTimeUtils.isDateTimeFormat("2000/11/22"));
        assertFalse(DateTimeUtils.isDateTimeFormat("yyyy/mm/dd"));

        assertTrue(DateTimeUtils.isDateTimeFormat("2000/11/22 12:34"));
        assertFalse(DateTimeUtils.isDateTimeFormat("yyyy/mm/dd hh:mm"));
    }

    @Test
    public void isTodayTest() throws Exception {
        LocalDate nowDate = LocalDate.now();
        LocalDateTime nowDateTime = LocalDateTime.now();

        assertTrue(DateTimeUtils.isToday(DateTimeUtils.toString(nowDate)));
        assertTrue(DateTimeUtils.isToday(nowDate.toString()));
        assertFalse(DateTimeUtils.isToday("yyyy/mm/dd"));

        assertTrue(DateTimeUtils.isToday(DateTimeUtils.toString(nowDateTime)));
        assertFalse(DateTimeUtils.isToday(nowDateTime.toString()));
        assertFalse(DateTimeUtils.isToday("yyyy/mm/dd hh:mm:ss"));
    }

    @Test
    public void parseToLocalDateTest() throws Exception {
        { /* null */
            Object obj = DateTimeUtils.parseToLocalDate(null);
            assertNull(obj);
        }

        { /* 空文字 */
            Object obj = DateTimeUtils.parseToLocalDate("");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToLocalDate("2000///11/22");
            assertNull(obj);
        }

        { /* 存在しない日付(月) */
            Object obj = DateTimeUtils.parseToLocalDate("2000/19/22");
            assertNull(obj);
        }

        { /* 存在しない日付(日) */
            Object obj = DateTimeUtils.parseToLocalDate("2000/11/99");
            assertNull(obj);
        }

        /* 共通変数 */
        Object obj = null;

        obj = DateTimeUtils.parseToLocalDate("2000/1/2");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);

        obj = DateTimeUtils.parseToLocalDate("2000/01/02");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);

        obj = DateTimeUtils.parseToLocalDate("2000/11/22");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);

        obj = DateTimeUtils.parseToLocalDate("2000-11-22");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);

        obj = DateTimeUtils.parseToLocalDate("2000/11/22", "uuuu/MM/dd");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);

        obj = DateTimeUtils.parseToLocalDate("2000/11/22", "uuuu-MM-dd");
        assertNull(obj);

        obj = DateTimeUtils.parseToLocalDate("2000-11-22", "uuuu/MM/dd");
        assertNull(obj);

        obj = DateTimeUtils.parseToLocalDate("2000-11-22", "uuuu-MM-dd");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);
    }

    @Test
    public void parseToLocalDateTimeTest() throws Exception {
        { /* null */
            Object obj = DateTimeUtils.parseToLocalDateTime(null);
            assertNull(obj);
        }

        { /* 空文字 */
            Object obj = DateTimeUtils.parseToLocalDateTime("");
            assertNull(obj);
        }

        { /* 年月日のみ */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/11/22");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000///11/22");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 12:::34");
            assertNull(obj);
        }

        { /* 存在しない日付(月) */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/19/22");
            assertNull(obj);
        }

        { /* 存在しない日付(日) */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/11/99");
            assertNull(obj);
        }

        { /* 存在しない時間 */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 24:00");
            assertNull(obj);
        }

        { /* 存在しない時間(時) */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 99:00");
            assertNull(obj);
        }

        { /* 存在しない時間(分) */
            Object obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 11:99");
            assertNull(obj);
        }

        { /* ISO_OFFSET_DATE_TIME */
            Object obj = DateTimeUtils.parseToLocalDateTime("2011-12-03T10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            assertNotNull(obj);
            assertTrue(obj instanceof LocalDateTime);
        }
        { /* ISO_OFFSET_DATE_TIME */
            Object obj = DateTimeUtils.parseToLocalDateTime("2011-12-03T10:15:30.000+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            assertNotNull(obj);
            assertTrue(obj instanceof LocalDateTime);
        }
        { /* 文字列形式 */
            Object obj = DateTimeUtils.parseToLocalDateTime("2011-12-01T10:15:30.000+0100", "uuuu-MM-dd'T'HH:mm:ss.SSSZ");
            assertNotNull(obj);
            assertTrue(obj instanceof LocalDateTime);
        }
        { /* 文字列形式 */
            Object obj = DateTimeUtils.parseToLocalDateTime("2011-12-01T10:15:30.000+0100", "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            assertNull(obj);
        }

        /* 共通変数 */
        Object obj = null;

        obj = DateTimeUtils.parseToLocalDateTime("2000/1/2 0:0");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/1/2 00:00");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/1/2 1:1");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/01/02 01:01");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 12:34");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 23:59");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/1/2 0:0:0");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/1/2 00:00:00");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/1/2 1:1:0");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/01/02 01:01:00");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 12:34:00");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 23:59:00");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 0:0", "uuuu/MM/dd H:m");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000/11/22 1:1", "uuuu/MM/dd H:m");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000-11-22 1:1", "uuuu-MM-dd H:m");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("2000-11-22 23:59", "uuuu-MM-dd HH:mm");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);
    }

    @Test
    public void parseToLocalDateTimeTest_DowMon() throws Exception {
        { /* 年月日のみ */
            Object obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 01 2019");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToLocalDateTime("Mar Fri 01 00::00::00 JST 2019");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 01 00::00::00 JST 2019");
            assertNull(obj);
        }

        { /* 存在しない日付(曜日) */
            Object obj = DateTimeUtils.parseToLocalDateTime("AAA Mar 01 00:00:00 JST 2019");
            assertNull(obj);
        }

        { /* 存在しない日付(月) */
            Object obj = DateTimeUtils.parseToLocalDateTime("Fri AAA 01 00:00:00 JST 2019");
            assertNull(obj);
        }

        { /* 存在しない日付(日) */
            Object obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 99 00:00:00 JST 2019");
            assertNull(obj);
        }

        { /* 存在しない時間 */
            Object obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 01 24:59:00 JST 2019");
            assertNull(obj);
        }

        { /* 存在しない時間 */
            Object obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 01 24:59:59 JST 2019");
            assertNull(obj);
        }

        { /* 存在しない時間(時) */
            Object obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 01 25:00:00 JST 2019");
            assertNull(obj);
        }

        { /* 存在しない時間(時) */
            Object obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 01 99:00:00 JST 2019");
            assertNull(obj);
        }

        { /* 存在しない時間(分) */
            Object obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 01 00:60:00 JST 2019");
            assertNull(obj);
        }

        { /* 存在しない時間(分) */
            Object obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 01 00:99:00 JST 2019");
            assertNull(obj);
        }

        { /* 存在しない時間(秒) */
            Object obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 01 00:00:60 JST 2019");
            assertNull(obj);
        }

        { /* 存在しない時間(秒) */
            Object obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 01 00:00:99 JST 2019");
            assertNull(obj);
        }

        /* 共通変数 */
        Object obj = null;

        obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 1 0:0:0 JST 2019");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 1 00:00:00 JST 2019");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 01 11:11:11 JST 2019");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 01 00:00:00 JST 2019");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);

        obj = DateTimeUtils.parseToLocalDateTime("Fri Mar 01 24:00:00 JST 2019");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDateTime);
    }

    @Test
    public void parseToLocalTimeTest() throws Exception {
        { /* null */
            Object obj = DateTimeUtils.parseToLocalTime(null);
            assertNull(obj);
        }

        { /* 空文字 */
            Object obj = DateTimeUtils.parseToLocalTime("");
            assertNull(obj);
        }

        { /* 年月日のみ */
            Object obj = DateTimeUtils.parseToLocalTime("2000/11/22");
            assertNull(obj);
        }

        { /* 年月日を含む */
            Object obj = DateTimeUtils.parseToLocalTime("2000/11/22 12:34");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToLocalTime("12:::34");
            assertNull(obj);
        }

        { /* 存在しない時間 */
            Object obj = DateTimeUtils.parseToLocalTime("24:00");
            assertNull(obj);
        }

        { /* 存在しない時間(時) */
            Object obj = DateTimeUtils.parseToLocalTime("99:00");
            assertNull(obj);
        }

        { /* 存在しない時間(分) */
            Object obj = DateTimeUtils.parseToLocalTime("11:99");
            assertNull(obj);
        }

        /* 共通変数 */
        Object obj = null;

        obj = DateTimeUtils.parseToLocalTime("0:0");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("00:00");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("1:1");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("01:01");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("12:34");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("23:59");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("0:0", "H:m");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("1:1", "H:m");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("00:00", "HH:mm");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);

        obj = DateTimeUtils.parseToLocalTime("23:59", "HH:mm");
        assertNotNull(obj);
        assertTrue(obj instanceof LocalTime);
    }

    @Test
    public void parseToDateTest() throws Exception {
        { /* null */
            Object obj = DateTimeUtils.parseToDate(null, null);
            assertNull(obj);
        }

        { /* 空文字 */
            Object obj = DateTimeUtils.parseToDate("", "");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToDate("2000///11/22", DateTimeUtils.DATE_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない日付(月) */
            Object obj = DateTimeUtils.parseToDate("2000/19/22", DateTimeUtils.DATE_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない日付(日) */
            Object obj = DateTimeUtils.parseToDate("2000/11/99", DateTimeUtils.DATE_FORMAT);
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToDate("2000/11/22 12:::34", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない時間 */
            Object obj = DateTimeUtils.parseToDate("2000/11/22 24:00", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない時間(時) */
            Object obj = DateTimeUtils.parseToDate("2000/11/22 99:00", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない時間(分) */
            Object obj = DateTimeUtils.parseToDate("2000/11/22 11:99", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        /* 共通変数 */
        Object obj = null;

        obj = DateTimeUtils.parseToDate("2000/1/2", DateTimeUtils.DATE_FORMAT);;
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/01/02", DateTimeUtils.DATE_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/11/22", DateTimeUtils.DATE_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/1/2 0:0", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/1/2 00:00", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/1/2 1:1", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/01/02 01:01", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/11/22 12:34", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);

        obj = DateTimeUtils.parseToDate("2000/11/22 23:59", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Date);
    }

    @Test
    public void parseToTimestampTest() throws Exception {
        { /* null */
            Object obj = DateTimeUtils.parseToTimestamp(null, null);
            assertNull(obj);
        }

        { /* 空文字 */
            Object obj = DateTimeUtils.parseToTimestamp("", "");
            assertNull(obj);
        }

        { /* 年月日のみ */
            Object obj = DateTimeUtils.parseToLocalTime("2000/11/22");
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToTimestamp("2000///11/22", DateTimeUtils.DATE_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない日付(月) */
            Object obj = DateTimeUtils.parseToTimestamp("2000/19/22", DateTimeUtils.DATE_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない日付(日) */
            Object obj = DateTimeUtils.parseToTimestamp("2000/11/99", DateTimeUtils.DATE_FORMAT);
            assertNull(obj);
        }

        { /* 形式不正 */
            Object obj = DateTimeUtils.parseToTimestamp("2000/11/22 12:::34", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない時間 */
            Object obj = DateTimeUtils.parseToTimestamp("2000/11/22 24:00", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない時間(時) */
            Object obj = DateTimeUtils.parseToTimestamp("2000/11/22 99:00", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        { /* 存在しない時間(分) */
            Object obj = DateTimeUtils.parseToTimestamp("2000/11/22 11:99", DateTimeUtils.DATETIME_FORMAT);
            assertNull(obj);
        }

        /* 共通変数 */
        Object obj = null;

        obj = DateTimeUtils.parseToTimestamp("2000/1/2 0:0", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Timestamp);

        obj = DateTimeUtils.parseToTimestamp("2000/1/2 00:00", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Timestamp);

        obj = DateTimeUtils.parseToTimestamp("2000/1/2 1:1", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Timestamp);

        obj = DateTimeUtils.parseToTimestamp("2000/01/02 01:01", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Timestamp);

        obj = DateTimeUtils.parseToTimestamp("2000/11/22 12:34", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Timestamp);

        obj = DateTimeUtils.parseToTimestamp("2000/1/2 23:59", DateTimeUtils.DATETIME_FORMAT);
        assertNotNull(obj);
        assertTrue(obj instanceof Timestamp);
    }

    @Test
    public void toStringTest() throws Exception {

        { /* LocalDate */
            LocalDate target;

            /* null */
            target = null;
            assertNull(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT));
            assertNull(DateTimeUtils.toString(target));

            /* フォーマット不正 */
            target = DateTimeUtils.parseToLocalDate("2000/01/02");
            assertNotNull(target);
            assertNull(DateTimeUtils.toString(target, DateTimeUtils.DATETIME_FORMAT));

            /* フォーマット不正 */
            target = DateTimeUtils.parseToLocalDate("2000/01/02");
            assertNotNull(target);
            assertNull(DateTimeUtils.toString(target, DateTimeUtils.TIME_FORMAT));

            target = DateTimeUtils.parseToLocalDate("2000/1/2");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT), "2000/1/2");

            target = DateTimeUtils.parseToLocalDate("2000/1/2");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT_FIXED), "2000/01/02");

            target = DateTimeUtils.parseToLocalDate("2000/01/02");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT), "2000/1/2");

            target = DateTimeUtils.parseToLocalDate("2000/01/02");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT_FIXED), "2000/01/02");

            target = DateTimeUtils.parseToLocalDate("2000/1/2");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target), "2000/01/02");

            target = DateTimeUtils.parseToLocalDate("2000/01/02");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target), "2000/01/02");

            target = DateTimeUtils.parseToLocalDate("2000/11/22");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target), "2000/11/22");
        }

        { /* LocalDateTime */
            LocalDateTime target;

            /* null */
            target = null;
            assertNull(DateTimeUtils.toString(target, DateTimeUtils.DATETIME_FORMAT_FULL));
            assertNull(DateTimeUtils.toString(target));

            target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT), "2000/1/2");

            target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATE_FORMAT_FIXED), "2000/01/02");

            target = DateTimeUtils.parseToLocalDateTime("2000/1/2 3:4");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATETIME_FORMAT), "2000/1/2 3:4");

            target = DateTimeUtils.parseToLocalDateTime("2000/1/2 3:4");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target, DateTimeUtils.DATETIME_FORMAT_FIXED), "2000/01/02 03:04");

            target = DateTimeUtils.parseToLocalDateTime("2000/1/2 3:4");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target, DateTimeUtils.TIME_FORMAT), "3:4");

            target = DateTimeUtils.parseToLocalDateTime("2000/1/2 3:4");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target, DateTimeUtils.TIME_FORMAT_FIXED), "03:04");

            target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target), "2000/01/02 12:34");

            target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target), "2000/01/02 12:34");

            target = DateTimeUtils.parseToLocalDateTime("2000/1/2 3:4");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target), "2000/01/02 03:04");

            target = DateTimeUtils.parseToLocalDateTime("2000/11/22 12:34");
            assertNotNull(target);
            assertEquals(DateTimeUtils.toString(target), "2000/11/22 12:34");
        }
    }

    @Test
    public void toFullStringTest() throws Exception {
        LocalDateTime target;

        /* null */
        target = null;
        assertNull(DateTimeUtils.toFullString(target));

        target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
        assertNotNull(target);
        assertEquals(DateTimeUtils.toFullString(target), "2000/01/02 12:34:00");

        target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34:56", DateTimeUtils.DATETIME_FORMAT_FULL);
        assertNotNull(target);
        assertEquals(DateTimeUtils.toFullString(target), "2000/01/02 12:34:56");

        target = DateTimeUtils.parseToLocalDateTime("2000/1/2 3:4:5", DateTimeUtils.DATETIME_FORMAT_FULL);
        assertNotNull(target);
        assertEquals(DateTimeUtils.toFullString(target), "2000/01/02 03:04:05");

        target = DateTimeUtils.parseToLocalDateTime("2000/11/22 12:34:56", DateTimeUtils.DATETIME_FORMAT_FULL);
        assertNotNull(target);
        assertEquals(DateTimeUtils.toFullString(target), "2000/11/22 12:34:56");
    }

    @Test
    public void toFormatStringByMinuteTest() throws Exception {
        assertEquals(DateTimeUtils.toFormatStringByMinute(-1), null);

        assertEquals(DateTimeUtils.toFormatStringByMinute(0), "00:00");
        assertEquals(DateTimeUtils.toFormatStringByMinute(1), "00:01");
        assertEquals(DateTimeUtils.toFormatStringByMinute(59), "00:59");
        assertEquals(DateTimeUtils.toFormatStringByMinute(60), "01:00");
        assertEquals(DateTimeUtils.toFormatStringByMinute(720), "12:00");
        assertEquals(DateTimeUtils.toFormatStringByMinute(1080), "18:00");

        assertEquals(DateTimeUtils.toFormatStringByMinute(1440), "00:00");
        assertEquals(DateTimeUtils.toFormatStringByMinute(1500), "01:00");

        assertEquals(DateTimeUtils.toFormatStringByMinute(2880), "00:00");
        assertEquals(DateTimeUtils.toFormatStringByMinute(3000), "02:00");
    }

    @Test
    public void toLocalDateTest() throws Exception {
        Object obj;

        /* null */
        obj = DateTimeUtils.toLocalDate(null);
        assertNull(obj);

        obj = DateTimeUtils.toLocalDate(new Date(0));
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);

        obj = DateTimeUtils.toLocalDate(new Timestamp(0));
        assertNotNull(obj);
        assertTrue(obj instanceof LocalDate);
    }

    @Test
    public void toLocalDateTimeTest() throws Exception {
        { /* LocalDate */
            LocalDate target;
            LocalDateTime actual;

            /* null */
            target = null;
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNull(actual);

            target = DateTimeUtils.parseToLocalDate("2000/01/02");
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNotNull(actual);
            assertEquals(actual.getYear(), 2000);
            assertEquals(actual.getMonthValue(), 1);
            assertEquals(actual.getDayOfMonth(), 2);
            assertEquals(actual.getHour(), 0);
            assertEquals(actual.getMinute(), 0);
            assertEquals(actual.getSecond(), 0);
        }

        { /* Date */
            Date target;
            LocalDateTime actual;

            /* null */
            target = null;
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNull(actual);

            target = DateTimeUtils.parseToDate("2000/01/02", DateTimeUtils.DATE_FORMAT);
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNotNull(actual);
            assertEquals(actual.getYear(), 2000);
            assertEquals(actual.getMonthValue(), 1);
            assertEquals(actual.getDayOfMonth(), 2);
            assertEquals(actual.getHour(), 0);
            assertEquals(actual.getMinute(), 0);
            assertEquals(actual.getSecond(), 0);

            target = DateTimeUtils.parseToDate("2000/01/02 12:34:56", DateTimeUtils.DATETIME_FORMAT_FULL);
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNotNull(actual);
            assertEquals(actual.getYear(), 2000);
            assertEquals(actual.getMonthValue(), 1);
            assertEquals(actual.getDayOfMonth(), 2);
            assertEquals(actual.getHour(), 12);
            assertEquals(actual.getMinute(), 34);
            assertEquals(actual.getSecond(), 56);
        }

        { /* Timestamp */
            Timestamp target;
            LocalDateTime actual;

            /* null */
            target = null;
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNull(actual);

            target = DateTimeUtils.parseToTimestamp("2000/01/02 12:34:56", DateTimeUtils.DATETIME_FORMAT_FULL);
            actual = DateTimeUtils.toLocalDateTime(target);
            assertNotNull(actual);
            assertEquals(actual.getYear(), 2000);
            assertEquals(actual.getMonthValue(), 1);
            assertEquals(actual.getDayOfMonth(), 2);
            assertEquals(actual.getHour(), 12);
            assertEquals(actual.getMinute(), 34);
            assertEquals(actual.getSecond(), 56);
        }
    }

    @Test
    public void toDateTest() throws Exception {
        { /* LocalDate */
            LocalDate target;
            Date actual;

            /* null */
            target = null;
            actual = DateTimeUtils.toDate(target);
            assertNull(actual);

            target = DateTimeUtils.parseToLocalDate("2000/01/02");
            actual = DateTimeUtils.toDate(target);
            assertNotNull(actual);
        }

        { /* LocalDateTime */
            LocalDateTime target;
            Date actual;

            /* null */
            target = null;
            actual = DateTimeUtils.toDate(target);
            assertNull(actual);

            target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
            actual = DateTimeUtils.toDate(target);
            assertNotNull(actual);
        }
    }

    @Test
    public void toTimestampTest() throws Exception {
        { /* LocalDateTime */
            LocalDateTime target;
            Timestamp actual;

            /* null */
            target = null;
            actual = DateTimeUtils.toTimestamp(target);
            assertNull(actual);

            target = DateTimeUtils.parseToLocalDateTime("2000/1/2 12:34");
            actual = DateTimeUtils.toTimestamp(target);
            assertNotNull(actual);
        }
    }

    @Test
    public void getLastDayOfMonthTest() throws Exception {
        /* null */
        assertNull(DateTimeUtils.getLastDayOfMonth(null));

        {
            LocalDate target = DateTimeUtils.parseToLocalDate("2017/12/01", DateTimeUtils.DATE_FORMAT);
            LocalDate expect = DateTimeUtils.parseToLocalDate("2017/12/31", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getLastDayOfMonth(target), expect);
        }
        {
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/01", DateTimeUtils.DATE_FORMAT);
            LocalDate expect = DateTimeUtils.parseToLocalDate("2018/01/31", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getLastDayOfMonth(target), expect);
        }
        {
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/02/01", DateTimeUtils.DATE_FORMAT);
            LocalDate expect = DateTimeUtils.parseToLocalDate("2018/02/28", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getLastDayOfMonth(target), expect);
        }
        {
            LocalDate target = DateTimeUtils.parseToLocalDate("2016/02/01", DateTimeUtils.DATE_FORMAT);
            LocalDate expect = DateTimeUtils.parseToLocalDate("2016/02/29", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getLastDayOfMonth(target), expect);
        }
        {
            LocalDate target = DateTimeUtils.parseToLocalDate("2000/02/01", DateTimeUtils.DATE_FORMAT);
            LocalDate expect = DateTimeUtils.parseToLocalDate("2000/02/29", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getLastDayOfMonth(target), expect);
        }
        {
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/04/01", DateTimeUtils.DATE_FORMAT);
            LocalDate expect = DateTimeUtils.parseToLocalDate("2018/04/30", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getLastDayOfMonth(target), expect);
        }
    }

    @Test
    public void getEraNameTest() throws Exception {
        /* null */
        assertNull(DateTimeUtils.getEraName(null));

        { /* 明治 */
            LocalDate target = DateTimeUtils.parseToLocalDate("1868/01/25", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getEraName(target), "明治");
        }
        { /* 明治 */
            LocalDate target = DateTimeUtils.parseToLocalDate("1912/07/29", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getEraName(target), "明治");
        }
        { /* 大正 */
            LocalDate target = DateTimeUtils.parseToLocalDate("1912/07/30", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getEraName(target), "大正");
        }
        { /* 大正 */
            LocalDate target = DateTimeUtils.parseToLocalDate("1926/12/24", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getEraName(target), "大正");
        }
        { /* 昭和 */
            LocalDate target = DateTimeUtils.parseToLocalDate("1926/12/25", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getEraName(target), "昭和");
        }
        { /* 昭和 */
            LocalDate target = DateTimeUtils.parseToLocalDate("1989/01/07", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getEraName(target), "昭和");
        }
    }

    @Test
    public void getDayOfWeekTest() throws Exception {
        /* null */
        assertNull(DateTimeUtils.getDayOfWeek(null));

        { /* 日曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/07", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "日");
        }
        { /* 月曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/08", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "月");
        }
        { /* 火曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/09", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "火");
        }
        { /* 水曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/10", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "水");
        }
        { /* 木曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/11", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "木");
        }
        { /* 金曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/12", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "金");
        }
        { /* 土曜日 */
            LocalDate target = DateTimeUtils.parseToLocalDate("2018/01/13", DateTimeUtils.DATE_FORMAT);
            assertEquals(DateTimeUtils.getDayOfWeek(target), "土");
        }
    }
}
