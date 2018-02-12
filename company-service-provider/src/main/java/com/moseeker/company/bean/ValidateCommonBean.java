package com.moseeker.company.bean;

import java.util.Set;

/**
 * Created by zztaiwll on 18/1/11.
 * 验证一些基本信息的类
 */
public class ValidateCommonBean {
    //未使用的id
    private Set<Integer> unuseId;
    //使用的id
    private Set<Integer> useId;


    public Set<Integer> getUnuseId() {
        return unuseId;
    }

    public void setUnuseId(Set<Integer> unuseId) {
        this.unuseId = unuseId;
    }

    public Set<Integer> getUseId() {
        return useId;
    }

    public void setUseId(Set<Integer> useId) {
        this.useId = useId;
    }

}
