/**
 * 
 */
package com.dianmic.dmutil.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dianmic.dmutil.data.CommonMapper;
import com.dianmic.dmutil.domain.CacheVersion;

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

    Logger                            log          = Logger.getLogger(GlobalCache.class);

    @Autowired
    private CommonMapper              commonMapper;

    public static Map<String, String> cacheVersion = new HashMap<String, String>();

    public GlobalCache() {
    }

    public void init() {

        initCacheVersion();

    }

    /**
     * 
     * @date 2019年3月23日 上午10:46:10
     * 
     * @author swf
     * 
     * @Description 初始加载：缓存配置文件
     *
     */
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

    /**
     * 
     * @date 2019年3月23日 上午10:46:10
     * 
     * @author swf
     * 
     * @Description 定时刷新：缓存配置文件
     *
     */
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

}
