package com.moseeker.searchengine.thrift;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.searchengine.domain.MeetBotResult;
import com.moseeker.searchengine.domain.PastPOJO;
import com.moseeker.searchengine.domain.SearchPast;
import com.moseeker.searchengine.service.impl.CompanySearchengine;
import com.moseeker.searchengine.service.impl.PositionSearchEngine;
import com.moseeker.searchengine.service.impl.SearchengineService;
import com.moseeker.searchengine.service.impl.TalentpoolSearchengine;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices.Iface;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchengineServiceImpl implements Iface {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SearchengineService service;
	@Autowired
	private CompanySearchengine companySearchengine;
	@Autowired
	private PositionSearchEngine positionSearchEngine;
	@Autowired
	private TalentpoolSearchengine talentpoolSearchengine;

	private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

	public SearchengineServiceImpl(){
		serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
	}

	@Override
	public Response query(String keywords, String cities, String industries, String occupations, String scale,
						  String employment_type, String candidate_source, String experience, String degree, String salary,
						  String company_name, int page_from, int page_size,String child_company_name,String department,
						  boolean order_by_priority,String custom,String hb_config_id) throws BIZException,TException {
		try {
			return service.query(keywords, cities, industries, occupations, scale, employment_type, candidate_source,
					experience, degree, salary, company_name, page_from, page_size, child_company_name, department, order_by_priority, custom,hb_config_id);
		}catch (Exception e){
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public Response updateposition(String position,int  id) throws BIZException,TException {
		try {
			return service.updateposition(position, id);
		}catch(Exception e){
			throw ExceptionUtils.convertException(e);
		}
	}


	@Override
	public Response companyQuery(String keyWords, String citys, String industry, String scale, int page, int pageSize)throws BIZException,TException{
		// TODO Auto-generated method stub
		try{
			Map<String,Object> res=companySearchengine.query(keyWords, citys, industry, scale, page, pageSize);
			if(res==null){
				return ResponseUtils.success("");
			}
			return ResponseUtils.success(res);
		}catch(Exception e){
			throw ExceptionUtils.convertException(e);
		}

	}

	@Override
	public Response positionQuery(String keyWords, String citys, String industry, String salaryCode, int page,
								  int pageSize, String startTime, String endTime,int companyId,int teamId,int motherCompanyId,int order,int moduleId,
								  String candidateSource
	) throws BIZException,TException {
		// TODO Auto-generated method stub
		try{
			Map<String,Object> res=positionSearchEngine.search(keyWords, industry, salaryCode, page, pageSize, citys, startTime,
					endTime,companyId,teamId,motherCompanyId,order,moduleId,candidateSource);
			if(res==null){
				return ResponseUtils.success("");
			}
			return ResponseUtils.success(res);
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
	}


    @Override
    public Response queryAwardRanking(List<Integer> employeeIds, String timespan, int pageSize, int pageNum, String keyword, int filter) throws BIZException,TException {
		try {
			return service.queryAwardRanking(employeeIds, timespan, pageSize, pageNum, keyword, filter);
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
    }

    @Override
    public Response queryAwardRankingInWx(List<Integer> companyIds, String timespan, int employeeId) throws BIZException,TException {
		try {
			return service.queryAwardRankingInWx(companyIds, timespan, employeeId);
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
    }

	@Override
	public Response fetchEmployees(List<Integer> companyIds, String keywords, int filter, String order, String asc,
								   String emailValidate, int pageSize, int pageNumber,int balanceType, String timeSpan, String selectIds)
			throws BIZException, TException {
		try {
			return service.fetchEmployees(companyIds, keywords, filter, order, asc, emailValidate, pageSize, pageNumber,
					balanceType, timeSpan, selectIds);
		}catch(Exception e){
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public Response listLeaderBoard(List<Integer> companyIds, String timespan, int employeeId, int pageNum,
									int pageSize) throws BIZException, TException {
		try {
			return service.listLeaderBoard(companyIds, timespan, employeeId, pageNum, pageSize);
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public int countLeaderBoard(List<Integer> companyIds, String timespan) throws BIZException, TException {
		return service.countLeaderBoard(companyIds, timespan);
	}

	@Override
    public Response updateEmployeeAwards(List<Integer> employeeIds) throws BIZException,TException {
		try {
			return service.updateEmployeeAwards(employeeIds);
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
    }

    @Override
    public Response deleteEmployeeDO(List<Integer> employeeIds) throws BIZException,TException {
		try {
			return service.deleteEmployeeDO(employeeIds);
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
    }

	@Override
	public Response searchPositionSuggest(Map<String, String> params) throws BIZException,TException {
		try{
			Map<String,Object> res=service.getPositionSuggest(params);

			logger.info("SearchengineServiceImpl params {}", JSON.toJSONString(params));

			if(res==null||res.isEmpty()){
				return ResponseUtils.success("");
			}
			return ResponseUtils.success(res);
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
	}

    @Override
    public Response searchProfileSuggest(Map<String, String> params) throws BIZException,TException {
        try{
            Map<String,Object> res=service.getProfileSuggest(params);
            if(res==null||res.isEmpty()){
                return ResponseUtils.success("");
            }
            return ResponseUtils.success(res);
        }catch(Exception e){

			throw ExceptionUtils.convertException(e);
        }
    }

    @Override
	public Response userQuery(Map<String, String> params) throws BIZException,TException {
		try{
			Map<String,Object> res=talentpoolSearchengine.talentSearch(params);
			if(res==null||res.isEmpty()){

				return ResponseUtils.success("");
			}
			return ResponseUtils.success(res);
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public Response userAggInfo(Map<String, String> params) throws BIZException,TException {
		try{
			Map<String,Object> res=talentpoolSearchengine.getAggInfo(params);
			if(res==null||res.isEmpty()){
				return ResponseUtils.success("");
			}
			return ResponseUtils.success(res);
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public Response queryPositionIndex(String keywords, String cities, String industries, String occupations, String scale, String employment_type, String candidate_source, String experience, String degree, String salary, String company_name, int page_from, int page_size,
									   String child_company_name, String department, boolean order_by_priority, String custom,String hb_config_id,String is_reference) throws BIZException,TException {
		try {
			return service.queryPositionIndex(keywords, cities, industries, occupations, scale, employment_type, candidate_source, experience,
					degree, salary, company_name, page_from, page_size, child_company_name, department, order_by_priority, custom,hb_config_id,is_reference);
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public Response queryPositionMini(Map<String, String> params) throws BIZException,TException {
		try{
			Map<String,Object> res=service.searchPositionMini(params);
			if(res==null||res.isEmpty()){
				return ResponseUtils.success("");
			}
			return ResponseUtils.success(res);
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public List<Integer> queryCompanyTagUserIdList(Map<String, String> params) throws BIZException,TException {
		try{
			List<Integer> res=talentpoolSearchengine.getUserListByCompanyTag(params);
			if(res==null){
				return new ArrayList<>();
			}
			return res;
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);		}
	}

	@Override
	public int queryCompanyTagUserIdListCount(Map<String, String> params) throws BIZException, TException {
		try{
			int result=talentpoolSearchengine.getUserListByCompanyTagCount(params);
			return result;
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
    public Response queryProfileFilterUserIdList(List<Map<String, String>> filterMapList, int page_number, int page_size) throws BIZException,TException {

        try{
            Map<String,Object> res=talentpoolSearchengine.getUserListByFilterIds(filterMapList, page_number, page_size);
            if(res==null||res.isEmpty()){
                return ResponseUtils.success("");
            }
            return ResponseUtils.success(res);
        }catch(Exception e){

			throw ExceptionUtils.convertException(e);
        }
    }

	@Override
	public List<Integer> getTalentUserIdList(Map<String, String> params) throws BIZException,TException {
		try{
			return talentpoolSearchengine.getTalentUserList(params);
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public Response searchpastPosition(Map<String, String> params) throws BIZException, TException {
		try{
			SearchPast searchPast=this.convertParams(params);
			PastPOJO result=talentpoolSearchengine.searchPastPosition(searchPast);
			String res= JSON.toJSONString(result,serializeConfig, SerializerFeature.DisableCircularReferenceDetect);
			Response respose=ResponseUtils.successWithoutStringify(res);
			return respose;
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
	}
	/*
	 转换参数
	 */
	private SearchPast convertParams(Map<String, String> params){
		String response= JSON.toJSONString(StringUtils.convertUnderKeyToCamel(params));
		logger.info(response);
		SearchPast paramsPOJO=JSON.parseObject(response,SearchPast.class);
		return paramsPOJO;
	}

	@Override
	public Response searchpastCompany(Map<String, String> params) throws BIZException, TException {
		try{
			SearchPast searchPast=this.convertParams(params);
			PastPOJO result=talentpoolSearchengine.searchPastCompany(searchPast);
			String res= JSON.toJSONString(result,serializeConfig, SerializerFeature.DisableCircularReferenceDetect);
			Response respose=ResponseUtils.successWithoutStringify(res);
			return respose;
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public Response mobotSearchPosition(Map<String, String> params) throws BIZException, TException {
		try{
			MeetBotResult result=service.mobotSearchPosition(params);
			Response respose=ResponseUtils.success(result);
			return respose;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw ExceptionUtils.convertException(e);
		}
	}



	@Override
	public Response userQueryById(List<Integer> userIdlist) throws BIZException,TException {
		try{
			Map<String,Object> res=talentpoolSearchengine.getEsDataByUserIds(userIdlist);
			if(res==null||res.isEmpty()){
				return ResponseUtils.success("");
			}
			return ResponseUtils.success(res);
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public int talentSearchNum(Map<String, String> params) throws BIZException,TException {
		try{
			int res=talentpoolSearchengine.talentSearchNum(params);
			return res;
		}catch(Exception e){

			throw ExceptionUtils.convertException(e);
		}
	}




}
