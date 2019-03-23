package com.dianmic.dmutil.domain;

import java.util.Date;

/**
 * @date 2014-3-26
 * 
 * @class com.dianmic.sync.domain.AlarmTypeJk
 * 
 * @author swf
 * 
 * @Description
 * 
 */
public class AlarmTypeJk extends BaseModel {

    private static final long serialVersionUID = -7706511738285989995L;

    private String            jkCode;
    private String            jkContent;
    private String            gxCode;
    private String            fqbh;
    private String            gxContent;

    private Integer           auto;
    private String            autoStr;
    private Date              uptime;
    private Integer           realAlarm;
    private String            realAlarmStr;
    private Integer           hasArea;

    public AlarmTypeJk() {
    }

    public boolean check() {
        if (jkCode != null) {
            return true;
        }
        return false;
    }

    public String getJkCode() {
        return jkCode;
    }

    public void setJkCode(String jkCode) {
        this.jkCode = jkCode;
    }

    public String getJkContent() {
        return jkContent;
    }

    public void setJkContent(String jkContent) {
        this.jkContent = jkContent;
    }

    public String getGxCode() {
        return gxCode;
    }

    public void setGxCode(String gxCode) {
        this.gxCode = gxCode;
    }

    public String getFqbh() {
        return fqbh;
    }

    public void setFqbh(String fqbh) {
        this.fqbh = fqbh;
    }

    public String getGxContent() {
        return gxContent;
    }

    public void setGxContent(String gxContent) {
        this.gxContent = gxContent;
    }

    public Integer getAuto() {
        return auto;
    }

    public void setAuto(Integer auto) {
        this.auto = auto;
    }

    public String getAutoStr() {
        return autoStr;
    }

    public void setAutoStr(String autoStr) {
        this.autoStr = autoStr;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    public Integer getRealAlarm() {
        return realAlarm;
    }

    public void setRealAlarm(Integer realAlarm) {
        this.realAlarm = realAlarm;
    }

    public String getRealAlarmStr() {
        return realAlarmStr;
    }

    public void setRealAlarmStr(String realAlarmStr) {
        this.realAlarmStr = realAlarmStr;
    }

    public Integer getHasArea() {
        return hasArea;
    }

    public void setHasArea(Integer hasArea) {
        this.hasArea = hasArea;
    }

}
