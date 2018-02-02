package com.moseeker.company.bean;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zztaiwll on 18/1/11.
 */
public class ValidateTagBean {
    //状态
    private int status;
    //错误信息
    private String errorMessage;
    private List<Map<String, Object>> hrTagList;
    private Set<Integer> idList;
    private  Set<Integer> userTagIdList;
    private Set<Integer> nouseList;

    public int getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<Map<String, Object>> getHrTagList() {
        return hrTagList;
    }

    public Set<Integer> getIdList() {
        return idList;
    }

    public Set<Integer> getUserTagIdList() {
        return userTagIdList;
    }

    public Set<Integer> getNouseList() {
        return nouseList;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setHrTagList(List<Map<String, Object>> hrTagList) {
        this.hrTagList = hrTagList;
    }

    public void setIdList(Set<Integer> idList) {
        this.idList = idList;
    }

    public void setUserTagIdList(Set<Integer> userTagIdList) {
        this.userTagIdList = userTagIdList;
    }

    public void setNouseList(Set<Integer> nouseList) {
        this.nouseList = nouseList;
    }
}
