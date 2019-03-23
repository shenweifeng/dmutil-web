package com.dianmic.dmutil.domain.exe;

import com.dianmic.dmutil.domain.BaseModel;

/**
 * 
 * 
 * @date 2019年1月24日
 * 
 * @author swf
 *
 * @Description Exe Dept Info
 *
 */
public class ExeDept extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2384626979444100839L;

    private String            deptNo;
    private String            deptName;
    private String            deptIsShops;

    private String            shopsNo;
    // 门店类型
    private String            shopsType;
    // 渠道类型
    private String            shopsQudao;
    // 租赁类型
    private String            shopsZulin;

    public boolean check() {
        return false;
    }

    public ExeDept() {
        super();
    }

    public ExeDept(String deptNo, String deptName, String deptIsShops) {
        super();
        this.deptNo = deptNo;
        this.deptName = deptName;
        this.deptIsShops = deptIsShops;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptIsShops() {
        return deptIsShops;
    }

    public void setDeptIsShops(String deptIsShops) {
        this.deptIsShops = deptIsShops;
    }

    public String getShopsNo() {
        return shopsNo;
    }

    public void setShopsNo(String shopsNo) {
        this.shopsNo = shopsNo;
    }

    public String getShopsType() {
        return shopsType;
    }

    public void setShopsType(String shopsType) {
        this.shopsType = shopsType;
    }

    public String getShopsQudao() {
        return shopsQudao;
    }

    public void setShopsQudao(String shopsQudao) {
        this.shopsQudao = shopsQudao;
    }

    public String getShopsZulin() {
        return shopsZulin;
    }

    public void setShopsZulin(String shopsZulin) {
        this.shopsZulin = shopsZulin;
    }
}
