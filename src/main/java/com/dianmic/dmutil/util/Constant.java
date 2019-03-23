package com.dianmic.dmutil.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
    public final static boolean debug                     = true;

    // cache key
    public final static String  cache_key_globalConfig    = "globalConfig";

    public final static int     upload_file_size_max      = 10000000;

    public final static String  split_douhao              = ",";
    public final static String  split_xie                 = "/";
    public final static String  upload_file_path          = "/home/web/res/";

    public final static String  formate_date_yyyyMMdd     = "yyyyMMdd";
    public final static String  formate_date_time_default = "yyyy-MM-dd HH:mm:ss";

    public final static String  job_key                   = "tom1";

    // 本地IP
    public final static String  ip_default_local          = "127.0.0.1";

    public final static int     excel_sheet_max_records   = 5000;

    public final static String  project_title             = "点微服务-通用服务";

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

}
