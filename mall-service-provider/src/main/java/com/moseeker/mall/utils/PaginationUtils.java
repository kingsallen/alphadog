package com.moseeker.mall.utils;



/**
 * 分页工具
 *
 * @author cjm
 * @date 2018-10-16 10:15
 **/
public class PaginationUtils {

    /**
     * 通过页面大小，当前页，总行数获取分页的起始下标
     * @param pageSize 页面大小
     * @param pageNum 当前页
     * @param totalRows 总行数
     * @author  cjm
     * @date  2018/10/16
     * @return 返回起始下标
     */
    public static int getStartIndex(int pageSize, int pageNum, int totalRows) {
        int maxPage = totalRows/pageSize;
        int startIndex = (pageNum - 1) * pageSize;
        if(totalRows % pageSize > 0){
            maxPage += 1;
        }
        // 如果起始下标大于总行数，重置起始下标为最后一页第一个数据
        if(startIndex > totalRows){
            startIndex = (maxPage - 1) * pageSize;
        }
        // 如果起始下标等于总行数，由于limit是从0开始，所以此下标是没有数据的，再减去一个pageSize
        if (startIndex == totalRows && totalRows >= pageSize) {
            startIndex = startIndex - pageSize;
        } else if (startIndex == totalRows) {
            startIndex = 0;
        }
        if(startIndex < 0){
            startIndex = 0;
        }
        return startIndex;
    }
}