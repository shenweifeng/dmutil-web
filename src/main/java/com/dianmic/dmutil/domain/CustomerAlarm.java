package com.dianmic.dmutil.domain;

import java.util.Date;

/**
 * 
 * 
 * @date 2016-1-9 下午1:43:38
 * 
 * @author swf
 * 
 * @Description 用户最后一条报警记录信息
 * 
 */
public class CustomerAlarm extends BaseModel {

    private static final long serialVersionUID = -369480581166687921L;

    private Integer           cuId;
    private Integer           alarmId;
    private Date              bjsj;
    private String            bjnr;
    private Date              uptime;

    public CustomerAlarm() {
    }

    public CustomerAlarm(Integer cuId, Integer alarmId, Date bjsj, String bjnr) {
        super();
        this.cuId = cuId;
        this.alarmId = alarmId;
        this.bjsj = bjsj;
        this.bjnr = bjnr;
    }

    public boolean check() {
        return true;
    }

    public Integer getCuId() {
        return cuId;
    }

    public void setCuId(Integer cuId) {
        this.cuId = cuId;
    }

    public Integer getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    public Date getBjsj() {
        return bjsj;
    }

    public void setBjsj(Date bjsj) {
        this.bjsj = bjsj;
    }

    public String getBjnr() {
        return bjnr;
    }

    public void setBjnr(String bjnr) {
        this.bjnr = bjnr;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

}
