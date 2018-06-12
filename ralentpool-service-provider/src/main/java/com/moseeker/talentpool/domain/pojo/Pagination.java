package com.moseeker.talentpool.domain.pojo;

import java.util.List;

/**
 * 分页信息
 */
public class Pagination<T> {

    protected int pageNo;           //页码
    protected int pageSize;         //每页显示的数量
    protected int totalRow;         //总共数量
    protected int totalPage;        //总共页码
    protected List<T> result;       //结果集

    public Pagination() {
        this.pageNo = 1;
        this.pageSize = 10;
        this.totalPage = 0;
    }

    public Pagination(int totalRow, int pageNo, int pageSize, List<T> result) {
        this.totalRow = totalRow;
        this.pageNo = pageNo;
        this.pageSize = pageSize;

        if (this.pageNo <=0) {
            this.pageNo = 1;
        }
        if (this.pageSize <= 0) {
            this.pageSize = 10;
        }
        if (this.totalRow <= 0) {
            this.totalRow = 0;
            this.totalPage = 0;
        } else {
            this.totalPage = this.totalRow/this.pageSize+1;
        }
        this.result = result;

    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        if (this.pageNo <=0) {
            this.pageNo = 1;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        if (this.pageSize <= 0) {
            this.pageSize = 10;
        } else if (totalRow > 0) {
            this.totalPage = this.totalRow/this.pageSize+1;
        }

    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
        if (this.totalRow <= 0) {
            this.totalRow = 0;
            this.totalPage = 0;
        } else {
            this.totalPage = this.totalRow/this.pageSize+1;
        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public List<T> getResult() {
        return result;
    }

    protected void setResult(List<T> result) {
        this.result = result;
    }
}
