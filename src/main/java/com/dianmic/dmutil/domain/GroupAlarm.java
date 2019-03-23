package com.dianmic.dmutil.domain;

import java.util.Date;

/**
 * 
 * 
 * @date 2015-2-4
 * 
 * @author swf
 * 
 * @Description 共信平台-自动处理转手动处理【客户组】
 * 
 */
public class GroupAlarm extends BaseModel {

    private static final long serialVersionUID = 6792400122719338440L;

    private Integer           groupId;
    private String            alarmTime;
    private String            alarmDsc;
    private String            alarmJk;
    private Date              uptime;

    public GroupAlarm() {
    }

    public boolean check() {
        return true;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmDsc() {
        return alarmDsc;
    }

    public void setAlarmDsc(String alarmDsc) {
        this.alarmDsc = alarmDsc;
    }

    public String getAlarmJk() {
        return alarmJk;
    }

    public void setAlarmJk(String alarmJk) {
        this.alarmJk = alarmJk;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

}
