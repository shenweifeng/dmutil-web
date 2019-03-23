package com.dianmic.dmutil.job;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dianmic.dmutil.cache.GlobalCache;
import com.dianmic.dmutil.domain.AlarmGx;
import com.dianmic.dmutil.domain.CustomerAlarm;
import com.dianmic.dmutil.service.AlarmService;
import com.dianmic.dmutil.util.Constant;

/**
 * 
 * @date 2014-4-10 下午12:12:45
 * 
 * @author swf
 * 
 * @Description 开启独立的线程来执行调度操作
 */
public class ThreadRefreshCacheJob extends Thread {

    Logger               log = Logger.getLogger(ThreadRefreshCacheJob.class);

    private GlobalCache  globalCache;
    private AlarmService alarmService;

    public ThreadRefreshCacheJob(GlobalCache globalCache, AlarmService alarmService) {
        this.globalCache = globalCache;
        this.alarmService = alarmService;
    }

    @Override
    public void run() {
        try {

            // log.info("[ThreadRefreshCacheJob] start...");
            RefreshCacheJob.start = false;
            Map<String, String> map = globalCache.listCacheVersionCurrent();
            if (!map.isEmpty()) {
                String version = null;

                // 用户资料
                version = map.get(Constant.cache_key_customer);
                if (version != null && !version.equals(GlobalCache.cacheVersion.get(Constant.cache_key_customer))) {
                    // 版本不一致，刷新缓存，并更新本地缓存版本
                    globalCache.initCustomer();
                    GlobalCache.cacheVersion.put(Constant.cache_key_customer, version);
                }

                // sdkAppIp
                version = map.get(Constant.cache_key_sdkAppIp);
                if (version != null && !version.equals(GlobalCache.cacheVersion.get(Constant.cache_key_sdkAppIp))) {
                    // 版本不一致，刷新缓存，并更新本地缓存版本
                    globalCache.initAppIp();
                    GlobalCache.cacheVersion.put(Constant.cache_key_sdkAppIp, version);
                }

                // alarmTypeGx
                version = map.get(Constant.cache_key_alarmTypeGx);
                if (version != null && !version.equals(GlobalCache.cacheVersion.get(Constant.cache_key_alarmTypeGx))) {
                    // 版本不一致，刷新缓存，并更新本地缓存版本
                    globalCache.initAlarmTypeGx();
                    GlobalCache.cacheVersion.put(Constant.cache_key_alarmTypeGx, version);
                }

                // alarmTypeJk
                version = map.get(Constant.cache_key_alarmTypeJk);
                if (version != null && !version.equals(GlobalCache.cacheVersion.get(Constant.cache_key_alarmTypeJk))) {
                    // 版本不一致，刷新缓存，并更新本地缓存版本
                    globalCache.initAlarmTypeJk();
                    GlobalCache.cacheVersion.put(Constant.cache_key_alarmTypeJk, version);
                }

                // weixinBind，2015年3月29日 22:21:27
                version = map.get(Constant.cache_key_weixinBind);
                if (version != null && !version.equals(GlobalCache.cacheVersion.get(Constant.cache_key_weixinBind))) {
                    // 版本不一致，刷新缓存，并更新本地缓存版本
                    globalCache.initWeixinBind();
                    GlobalCache.cacheVersion.put(Constant.cache_key_weixinBind, version);
                }

                // jkDevice，2019年2月16日19:34:19
                version = map.get(Constant.cache_key_videoJk);
                if (version != null && !version.equals(GlobalCache.cacheVersion.get(Constant.cache_key_videoJk))) {
                    // 版本不一致，刷新缓存，并更新本地缓存版本
                    globalCache.initVideoJk();
                    GlobalCache.cacheVersion.put(Constant.cache_key_videoJk, version);
                }

            }

            // 更新用户最后一条报警信息记录，2016年1月9日 13:57:59
            if (!GlobalCache.customerAlarmMap.isEmpty()) {
                Map<Integer, AlarmGx> cache = new HashMap<Integer, AlarmGx>();
                cache.putAll(GlobalCache.customerAlarmMap);
                GlobalCache.customerAlarmMap.clear();

                for (AlarmGx ag : cache.values()) {
                    alarmService.saveOrUpdateCustomerAlarm(
                            new CustomerAlarm(ag.getCuId(), ag.getId(), ag.getBjsj(), ag.getJqnr()));
                }
                cache.clear();
            }

            // log.info("now_hour = " + GlobalCache.now_hour);

            RefreshCacheJob.start = true;
            // log.info("[ThreadRefreshCacheJob] end...");
        } catch (Exception e) {
            RefreshCacheJob.start = true;
            log.error("ThreadRefreshCacheJob, msg:" + e.getMessage() + " , cause:" + e.getCause());
        }
    }

}
