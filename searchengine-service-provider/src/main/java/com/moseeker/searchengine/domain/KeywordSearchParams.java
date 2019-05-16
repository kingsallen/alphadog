package com.moseeker.searchengine.domain;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;

public class KeywordSearchParams {
    private String keyWord;
    private String companyId;
    private String hrId;
    private String profilePoolId;
    private String tagIds;
    private String favoriteHrs;
    private String isPublic;
    public KeywordSearchParams(String keyWord, String companyId, String hrId, String profilePoolId, String tagIds, String favoriteHrs, String isPublic) {
        this.keyWord = keyWord;
        this.companyId = companyId;
        this.hrId = hrId;
        this.profilePoolId = profilePoolId;
        this.tagIds = tagIds;
        this.favoriteHrs = favoriteHrs;
        this.isPublic = isPublic;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getFavoriteHrs() {
        return favoriteHrs;
    }

    public void setFavoriteHrs(String favoriteHrs) {
        this.favoriteHrs = favoriteHrs;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProfilePoolId() {
        return profilePoolId;
    }

    public void setProfilePoolId(String profilePoolId) {
        this.profilePoolId = profilePoolId;
    }

    public String getHrId() {
        return hrId;
    }

    public void setHrId(String hrId) {
        this.hrId = hrId;
    }

}
