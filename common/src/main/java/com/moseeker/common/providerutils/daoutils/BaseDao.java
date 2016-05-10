package com.moseeker.common.providerutils.daoutils;

import java.util.List;

//todo 基础接口里不应该出现jooq相关的类
import com.moseeker.thrift.gen.profile.struct.CommonQuery;

/**
 * 
 * 定义了一些比较通用的底层数据操作的接口 
 * <p>Company: MoSeeker</P>  
 * <p>date: May 5, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version Beta
 * @param <S> 基于thrift通信的数据结构
 */
public interface BaseDao<S> {
	
	S getResource(CommonQuery query) throws Exception;

	List<S> getResources(CommonQuery query) throws Exception;
	
	int getResourceCount(CommonQuery query) throws Exception ;

	int postResources(List<S> structs) throws Exception;

	int putResources(List<S> structs) throws Exception;

	int delResources(List<S> structs) throws Exception;

	int postResource(S struct) throws Exception;

	int putResource(S struct) throws Exception;

	int delResource(S struct) throws Exception;

}
