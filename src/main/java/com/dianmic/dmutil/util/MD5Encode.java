package com.dianmic.dmutil.util;

import java.security.MessageDigest;

/**
 * 
 * 
 * @date 2019年3月23日
 * 
 * @author swf
 *
 * @Description MD5处理工具类
 *
 */
public class MD5Encode {

    public static String encode(String sourceString) {
        String resultString = null;
        try {
            resultString = new String(sourceString);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 进行加密
            resultString = byte2hexString(md.digest(resultString.getBytes("utf-8")));
        } catch (Exception ex) {
        }
        return resultString;
    }

    /**
     * 
     * 方法说明:把字节数组转换成字符串.
     * 
     * @param bytes
     * @return
     */
    private static final String byte2hexString(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

}
