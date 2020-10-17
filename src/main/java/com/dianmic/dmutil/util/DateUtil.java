package com.dianmic.dmutil.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 
 * 
 * @date 2019年3月23日
 * 
 * @author swf
 *
 * @Description 日期工具类
 *
 */
public class DateUtil {
    /**
     * 两个日期的相减
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getDateSub(java.util.Date startTime, java.util.Date endTime) {
        long sub = startTime.getTime() - endTime.getTime();
        return sub;
    }

    /**
     * 日期加多少天后的日期
     * 
     * @param beforeAddTime
     * @param days
     * @return
     */
    public static Date getDateSumDays(Date beforeAddTime, int days) {

        Calendar calendar = new GregorianCalendar();

        calendar.setTime(beforeAddTime);

        calendar.add(Calendar.DATE, days);// 把日期往后增加n天.整数往后推,负数往前移动

        return beforeAddTime = calendar.getTime();
    }

    public static Date addDateMonth(Date beforeAddTime, int month) {

        Calendar calendar = new GregorianCalendar();

        calendar.setTime(beforeAddTime);

        calendar.add(Calendar.MONTH, month);// 把日期往后增加n月.整数往后推,负数往前移动

        return beforeAddTime = calendar.getTime();
    }

    public static String getDateForNextMonth(String src) {
        if (StringUtil.isEmpty(src)) {
            return "";
        }
        Date d1 = strToDate(src, "yyyy-MM-dd");
        if (d1 == null) {
            return "";
        }
        Date d2 = addDateMonth(d1, 1);
        return dateToString(d2, "yyyy-MM-dd");
    }

    public static Calendar getTodayForStart() {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal;
    }

    /**
     * 随机取当天8点-24点的时间
     * 
     * @return
     */
    public static Date getRandomDate() {
        Date curr = new Date();
        Date rs = null;
        // 设置范围
        Date start = DateUtils.setHours(curr, 8);
        if (start.before(curr)) {
            start = curr;
        }
        Date end = null;
        end = DateUtils.setHours(curr, 23);
        end = DateUtils.setMinutes(end, 59);
        if (start.getTime() < end.getTime()) {
            long date = random(start.getTime(), end.getTime());
            rs = new Date(date);
        }
        return rs;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    public static int getAge(Date birthday) {
        int age = 22;
        if (null != birthday) {
            Date now = new Date();
            long i = (now.getTime() - birthday.getTime()) / (1000 * 60 * 60 * 24);
            age = (int) (i / 365);
            if (15 > age || 100 < age) {
                age = 22;
            }
        }
        return age;
    }

    public static Date strToDate(String d) {
        Date date;
        java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = df2.parse(d);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    public static Date strToDate(String d, String fm) {
        SimpleDateFormat df = new SimpleDateFormat(fm);
        Date date = null;
        try {
            date = df.parse(d);
        } catch (Exception ex) {
        }
        return date;
    }

    public static String dateToString(Date date) {
        if (date == null) {
            return "";
        }
        String d = "";
        java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            d = df.format(date);
        } catch (Exception e) {
        }
        return d;
    }

    public static String dateTimeToString(Date dt, String df) {
        String pstrDate = null; // return value
        if (dt == null) {
            return "";
        }
        String formatStyle = null;
        if ((df == null) || (df.equals(""))) {
            formatStyle = "yyyy-MM-dd HH:mm:ss";
        } else {
            formatStyle = df;
        }
        SimpleDateFormat oFormatter = null;
        try {
            oFormatter = new SimpleDateFormat(formatStyle);
            pstrDate = oFormatter.format(formatStyle);
        } catch (Exception e) {
            oFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            pstrDate = oFormatter.format(dt);
        }
        return pstrDate;
    }

    public static String dateToString(Date date, String formate) {
        if (date == null) {
            return "";
        }
        if (StringUtils.isEmpty(formate)) {
            formate = "yyyy-MM-dd HH:mm:ss";
        }
        String d = "";
        java.text.DateFormat df = new java.text.SimpleDateFormat(formate);
        try {
            d = df.format(date);
        } catch (Exception e) {
        }
        return d;
    }

    public static String dateToString(Date date, String formate, String defaultDate) {
        if (date == null) {
            return defaultDate;
        }
        if (!StringUtil.isNotEmpty(formate)) {
            formate = "yyyy-MM-dd HH:mm:ss";
        }
        String d = defaultDate;
        java.text.DateFormat df = new java.text.SimpleDateFormat(formate);
        try {
            d = df.format(date);
        } catch (Exception e) {
            d = defaultDate;
        }
        return d;
    }

    /***
     * 生成现在的时间格式字体串
     * 
     * @param pstrDateFormat
     * @return
     */
    public static String getCurDateToString(String pstrDateFormat) {
        String pstrDate = null; // return value
        Date curDate = new Date();
        String formatStyle = null;
        if ((pstrDateFormat == null) || (pstrDateFormat.equals(""))) {
            formatStyle = "yyyy-MM-dd";
        } else {
            formatStyle = pstrDateFormat;
        }
        SimpleDateFormat oFormatter = new SimpleDateFormat(formatStyle);
        pstrDate = oFormatter.format(curDate);
        return pstrDate;
    }

    /**
     * 字符串转日期
     * 
     * @param d
     * @param type
     *            1：日期，2：日期+时间
     * @return
     */
    public static Date str2date(String d, int type) {
        if (StringUtil.isEmpty(d)) {
            return null;
        }
        Date date = null;
        String format = "yyyy-MM-dd";
        if (d.indexOf("-") > 0) {
            // yyyy-MM-dd HH:mm:ss
            if (d.substring(0, d.indexOf("-")).length() == 2) {
                // 补全4位年份
                d = 20 + d;
            }
        } else if (d.indexOf("/") > 0) {
            format = "yyyy/MM/dd";
            // yyyy/MM/dd HH:mm:ss
            if (d.substring(0, d.indexOf("/")).length() == 2) {
                // 补全4位年份
                d = 20 + d;
            }
        } else if (d.indexOf("\\.") > 0) {
            format = "yyyy.MM.dd";
            // yyyy/MM/dd HH:mm:ss
            if (d.substring(0, d.indexOf("\\.")).length() == 2) {
                // 补全4位年份
                d = 20 + d;
            }
        } else {
            format = "yyyyMMdd";
        }
        if (type == 2) {
            format += " HH:mm:ss";
        }
        SimpleDateFormat df2 = new SimpleDateFormat(format);
        try {
            date = df2.parse(d);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }
}
