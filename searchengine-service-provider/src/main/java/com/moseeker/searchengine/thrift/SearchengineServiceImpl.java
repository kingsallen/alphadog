package com.moseeker.searchengine.thrift;

import org.apache.thrift.TException;
import org.elasticsearch.action.search.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.searchengine.service.impl.CompanySearchengine;
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
    
    @Override
    public Response query(String keywords, String cities, String industries, String occupations, String scale,
            String employment_type, String candidate_source, String experience, String degree, String salary,
            String company_name, int page_from, int page_size,String child_company_name,String department,boolean order_by_priority,String custom) throws TException {
        return service.query(keywords, cities, industries, occupations, scale, employment_type, candidate_source, experience, degree, salary, company_name, page_from, page_size, child_company_name, department, order_by_priority, custom);
    }
    
    @Override
    public Response updateposition(String position,int  id) throws TException {
       return service.updateposition(position, id);
    }

	@Override
	public Response companyQuery(String keyWords, String citys, String industry, String scale, int page, int pageSize){
		// TODO Auto-generated method stub
		try{
			SearchResponse res=companySearchengine.queryString(keyWords, citys, industry, scale, page, pageSize);
			return ResponseUtils.success(res);
		}catch(Exception e){
			logger.info(e.getMessage(),e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
		}
		
	}

}
