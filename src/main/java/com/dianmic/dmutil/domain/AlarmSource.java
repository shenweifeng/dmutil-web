package com.dianmic.dmutil.domain;

import java.util.Date;

/**
 * @date 2014-3-26
 * 
 * @class com.dianmic.sync.domain.AlarmSource
 * 
 * @author swf
 * 
 * @Description 共信平台报警数据-原始流水记录
 * 
 */
public class AlarmSource extends BaseModel {

    private static final long serialVersionUID = 7203786870968399486L;

    private Integer           id;
    private String            yhbh;
    private String            jqnr;
    private Date              bjsj;
    private String            fqbh;

    private String            bjxx;
    private Integer           cuId;
    private String            source;
    private Date              uptime;

    private String            ip;

    private String            protocol;
    private String            subsystem;
    private String            sbid;

    private String            jqnrNew;
    private String            subsystemNew;

    public AlarmSource() {
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

    public Integer getCuId() {
        return cuId;
    }

    public void setCuId(Integer cuId) {
        this.cuId = cuId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getSbid() {
        return sbid;
    }

    public void setSbid(String sbid) {
        this.sbid = sbid;
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
