package com.moseeker.common.providerutils.bzutils;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.Pagination;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.struct.ProviderResult;

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

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected BaseDao<S> dao;

	protected abstract void initDao();

	public ProviderResult getResources(CommonQuery query) throws TException {
		ProviderResult pr = new ProviderResult();
		if(dao == null) {
			initDao();
		}
		List<S> result = new ArrayList<>();
		try {
			result = dao.getResources(query);
			pr.setStatus(0);
			pr.setMessage(Constant.TIPS_SUCCESS);
			pr.setData(JSON.toJSONString(result));
		} catch (Exception e) {
			logger.error("error", e);
			pr.setStatus(1);
			pr.setMessage(e.getMessage());
			pr.setData(JSON.toJSONString(result));
		} finally {
			//do nothing
		}
		return pr;
	}

	public ProviderResult postResources(List<S> structs) throws TException {
		ProviderResult pr = new ProviderResult();
		if(dao == null) {
			initDao();
		}
		int i = 0;
		try {
			i = dao.postResources(structs);
			pr.setStatus(0);
			pr.setMessage(Constant.TIPS_SUCCESS);
			pr.setData(String.valueOf(i));
		} catch (Exception e) {
			logger.error("error", e);
			pr.setStatus(1);
			pr.setMessage(e.getMessage());
			pr.setData(String.valueOf(i));
		} finally {
			//do nothing
		}
		
		return pr;
	}

	public ProviderResult putResources(List<S> structs) throws TException {
		ProviderResult pr = new ProviderResult();
		if(dao == null) {
			initDao();
		}
		int i = 0;
		try {
			i = dao.putResources(structs);
			pr.setStatus(0);
			pr.setMessage(Constant.TIPS_SUCCESS);
			pr.setData(String.valueOf(i));
		} catch (Exception e) {
			logger.error("error", e);
			pr.setStatus(1);
			pr.setMessage(e.getMessage());
			pr.setData(String.valueOf(i));
		} finally {
			//do nothing
		}
		return pr;
	}

	public ProviderResult delResources(List<S> structs) throws TException {
		ProviderResult pr = new ProviderResult();
		if(dao == null) {
			initDao();
		}
		int i = 0;
		try {
			i = dao.delResources(structs);
			pr.setStatus(0);
			pr.setMessage(Constant.TIPS_SUCCESS);
			pr.setData(String.valueOf(i));
		} catch (Exception e) {
			logger.error("error", e);
			pr.setStatus(1);
			pr.setMessage(e.getMessage());
			pr.setData(String.valueOf(i));
		} finally {
			//do nothing
		}
		return pr;
	}
	
	public ProviderResult getResource(CommonQuery query) throws TException {
		ProviderResult pr = new ProviderResult();
		if(dao == null) {
			initDao();
		}
		S struct = null;
		try {
			struct = dao.getResource(query);
			pr.setStatus(0);
			pr.setMessage(Constant.TIPS_SUCCESS);
			pr.setData(JSON.toJSONString(struct));
		} catch (Exception e) {
			logger.error("error", e);
			pr.setStatus(1);
			pr.setMessage(e.getMessage());
			pr.setData(Constant.NONE_JSON);
		} finally {
			//do nothing
		}
		return pr;
	}

	public ProviderResult postResource(S struct) throws TException {
		ProviderResult pr = new ProviderResult();
		if(dao == null) {
			initDao();
		}
		int i = 0;
		try {
			i = dao.postResource(struct);
			pr.setStatus(0);
			pr.setMessage(Constant.TIPS_SUCCESS);
			pr.setData(String.valueOf(i));
		} catch (Exception e) {
			logger.error("error", e);
			pr.setStatus(1);
			pr.setMessage(e.getMessage());
			pr.setData(String.valueOf(i));
		} finally {
			//do nothing
		}
		return pr;
	}

	public ProviderResult putResource(S struct) throws TException {
		ProviderResult pr = new ProviderResult();
		if(dao == null) {
			initDao();
		}
		int i = 0;
		try {
			i = dao.putResource(struct);
			pr.setStatus(0);
			pr.setMessage(Constant.TIPS_SUCCESS);
			pr.setData(String.valueOf(i));
		} catch (Exception e) {
			logger.error("error", e);
			pr.setStatus(1);
			pr.setMessage(e.getMessage());
			pr.setData(String.valueOf(i));
		} finally {
			//do nothing
		}
		return pr;
	}

	public ProviderResult delResource(S struct) throws TException {
		ProviderResult pr = new ProviderResult();
		if(dao == null) {
			initDao();
		}
		int i = 0;
		try {
			i = dao.delResource(struct);
			pr.setStatus(0);
			pr.setMessage(Constant.TIPS_SUCCESS);
			pr.setData(String.valueOf(i));
		} catch (Exception e) {
			logger.error("error", e);
			pr.setStatus(1);
			pr.setMessage(e.getMessage());
			pr.setData(String.valueOf(i));
		} finally {
			//do nothing
		}
		return pr;
	}
	
	protected ProviderResult getTotalRow(CommonQuery query) throws TException {
		ProviderResult pr = new ProviderResult();
		if(dao == null) {
			initDao();
		}
		int totalRow = 0;
		try {
			totalRow = dao.getResourceCount(query);
			pr.setStatus(0);
			pr.setMessage(Constant.TIPS_SUCCESS);
			pr.setData(String.valueOf(totalRow));
		} catch (Exception e) {
			logger.error("error", e);
			pr.setStatus(1);
			pr.setMessage(e.getMessage());
			pr.setData(String.valueOf(0));
		} finally {
			//do nothing
		}
		return pr;
	}
	
	public ProviderResult getPagination(CommonQuery query) throws TException {
		ProviderResult pr = new ProviderResult();
		try {
			Pagination<S> pagination = new Pagination<>();
			int totalRow = dao.getResourceCount(query);
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
			List<S> result = dao.getResources(query);
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			pagination.setTotalPage(totalPage);
			pagination.setTotalRow(totalRow);
			pagination.setResults(result);
			pr.setData(JSON.toJSONString(pagination));
			pr.setStatus(0);
			pr.setMessage(Constant.TIPS_SUCCESS);
		} catch (Exception e) {
			pr.setData(Constant.NONE_JSON);
			pr.setStatus(1);
			pr.setMessage(e.getMessage());
		} finally {
			//do nothing
		}
		return pr;
	}
}
