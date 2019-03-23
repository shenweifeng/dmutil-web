package com.dianmic.dmutil.domain;

import java.util.Date;

/**
 * 
 * 
 * @date 2015-5-13
 * 
 * @author swf
 * 
 * @Description 视频设备信息-美安
 * 
 */
public class VideoMa extends BaseModel {

    private static final long serialVersionUID = 2456286513670183450L;

    private String            sbid;
    private String            sbmc;
    private String            userName;
    private String            userPass;

    private Integer           status;
    private Integer           cuId;
    private Date              uptime;

    private Integer           isonline;

    public VideoMa() {
    }

    public boolean check() {
        return true;
    }

    public String getSbid() {
        return sbid;
    }

    public void setSbid(String sbid) {
        this.sbid = sbid;
    }

    public String getSbmc() {
        return sbmc;
    }

    public void setSbmc(String sbmc) {
        this.sbmc = sbmc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCuId() {
        return cuId;
    }

    public void setCuId(Integer cuId) {
        this.cuId = cuId;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    public Integer getIsonline() {
        return isonline;
    }

    public void setIsonline(Integer isonline) {
        this.isonline = isonline;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

}
