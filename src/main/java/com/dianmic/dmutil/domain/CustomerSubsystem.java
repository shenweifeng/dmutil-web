package com.dianmic.dmutil.domain;

/**
 * 
 * 
 * @date 2019年3月15日
 * 
 * @author swf
 *
 * @Description 客户资料-子系统
 *
 */
public class CustomerSubsystem extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -3121441907734876292L;

    private Integer           cuId;
    private Integer           subsystemCode;
    private String            subsystemName;

    public CustomerSubsystem() {
    }

    @Override
    public boolean check() {
        return false;
    }

    public CustomerSubsystem(Integer cuId, Integer subsystemCode) {
        super();
        this.cuId = cuId;
        this.subsystemCode = subsystemCode;
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
