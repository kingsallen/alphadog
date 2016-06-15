package com.moseeker.common.providerutils.bzutils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.jooq.impl.UpdatableRecordImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.common.util.Pagination;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;

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
public abstract class JOOQBaseServiceImpl<S extends TBase, R extends UpdatableRecordImpl<R>> {

	// 用于同类类型转换(formToDB,dbToStruct)不满足当前方法，做的一个补充。这个trasient类在方法执行前判断是否为空，如果不为空，则使用transient类对象。方法结束时，置为null

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected BaseDao<R> dao;

	protected abstract void initDao();

	public Response getResources(CommonQuery query) throws TException {
		if(dao == null) {
			initDao();
		}
		try {
			List<R> records = dao.getResources(query);
			List<S> structs = DBsToStructs(records);
			
			if (!structs.isEmpty()){
				return ResponseUtils.success(structs);
			}
			
		} catch (Exception e) {
			logger.error("getResources error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	public Response postResources(List<S> structs) throws TException {
		if(dao == null) {
			initDao();
		}
		int i = 0;
		try {
			List<R> records = structsToDBs(structs);
			i = dao.postResources(records);
			if ( i > 0 ){
				return ResponseUtils.success(String.valueOf(i));
			}

		} catch (Exception e) {
			logger.error("postResources error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

		} finally {
			//do nothing
		}
		
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}

	public Response putResources(List<S> structs) throws TException {
		if(dao == null) {
			initDao();
		}
		int i = 0;
		try {
			List<R> records = structsToDBs(structs);
			i = dao.putResources(records);
			if ( i > 0 ){
				return ResponseUtils.success(String.valueOf(i));
			}
		} catch (Exception e) {
			logger.error("putResources error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
	}

	public Response delResources(List<S> structs) throws TException {
		if(dao == null) {
			initDao();
		}
		int i = 0;
		try {
			List<R> records = structsToDBs(structs);
			i = dao.delResources(records);
			if ( i > 0 ){
				return ResponseUtils.success(String.valueOf(i));
			}
		} catch (Exception e) {
			logger.error("delResources error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
	}
	
	public Response getResource(CommonQuery query) throws TException {
		if(dao == null) {
			initDao();
		}
		R record = null;
		try {
			record = dao.getResource(query);
			S s = DBToStruct(record);
			if ( record != null){
				return ResponseUtils.success(s);
			}

		} catch (Exception e) {
			logger.error("getResource error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	public Response postResource(S struct) throws TException {
		if(dao == null) {
			initDao();
		}
		int i = 0;
		try {
			R record = structToDB(struct);
			i = dao.postResource(record);
			
			if ( i > 0 ){
				return ResponseUtils.success(String.valueOf(i));
			}	

		} catch (Exception e) {
			logger.error("postResource error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}

	public Response putResource(S struct) throws TException {
		if(dao == null) {
			initDao();
		}
		int i = 0;
		try {
			R r = structToDB(struct);
			i = dao.putResource(r);
			if ( i > 0 ){
				return ResponseUtils.success(String.valueOf(i));
			}
		} catch (Exception e) {
			logger.error("putResource error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
	}

	public Response delResource(S struct) throws TException {
		if(dao == null) {
			initDao();
		}
		int i = 0;
		try {
			R r = structToDB(struct);
			i = dao.delResource(r);
			if ( i > 0 ){
				return ResponseUtils.success(String.valueOf(i));
			}
		} catch (Exception e) {
			logger.error("delResource error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
	}
	
	protected Response getTotalRow(CommonQuery query) throws TException {
		if(dao == null) {
			initDao();
		}
		int totalRow = 0;
		try {
			totalRow = dao.getResourceCount(query);
			return ResponseUtils.success(String.valueOf(totalRow));
		} catch (Exception e) {
			logger.error("getTotalRow error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

		} finally {
			//do nothing
		}
	}
	
	public Response getPagination(CommonQuery query) throws TException {
		try {
			Pagination<R> pagination = new Pagination<>();
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
			List<R> records = dao.getResources(query);
			
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			pagination.setTotalPage(totalPage);
			pagination.setTotalRow(totalRow);
			pagination.setResults(records);
			return ResponseUtils.success(String.valueOf(pagination));				
			

		} catch (Exception e) {
			logger.error("getPagination error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);

		} finally {
			//do nothing
		}
	}
	
	protected abstract R structToDB(S s) throws ParseException;
	
	protected List<R> structsToDBs(List<S> structs) {
		List<R> records = new ArrayList<>();
		if(structs != null && structs.size() > 0) {
			for(S s : structs) {
				try {
					records.add(structToDB(s));
				} catch (ParseException e) {
					continue;
				}
			}
		}
		return records;
	}
	
	protected abstract S DBToStruct(R r);
	
	protected List<S> DBsToStructs(List<R> records) {
		List<S> structs = new ArrayList<>();
		if(records != null && records.size() > 0) {
			for(R r : records) {
				structs.add(DBToStruct(r));
			}
		}
		return structs;
	}
}
