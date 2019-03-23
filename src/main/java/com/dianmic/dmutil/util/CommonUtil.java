package com.dianmic.dmutil.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.dianmic.dmutil.cache.GlobalCache;

/**
 * 
 * @date 2013-9-6 下午12:15:59
 * 
 * @author swf
 * 
 * @Description 基础工具类
 */
public class CommonUtil {

    // private static Logger log = Logger.getLogger(CommonUtil.class);

    public static void sendSmsMessage(String mobile, String content) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", mobile);
        map.put("content", content);
        // HttpUtil.postForm(Constant.api_sms_sendMessage, map2string(map));

    }

    public static void sendWeixinMessageTemplate(String alarmContent, String yhmc, String alarmTime,
            Set<String> fromUserNames, String subsystem, String alarmType) {
        Map<String, String> map = new HashMap<String, String>();
        for (String s : fromUserNames) {
            map.put("fromUserName", s);
            map.put("templateId", GlobalCache.weixin_message_templateId_buchefang);
            map.put("shId", "1001");
            map.put("alarmType", alarmContent);
            map.put("alarmTime", alarmTime);
            if (Constant.alarmType_buchefang.equals(alarmType)) {
                // 布撤防信息
                if (StringUtil.isNotEmpty(subsystem)) {
                    map.put("first", String.format("%s, 已%s【%s】", yhmc, alarmContent, subsystem));
                } else {
                    map.put("first", String.format("%s, 已%s", yhmc, alarmContent));
                }
            } else if (Constant.alarmType_jingqing.equals(alarmType)) {
                // 警情信息
                map.put("first", String.format("%s, 警情信息【%s】", yhmc, alarmContent));
            } else {
                map.put("first", String.format("%s, 您有一条消息通知", yhmc, alarmContent));
            }
            map.put("remark", "如有疑问，请联系客服！");
            map.put("keywordList", String.format("%s,%s,%s", alarmType, alarmContent, alarmTime));
            HttpUtil.postForm(Constant.api_weixin_sendMessageTemplate, map);
        }

    }

    // public static void main(String[] args) {
    // Map<String, Object> map = new HashMap<String, Object>();
    // map.put("fromUserName", "oKgXhvuciX4tyoGj1c8o6CRCDgTg");
    // map.put("templateId", "YsO-oQgTpB3fnIx_ZWPBP_gtoztxF0tXSV5v4mHsHsg");
    // // map.put("yhmc", "1");
    // map.put("alarmType", "1");
    // map.put("shId", "1001");
    // map.put("first", "厦门点微信息, 已布防[保险柜]");
    // // map.put("first", "厦门点微信息, 发生警情【窃盗[1防区]】");
    // map.put("remark", "如有疑问，请咨询在线客服！");
    // map.put("alarmTime", "1");
    // List<String> keywordList = new ArrayList<>();
    // map.put("keywordList", "布撤防,布防,2019-3-13 18:33:04");
    // boolean ret = HttpUtil.postForm(Constant.api_weixin_sendMessageTemplate,
    // map2string(map));
    // System.out.println(ret);
    // }

    public static String map2string(Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(50);
        Set<String> set = map.keySet();
        for (String s : set) {
            sb.append(s).append("=").append(map.get(s)).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static String getCurrentHour() {
        return DateUtil.getCurDateToString("HH");
    }

    public static boolean isPhoto(String photoName) {
        if (StringUtil.isNotEmpty(photoName) && (photoName.endsWith(".jpg") || photoName.endsWith(".jpeg")
                || photoName.endsWith(".png") || photoName.endsWith(".gif"))) {
            return true;
        }
        return false;
    }

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

    /**
     * 
     * @date 2014-8-31 下午2:57:03
     * 
     * @author swf
     * 
     * @Description 格式化用户编号
     * 
     * @param yhbh
     * @return
     */
    public static String formatYhbh(String yhbh) {
        if (StringUtil.isEmpty(yhbh)) {
            return "";
        }
        yhbh = yhbh.trim();
        while (yhbh.length() < Constant.yhbh_count) {
            yhbh = "0" + yhbh;
        }
        return yhbh;
    }

    public static boolean checkKey4Notify(String key) {
        if (StringUtil.isNotEmpty(key) && MD5Encode
                .encode(Constant.md5_key_notify + DateUtil.dateToString(new Date(), "yyyyMMdd")).equals(key)) {
            return true;
        }
        return false;
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

    public static BufferedImage bytes2BufferedImage(byte[] src) {
        if (src == null || src.length == 0) {
            return null;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(src);
        try {
            return ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSignature(String product, int verCode) {
        String res = "";
        if (StringUtils.isEmpty(product) || verCode <= 0) {
            return res;
        }
        if (product.equals("cn.xianglianai")) {
            if (verCode >= 29) {
                res = Constant.signatures.get("ver_1");
            }
        } else if (product.equals("cn.shuangshuangfei")) {
            if (verCode >= 29) {
                res = Constant.signatures.get("ver_1");
            }
        } else if (product.equals("cn.relian99")) {
            if (verCode >= 29) {
                res = Constant.signatures.get("ver_1");
            }
        } else if (product.equals("cn.lianaibaodian")) {
            if (verCode >= 29) {
                res = Constant.signatures.get("ver_1");
            }
        }
        return res;
    }

    /**
     * 
     * @date 2013-9-6 下午12:24:21
     * 
     * @author swf
     * 
     * @Description 十六进制字符串数组转换成十进制整型数组
     * 
     * @param src
     * @return
     */
    public static int[] getString2Int(String[] src) {
        if (StringUtil.isNotEmpty(src)) {
            int size = src.length;
            int[] ret = new int[size];
            try {
                for (int i = 0; i < size; i++) {
                    ret[i] = Integer.parseInt(src[Constant.apk_encode_key[i]], 16);
                    // ret[i] =
                    // Integer.parseInt(src[Constant.apk_encode_key[i]]);
                    // ret[i] = src[Constant.apk_encode_key[i]];
                }
            } catch (NumberFormatException e) {
                ret = null;
            }
            return ret;
        }
        return null;
    }

    public static String toHex(int src) {
        return Integer.toHexString(src);
    }

    public static int toInt(String src) {
        int ret = 0;
        try {
            ret = Integer.parseInt(src, Constant.radix_16);
        } catch (NumberFormatException e) {
            ret = 0;
        }
        return ret;
    }

    public static String formatMaYhbh(String yhbh) {
        if (StringUtil.isEmpty(yhbh)) {
            return "";
        }
        yhbh = yhbh.trim();
        while (yhbh.length() < Constant.yhbh_count) {
            yhbh = "0" + yhbh;
        }
        yhbh = "M" + yhbh.substring(1);
        return yhbh;
    }

    public static String formatMaFqbh(String fqbh) {
        if (StringUtil.isEmpty(fqbh)) {
            return "";
        }
        fqbh = fqbh.trim();
        while (fqbh.length() < Constant.fqbh_count) {
            fqbh = "0" + fqbh;
        }
        return fqbh;
    }

    public static String formatMaBjxx(String bjxx) {
        if (StringUtil.isEmpty(bjxx)) {
            return "";
        }
        bjxx = bjxx.trim();
        if (bjxx.length() < 4) {
            return bjxx;
        }
        String first = bjxx.substring(0, 1);
        if ("1".equals(first)) {
            bjxx = "E" + bjxx.substring(1);
        } else if ("3".equals(first)) {
            bjxx = "R" + bjxx.substring(1);
        }
        return bjxx;
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

    // 设备离线，2014年12月13日 09:57:59
    public static boolean isVideoJkOffline(String bjxx) {
        if ("20000".equals(bjxx) || "20010".equals(bjxx)) {
            return true;
        }
        return false;
    }

    // 设备恢复，2014年12月13日 09:58:10
    public static boolean isVideoJkOnline(String bjxx) {
        return "20009".equals(bjxx);
    }

    public static List<String> sortAlarmPic(List<String> list) {
        List<String> ret = new ArrayList<String>();
        // 0_20190219163928_682.jpg
        int i1 = -1;
        int i2 = -1;
        String s1 = null;
        String s2 = null;
        Map<String, String> map = new HashMap<String, String>();
        List<String> tmp = new ArrayList<String>();
        for (String s : list) {

            i1 = s.lastIndexOf("_");
            if (i1 <= -1) {
                tmp.add(s);
                map.put(s, s);
                continue;
            }

            s1 = s.substring(0, i1);
            i2 = s.lastIndexOf(s1);
            if (i2 <= -1) {
                tmp.add(s);
                map.put(s, s);
                continue;
            }

            s2 = s.substring(i2 + 1);
            tmp.add(s2);
            map.put(s2, s);
        }
        Collections.sort(tmp);
        for (String s : tmp) {
            ret.add(map.get(s));
        }
        return ret;
    }

    /**
     * 
     * @date 2019年3月14日 上午10:27:22
     * 
     * @author swf
     * 
     * @Description 校验是否带防区-DSC
     * 
     * @param alarm
     * @return
     */
    public static boolean checkHasAreaDsc(String alarm) {
        boolean ret = true;
        if (StringUtil.isEmpty(alarm)) {
            return ret;
        }
        for (String s : GlobalCache.alarmContentWithoutArea) {
            if (alarm.contains(s)) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    // 判断是否有无防区显示
    public static String getAlarmContentDsc(String alarmContent, String fqbh) {
        String ret = alarmContent;
        if (StringUtil.isNotEmpty(alarmContent)) {
            boolean hasArea = false;
            for (String s : GlobalCache.alarmContentWithoutArea) {
                if (alarmContent.contains(s)) {
                    hasArea = true;
                    break;
                }
            }
            if (hasArea) {
                // 无防区
                return ret;
            }
            // 有防区
            if (StringUtil.isNotEmpty(fqbh)) {
                int fq = NumberUtils.toInt(fqbh, -1);
                if (fq <= 0) {
                    return ret;
                }

                ret = String.format("%s[%s防区]", alarmContent, fq);
            }
        }
        return ret;
    }
}
