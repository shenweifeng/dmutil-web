package com.dianmic.dmutil.domain;

import java.util.Date;

/**
 * @date 2014-3-26
 * 
 * @class com.dianmic.sync.domain.AlarmTypeGx
 * 
 * @author swf
 * 
 * @Description
 * 
 */
public class AlarmTypeGx extends BaseModel {

    private static final long serialVersionUID = 4215806277750639761L;

    private Integer           id;
    private String            alarmCode;
    private String            alarmType;
    private String            alarmContent;
    private String            alarmTsbz;

    private Integer           auto;
    private String            autoStr;
    private Date              uptime;
    private Integer           realAlarm;
    private String            realAlarmStr;

    // 协议：1-cid，2-sia
    private Integer           protocol;
    private Integer           hasArea;

    public AlarmTypeGx() {
    }

    public boolean check() {
        if (alarmCode != null) {
            return true;
        }
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

    public String getAlarmTsbz() {
        return alarmTsbz;
    }

    public void setAlarmTsbz(String alarmTsbz) {
        this.alarmTsbz = alarmTsbz;
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

    public Integer getProtocol() {
        return protocol;
    }

    public void setProtocol(Integer protocol) {
        this.protocol = protocol;
    }

    public Integer getHasArea() {
        return hasArea;
    }

    public void setHasArea(Integer hasArea) {
        this.hasArea = hasArea;
    }

}
