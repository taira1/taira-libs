package jp.taira.libs.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日付・時間ユーティリティクラス
 */
@Slf4j
public class DateTimeUtils {

    /** 協定世界時 */
    public static final TimeZone TZ_UTC = TimeZone.getTimeZone("UTC");

    /** 日本標準時 */
    public static final TimeZone TZ_JST = TimeZone.getTimeZone("JST");

    /** 協定世界時のタイムゾーンID */
    public static final ZoneId ZONEID_UTC = ZoneId.of(TZ_UTC.getID(), ZoneId.SHORT_IDS);

    /** 日本標準時のタイムゾーンID */
    public static final ZoneId ZONEID_JST = ZoneId.of(TZ_JST.getID(), ZoneId.SHORT_IDS);

    /** 日付の区切り文字 */
    public static final String DATE_SPLIT = "/";

    /** 最大年月日 */
    public static final String DATE_MAX = "2999" + DATE_SPLIT + "12" + DATE_SPLIT + "31";

    /** 最小年月日 */
    public static final String DATE_MIN = "1970" + DATE_SPLIT + "01" + DATE_SPLIT + "01";

    /** 年月日フォーマット */
    public static final String DATE_FORMAT = "uuuu" + DATE_SPLIT + "[]M" + DATE_SPLIT + "[]d";

    /** 年月日フォーマット */
    public static final String DATE_FORMAT_FIXED = "uuuu" + DATE_SPLIT + "MM" + DATE_SPLIT + "dd";

    /** 時分フォーマット */
    public static final String TIME_FORMAT = "[]H:[]m";

    /** 時分フォーマット */
    public static final String TIME_FORMAT_FIXED = "HH:mm";

    /** 時分秒フォーマット */
    public static final String TIME_FORMAT_FULL = TIME_FORMAT + ":[]s";

    /** 時分秒フォーマット */
    public static final String TIME_FORMAT_FULL_FIXED = TIME_FORMAT_FIXED + ":ss";

    /** 時分秒(マイクロ)フォーマット */
    public static final String TIME_FORMAT_FULLTIME = TIME_FORMAT_FULL + ".[]S";

    /** 時分秒(マイクロ)フォーマット */
    public static final String TIME_FORMAT_FULLTIME_FIXED = TIME_FORMAT_FULL_FIXED + ".SSSSSS";

    /** 年月日時分フォーマット */
    public static final String DATETIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

    /** 年月日時分フォーマット */
    public static final String DATETIME_FORMAT_FIXED = DATE_FORMAT_FIXED + " " + TIME_FORMAT_FIXED;

    /** 年月日時分秒フォーマット */
    public static final String DATETIME_FORMAT_FULL = DATE_FORMAT + " " + TIME_FORMAT_FULL;

    /** 年月日時分秒フォーマット */
    public static final String DATETIME_FORMAT_FULL_FIXED = DATE_FORMAT_FIXED + " " + TIME_FORMAT_FULL_FIXED;

    /** 年月日時分秒(マイクロ)フォーマット */
    public static final String DATETIME_FORMAT_FULLTIME = DATE_FORMAT + " " + TIME_FORMAT_FULLTIME;

    /** 年月日時分秒(マイクロ)フォーマット */
    public static final String DATETIME_FORMAT_FULLTIME_FIXED = DATE_FORMAT_FIXED + " " + TIME_FORMAT_FULLTIME_FIXED;

    private DateTimeUtils() {
        throw new IllegalAccessError("Constants class.");
    }

    /**
     * 年月日の区切り文字を'-'に変換する。
     *
     * @param target 対象文字列
     * @return 変換された文字列
     */
    public static String convertDateSplit(final String target) {
        if (StringUtils.isBlank(target)) {
            return target;
        }

        return target.replace(DATE_SPLIT, "-");
    }

    /**
     * 日付フォーマットかを判断する。
     *
     * @param target 対象文字列
     * @param format 判定するフォーマット
     * @return 日付のフォーマットである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isDateFormat(final String target, final DateTimeFormatter format) {
        if (StringUtils.isBlank(target) || format == null) {
            return false;
        }

        final LocalDate localDate = parseToLocalDate(target, format);
        final LocalDateTime localDateTime = parseToLocalDateTime(target, format);

        return localDate != null || localDateTime != null;
    }

    /**
     * 日付フォーマットかを判断する。
     *
     * @param target 対象文字列
     * @param pattern パターン文字列
     * @return 日付のフォーマットである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isDateFormat(final String target, final String pattern) {
        if (StringUtils.isBlank(target) || StringUtils.isEmpty(pattern)) {
            return false;
        }

        return isDateFormat(target, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 年月日フォーマットかを判断する。
     *
     * @param target 対象文字列
     * @return 年月日のフォーマットである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isDateFormat(final String target) {
        return isDateFormat(convertDateSplit(target), convertDateSplit(DATE_FORMAT));
    }

    /**
     * 年月日時分フォーマットかを判断する。
     *
     * @param target 対象文字列
     * @return 年月日時分のフォーマットである場合はtrue、そうでない場合はfalse。
     */
    public static boolean isDateTimeFormat(final String target) {
        return isDateFormat(convertDateSplit(target), convertDateSplit(DATETIME_FORMAT));
    }

    /**
     * 今日の日付かを判断する。
     *
     * @param target 対象文字列
     * @return 今日の日付である場合はtrue、そうでない場合はfalse。
     */
    public static boolean isToday(final String target) {
        if (StringUtils.isBlank(target)) {
            return false;
        }

        LocalDate now = LocalDate.now(ZONEID_JST);
        String str = convertDateSplit(target).replaceAll(" .*", "");
        if (!isDateFormat(str)) {
            return false;
        }

        LocalDate localDate = parseToLocalDate(str, convertDateSplit(DATE_FORMAT));
        if (localDate == null) {
            return false;
        }

        return now.isEqual(localDate);
    }

    /**
     * LocalDateに変換する。
     *
     * @param target 対象文字列
     * @param format 変換するフォーマット
     * @return 変換されたLocalDateオブジェクト
     */
    public static LocalDate parseToLocalDate(final String target, final DateTimeFormatter format) {
        if (StringUtils.isBlank(target) || format == null) {
            return null;
        }

        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(target, format.withResolverStyle(ResolverStyle.STRICT));
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }

        return localDate;
    }

    /**
     * LocalDateに変換する。
     *
     * @param target 対象文字列
     * @param pattern パターン文字列
     * @return 変換されたLocalDateオブジェクト
     */
    public static LocalDate parseToLocalDate(final String target, final String pattern) {
        return parseToLocalDate(target, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * LocalDateに変換する。
     *
     * @param target 対象文字列
     * @return 変換されたLocalDateオブジェクト
     */
    public static LocalDate parseToLocalDate(final String target) {
        return parseToLocalDate(convertDateSplit(target), convertDateSplit(DATE_FORMAT));
    }

    /**
     * LocalDateTimeに変換する。
     *
     * @param target 対象文字列
     * @param format 変換するフォーマット
     * @return 変換されたLocalDateTimeオブジェクト
     */
    public static LocalDateTime parseToLocalDateTime(final String target, final DateTimeFormatter format) {
        if (StringUtils.isBlank(target) || format == null) {
            return null;
        }
        log.debug("target = {}", target);
        log.debug("format = {}", format);

        LocalDateTime localDateTime = null;
        try {
            localDateTime = LocalDateTime.parse(convertDateSplit(target), format.withResolverStyle(ResolverStyle.STRICT));
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }

        return localDateTime;
    }

    /**
     * LocalDateTimeに変換する。
     *
     * @param target 対象文字列
     * @param pattern パターン文字列
     * @return 変換されたLocalDateTimeオブジェクト
     */
    public static LocalDateTime parseToLocalDateTime(final String target, final String pattern) {
        return parseToLocalDateTime(target, DateTimeFormatter.ofPattern(convertDateSplit(pattern)));
    }

    /**
     * LocalDateTimeに変換する。
     *
     * @param target 対象文字列
     * @return 変換されたLocalDateTimeオブジェクト
     */
    public static LocalDateTime parseToLocalDateTime(final String target) {
        if (StringUtils.isBlank(target)) {
            return null;
        }

        LocalDateTime localDateTime = null;
        try {
            localDateTime = ZonedDateTime.parse(target, DateTimeFormatter.ofPattern("EEE MMM []d []H:[]m:[]s zzz uuuu", Locale.ENGLISH)).toLocalDateTime();
        } catch (Exception e) {
            if (target.matches("^\\d{4}[-/]\\d{1,2}[-/]\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
                return parseToLocalDateTime(target, DATETIME_FORMAT_FULL);
            }

            return parseToLocalDateTime(target, DATETIME_FORMAT);
        }

        return localDateTime;
    }

    /**
     * LocalTimeに変換する。
     *
     * @param target 対象文字列
     * @param format 変換するフォーマット
     * @return 変換されたLocalTimeオブジェクト
     */
    public static LocalTime parseToLocalTime(final String target, final DateTimeFormatter format) {
        if (StringUtils.isBlank(target) || format == null) {
            return null;
        }

        LocalTime localTime = null;
        try {
            localTime = LocalTime.parse(target, format.withResolverStyle(ResolverStyle.STRICT));
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }

        return localTime;
    }

    /**
     * LocalTimeに変換する。
     *
     * @param target 対象文字列
     * @param pattern パターン文字列
     * @return 変換されたLocalTimeオブジェクト
     */
    public static LocalTime parseToLocalTime(final String target, final String pattern) {
        return parseToLocalTime(target, DateTimeFormatter.ofPattern(convertDateSplit(pattern)));
    }

    /**
     * LocalTimeに変換する。
     *
     * @param target 対象文字列
     * @return 変換されたLocalTimeオブジェクト
     */
    public static LocalTime parseToLocalTime(final String target) {
        return parseToLocalTime(target, TIME_FORMAT);
    }

    /**
     * Dateに変換する。
     *
     * @param target 対象文字列
     * @param pattern パターン文字列
     * @return 変換されたDateオブジェクト
     */
    public static Date parseToDate(final String target, final String pattern) {
        if (StringUtils.isBlank(target) || StringUtils.isEmpty(pattern)) {
            return null;
        }

        if (target.matches("^\\d{4}\\d{2}\\d{2}$") || target.matches("^\\d+.\\d+.\\d+$")) {
            LocalDate localDate = parseToLocalDate(target, pattern);

            return toDate(localDate);
        } else {
            LocalDateTime localDateTime = parseToLocalDateTime(target, pattern);

            return toDate(localDateTime);
        }
    }

    /**
     * Timestampに変換する。
     *
     * @param target 対象文字列
     * @param pattern パターン文字列
     * @return 変換されたTimestampオブジェクト
     */
    public static Timestamp parseToTimestamp(final String target, final String pattern) {
        if (StringUtils.isBlank(target) || StringUtils.isEmpty(pattern)) {
            return null;
        }

        LocalDateTime localDateTime = parseToLocalDateTime(target, pattern);

        return toTimestamp(localDateTime);
    }

    /**
     * LocalDateを、文字列に変換する。
     *
     * @param target 変換対象
     * @param format 変換するフォーマット
     * @return 変換後の文字列
     */
    public static String toString(final LocalDate target, final DateTimeFormatter format) {
        if (target == null || format == null) {
            return null;
        }

        try {
            return target.format(format.withResolverStyle(ResolverStyle.STRICT));
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    /**
     * LocalDateを、文字列に変換する。<br>
     * 可変長にする場合は、DATE_FORMATを指定する。
     *
     * @param target 変換対象
     * @param pattern パターン文字列
     * @return 変換後の文字列(固定長)
     */
    public static String toString(final LocalDate target, final String pattern) {
        if (target == null || StringUtils.isEmpty(pattern)) {
            return null;
        }

        return toString(target, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * LocalDateを、文字列に変換する。
     *
     * @param target 変換対象
     * @return 変換後の文字列(固定長)
     */
    public static String toString(final LocalDate target) {
        if (target == null) {
            return null;
        }

        return toString(target, DATE_FORMAT_FIXED);
    }

    /**
     * LocalDateTimeを、文字列に変換する。
     *
     * @param target 変換対象
     * @param format 変換するフォーマット
     * @return 変換後の文字列
     */
    public static String toString(final LocalDateTime target, final DateTimeFormatter format) {
        if (target == null || format == null) {
            return null;
        }

        try {
            return target.format(format.withResolverStyle(ResolverStyle.STRICT));
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    /**
     * LocalDateTimeを、文字列に変換する。<br>
     * 可変長にする場合は、DATETIME_FORMATを指定する。
     *
     * @param target 変換対象
     * @param pattern パターン文字列
     * @return 変換後の文字列(固定長)
     */
    public static String toString(final LocalDateTime target, final String pattern) {
        if (target == null || StringUtils.isEmpty(pattern)) {
            return null;
        }

        return toString(target, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * LocalDateTimeを、文字列に変換する。<br>
     * '年月日時分'フォーマットで変換を行う。
     *
     * @param target 変換対象
     * @return 変換後の文字列(固定長)
     */
    public static String toString(final LocalDateTime target) {
        if (target == null) {
            return null;
        }

        return toString(target, DATETIME_FORMAT_FIXED);
    }

    /**
     * LocalDateTimeを、文字列に変換する。<br>
     * '年月日時分秒'フォーマットで変換を行う。
     *
     * @param target 変換対象
     * @return 変換後の文字列(固定長)
     */
    public static String toFullString(final LocalDateTime target) {
        return toString(target, DATETIME_FORMAT_FULL_FIXED);
    }

    /**
     * 分を、時分の文字列に変換する。
     *
     * @param target 変換対象
     * @return 変換された時分文字列
     */
    public static String toFormatStringByMinute(final long target) {
        if (target < 0) {
            return null;
        }
        return LocalTime.MIN.plus(
                Duration.ofMinutes(target)
        ).toString();
    }

    /**
     * LocalDateに変換する。
     *
     * @param target 変換対象
     * @return 変換されたLocalDateオブジェクト
     */
    public static LocalDate toLocalDate(final Date target) {
        if (target == null) {
            return null;
        }

        final LocalDateTime localDateTime = toLocalDateTime(target);

        return localDateTime.toLocalDate();
    }

    /**
     * LocalDateに変換する。
     *
     * @param target 変換対象
     * @return 変換されたLocalDateオブジェクト
     */
    public static LocalDate toLocalDate(final Timestamp target) {
        if (target == null) {
            return null;
        }

        final LocalDateTime localDateTime = toLocalDateTime(target);

        return localDateTime.toLocalDate();
    }

    /**
     * LocalDateTimeに変換する。
     *
     * @param target 変換対象
     * @return 変換されたLocalDateTimeオブジェクト
     */
    public static LocalDateTime toLocalDateTime(final LocalDate target) {
        if (target == null) {
            return null;
        }

        return target.atTime(LocalTime.of(00, 00, 00));
    }

    /**
     * LocalDateTimeに変換する。
     *
     * @param target 変換対象
     * @return 変換されたLocalDateTimeオブジェクト
     */
    public static LocalDateTime toLocalDateTime(final Date target) {
        if (target == null) {
            return null;
        }

        Instant instant = target.toInstant();

        return LocalDateTime.ofInstant(instant, ZONEID_JST);
    }

    /**
     * LocalDateTimeに変換する。
     *
     * @param target 変換対象
     * @return 変換されたLocalDateTimeオブジェクト
     */
    public static LocalDateTime toLocalDateTime(final Timestamp target) {
        if (target == null) {
            return null;
        }

        Instant instant = target.toInstant();

        return LocalDateTime.ofInstant(instant, ZONEID_JST);
    }

    /**
     * Dateに変換する。
     *
     * @param target 変換対象
     * @return 変換されたDateオブジェクト
     */
    public static Date toDate(final LocalDateTime target) {
        if (target == null) {
            return null;
        }

        ZonedDateTime zdt = target.atZone(ZONEID_JST);

        return Date.from(zdt.toInstant());
    }

    /**
     * Dateに変換する。
     *
     * @param target 変換対象
     * @return 変換されたDateオブジェクト
     */
    public static Date toDate(final LocalDate target) {
        if (target == null) {
            return null;
        }

        ZonedDateTime zdt = target.atStartOfDay(ZONEID_JST);

        return Date.from(zdt.toInstant());
    }

    /**
     * Timestampに変換する。
     *
     * @param target 変換対象
     * @return 変換されたTimestampオブジェクト
     */
    public static Timestamp toTimestamp(final LocalDateTime target) {
        if (target == null) {
            return null;
        }

        return Timestamp.valueOf(target);
    }

    /**
     * 月末日を取得する。
     *
     * @param target 取得対象
     * @return 月末日を格納したLocalDateオブジェクト
     */
    public static LocalDate getLastDayOfMonth(final LocalDate target) {
        if (target == null) {
            return null;
        }

        return target.withDayOfMonth(1).with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 元号を取得する。
     *
     * @param target 取得対象
     * @return 元号
     */
    public static String getEraName(final LocalDate target) {
        if (target == null) {
            return null;
        }

        if (target.isAfter(LocalDate.of(2019,  5,  1).minusDays(1))) {
            return "令和";
        } else if (target.isAfter(LocalDate.of(1989,  1,  8).minusDays(1)) && target.isBefore(LocalDate.of(2019,  5,  1))) {
            return "平成";
        } else if (target.isAfter(LocalDate.of(1926, 12, 25).minusDays(1)) && target.isBefore(LocalDate.of(1989,  1,  8))) {
            return "昭和";
        } else if (target.isAfter(LocalDate.of(1912,  7, 30).minusDays(1)) && target.isBefore(LocalDate.of(1926, 12, 25))) {
            return "大正";
        } else if (target.isAfter(LocalDate.of(1868,  1, 25).minusDays(1)) && target.isBefore(LocalDate.of(1912,  7, 30))) {
            return "明治";
        }

        return null;
    }

    /**
     * 曜日を取得する。
     *
     * @param target 取得対象
     * @return 曜日
     */
    public static String getDayOfWeek(final LocalDate target) {
        if (target == null) {
            return null;
        }

        final DayOfWeek dayOfWeek = target.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return "月";
        } else if (dayOfWeek == DayOfWeek.TUESDAY) {
            return "火";
        } else if (dayOfWeek == DayOfWeek.WEDNESDAY) {
            return "水";
        } else if (dayOfWeek == DayOfWeek.THURSDAY) {
            return "木";
        } else if (dayOfWeek == DayOfWeek.FRIDAY) {
            return "金";
        } else if (dayOfWeek == DayOfWeek.SATURDAY) {
            return "土";
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            return "日";
        }

        return null;
    }
}
