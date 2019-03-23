package com.dianmic.dmutil.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dianmic.dmutil.cache.GlobalCache;
import com.dianmic.dmutil.service.AlarmService;
import com.dianmic.dmutil.util.DateUtil;

/**
 * 
 * 
 * @date 2014-8-2
 * 
 * @author swf
 * 
 * @Description 定时刷新缓存数据：用户资料，1小时刷新1次
 * 
 */
public class RefreshCacheJob extends QuartzJobBean {

    Logger                log   = Logger.getLogger(RefreshCacheJob.class);

    public static boolean start = true;

    private GlobalCache   globalCache;
    private AlarmService  alarmService;

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        if (start && globalCache != null && alarmService != null) {
            // 取当前小时数，用于手动警情处理用。
            GlobalCache.now_hour = DateUtil.getCurDateToString("HH");
            new ThreadRefreshCacheJob(globalCache, alarmService).start();
        }
    }

    public GlobalCache getGlobalCache() {
        return globalCache;
    }

    public void setGlobalCache(GlobalCache globalCache) {
        this.globalCache = globalCache;
    }

    public AlarmService getAlarmService() {
        return alarmService;
    }

    public void setAlarmService(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

}
