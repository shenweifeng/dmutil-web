package com.dianmic.dmutil.domain;

/**
 * 
 * @date 2015-7-15 上午10:22:13
 * 
 * @author swf
 * 
 * @Description
 * 
 */
public class AlarmTypeKl extends BaseModel {

    private static final long serialVersionUID = -6727809054068732237L;

    private String            klId;
    private String            klContent;
    private String            gxCode;
    private String            gxContent;
    private Integer           auto;

    private Integer           realAlarm;

    public AlarmTypeKl() {
    }

    public boolean check() {
        if (klId != null) {
            return true;
        }
        return false;
    }

    public String getKlId() {
        return klId;
    }

    public void setKlId(String klId) {
        this.klId = klId;
    }

    public String getKlContent() {
        return klContent;
    }

    public void setKlContent(String klContent) {
        this.klContent = klContent;
    }

    public String getGxCode() {
        return gxCode;
    }

    public void setGxCode(String gxCode) {
        this.gxCode = gxCode;
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

    public Integer getRealAlarm() {
        return realAlarm;
    }

    public void setRealAlarm(Integer realAlarm) {
        this.realAlarm = realAlarm;
    }

}
