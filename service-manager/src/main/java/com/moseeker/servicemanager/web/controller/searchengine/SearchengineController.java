package com.moseeker.servicemanager.web.controller.searchengine;

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

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;

@Controller
public class SearchengineController {

    Logger logger = LoggerFactory.getLogger(SearchengineController.class);

    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICEMANAGER
            .getService(SearchengineServices.Iface.class);

    PositionServices.Iface positonServices = ServiceManager.SERVICEMANAGER.
            getService(PositionServices.Iface.class);

    CompanyServices.Iface companyServices = ServiceManager.SERVICEMANAGER.
            getService(CompanyServices.Iface.class);

    @RequestMapping(value = "/search/update", method = RequestMethod.GET, produces = "application/json;charset=utf-8")

    @ResponseBody
    public String update_position(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> reqParams = null;
        String position = "";
        Response search_res=null;
        try {
            reqParams = ParamUtils.mergeRequestParameters(request);
            Integer id = BeanUtils.converToInteger(reqParams.get("id"));
            Response result = positonServices.getPositionById(id);
            position = result.data;
            Map position_map = (Map) JSON.parse(position);
//            System.out.println(position_map);

            String company_id = BeanUtils.converToString(position_map.get("company_id"));
            String publisher_company_id = BeanUtils.converToString(position_map.get("publisher_company_id"));
//            System.out.println(company_id);
//            System.out.println(publisher_company_id);
            
            CommonQuery query = new CommonQuery();
            query.putToEqualFilter("id", company_id);
            Response company_resp = companyServices.getAllCompanies(query);
            String company = company_resp.data;
            List company_maps = (List) JSON.parse(company);
            Map company_map = (Map) company_maps.get(0);
            String company_name = (String) company_map.get("name");
            String scale = (String) company_map.get("scale");
            position_map.put("company_name",company_name);
            position_map.put("scale",scale);
            
            if (!publisher_company_id.equals("0")){
                CommonQuery query_child = new CommonQuery();
                query_child.putToEqualFilter("id", publisher_company_id);
                Response company_resp_child = companyServices.getAllCompanies(query_child);
                String company_child = company_resp_child.data;
                List company_maps_child = (List) JSON.parse(company_child);
                Map company_map_child = (Map) company_maps_child.get(0);
                String child_company_name = (String) company_map_child.get("name");
                position_map.put("child_company_name", child_company_name );
            }
            
            position = JSON.toJSONString(position_map);
//            System.out.println(position);
            
            
            // Response update_result =
             search_res = searchengineServices.updateposition(position,id);
            // System.out.println(position);
             
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println(e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
        
        return ResponseLogNotification.success(request, search_res);
    }

    @RequestMapping(value = "/search/position", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String search_position(HttpServletRequest request, HttpServletResponse response) {

        try {
            Map<String, Object> reqParams = ParamUtils.mergeRequestParameters(request);
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
            String company_name = BeanUtils.converToString(reqParams.get("company_name"));
            int page_from = BeanUtils.converToInteger(reqParams.get("page_from"));
            int page_size = BeanUtils.converToInteger(reqParams.get("page_size"));
            String child_company_name = BeanUtils.converToString(reqParams.get("child_company_name"));
            String department = BeanUtils.converToString(reqParams.get("department"));
            
            Response result = searchengineServices.query(keywords, cities, industries, occupations, scale,
                    employment_type, candidate_source, experience, degree, salary, company_name, page_from, page_size,
                    child_company_name,department);
//            System.out.println(result.getStatus());
            if (result.getStatus() == 0) {
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, result);
            }

        } catch (Exception e) {
            // System.out.println("not even searched" );
            // System.out.println(e );
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

}
