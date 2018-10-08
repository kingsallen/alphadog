package com.moseeker.common.biztools;

import com.moseeker.common.constants.Constant;

/**
 * @Author: jack
 * @Date: 2018/10/8
 */
public class PageUtil {

    private int index;
    private int size;

    public PageUtil(int pageNum, int pageSize) {
        size = pageSize < 1 ? Constant.PAGE_SIZE:pageSize;
        index = ((pageNum < 1 ? 1: pageNum)-1) * size;
    }

    public int getIndex() {
        return index;
    }

    public int getSize() {
        return size;
    }
}
