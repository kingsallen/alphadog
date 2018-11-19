package com.moseeker.searchengine.domain;

/**
 * Created by zztaiwll on 18/5/16.
 */
public class SearchPast {

    private String publisher;
    private String companyId;
    private String keyword;
    private String pageNumber;
    private String pageSize;
    private String isTalent;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getIsTalent() {
        return isTalent;
    }

    public void setIsTalent(String isTalent) {
        this.isTalent = isTalent;
    }
}
