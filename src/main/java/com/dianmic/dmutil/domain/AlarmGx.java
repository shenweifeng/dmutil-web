package com.dianmic.dmutil.domain;

import java.util.Date;

import com.dianmic.dmutil.cache.GlobalCache;

/**
 * @date 2014-3-26
 * 
 * @class com.dianmic.sync.domain.AlarmGx
 * 
 * @author swf
 * 
 * @Description 共信平台报警数据
 * 
 */
public class AlarmGx extends BaseModel {

    private static final long serialVersionUID = 6516919397400231795L;

    private Integer           id;
    private String            yhbh;
    private String            jqnr;
    private Date              bjsj;
    private String            fqbh;

    private String            bjxx;
    private String            tsbz;
    private String            alarmPath;
    private String            deviceChain;
    private String            del;

    private Integer           cuId;
    private String            comCode;
    private Integer           opuser;
    private Date              uptime;
    private String            source;
    private Integer           sfwb;

    // for customer_current_status
    private String            yhmc;
    private Date              alarmUptime;
    private Integer           alarmId;

    private String            protocol;
    private String            subsystem;

    private String            jqnrNew;
    private String            subsystemNew;

    public AlarmGx() {
    }

    public boolean check() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYhbh() {
        return yhbh;
    }

    public void setYhbh(String yhbh) {
        this.yhbh = yhbh;
    }

    public String getJqnr() {
        return jqnr;
    }

    public void setJqnr(String jqnr) {
        this.jqnr = jqnr;
    }

    public Date getBjsj() {
        return bjsj;
    }

    public void setBjsj(Date bjsj) {
        this.bjsj = bjsj;
    }

    public String getFqbh() {
        return fqbh;
    }

    public void setFqbh(String fqbh) {
        this.fqbh = fqbh;
    }

    public String getBjxx() {
        return bjxx;
    }

    public void setBjxx(String bjxx) {
        this.bjxx = bjxx;
    }

    public String getTsbz() {
        return tsbz;
    }

    public void setTsbz(String tsbz) {
        this.tsbz = tsbz;
    }

    public String getAlarmPath() {
        return alarmPath;
    }

    public void setAlarmPath(String alarmPath) {
        this.alarmPath = alarmPath;
    }

    public String getDeviceChain() {
        return deviceChain;
    }

    public void setDeviceChain(String deviceChain) {
        this.deviceChain = deviceChain;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public Integer getCuId() {
        return cuId;
    }

    public void setCuId(Integer cuId) {
        this.cuId = cuId;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public Integer getOpuser() {
        return opuser;
    }

    public void setOpuser(Integer opuser) {
        this.opuser = opuser;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getYhmc() {
        if (cuId != null && GlobalCache.cacheCustomerCuIdYhbh.containsKey(cuId)) {
            yhmc = GlobalCache.cacheCustomerCuIdYhbh.get(cuId).getYhmc();
        }
        return yhmc;
    }

    public void setYhmc(String yhmc) {
        this.yhmc = yhmc;
    }

    public Date getAlarmUptime() {
        if (uptime != null) {
            return uptime;
        }
        alarmUptime = new Date();
        return alarmUptime;
    }

    public void setAlarmUptime(Date alarmUptime) {
        this.alarmUptime = alarmUptime;
    }

    public Integer getAlarmId() {
        if (id != null) {
            alarmId = id;
        }
        return alarmId;
    }

    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    public Integer getSfwb() {
        return sfwb;
    }

    public void setSfwb(Integer sfwb) {
        this.sfwb = sfwb;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSubsystem() {
        return subsystem;
    }

    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    public String getJqnrNew() {
        return jqnrNew;
    }

    public void setJqnrNew(String jqnrNew) {
        this.jqnrNew = jqnrNew;
    }

    public String getSubsystemNew() {
        return subsystemNew;
    }

    public void setSubsystemNew(String subsystemNew) {
        this.subsystemNew = subsystemNew;
    }

}
