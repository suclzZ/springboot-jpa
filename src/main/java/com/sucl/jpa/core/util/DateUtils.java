package com.sucl.jpa.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sucl
 * @date 2019/4/23
 */
public class DateUtils {
    public static final String FORMATE_SLASH = "yyyy-MM-dd";
    public static final String FORMATE_HYPHEN = "yyyy/MM/dd";
    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    public static boolean isDate(String date){
        Pattern pattern = Pattern.compile("\\d{4}(-|/)\\d{2}(-|/)\\d{2}");
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static Date getDate(String value) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATE_SLASH);
        Date date = null;
        try {
            date = sdf.parse(value);
        } catch (ParseException e) {
            SimpleDateFormat sdf2 = new SimpleDateFormat(FORMATE_HYPHEN);
            try {
                date = sdf2.parse(value);
            } catch (ParseException e1) {
                throw new RuntimeException(String.format("不支持的日期转换【%s】",value));
            }
        }
        return date;
    }

    public static String getToday() {
        String time = "";
        time = getToday("yyyy-MM-dd");
        return time;
    }

    public static String getToday(String format) {
        return getDateStr(Calendar.getInstance().getTime(), format);
    }

    public static String getDateStr(Date date, String format) {
        if(date==null) return null;
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static Date parseMills(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public static String getYearCode() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String yearStr = new Integer(year).toString();
        return yearStr.substring(2, 4);
    }

    public static Date getDate(String date,String format){
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取指定日期开始时间
     * @param date
     * @return
     */
    public static String getStartDateTime(String date){
        LocalDate localDate = date!=null?LocalDate.parse(date,DateTimeFormatter.ISO_DATE):LocalDate.now();
        return LocalDateTime.of(localDate, LocalTime.MIN).format(DATE_TIME_FORMATTER);
    }

    public static Date getStartDate(String date){
        return getDate(getStartDateTime(date));
    }

    /**
     * 获取指定日期终止时间
     * @param date
     * @return
     */
    public static String getEndDateTime(String date){
        LocalDate localDate = date!=null?LocalDate.parse(date,DateTimeFormatter.ISO_DATE):LocalDate.now();
        return LocalDateTime.of(localDate, LocalTime.MAX).format(DATE_TIME_FORMATTER);
    }

    public static Date getEndDate(String date){
        return getDate(getEndDateTime(date));
    }

    public static String getPreDateTime(String dateTime){
        LocalDateTime localDateTime = dateTime!=null? LocalDateTime.parse(dateTime):LocalDateTime.now();
        localDateTime = localDateTime.minusDays(1);
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String getNextDateTime(String dateTime){
        LocalDateTime localDateTime = dateTime!=null? LocalDateTime.parse(dateTime):LocalDateTime.now();
        localDateTime = localDateTime.plusDays(1);
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
