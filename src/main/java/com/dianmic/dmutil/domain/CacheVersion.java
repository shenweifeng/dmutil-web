package com.dianmic.dmutil.domain;

import java.util.Date;

/**
 * 
 * 
 * @date 2019年3月23日
 * 
 * @author swf
 *
 * @Description 缓存刷新版本
 *
 */
public class CacheVersion extends BaseModel {

    private static final long serialVersionUID = 4938646834739395525L;

    private String            cacheKey;
    private String            cacheVersion;
    private Date              uptime;

    public CacheVersion() {
    }

    public boolean check() {
        return true;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getCacheVersion() {
        return cacheVersion;
    }

    public void setCacheVersion(String cacheVersion) {
        this.cacheVersion = cacheVersion;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

}
