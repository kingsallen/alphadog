package com.moseeker.chat.utils;

/**
 * Created by jack on 09/03/2017.
 */
public class Page {

    private int pageNo;
    private int pageSize;
    private int totalPage;
    private int totalRow;

    private boolean havePrePage;
    private boolean haveNextPage;

    public Page(int pageNo, int pageSize, int totalRow) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;

        if(this.pageNo <= 0) {
            this.pageNo = 0;
        }
        if(this.pageSize <= 0 ) {
            this.pageSize = 10;
        }

        this.totalRow = totalRow;

        if(totalRow == 0) {
            totalPage = 0;
        } else {
            totalPage = totalRow/pageSize+1;
        }

        if(pageNo > 1 && totalPage > 0) {
            haveNextPage = true;
        }
        if(totalPage > pageNo) {
            haveNextPage = true;
        }
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public boolean isHavePrePage() {
        return havePrePage;
    }

    public boolean isHaveNextPage() {
        return haveNextPage;
    }
}
