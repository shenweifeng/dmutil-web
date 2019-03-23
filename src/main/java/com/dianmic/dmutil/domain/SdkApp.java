package com.dianmic.dmutil.domain;

import java.util.Date;

/**
 * 
 * 
 * @date 2014-8-31
 * 
 * @author swf
 * 
 * @Description SDK授权APP
 * 
 */
public class SdkApp extends BaseModel {

    private static final long serialVersionUID = 1420374919973743564L;

    private String            appId;
    private String            appIp;
    private Date              uptime;
    private String            appName;
    private Integer           status;

    public SdkApp() {
    }

    public boolean check() {
        return true;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppIp() {
        return appIp;
    }

    public void setAppIp(String appIp) {
        this.appIp = appIp;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
