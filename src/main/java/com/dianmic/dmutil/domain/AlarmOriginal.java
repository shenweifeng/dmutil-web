package com.dianmic.dmutil.domain;

import java.util.Date;

/**
 * @date 2014-3-26
 * 
 * @class com.dianmic.sync.domain.AlarmSource
 * 
 * @author swf
 * 
 * @Description 共信平台报警数据-原始流水记录-未解析
 * 
 */
public class AlarmOriginal extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 6334911471743206909L;

    private Integer           id;
    private String            p1;
    private String            p2;
    private String            ip;
    private Date              ctime;

    public AlarmOriginal() {
    }

    public boolean check() {
        return true;
    }

    public AlarmOriginal(String p1, String p2, String ip) {
        super();
        this.p1 = p1;
        this.p2 = p2;
        this.ip = ip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

}
