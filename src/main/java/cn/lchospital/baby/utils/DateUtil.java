package cn.lchospital.baby.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;

/**
 * java8 日期工具类
 *
 * @author n3verl4nd
 * @date 2019/12/19
 */
public class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
    private static final String PATTERN_DATE = "yyyy-MM-dd";
    private static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    private static final DateTimeFormatter uuuuMMddHHmmss  = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss", Locale.CHINA);
    private static final DateTimeFormatter uuuuMMdd  = DateTimeFormatter.ofPattern("uuuu-MM-dd", Locale.CHINA);

    /**
     * @param pattern
     * @param strDate
     * @return
     */
    public static Date convertStringToDate(String pattern, String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        if (StringUtils.isEmpty(pattern)) {
            pattern = PATTERN_DATE_TIME;
        }
        DateTimeFormatter dateTimeFormatter = null;
        try {
            dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        } catch (Exception e) {
            LOGGER.error("[DateUtil.convertStringToDate] format error, pattern: {}, strDate: {}, error: ", pattern, strDate, e);
        }
        if (dateTimeFormatter == null) {
            return null;
        }

        LocalDateTime localDateTime = null;
        try {
            localDateTime = LocalDateTime.parse(strDate, dateTimeFormatter);
        } catch (Exception e) {
            LOGGER.error("[DateUtil.convertStringToDate] format error, pattern: {}, strDate: {}, error: ", pattern, strDate, e);
        }
        if (localDateTime == null) {
            return null;
        }
        return localDateTimeToDate(localDateTime);
    }

    /**
     * @param pattern
     * @param date
     * @return
     */
    public static String convertDateToString(String pattern, Date date) {
        if (date == null) {
            date = new Date();
        }
        if (StringUtils.isEmpty(pattern)) {
            pattern = PATTERN_DATE_TIME;
        }
        DateTimeFormatter dateTimeFormatter = null;
        try {
            dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        } catch (Exception e) {
            LOGGER.error("[DateUtil.convertStringToDate] format error, pattern: {}, date: {}, error: ", pattern, date, e);
        }
        if (dateTimeFormatter == null) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);

        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 格式: yyyy-MM-dd
     *
     * @return
     */
    public static String currentDateStr() {
        return LocalDate.now().toString();
    }

    public static String currentDateTimeStr() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public static String currentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern(PATTERN_DATE_TIME));
    }

    /**
     * 获取几天后的日期
     *
     * @param days
     * @return
     */
    public static Date dateAfter(Date date, int days) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime afterLocalDateTime = localDateTime.plusDays(days);
        return localDateTimeToDate(afterLocalDateTime);
    }

    /**
     * 获取系统当前SQL类型的Date
     *
     * @return 当前时间
     */
    public static java.sql.Date getSqlNowDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    /**
     * 获得当天 00:00:00
     *
     * @return
     */
    public static Date firstOfToday() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return localDateTimeToDate(localDateTime);
    }

    /**
     * 获得当天 23:59:59
     *
     * @return
     */
    public static Date lastOfToday() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return localDateTimeToDate(localDateTime);
    }

    /**
     * 获得每周第一天
     *
     * @return
     */
    public static Date firstOfWeek() {
        LocalDateTime localDateTime = LocalDateTime.now().with(DayOfWeek.MONDAY);
        return localDateTimeToDate(localDateTime);
    }

    /**
     * 获得每周最后一天
     *
     * @return
     */
    public static Date lastOfWeek() {
        LocalDateTime localDateTime = LocalDateTime.now().with(DayOfWeek.SUNDAY);
        return localDateTimeToDate(localDateTime);
    }

    /**
     * 获得距月底的秒数
     *
     * @return
     */
    private static int getSecondsToLastDayOfMonth() {
        LocalDateTime from = LocalDateTime.now().withNano(0);
        LocalDateTime to = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);
        Duration duration = Duration.between(from, to);
        long seconds = duration.getSeconds();
        return (int) seconds;
    }

    /**
     * 时间戳(毫秒)转 LocalDate
     *
     * @param timestamps
     * @return
     */
    public static LocalDate timeStampsToLocalDate(long timestamps) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamps), DEFAULT_ZONE_ID).toLocalDate();
    }

    public static Date timeStampsToDate(long timestamps) {
        Instant instant = Instant.ofEpochMilli(timestamps);
        return Date.from(instant);
    }

    public static Date timeStampsToDate(String timestamps) {
        Instant instant = Instant.ofEpochMilli(Long.parseLong(timestamps));
        return Date.from(instant);
    }

    /**
     * 时间戳(毫秒)转 LocalDate 字符串
     *
     * @param timestamps
     * @return
     */
    public static String timeStampsToLocalDateStr(long timestamps) {
        LocalDate localDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamps), DEFAULT_ZONE_ID).toLocalDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE);
        return localDate.format(dateFormatter);
    }

    /**
     * 时间戳(毫秒)转 LocalDateTime
     *
     * @param timestamps
     * @return
     */
    public static LocalDateTime timeStampsToLocalDateTime(long timestamps) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamps), DEFAULT_ZONE_ID);
    }

    /**
     * 时间戳(毫秒)转 LocalDateTime 字符串
     *
     * @param timestamps
     * @return
     */
    public static String timeStampsToLocalDateTimeStr(long timestamps) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE_TIME);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamps), DEFAULT_ZONE_ID);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(DEFAULT_ZONE_ID).toInstant();
        return Date.from(instant);
    }

    /**
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, DEFAULT_ZONE_ID);
    }

    /**
     * 获得两个日期间隔的天数(start < end)
     *
     * @param start
     * @param end
     * @return
     */
    public static int daysBetween(Date start, Date end) {
        LocalDateTime startLocalDateTime = dateToLocalDateTime(start);
        LocalDateTime endLocalDateTime = dateToLocalDateTime(end);
        Period period = Period.between(endLocalDateTime.toLocalDate(), startLocalDateTime.toLocalDate());
        return period.getDays();
    }

    public static String getGmtDateStr() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        return now.format(DateTimeFormatter.RFC_1123_DATE_TIME);
    }

    public static String getTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        return String.valueOf(now.toEpochSecond(ZoneOffset.of("+8")));
    }

    /**
     * 获得 N 分钟前的日期
     * @param minutes
     * @return
     */
    public static Date getDateBefore(int minutes) {
        LocalDateTime now = LocalDateTime.now();
        return localDateTimeToDate(now.minusMinutes(minutes));
    }

    public static String getDateStrBefore(int minutes) {
        LocalDateTime now = LocalDateTime.now();
        return now.minusMinutes(minutes).format(DateTimeFormatter.ofPattern(PATTERN_DATE_TIME));
    }

    /**
     * 获得 N 分钟后的日期
     * @param minutes
     * @return
     */
    public static Date getDateAfter(int minutes) {
        LocalDateTime now = LocalDateTime.now();
        return localDateTimeToDate(now.plusMinutes(minutes));
    }

    /**
     * @MethodName isValidUuuuMMddHHmmss
     * @Description  校验格式为 yyyy-MM-dd HH:mm:ss 字符串的时间格式是否合法
     * @param: dateStr
     * @return: boolean
     * @Author hl
     * @Date 16:08 16:08
     */
    public static boolean isValidYyyyMMddHHmmss(String dateStr) {
        try {
            //  ResolverStyle.STRICT (解析模式) 一共有三种
            //  STRICT：严格模式，日期、时间必须完全正确
            //  SMART：智能模式，针对日可以自动调整。月的范围在 1 到 12，日的范围在 1 到 31。比如输入是2月30号，当年2月只有28天，返回的日期就是2月28日
            //  LENIENT：宽松模式，主要针对月和日，会自动后延

            DateTimeFormatter dateTimeFormatter = uuuuMMddHHmmss.withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(dateStr, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * @MethodName isValidUuuuMMdd
     * @Description  校验格式为 yyyy-MM-dd 字符串的时间格式是否合法
     * @param: dateStr
     * @return: boolean
     * @Author hl
     * @Date 16:08 16:08
     */
    public static boolean isValidYyyyMMdd(String dateStr) {
        try {
            DateTimeFormatter dateTimeFormatter = uuuuMMdd.withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(dateStr, uuuuMMdd);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static LocalDateTime  convertLocalDateTime(String dateStr) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(PATTERN_DATE_TIME);
        return LocalDateTime.parse(dateStr, df);
    }

    public static void main(String[] args) {
        System.out.println(getGmtDateStr());
    }
}