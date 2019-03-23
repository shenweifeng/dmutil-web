package com.dianmic.dmutil.domain;

import java.util.Date;

import com.dianmic.dmutil.util.Constant;

/**
 * @date 2014-3-26
 * 
 * @class com.dianmic.domain.AlarmPic
 * 
 * @author swf
 * 
 * @Description
 * 
 */
public class AlarmPic extends BaseModel {

    private static final long serialVersionUID = 3769237223073693094L;

    private Integer           id;
    private Integer           alarmId;
    private String            photo;
    private Date              uptime;
    private Integer           cuId;

    private String            photourl;

    public AlarmPic() {
    }

    public boolean check() {
        if (alarmId != null) {
            return true;
        }
        return false;
    }

    public AlarmPic(Integer alarmId, String photo, Integer cuId) {
        super();
        this.alarmId = alarmId;
        this.photo = photo;
        this.cuId = cuId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    public Integer getCuId() {
        return cuId;
    }

    public void setCuId(Integer cuId) {
        this.cuId = cuId;
    }

    public String getPhotourl() {
        if (photo != null) {
            photourl = Constant.pic_server + photo;
        }
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

}
