package com.dianmic.dmutil.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;

import com.dianmic.dmutil.util.StringUtil;

/**
 * @date 2014-3-26
 * 
 * @class com.dianmic.sync.domain.Customer
 * 
 * @author swf
 * 
 * @Description `id`, `yhbh`, `yhmc`, `yhdz`, `yhdh`
 * 
 */
public class CustomerGroup extends BaseModel {

    private static final long serialVersionUID = 554851308245178818L;

    private Integer           cuId;
    private String            groupId;
    private Set<Integer>      groupIdSet       = new HashSet<Integer>();

    public CustomerGroup() {
    }

    public boolean check() {
        return false;
    }

    public Integer getCuId() {
        return cuId;
    }

    public void setCuId(Integer cuId) {
        this.cuId = cuId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Set<Integer> getGroupIdSet() {
        if (groupIdSet.isEmpty() && StringUtil.isNotEmpty(groupId)) {
            String[] s = groupId.split(",");
            for (String s1 : s) {
                groupIdSet.add(NumberUtils.toInt(s1));
            }
        }
        return groupIdSet;
    }

    public void setGroupIdSet(Set<Integer> groupIdSet) {
        this.groupIdSet = groupIdSet;
    }

}
