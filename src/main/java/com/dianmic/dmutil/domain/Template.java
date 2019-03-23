/**
 * 
 */
package com.dianmic.dmutil.domain;

import java.util.Date;

import com.dianmic.dmutil.util.StringUtil;

/**
 * 
 * 
 * @date 2019年3月23日
 * 
 * @author swf
 *
 * @Description 通用模板
 *
 */
public class Template extends BaseModel {

    private static final long serialVersionUID = -808665079400661593L;

    private String            keyId;
    private String            keyName;
    private String            keyValue;
    private Date              uptime;

    public Template() {
    }

    public boolean check() {
        if (StringUtil.isNotEmpty(keyId, keyName, keyValue) && keyValue.length() < 200) {
            return true;
        }
        return false;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

}
