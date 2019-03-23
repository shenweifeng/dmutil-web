package com.dianmic.dmutil.util;

import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 
 * @date 2019年3月23日
 * 
 * @author swf
 *
 * @Description 基础工具类
 *
 */
public class CommonUtil {

    /**
     * 
     * @date 2014-8-28 上午12:35:24
     * 
     * @author swf
     * 
     * @Description 获取指定文件名的文件后缀
     * 
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName) {
        if (StringUtil.isNotEmpty(fileName) && fileName.indexOf(".") > -1) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }

    /**
     * 
     * @date 2014-8-31 下午5:47:21
     * 
     * @author swf
     * 
     * @Description 获取访问IP
     * 
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String forwaredFor = request.getHeader("X-Forwarded-For");
        String ip = "";
        if (StringUtil.isNotEmpty(forwaredFor)) {
            String[] ipArray = forwaredFor.split(",");
            ip = ipArray[0];
        } else {
            ip = request.getRemoteAddr();
        }
        if (ip.startsWith("0:0:")) {
            ip = Constant.ip_default_local;
        }
        return ip;
    }

    public static boolean isEmpty(String src) {
        return null == src || src.trim().length() == 0;
    }

    public static boolean isEmpty(String... src) {
        if (src == null) {
            return true;
        }
        for (String s : src) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    public static Integer str2int(String src, Integer defaultValue) {
        if (isEmpty(src)) {
            return defaultValue;
        }
        Integer ret = defaultValue;
        try {
            ret = Integer.parseInt(src);
        } catch (Exception e) {
            ret = defaultValue;
        }
        return ret;
    }

    public static String toHex(int src) {
        return Integer.toHexString(src);
    }

    public static String getMobileCode() {
        StringBuffer code = new StringBuffer(6);
        Random r = new Random();
        int i = 0;
        while (++i <= 6) {
            code.append(r.nextInt(10));
        }
        return code.toString();
    }

    public static String getCurrrentTime4FileName() {
        return DateUtil.dateToString(new Date(), "yyyy_MM_dd_HH_ss_mm_SSS") + "_" + getMobileCode();
    }
}
