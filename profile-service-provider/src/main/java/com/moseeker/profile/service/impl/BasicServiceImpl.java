package com.moseeker.profile.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.jooq.Record;
import org.jooq.Result;

import com.moseeker.common.util.Pagination;
import com.moseeker.profile.dao.BasicDao;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;

public abstract class BasicServiceImpl<T, K> {

	// protected transient T,K
	// 用于同类类型转换(formToDB,dbToStruct)不满足当前方法，做的一个补充。这个trasient类在方法执行前判断是否为空，如果不为空，则使用transient类对象。方法结束时，置为null

	protected BasicDao<T> dao;

	protected abstract void initDao();

	@SuppressWarnings("unchecked")
	public List<K> getProfiles(CommonQuery query, K structK) throws TException {
		List<K> structs = new ArrayList<>();
		try {
			if(dao == null) {
				initDao();
			}
			Result<Record> result = dao.getProfiles(query);
			if (result != null && result.size() > 0) {
				for (Record r : result) {
					K k = dbToStruct((T) r);
					structs.add(k);
				}
			}
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		} finally {

		}
		return structs;
	}

	public int postProfiles(List<K> structKs) throws TException {
		int result = 0;
		try {
			List<T> records = formToDB(structKs);
			if(dao == null) {
				initDao();
			}
			result = dao.postProfiles(records);
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		}
		return result;
	}

	public int putProfiles(List<K> structKs) throws TException {
		int result = 0;
		try {
			List<T> records = formToDB(structKs);
			if(dao == null) {
				initDao();
			}
			result = dao.putProfiles(records);
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		}
		return result;
	}

	public int delProfiles(List<K> structKs) throws TException {
		int result = 0;
		try {
			List<T> records = formToDB(structKs);
			if(dao == null) {
				initDao();
			}
			result = dao.delProfiles(records);
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		}
		return result;
	}

	public int postProfile(K structK) throws TException {
		int result = 0;
		try {
			T record = formToDB(structK);
			if(dao == null) {
				initDao();
			}
			result = dao.postProfile(record);
		} catch (SQLException | ParseException e) {
			throw new TException(e.getMessage());
		}
		return result;
	}

	public int putProfile(K structK) throws TException {
		int result = 0;
		try {
			T record = formToDB(structK);
			if(dao == null) {
				initDao();
			}
			result = dao.putProfile(record);
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		} catch (ParseException e) {
			throw new TException(e.getMessage());
		}
		return result;
	}

	public int delProfile(K structK) throws TException {
		int result = 0;
		try {
			T records = formToDB(structK);
			if(dao == null) {
				initDao();
			}
			result = dao.delProfile(records);
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		} catch (ParseException e) {
			throw new TException(e.getMessage());
		}
		return result;
	}
	
	protected int getTotalRow(CommonQuery query, K k) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	protected Pagination<K> getPagination(CommonQuery query, K k)
			throws TException {
		Pagination<K> pagination = new Pagination<>();
		int totalRow = getTotalRow(query, k);
		int pageNo = 1;
		int pageSize = 10;
		if(query.getPage() > 1) {
			pageNo = query.getPage();
		}
		if(query.getPer_page() > 0) {
			pageSize = query.getPer_page();
		}
		int totalPage = (int) (totalRow / pageSize);
		if (totalRow % pageSize != 0) {
			totalPage++;
		}
		List<K> result = getProfiles(query, k);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setTotalPage(totalPage);
		pagination.setTotalRow(totalRow);
		pagination.setResults(result);
		return pagination;
	}

	protected abstract K dbToStruct(T r);

	protected abstract T formToDB(K structK) throws ParseException;

	protected List<T> formToDB(List<K> structKs) throws TException {
		List<T> records = new ArrayList<>();
		if (structKs != null && structKs.size() > 0) {
			for (K structK : structKs) {
				try {
					records.add(formToDB(structK));
				} catch (ParseException e) {
					throw new TException(e.getMessage());
				}
			}
		}
		return records;
	}
}
