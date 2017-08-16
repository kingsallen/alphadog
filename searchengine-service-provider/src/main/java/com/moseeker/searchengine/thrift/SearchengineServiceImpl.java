package com.moseeker.searchengine.thrift;

import java.util.Map;
import java.util.List;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.searchengine.service.impl.CompanySearchengine;
import com.moseeker.searchengine.service.impl.PositionSearchEngine;
import com.moseeker.searchengine.service.impl.SearchengineService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices.Iface;

@Service
public class SearchengineServiceImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SearchengineService service;

    @Autowired
    private CompanySearchengine companySearchengine;

    @Autowired
    private PositionSearchEngine positionSearchEngine;
    

    @Override
    public Response query(String keywords, String cities, String industries, String occupations, String scale,
                          String employment_type, String candidate_source, String experience, String degree, String salary,
                          String company_name, int page_from, int page_size, String child_company_name, String department, boolean order_by_priority, String custom) throws TException {
        return service.query(keywords, cities, industries, occupations, scale, employment_type, candidate_source, experience, degree, salary, company_name, page_from, page_size, child_company_name, department, order_by_priority, custom);
    }

    @Override
    public Response updateposition(String position, int id) throws TException {
        return service.updateposition(position, id);
    }

    @Override
    public Response queryAwardRanking(List<Integer> employeeIds, String timespan, int pageSize, int pageNum, String keyword, int filter) throws TException {
        return service.queryAwardRanking(employeeIds, timespan, pageSize, pageNum, keyword, filter);
    }

    @Override
    public Response queryAwardRankingInWx(List<Integer> companyIds, String timespan, int employeeId) throws TException {
        return service.queryAwardRankingInWx(companyIds, timespan, employeeId);
    }

	@Override
	public Response companyQuery(String keyWords, String citys, String industry, String scale, int page, int pageSize){
		// TODO Auto-generated method stub
		try{
			Map<String,Object> res=companySearchengine.query(keyWords, citys, industry, scale, page, pageSize);
			if(res==null){
				return ResponseUtils.success("");
			}
			return ResponseUtils.success(res);
		}catch(Exception e){
			logger.info(e.getMessage(),e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
		}
		
	}

	@Override
	public Response positionQuery(String keyWords, String citys, String industry, String salaryCode, int page,
			int pageSize, String startTime, String endTime,int order) throws TException {
		// TODO Auto-generated method stub
		try{
			Map<String,Object> res=positionSearchEngine.search(keyWords, industry, salaryCode, page, pageSize, citys, startTime, endTime,order);
			if(res==null){
				return ResponseUtils.success("");
			}
			return ResponseUtils.success(res);
		}catch(Exception e){
			logger.info(e.getMessage(),e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
		}
	}

    @Override
    public Response updateEmployeeAwards(List<Integer> employeeIds) throws TException {
        return service.updateEmployeeAwards(employeeIds);
    }

    @Override
    public Response deleteEmployeeDO(List<Integer> employeeIds) throws TException {
        return service.deleteEmployeeDO(employeeIds);
    }

}
