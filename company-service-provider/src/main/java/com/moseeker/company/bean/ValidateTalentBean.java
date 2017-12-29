package com.moseeker.company.bean;

import java.util.Set;

/**
 * Created by zztaiwll on 17/12/29.
 */
public class ValidateTalentBean {

    private Set<Integer> unUseUserIdSet;
    private Set<Integer> userIdSet;

    public Set<Integer> getUnUseUserIdSet() {
        return unUseUserIdSet;
    }

    public void setUnUseUserIdSet(Set<Integer> unUseUserIdSet) {
        this.unUseUserIdSet = unUseUserIdSet;
    }

    public Set<Integer> getUserIdSet() {
        return userIdSet;
    }

    public void setUserIdSet(Set<Integer> userIdSet) {
        this.userIdSet = userIdSet;
    }
}
