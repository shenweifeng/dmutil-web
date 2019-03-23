package com.dianmic.dmutil.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用于存储系统全局的静态变量
 * 
 * 2013年7月23日16:39:04
 * 
 * @author swf1986
 * 
 */
public class Constant {

    // true-测试，false-现场
    public final static boolean               debug                           = true;

    // 是否开启调试日志，2015年7月15日 11:10:08
    public static boolean                     debug_log                       = true;

    public final static String                api_weixin_sendMessageTemplate  = "http://weixin.dianmic.com/wx/sendmessagetemplate";
    public final static String                api_sms_sendMessage             = "http://125.46.78.214/sms/send";

    // 0SS SETTING
    public final static String                dir_root_dmalarm                = "dmalarm/";
    // 用户图片上传路径
    public final static String                dir_user_img                    = dir_root_dmalarm + "user/";

    public final static String                cache_key_customer              = "customer";
    public final static String                cache_key_alarmTypeGx           = "alarmTypeGx";
    public final static String                cache_key_alarmTypeJk           = "alarmTypeJk";

    public final static String                cache_key_alarmTypeKl           = "alarmTypeKl";
    public final static String                cache_key_klsb                  = "klsb";

    public final static String                cache_key_alarmTypeAuto         = "alarmTypeAuto";
    public final static String                cache_key_sdkAppIp              = "sdkAppIp";
    public final static String                cache_key_videoJk               = "videoJk";
    public final static String                cache_key_videoIpc              = "videoIpc";
    public final static String                cache_key_groupAlarm            = "groupAlarm";
    // public final static String cache_key_customerGroup = "customerGroup";
    public final static String                cache_key_weixinBind            = "weixinBind";
    public final static String                cache_key_template              = "template";

    public final static String                template_key_smsbf              = "smsbf";
    public final static String                template_key_smscf              = "smscf";
    public final static String                template_key_smstxbf            = "smstxbf";

    // sbid_message.txt, sbid_network.txt
    public final static String                video_jk_upload_prefix          = "video/jk/";

    public final static int                   alarm_source_kl                 = 6;

    public final static int                   upload_file_size_max            = 10000000;
    public final static String                split_douhao                    = ",";
    public final static String                split_xie                       = "/";
    public final static String                upload_file_path                = "/home/web/res/";
    public final static String                formate_date_yyyyMMdd           = "yyyyMMdd";
    public final static String                formate_date_time_default       = "yyyy-MM-dd HH:mm:ss";
    // 资源服务器地址
    // public final static String pic_server = "http://pic.woyaohuozhe.com/";
    public final static String                pic_server                      = "http://125.46.78.214:90/";

    // 用户编号格式长度
    public final static int                   yhbh_count                      = 8;
    public final static int                   fqbh_count                      = 3;

    // 通知接口：md5-key： md5(Gongxin_2014_Sdk_Api_Notify)
    public final static String                md5_key_notify                  = "8dbfe687fd9546f76bcaf837d38e5169";

    public final static String                job_key                         = "tom1";

    // public static String tomId = "";

    // 支付宝订单号头
    public final static String                ALIPAY_ORDER_HEAD               = "A";
    // 易宝订单号头
    public final static String                YEEPAY_ORDER_HEAD               = "Y";
    // 银联订单号头
    public final static String                UPMP_ORDER_HEAD                 = "U";
    // 掌中付订单号头
    public final static String                ZZF_ORDER_HEAD                  = "Z";
    // 苹果订单号头
    public final static String                APPLE_ORDER_HEAD                = "X";
    public final static int                   version_split_uid_guide         = 10300000;
    // 1，2，3级缓存睡眠时间
    public final static Integer               CACHE_SLEEP_TIME                = 200;
    // 查询心跳异常睡眠时间
    public final static Integer               HEARTBEAT_SLEEP_TIME            = 2000;
    // 心跳查询5次异常就跳出
    public static Integer                     HEARTBEAT_TIMEOUT               = 5;

    public final static String                PRODUCT_NAME_CN_XIANGLIANAI_IOS = "cn.xianglianai.ios";

    public final static String                PRODUCT_NAME_CN_XIANGLIANAI_WAP = "cn.xianglianai.wap";

    public final static String                PRODUCT_NAME_CN_XIANGLIANAI_WP  = "cn.xianglianai.wp";

    // 苹果开通会员的密钥盐
    public static final String                APP_STORE_SIGN_SALT             = "H3#VflsI1^ncsmt@LV56&5TlKl6x";
    // 苹果开通会员验证网址
    public static final String                URL_VERIFY_RECEIPT              = "https://buy.itunes.apple.com/verifyReceipt";
    public static final String                URL_VERIFY_RECEIPT_SANDBOX      = "https://sandbox.itunes.apple.com/verifyReceipt";
    // 状态 ：会员
    public static final Integer               STATUS_MEMBER                   = 2;
    public static final Integer               STATUS_NON_MEMBER               = 1;
    // 僵尸用户认定为5天前没有心跳的用户
    public static final Integer               ZOMBIE_DAYS                     = 5;
    // 2级缓存失败表缓存天数
    public static final Integer               FAIL_CACHE_2ND_DAYS             = 10;
    // 一级订单缓存天数，例如1是缓存1天
    public static final Integer               ORDER_CACHE_1ST_DAYS            = 7;
    // 获得心跳数据的主API地址
    public static final String                API_HEARTBEAT_URL               = "http://api.xianglianai.cn/friend/api";

    // 掌中付 商户名称
    public static final String                MERCHANT_NAME                   = "北京艾瑞斯科技";
    public static final String                MERCHANT_ID_YUYIN               = "30560";

    public final static String                LOOKUP_IP                       = "127.0.0.1";

    /**
     * apk包签名密钥
     */
    public final static int[]                 apk_encode_key                  = { 29, 47, 0, 21, 26, 24, 1, 36, 62, 32,
            27, 54, 55, 22, 60, 28, 25, 9, 58, 10, 41, 52, 61, 45, 56, 4, 39, 17, 48, 7, 16, 57, 59, 6, 44, 18, 43, 20,
            23, 33, 46, 38, 19, 11, 51, 5, 42, 12, 40, 35, 63, 31, 8, 34, 3, 2, 13, 14, 49, 50, 53, 15, 37, 30 };

    public final static int                   radix_16                        = 16;

    final static Map<String, String>          signatures                      = new HashMap<String, String>();

    /**
     * 密钥组，key:so版本号 value:密钥
     */
    public final static Map<Integer, String>  keyMap                          = new HashMap<Integer, String>();
    public final static Map<Integer, Boolean> keyEnableMap                    = new HashMap<Integer, Boolean>();
    public final static String                keyHeader                       = "xbuMIh0#hJ%bwJBuy";
    public final static Set<String>           ip_white                        = new HashSet<String>();

    public static Set<String>                 blackUser                       = new HashSet<String>();

    final static int                          msg_split_uid_01                = 15090000;

    static {

        keyMap.put(1, "5yKbiUx3sQZI0QwTZfzT9ue6SbC8Qel9IKL31dOM1AMK9nRs7OFzYZVBrhGFurz3");
        keyMap.put(2, "5yKbiUx3sQZI0QwTZfzT9ue6SbC8Qel9IKL31dOM1AMK9nRs7OFzYZVBrhGFurz3");
        keyEnableMap.put(1, true);
        keyEnableMap.put(2, true);

        // 127.0.0.1
        ip_white.add(LOOKUP_IP);
        // msg1
        ip_white.add("115.28.161.144");
        ip_white.add("10.144.161.15");
        // cache
        ip_white.add("115.28.41.122");
        ip_white.add("10.144.48.208");
        // wap_api
        ip_white.add("115.28.3.117");
        ip_white.add("10.144.2.213");
        // web2
        ip_white.add("115.28.160.30");
        ip_white.add("10.144.160.98");
        // slave02
        ip_white.add("115.28.46.81");
        ip_white.add("10.144.35.113");
        // pic_upload
        ip_white.add("115.28.14.139");
        ip_white.add("10.144.16.178");
        // pic0
        ip_white.add("115.28.160.14");
        ip_white.add("10.144.160.133");
        // slave01
        ip_white.add("115.28.10.251");
        ip_white.add("10.144.10.240");
        // op
        ip_white.add("115.28.51.192");
        ip_white.add("10.144.45.237");
        // api
        ip_white.add("42.96.173.91");
        ip_white.add("10.129.56.196");
        // proxy_pic1
        ip_white.add("42.96.137.84");
        ip_white.add("10.129.18.123");
    }

    /**
     * **********************************************************************
     * ************************ 可修改常量 ***********************************
     * **********************************************************************
     */

    /**
     * 设置可变常量的值 2013年8月12日18:29:33
     * 
     * @param name
     * @param value
     * @return
     */
    public static boolean setProperties(String name, Object value) {
        Field field = null;
        try {
            field = Constant.class.getField(name);
            if (null != field && null != value) {
                field.set(name, value);
                return true;
            }
        } catch (NoSuchFieldException e) {
        } catch (SecurityException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
        return false;
    }

    /**
     * 获取指定name常量值
     * 
     * @param name
     * @return
     */
    public static Object getProperties(String name) {
        Field field = null;
        try {
            field = Constant.class.getField(name);
            if (field != null) {
                return field.get(field.getType());
            }
        } catch (NoSuchFieldException e) {
        } catch (SecurityException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
        return null;
    }

    // public static void main(String[] args) {
    // System.out.println(Constant.getProperties("version_split_uid_guide"));
    // }

    /**
     * 获取所有的常量值
     * 
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Map<String, Object> getPropertiesAll() throws IllegalArgumentException, IllegalAccessException {
        Map<String, Object> ret = new HashMap<String, Object>();
        Field[] fields = Constant.class.getFields();
        if (fields != null && fields.length > 0) {
            for (Field f : fields) {
                System.out.println(f.getType().getSimpleName());
                ret.put(f.getName(), f.get(f.getType()));
            }
        }
        return ret;
    }

    /**
     * 获取当前毫秒数除100，取模五位
     * 
     * @return
     */
    public static String getCurrentTimeLast5() {
        return String.format("%05d", System.currentTimeMillis() / 100 % 100000);
    }

    // 本地IP
    public final static String ip_default_local        = "127.0.0.1";

    public final static int    excel_sheet_max_records = 5000;

    public final static String project_title           = "点微服务";
    public final static int    serverId_dsc            = 1001;

    public final static String alarmType_buchefang     = "布撤防";
    public final static String alarmType_jingqing      = "警情";
}
