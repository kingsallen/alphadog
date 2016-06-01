package com.moseeker.common.util;

import java.util.List;
/**
 * 
 * 分页的数据结构 
 * <p>Company: MoSeeker</P>  
 * <p>date: Apr 29, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 * @param <S>
 */
public class Pagination<S> {
	
	public Pagination() {}
	
	public Pagination(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	public Pagination(int pageNo, int pageSize, int totalPage, int totalRow, List<S> results) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalRow = totalRow;
		this.results = results;
	}

	private int pageNo;
	private int pageSize;
	private int totalPage;
	private int totalRow;
	private List<S> results;
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}
	public List<S> getResults() {
		return results;
	}
	public void setResults(List<S> results) {
		this.results = results;
	}
}
