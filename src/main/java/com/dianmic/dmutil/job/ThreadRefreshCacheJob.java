package com.dianmic.dmutil.job;

import org.apache.log4j.Logger;

import com.dianmic.dmutil.cache.GlobalCache;

/**
 * 
 * 
 * @date 2019年3月23日
 * 
 * @author swf
 *
 * @Description 开启独立的线程来执行调度操作
 *
 */
public class ThreadRefreshCacheJob extends Thread {

    Logger              log = Logger.getLogger(ThreadRefreshCacheJob.class);

    @SuppressWarnings("unused")
    private GlobalCache globalCache;

    public ThreadRefreshCacheJob(GlobalCache globalCache) {
        this.globalCache = globalCache;
    }

    @Override
    public void run() {
        try {

            // log.info("[ThreadRefreshCacheJob] start...");
            RefreshCacheJob.start = false;

            // do somethings

            RefreshCacheJob.start = true;
            // log.info("[ThreadRefreshCacheJob] end...");
        } catch (Exception e) {
            RefreshCacheJob.start = true;
            log.error("ThreadRefreshCacheJob, msg:" + e.getMessage() + " , cause:" + e.getCause());
        }
    }

}
