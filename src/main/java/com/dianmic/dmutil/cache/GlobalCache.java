/**
 * 
 */
package com.dianmic.dmutil.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dianmic.dmutil.data.CommonMapper;
import com.dianmic.dmutil.domain.AlarmGx;
import com.dianmic.dmutil.domain.AlarmTypeGx;
import com.dianmic.dmutil.domain.AlarmTypeJk;
import com.dianmic.dmutil.domain.CacheVersion;
import com.dianmic.dmutil.domain.Customer;
import com.dianmic.dmutil.domain.GroupAlarm;
import com.dianmic.dmutil.domain.SdkApp;
import com.dianmic.dmutil.domain.VideoJk;
import com.dianmic.dmutil.domain.WeixinBind;
import com.dianmic.dmutil.util.Constant;
import com.dianmic.dmutil.util.DateUtil;

/**
 * 
 * 
 * @date 2014-8-26
 * 
 * @author swf
 * 
 * @Description
 * 
 */
public class GlobalCache {

    Logger                                  log                                 = Logger.getLogger(GlobalCache.class);

    @Autowired
    private CommonMapper                    commonMapper;

    public static Map<String, Integer>      cacheCustomerYhbhCuId               = new HashMap<String, Integer>();
    public static Map<Integer, Customer>    cacheCustomerCuIdYhbh               = new HashMap<Integer, Customer>();

    public static Map<String, AlarmTypeGx>  cacheAlarmTypeGx                    = new HashMap<String, AlarmTypeGx>();

    public static Map<String, AlarmTypeJk>  cacheAlarmTypeJk                    = new HashMap<String, AlarmTypeJk>();

    public static int                       sync_current_alarm_id               = 1466851305;

    public static Map<String, Set<String>>  appIp                               = new HashMap<String, Set<String>>();

    public static Set<String>               whiteIp                             = new HashSet<String>();

    public static Map<String, String>       cacheVersion                        = new HashMap<String, String>();

    public static Map<Integer, GroupAlarm>  cacheGroupAlarmDsc                  = new HashMap<Integer, GroupAlarm>();

    public static String                    now_hour                            = "00";

    // 微信，2015年3月29日 22:15:27
    public static Map<Integer, Set<String>> cacheWeixinBind                     = new HashMap<Integer, Set<String>>();

    public static Map<String, String>       cacheWeixinMessageTemplateIds       = new HashMap<String, String>();

    public static Map<String, Integer>      alarmSourceMap                      = new HashMap<String, Integer>();

    public static Map<Integer, AlarmGx>     customerAlarmMap                    = new HashMap<Integer, AlarmGx>();

    public static Map<String, VideoJk>      cacheVideoJk                        = new HashMap<String, VideoJk>();

    public static Set<String>               alarmContentWithoutArea             = new HashSet<String>();

    public static String                    weixin_message_templateId_buchefang = "YsO-oQgTpB3fnIx_ZWPBP_gtoztxF0tXSV5v4mHsHsg";

    public GlobalCache() {
    }

    public void init() {

        initCustomer();

        initAlarmTypeGx();

        initAlarmTypeJk();

        initAppIp();

        initCacheVersion();

        initWeixinBind();

        initVideoJk();

        now_hour = DateUtil.getCurDateToString("HH");

        cacheWeixinMessageTemplateIds.put("布防", weixin_message_templateId_buchefang);
        cacheWeixinMessageTemplateIds.put("撤防", weixin_message_templateId_buchefang);

        initAlarmSource();
        initAlarmContentWithoutArea();

    }

    public void initAlarmContentWithoutArea() {
        alarmContentWithoutArea.clear();
        alarmContentWithoutArea.add("布防");
        alarmContentWithoutArea.add("撤防");
    }

    public void initAlarmTypeJk() {
        Map<String, AlarmTypeJk> mapjk = new LinkedHashMap<String, AlarmTypeJk>();
        List<AlarmTypeJk> list = commonMapper.listAlarmTypeJk();
        log.info(String.format("[initAlarmTypeJk] 刷新前, 总用户数量[%d]", cacheAlarmTypeJk.size()));
        if (list != null && !list.isEmpty()) {
            for (AlarmTypeJk obj : list) {
                mapjk.put(obj.getJkCode(), obj);
            }
        }
        cacheAlarmTypeJk.clear();
        cacheAlarmTypeJk.putAll(mapjk);
        log.info(String.format("[initAlarmTypeJk] 刷新后, 总用户数量[%d]", cacheAlarmTypeJk.size()));
    }

    public void initAlarmSource() {
        alarmSourceMap.clear();
        // 系统3
        alarmSourceMap.put("DSC", 1);
        alarmSourceMap.put("T", 1);
        // JK
        alarmSourceMap.put("JK", 2);
        alarmSourceMap.put("N", 2);
        // 美安
        alarmSourceMap.put("MA", 3);
        // gprs
        alarmSourceMap.put("G", 4);
        // 浙大飞腾
        alarmSourceMap.put("Z", 5);
        // 康联
        alarmSourceMap.put("KL", Constant.alarm_source_kl);
    }

    public void initVideoJk() {
        log.info(String.format("[initVideoJk] 刷新前, 总用户数量[%d]", cacheVideoJk.size()));
        List<VideoJk> list = commonMapper.listVideoJk();
        Map<String, VideoJk> map = new HashMap<String, VideoJk>();
        if (list != null && !list.isEmpty()) {
            for (VideoJk obj : list) {
                if (obj.getCuId() != null) {
                    map.put(obj.getSbid(), obj);
                }
            }
        }
        cacheVideoJk.clear();
        cacheVideoJk.putAll(map);
        log.info(String.format("[initVideoJk] 刷新后, 总用户数量[%d]", cacheVideoJk.size()));
    }

    public void initWeixinBind() {
        log.info(String.format("[initWeixinBind] 刷新前, 总用户数量[%d]", cacheWeixinBind.size()));
        List<WeixinBind> list = commonMapper.listWeixinBind();
        if (list != null && !list.isEmpty()) {
            Map<Integer, Set<String>> _cacheWeixinBind = new HashMap<Integer, Set<String>>();
            Set<String> set = null;
            for (WeixinBind obj : list) {
                if (_cacheWeixinBind.containsKey(obj.getCuId())) {
                    set = _cacheWeixinBind.get(obj.getCuId());
                } else {
                    set = new HashSet<String>();
                }
                set.add(obj.getFromUserName());
                _cacheWeixinBind.put(obj.getCuId(), set);
            }
            cacheWeixinBind.clear();
            cacheWeixinBind.putAll(_cacheWeixinBind);
        }
        log.info(String.format("[initWeixinBind] 刷新后, 总用户数量[%d]", cacheWeixinBind.size()));
    }

    public void initCacheVersion() {
        List<CacheVersion> list = commonMapper.listCacheVersion();
        if (list != null && !list.isEmpty()) {
            Map<String, String> map = new HashMap<String, String>();
            for (CacheVersion cv : list) {
                map.put(cv.getCacheKey(), cv.getCacheVersion());
            }
            cacheVersion.clear();
            cacheVersion.putAll(map);
        }
    }

    public Map<String, String> listCacheVersionCurrent() {
        Map<String, String> map = new HashMap<String, String>();
        List<CacheVersion> list = commonMapper.listCacheVersion();
        if (list != null && !list.isEmpty()) {
            for (CacheVersion cv : list) {
                map.put(cv.getCacheKey(), cv.getCacheVersion());
            }
        }
        return map;
    }

    public void initAppIp() {

        whiteIp.clear();
        whiteIp.add("127.0.0.1");
        whiteIp.add("192.168.1.157");

        log.info(String.format("[initAppIp] 刷新前, 总用户数量[%d]", appIp.size()));
        Map<String, Set<String>> appIpTemp = new HashMap<String, Set<String>>();
        List<SdkApp> sdkApp = commonMapper.listSdkApp();
        if (sdkApp != null && !sdkApp.isEmpty()) {
            Set<String> set = null;
            for (SdkApp sdk : sdkApp) {
                if (appIpTemp.containsKey(sdk.getAppId())) {
                    appIpTemp.get(sdk.getAppId()).add(sdk.getAppIp());
                } else {
                    set = new HashSet<String>();
                    set.add(sdk.getAppIp());
                    appIpTemp.put(sdk.getAppId(), set);
                }
            }
        }
        appIp.clear();
        appIp.putAll(appIpTemp);
        log.info(String.format("[initAppIp] 刷新后, 总用户数量[%d]", appIp.size()));
    }

    public void initAlarmTypeGx() {
        Map<String, AlarmTypeGx> map = new LinkedHashMap<String, AlarmTypeGx>();
        List<AlarmTypeGx> list = commonMapper.listAlarmTypeGx();
        log.info(String.format("[initAlarmTypeGx] 刷新前, 总用户数量[%d]", cacheAlarmTypeGx.size()));
        if (list != null && !list.isEmpty()) {
            for (AlarmTypeGx obj : list) {
                map.put(obj.getAlarmCode(), obj);
            }
        }
        cacheAlarmTypeGx.clear();
        cacheAlarmTypeGx.putAll(map);
        log.info(String.format("[initAlarmTypeGx] 刷新后, 总用户数量[%d]", cacheAlarmTypeGx.size()));
    }

    public void initCustomer() {
        Map<String, Integer> mapYhbhCuId = new HashMap<String, Integer>();
        Map<Integer, Customer> mapCuIdYhbh = new HashMap<Integer, Customer>();
        List<Customer> list = commonMapper.listCustomer();
        log.info(String.format("[initCustomer] 刷新前, 总用户数量[%d]", cacheCustomerYhbhCuId.size()));
        if (list != null && !list.isEmpty()) {
            for (Customer obj : list) {
                mapYhbhCuId.put(obj.getYhbh(), obj.getId());
                mapCuIdYhbh.put(obj.getId(), obj);
            }
        }
        cacheCustomerYhbhCuId.clear();
        cacheCustomerYhbhCuId.putAll(mapYhbhCuId);
        log.info(String.format("[initCustomer] 刷新后, 总用户数量[%d]", cacheCustomerYhbhCuId.size()));

        cacheCustomerCuIdYhbh.clear();
        cacheCustomerCuIdYhbh.putAll(mapCuIdYhbh);
    }

}
