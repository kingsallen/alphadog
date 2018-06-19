package com.moseeker.entity.pojo.mq;

/**
 * Created by zztaiwll on 18/5/22.
 */
public class AIRecomParams {
    private int userId;
    private int companyId;
    private int type;
    private String positionIds;
    private String enableQxRetry;
    private String url;
    private int templateId;
    private String algorithmName;


    public AIRecomParams(int userId, int companyId, int type, String positionIds, String enableQxRetry, String url, int templateId,String algorithmName){
        this.userId=userId;
        this.companyId=companyId;
        this.type=type;
        this.positionIds=positionIds;
        this.enableQxRetry=enableQxRetry;
        this.url=url;
        this.templateId=templateId;
        this.algorithmName=algorithmName;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(String positionIds) {
        this.positionIds = positionIds;
    }

    public String getEnableQxRetry() {
        return enableQxRetry;
    }

    public void setEnableQxRetry(String enableQxRetry) {
        this.enableQxRetry = enableQxRetry;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}
