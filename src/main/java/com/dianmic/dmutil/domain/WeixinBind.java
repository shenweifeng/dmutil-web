package com.dianmic.dmutil.domain;

import java.util.Date;

/**
 * 
 * 
 * @date 2015-3-29
 * 
 * @author swf
 * 
 * @Description 微信-用户绑定记录
 * 
 */
public class WeixinBind extends BaseModel {

    private static final long serialVersionUID = -217778261290150696L;

    private String            fromUserName;
    private String            phone;
    private String            yhbh;
    private Integer           cuId;
    private Date              uptime;

    private String            toUserName;
    private String            nickName;
    private Integer           sex;
    private Integer           status;

    public WeixinBind() {
    }

    public boolean check() {
        if (fromUserName != null) {
            return true;
        }
        return false;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getYhbh() {
        return yhbh;
    }

    public void setYhbh(String yhbh) {
        this.yhbh = yhbh;
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

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
