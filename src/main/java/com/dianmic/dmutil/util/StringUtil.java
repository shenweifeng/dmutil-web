package com.dianmic.dmutil.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class StringUtil {

    public static String getCurrentDay() {
        return DateUtil.dateToString(new Date(), Constant.formate_date_yyyyMMdd);
    }

    public static String getCurrentDayTime() {
        return DateUtil.dateToString(new Date(), Constant.formate_date_time_default);
    }

    public static String getCurrentTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 清除所有XSS攻击的字符串
     */
    public static String getSafeStringXSS(String s) {
        if (s == null || "".equals(s)) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '\'':
                sb.append("&prime;");// &acute;");
                break;
            case '′':
                sb.append("&prime;");// &acute;");
                break;
            case '\"':
                sb.append("&quot;");
                break;
            case '＂':
                sb.append("&quot;");
                break;
            case '&':
                sb.append("＆");
                break;
            case '#':
                sb.append("＃");
                break;
            case '\\':
                sb.append('￥');
                break;
            case '=':
                sb.append("&#61;");
                break;
            default:
                sb.append(c);
                break;
            }
        }
        return sb.toString();
    }

    public static Integer object2Integer(Object obj) {
        if (null == obj) {
            return null;
        }
        Integer ret = null;
        try {
            ret = Integer.parseInt(String.valueOf(obj));
        } catch (NumberFormatException e) {
            ret = null;
        }
        return ret;
    }

    /**
     * 数组是否全部为数字类型，只要一个为非就返回false
     * 
     * @param src
     * @return
     */
    public static boolean isNumber(String... src) {
        if (null == src || src.length == 0) {
            return false;
        }
        for (String s : src) {
            if (StringUtils.isEmpty(s) || !NumberUtils.isNumber(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数组是否全部为日期类型，只要一个为非就返回false
     * 
     * @param src
     * @return
     */
    public static boolean isDate(String... src) {
        if (null == src || src.length == 0) {
            return false;
        }
        for (String s : src) {
            if (StringUtils.isEmpty(s) || null == DateUtil.strToDate(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数组是否全部不为空，只要一个为非就返回false
     * 
     * @param src
     * @return
     */
    public static boolean isNotEmpty(String... src) {
        if (null == src || src.length == 0) {
            return false;
        }
        for (String s : src) {
            if (StringUtils.isEmpty(s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(String src) {
        return StringUtils.isEmpty(src);
    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(1[3,5,8])\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static String formateNull(String src) {
        if (isNotEmpty(src)) {
            return src.trim();
        }
        return "";
    }

}
