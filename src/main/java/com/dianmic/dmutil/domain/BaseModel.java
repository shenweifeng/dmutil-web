/**
 * 
 */
package com.dianmic.dmutil.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * 
 * @date 2019年3月23日
 * 
 * @author swf
 *
 * @Description bean基类
 *
 */
public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * 新增时非空校验
     * 
     * @return
     */
    public abstract boolean check();

}
