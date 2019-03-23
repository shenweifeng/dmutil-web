package com.dianmic.dmutil.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.dianmic.dmutil.cache.GlobalCache;

/**
 * 
 * 
 * @date 2019年3月23日
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

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        if (start && globalCache != null) {
            new ThreadRefreshCacheJob(globalCache).start();
        }
    }

    public GlobalCache getGlobalCache() {
        return globalCache;
    }

    public void setGlobalCache(GlobalCache globalCache) {
        this.globalCache = globalCache;
    }

}
