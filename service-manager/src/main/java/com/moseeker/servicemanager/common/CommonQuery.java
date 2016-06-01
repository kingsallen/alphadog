package com.moseeker.servicemanager.common;

public abstract interface CommonQuery<T> {

	public int getAppid();

	public T setAppid(int appid);

	public int getLimit();

	public T setLimit(int limit);

	public int getOffset();

	public T setOffset(int offset);

	public int getPage();

	public T setPage(int page);

	public int getPer_page();

	public T setPer_page(int per_page);

	public String getSortby();

	public T setSortby(String sortby);

	public String getOrder();

	public T setOrder(String order);

	public String getFields();

	public T setFields(String fields);

	public boolean isNocache();

	public T setNocache(boolean nocache);
}
