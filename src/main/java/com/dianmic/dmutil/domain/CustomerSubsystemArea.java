package com.dianmic.dmutil.domain;

/**
 * 
 * 
 * @date 2019年3月15日
 * 
 * @author swf
 *
 * @Description 客户资料-子系统-防区资料
 *
 */
public class CustomerSubsystemArea extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2748515145521287180L;

    private Integer           cuId;
    private Integer           areaCode;
    private String            areaName;
    private Integer           subsystemCode;
    private String            subsystemName;

    public CustomerSubsystemArea() {
    }

    public CustomerSubsystemArea(Integer cuId, Integer areaCode) {
        super();
        this.cuId = cuId;
        this.areaCode = areaCode;
    }

    @Override
    public boolean check() {
        return false;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getSubsystemCode() {
        return subsystemCode;
    }

    public void setSubsystemCode(Integer subsystemCode) {
        this.subsystemCode = subsystemCode;
    }

    public String getSubsystemName() {
        return subsystemName;
    }

    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

    public Integer getCuId() {
        return cuId;
    }

    public void setCuId(Integer cuId) {
        this.cuId = cuId;
    }

}
