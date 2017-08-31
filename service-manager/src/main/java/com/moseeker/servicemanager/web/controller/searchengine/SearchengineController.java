package com.moseeker.servicemanager.web.controller.searchengine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CounterIface
public class SearchengineController {

    Logger logger = LoggerFactory.getLogger(SearchengineController.class);

    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICEMANAGER
            .getService(SearchengineServices.Iface.class);

    PositionServices.Iface positonServices = ServiceManager.SERVICEMANAGER.
            getService(PositionServices.Iface.class);

    CompanyServices.Iface companyServices = ServiceManager.SERVICEMANAGER.
            getService(CompanyServices.Iface.class);


    @RequestMapping(value = "/search/update", method = RequestMethod.POST)
    @ResponseBody
    public String update_position(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> reqParams = null;
        String position = "";
        Response search_res=null;
        try {
            reqParams = ParamUtils.parseRequestParam(request);
            Integer id = BeanUtils.converToInteger(reqParams.get("id"));
            Response result = positonServices.getPositionById(id);
            position = result.data;
            Map position_map =JSON.parseObject(position,Map.class);
            
            String company_id = BeanUtils.converToString(position_map.get("company_id"));
            CommonQuery query = new CommonQuery();
            query.setEqualFilter(new HashMap<String, String>(){{put("id", company_id);}});
            Response company_resp = companyServices.getAllCompanies(query);
            String company = company_resp.data;
            logger.info("======"+company);
            if(StringUtils.isNotNullOrEmpty(company)&&company.startsWith("[")){
            	 List company_maps = JSON.parseObject(company,List.class);
                 Map company_map = (Map) company_maps.get(0);
                 String company_name = (String) company_map.get("name");
                 String scale = (String) company_map.get("scale");
                 position_map.put("company_name",company_name);
                 String degree_name = BeanUtils.converToString(position_map.get("degree_name"));
                 Integer degree_above =BeanUtils.converToInteger(position_map.get("degree_above"));
                 if(degree_above==1){
                     degree_name = degree_name+"及以上";
                 }
                 position_map.put("degree_name",degree_name);
                 position_map.put("scale",scale);
                 
            }
           
            position = JSON.toJSONString(position_map);
            logger.info(position);
            search_res = searchengineServices.updateposition(position,id);

             
        } catch (Exception e) {

           e.printStackTrace();
           logger.error(e.getMessage(),e);
           return ResponseLogNotification.fail(request, e.getMessage());
        }
        
        return ResponseLogNotification.success(request, search_res);
    }

    @RequestMapping(value = "/search/position", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String search_position(HttpServletRequest request, HttpServletResponse response) {
    
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            logger.info(JSON.toJSONString(reqParams)+"=============");
            String keywords = BeanUtils.converToString(reqParams.get("keywords"));
            String cities = BeanUtils.converToString(reqParams.get("cities"));
            String industries = BeanUtils.converToString(reqParams.get("industries"));
            String occupations = BeanUtils.converToString(reqParams.get("occupations"));
            String scale = BeanUtils.converToString(reqParams.get("scale"));
            String employment_type = BeanUtils.converToString(reqParams.get("employment_type"));
            String candidate_source = BeanUtils.converToString(reqParams.get("candidate_source"));
            String experience = BeanUtils.converToString(reqParams.get("experience"));
            String degree = BeanUtils.converToString(reqParams.get("degree"));
            String salary = BeanUtils.converToString(reqParams.get("salary"));
            String company_id = BeanUtils.converToString(reqParams.get("company_id"));
            int page_from = BeanUtils.converToInteger(reqParams.get("page_from"));
            int page_size = BeanUtils.converToInteger(reqParams.get("page_size"));
            String child_company_id = BeanUtils.converToString(reqParams.get("child_company_id"));
            //由于department废弃，查询部门时使用team_name
            String department = BeanUtils.converToString(reqParams.get("team_name"));
            boolean order_by_priority = BeanUtils.convertToBoolean(reqParams.get("order_by_priority"));
            String custom = BeanUtils.converToString(reqParams.get("custom"));
            
            logger.info(keywords, cities, industries, occupations, scale,
                    employment_type, candidate_source, experience, degree, salary, company_id, page_from, page_size,
                    child_company_id,department, order_by_priority, custom,"=============");
            Response result = searchengineServices.query(keywords, cities, industries, occupations, scale,
                    employment_type, candidate_source, experience, degree, salary, company_id, page_from, page_size,
                    child_company_id,department, order_by_priority, custom);
            logger.info(keywords, cities, industries, occupations, scale,
                    employment_type, candidate_source, experience, degree, salary, company_id, page_from, page_size,
                    child_company_id,department, order_by_priority, custom,"=============");
            if (result.getStatus() == 0) {
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, result);
            }

        } catch (Exception e) {
        	logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    @RequestMapping(value = "/search/updateByCompanyId", method = RequestMethod.POST)
    @ResponseBody
    public String updatePositionByCompanyId(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> reqParams = null;
        Response result=new Response();;
        try {
            reqParams = ParamUtils.parseRequestParam(request);
            Integer company_id = BeanUtils.converToInteger(reqParams.get("company_id"));
            Response es_result = searchengineServices.query(null, null, null, null, null,
            		null, null, null, null, null, company_id+"", 0, 1000,
                    null,null, false, null);
            if(es_result.getStatus()==0&&StringUtils.isNotNullOrEmpty(es_result.getData())){
            	JSONObject es_data=JSON.parseObject(es_result.getData());
            	List<String> position_id_list=(List<String>) es_data.get("jd_id_list");
            	for(String position_id : position_id_list){
            		String position=this.getJobPosition(Integer.parseInt(position_id));
            		if(StringUtils.isNotNullOrEmpty(position)){
            			searchengineServices.updateposition(position,Integer.parseInt(position_id));	
            		}
            		Thread.currentThread().sleep(600);
            	} 	
            	result=ResponseUtils.success("");
            }else{
            	result=ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
             
        } catch (Exception e) {

           e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
        
        return ResponseLogNotification.success(request,result);
    }
    
    private String getJobPosition(int id) throws Exception{
        String position = "";
        try{
	    	  Response result = positonServices.getPositionById(id);
	          position = result.data;
	          Map position_map = (Map) JSON.parse(position);
	          
	          String company_id = BeanUtils.converToString(position_map.get("company_id"));
	          CommonQuery query = new CommonQuery();
	          query.setEqualFilter(new HashMap<String, String>(){{put("id", company_id);}});
	          Response company_resp = companyServices.getAllCompanies(query);
	          String company = company_resp.data;
	          List company_maps = (List) JSON.parse(company);
	          Map company_map = (Map) company_maps.get(0);
	          String company_name = (String) company_map.get("name");
	          String scale = (String) company_map.get("scale");
	          position_map.put("company_name",company_name);
	          String degree_name = BeanUtils.converToString(position_map.get("degree_name"));
	          Integer degree_above =BeanUtils.converToInteger(position_map.get("degree_above"));
	          if(degree_above==1){
	              degree_name = degree_name+"及以上";
	          }
	          position_map.put("degree_name",degree_name);
	          position_map.put("scale",scale);        
	          position = JSON.toJSONString(position_map);
	          return position;
        }catch(Exception e){
      	  logger.info(e.getMessage(),e);
        }
        return null;
  }
    
    //pc端企业搜索的es
    @RequestMapping(value = "/search/company", method = RequestMethod.GET)
    @ResponseBody
    public String searchCompany(HttpServletRequest request, HttpServletResponse response){
    	 Map<String, Object> reqParams = null;
    	 try{
    		 reqParams = ParamUtils.parseRequestParam(request);
    		 String keyWord=(String) reqParams.get("keyWord");
    		 String citys=(String) reqParams.get("citys");
    		 String industry=(String) reqParams.get("industry");
    		 String scale=(String) reqParams.get("scale");
    		 String page=(String) reqParams.get("page");
    		 String pageSize=(String) reqParams.get("pageSize");
    		 if(StringUtils.isNullOrEmpty(page)){
    			 page="1";
    		 }
    		 if(StringUtils.isNullOrEmpty(pageSize)){
    			 pageSize="10";
    		 }
    		  logger.info(keyWord, citys, industry, scale, page,
    				  pageSize,"=============");
    		 Response res=searchengineServices.companyQuery(keyWord,citys,industry,scale,Integer.parseInt(page), Integer.parseInt(pageSize));
    		 return ResponseLogNotification.success(request,res);
    	 }catch(Exception e){
    		 logger.info(e.getMessage(),e);
    		 return ResponseLogNotification.fail(request, e.getMessage());
    	 }
    	
    }
  //pc端企业搜索的es
    @RequestMapping(value = "/search/pc/position", method = RequestMethod.GET)
    @ResponseBody
    public String searchPosition(HttpServletRequest request, HttpServletResponse response){
    	 Map<String, Object> reqParams = null;
    	 try{
    		 reqParams = ParamUtils.parseRequestParam(request);
    		 String keyWord=(String) reqParams.get("keyWord");
    		 String citys=(String) reqParams.get("citys");
    		 String industry=(String) reqParams.get("industry");
    		 String scale=(String) reqParams.get("scale");
    		 String page=(String) reqParams.get("page");
    		 String pageSize=(String) reqParams.get("pageSize");
    		 String salaryCode=(String) reqParams.get("salaryCode");
    		 String startTime=(String) reqParams.get("startTime");
    		 String endTime=(String) reqParams.get("endTime");
             String order=(String) reqParams.get("order");
             String companyId=(String)reqParams.get("companyId");
             String teamId=(String)reqParams.get("teamId");
             String motherCompanyId=(String)reqParams.get("motherCompanyId");
             if(companyId==null){
                 companyId="0";
             }
             if(teamId==null){
                 teamId="0";
             }
             if(motherCompanyId==null){
                 motherCompanyId="0";
             }
             if(order==null){
                 order="0";
             }
             if(StringUtils.isNullOrEmpty(page)){
                 page="1";
             }
             if(StringUtils.isNullOrEmpty(pageSize)){
                 pageSize="10";
             }
             logger.info(keyWord, citys, industry, scale, page,
                     pageSize,"=============");
             Response res=searchengineServices.positionQuery(keyWord, citys, industry, salaryCode, Integer.parseInt(page),
                     Integer.parseInt(pageSize), startTime, endTime,Integer.parseInt(companyId),
                     Integer.parseInt(teamId),Integer.parseInt(motherCompanyId),Integer.parseInt(order));
             return ResponseLogNotification.success(request,res);
    	 }catch(Exception e){
    		 logger.info(e.getMessage(),e);
    		 return ResponseLogNotification.fail(request, e.getMessage());
    	 }
    }
}
