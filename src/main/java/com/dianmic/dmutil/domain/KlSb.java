package com.dianmic.dmutil.domain;

/**
 * 
 * 
 * @date 2015-7-15 上午10:30:05
 * 
 * @author swf
 * 
 * @Description 康联设备
 * 
 */
public class KlSb extends BaseModel {

    private static final long serialVersionUID = 6116720215013014349L;

    private String            fimei;
    private String            yhbh;
    private Integer           cuId;
    private Integer           status;

    public KlSb() {
    }

    public boolean check() {
        return true;
    }

    public String getFimei() {
        return fimei;
    }

    public void setFimei(String fimei) {
        this.fimei = fimei;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
