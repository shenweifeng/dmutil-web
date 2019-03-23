package com.dianmic.dmutil.domain;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * 
 * @date 2019年3月23日
 * 
 * @author swf
 *
 * @Description 请求数据
 *
 */
public class RequestParams extends BaseModel {

    private static final long serialVersionUID = -5871042701173869021L;

    private String            p1;
    private String            p2;
    private String            p3;
    private String            p4;
    private MultipartFile[]   p5;

    private String            p6;
    private String            p7;
    private String            p8;

    public RequestParams() {
    }

    public boolean check() {
        return true;
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

    public String getP3() {
        return p3;
    }

    public void setP3(String p3) {
        this.p3 = p3;
    }

    public String getP4() {
        return p4;
    }

    public void setP4(String p4) {
        this.p4 = p4;
    }

    public MultipartFile[] getP5() {
        return p5;
    }

    public void setP5(MultipartFile[] p5) {
        this.p5 = p5;
    }

    public String getP6() {
        return p6;
    }

    public void setP6(String p6) {
        this.p6 = p6;
    }

    public String getP7() {
        return p7;
    }

    public void setP7(String p7) {
        this.p7 = p7;
    }

    public String getP8() {
        return p8;
    }

    public void setP8(String p8) {
        this.p8 = p8;
    }

}
