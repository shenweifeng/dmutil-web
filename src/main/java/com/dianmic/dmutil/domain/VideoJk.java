package com.dianmic.dmutil.domain;

import java.util.Date;

/**
 * 
 * 
 * @date 2014-8-31
 * 
 * @author swf
 * 
 * @Description JK视频设备信息
 * 
 */
public class VideoJk extends BaseModel {

    private static final long serialVersionUID = 9032261146211318510L;

    private String            sbid;
    private String            sbmc;
    private String            spss;
    private String            splx;
    private String            spsz;

    private Integer           status;
    private Integer           cuId;
    private Date              uptime;

    private Integer           isonline;

    public VideoJk() {
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

    public String getSpss() {
        return spss;
    }

    public void setSpss(String spss) {
        this.spss = spss;
    }

    public String getSplx() {
        return splx;
    }

    public void setSplx(String splx) {
        this.splx = splx;
    }

    public String getSpsz() {
        return spsz;
    }

    public void setSpsz(String spsz) {
        this.spsz = spsz;
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

}
