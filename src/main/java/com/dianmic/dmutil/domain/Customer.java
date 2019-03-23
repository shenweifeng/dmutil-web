package com.dianmic.dmutil.domain;

/**
 * 
 * 
 * @date 2019年1月4日
 * 
 * @author swf
 *
 * @Description 客户资料
 *
 */
public class Customer extends BaseModel {

    private static final long serialVersionUID = 554851308245178818L;

    private Integer           id;
    private String            yhbh;
    private String            yhmc;

    public Customer() {
    }

    public boolean check() {
        if (yhbh != null) {
            return true;
        }
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYhbh() {
        return yhbh;
    }

    public void setYhbh(String yhbh) {
        this.yhbh = yhbh;
    }

    public String getYhmc() {
        return yhmc;
    }

    public void setYhmc(String yhmc) {
        this.yhmc = yhmc;
    }

}
