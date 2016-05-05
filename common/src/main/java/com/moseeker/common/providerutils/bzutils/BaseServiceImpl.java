package com.moseeker.common.providerutils.bzutils;

import java.util.List;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.common.util.Pagination;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;

/**
 * 
 * 定义通用的增删改查功能。 
 * <p>Company: MoSeeker</P>  
 * <p>date: May 5, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version Beta
 * @param <S> 定义的基于thrift通信的数据结构
 */
@SuppressWarnings("rawtypes")
public abstract class BaseServiceImpl<S extends TBase> {

	// 用于同类类型转换(formToDB,dbToStruct)不满足当前方法，做的一个补充。这个trasient类在方法执行前判断是否为空，如果不为空，则使用transient类对象。方法结束时，置为null

	protected BaseDao<S> dao;

	protected abstract void initDao();

	public List<S> getResources(CommonQuery query, S structK) throws TException {
		if(dao == null) {
			initDao();
		}
		return dao.getResources(query);
	}

	public int postResources(List<S> structs) throws TException {
		if(dao == null) {
			initDao();
		}
		return dao.postResources(structs);
	}

	public int putResources(List<S> structs) throws TException {
		return dao.putResources(structs);
	}

	public int delResources(List<S> structs) throws TException {
		if(dao == null) {
			initDao();
		}
		return dao.delResources(structs);
	}
	
	public S getResource(CommonQuery query, S structK) throws TException {
		if(dao == null) {
			initDao();
		}
		return dao.getResource(query);
	}

	public int postResource(S struct) throws TException {
		if(dao == null) {
			initDao();
		}
		return dao.postResource(struct);
	}

	public int putResource(S struct) throws TException {
		if(dao == null) {
			initDao();
		}
		return dao.putResource(struct);
	}

	public int delResource(S struct) throws TException {
		if(dao == null) {
			initDao();
		}
		return dao.delResource(struct);
	}
	
	protected int getTotalRow(CommonQuery query, S struct) throws TException {
		if(dao == null) {
			initDao();
		}
		return dao.getResourceCount(query);
	}
	
	protected Pagination<S> getBasePagination(CommonQuery query, S struct)
			throws TException {
		Pagination<S> pagination = new Pagination<>();
		int totalRow = getTotalRow(query, struct);
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
		List<S> result = getResources(query, struct);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setTotalPage(totalPage);
		pagination.setTotalRow(totalRow);
		pagination.setResults(result);
		return pagination;
	}
}
