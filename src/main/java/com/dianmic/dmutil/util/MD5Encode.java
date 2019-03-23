package com.dianmic.dmutil.util;

import java.security.MessageDigest;

/**
 * 
 * 类说明:为密码字符串进行MD5加密
 * 
 * @author qiujy
 * @version v1.0 Oct 18, 2005
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
