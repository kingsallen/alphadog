package com.moseeker.servicemanager.web.controller.searchengine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.CleanJsonResponse4Alphacloud;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CounterIface
public class SearchengineController {

    Logger logger = LoggerFactory.getLogger(SearchengineController.class);

    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICE_MANAGER
            .getService(SearchengineServices.Iface.class);

    PositionServices.Iface positonServices = ServiceManager.SERVICE_MANAGER.
            getService(PositionServices.Iface.class);

    CompanyServices.Iface companyServices = ServiceManager.SERVICE_MANAGER.
            getService(CompanyServices.Iface.class);

    @RequestMapping(value = "/search/update", method = RequestMethod.POST)
    @ResponseBody
    public String updatePosition(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> reqParams = null;
        String position = "";
        Response search_res = null;
        try {
            reqParams = ParamUtils.parseRequestParam(request);
            Integer id = BeanUtils.converToInteger(reqParams.get("id"));
            Response result = positonServices.getPositionById(id);
            position = result.data;
            Map position_map = JSON.parseObject(position, Map.class);

            String company_id = BeanUtils.converToString(position_map.get("company_id"));
            CommonQuery query = new CommonQuery();
            query.setEqualFilter(new HashMap<String, String>() {{
                put("id", company_id);
            }});
            Response company_resp = companyServices.getAllCompanies(query);
            String company = company_resp.data;
            logger.info("======" + company);
            if (StringUtils.isNotNullOrEmpty(company) && company.startsWith("[")) {
                List company_maps = JSON.parseObject(company, List.class);
                Map company_map = (Map) company_maps.get(0);
                String company_name = (String) company_map.get("name");
                String scale = (String) company_map.get("scale");
                position_map.put("company_name", company_name);
                String degree_name = BeanUtils.converToString(position_map.get("degree_name"));
                Integer degree_above = BeanUtils.converToInteger(position_map.get("degree_above"));
                if (degree_above == 1) {
                    degree_name = degree_name + "及以上";
                }
                position_map.put("degree_name", degree_name);
                position_map.put("scale", scale);

            }

            position = JSON.toJSONString(position_map);
            logger.info(position);
            search_res = searchengineServices.updateposition(position, id);


        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }

        return ResponseLogNotification.success(request, search_res);
    }

    @RequestMapping(value = "/search/position", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String searchPCPosition(HttpServletRequest request, HttpServletResponse response) {

        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            logger.info(JSON.toJSONString(reqParams) + "=============");
            String keywords = StringUtils.filterStringForSearch(BeanUtils.converToString(reqParams.get("keywords")));
            String cities = BeanUtils.converToString(reqParams.get("cities"));
            String industries = StringUtils.filterStringForSearch(BeanUtils.converToString(reqParams.get("industries")));
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
                    child_company_id, department, order_by_priority, custom, "=============");
            Response result = searchengineServices.query(keywords, cities, industries, occupations, scale,
                    employment_type, candidate_source, experience, degree, salary, company_id, page_from, page_size,
                    child_company_id, department, order_by_priority, custom,null);
            logger.info(keywords, cities, industries, occupations, scale,
                    employment_type, candidate_source, experience, degree, salary, company_id, page_from, page_size,
                    child_company_id, department, order_by_priority, custom, "=============");
            if (result.getStatus() == 0) {
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, result);
            }

        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/search/updateByCompanyId", method = RequestMethod.POST)
    @ResponseBody
    public String updatePositionByCompanyId(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> reqParams = null;
        Response result = new Response();
        ;
        try {
            reqParams = ParamUtils.parseRequestParam(request);
            Integer company_id = BeanUtils.converToInteger(reqParams.get("company_id"));
            Response es_result = searchengineServices.query(null, null, null, null, null,
                    null, null, null, null, null, company_id + "", 0, 1000,
                    null, null, false, null,null);
            if (es_result.getStatus() == 0 && StringUtils.isNotNullOrEmpty(es_result.getData())) {
                JSONObject es_data = JSON.parseObject(es_result.getData());
                List<String> position_id_list = (List<String>) es_data.get("jd_id_list");
                String company = "";
                try {
                    CommonQuery query = new CommonQuery();
                    query.setEqualFilter(new HashMap<String, String>() {{
                        put("id", company_id + "");
                    }});
                    Response company_resp = companyServices.getAllCompanies(query);
                    company = company_resp.data;
                } catch (Exception e) {
                    logger.info(e.getMessage(), e);
                }
                for (String position_id : position_id_list) {
                    String position = this.getJobPosition(Integer.parseInt(position_id), company);
                    if (StringUtils.isNotNullOrEmpty(position)) {
                        searchengineServices.updateposition(position, Integer.parseInt(position_id));
                    }
                    Thread.currentThread().sleep(600);
                }
                result = ResponseUtils.success("");
            } else {
                result = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }

        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }

        return ResponseLogNotification.success(request, result);
    }

    private String getJobPosition(int id, String company) throws Exception {
        String position = "";
        try {
            Response result = positonServices.getPositionById(id);
            position = result.data;
            Map position_map = (Map) JSON.parse(position);
            if (position_map == null || position_map.isEmpty()) {
                return position;
            }
            if (StringUtils.isNotNullOrEmpty(company)) {
                List company_maps = (List) JSON.parse(company);
                Map company_map = (Map) company_maps.get(0);
                String company_name = (String) company_map.get("name");
                String scale = (String) company_map.get("scale");
                position_map.put("company_name", company_name);
                String degree_name = BeanUtils.converToString(position_map.get("degree_name"));
                Integer degree_above = BeanUtils.converToInteger(position_map.get("degree_above"));
                if (degree_above == 1) {
                    degree_name = degree_name + "及以上";
                }
                position_map.put("degree_name", degree_name);
                position_map.put("scale", scale);
            }
            position = JSON.toJSONString(position_map);
            return position;
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return null;
    }

    //pc端企业搜索的es
    @RequestMapping(value = "/search/company", method = RequestMethod.GET)
    @ResponseBody
    public String searchCompany(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> reqParams = null;
        try {
            reqParams = ParamUtils.parseRequestParam(request);
            String keyWord = StringUtils.filterStringForSearch((String) reqParams.get("keyWord"));
            String citys = StringUtils.filterStringForSearch((String) reqParams.get("citys"));
            String industry = (String) reqParams.get("industry");
            String scale = (String) reqParams.get("scale");
            String page = (String) reqParams.get("page");
            String pageSize = (String) reqParams.get("pageSize");
            if (keyWord == null) {
                keyWord = "";
            }
            if (citys == null) {
                citys = "";
            }
            if (industry == null) {
                industry = "";
            }
            if (scale == null) {
                scale = "";
            }
            if (StringUtils.isNullOrEmpty(page)) {
                page = "1";
            }
            if (StringUtils.isNullOrEmpty(pageSize)) {
                pageSize = "10";
            }
            logger.info("======keyWord={},citys={},industry={},scale={},page={},pageSize={},pageSize={},order={}============", keyWord, citys, industry, scale, page, pageSize);
            Response res = searchengineServices.companyQuery(keyWord, citys, industry, scale, Integer.parseInt(page), Integer.parseInt(pageSize));
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }

    }

    //pc端企业搜索的es
    @RequestMapping(value = "/search/pc/position", method = RequestMethod.GET)
    @ResponseBody
    public String searchPosition(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> reqParams = null;
        try {
            reqParams = ParamUtils.parseRequestParam(request);
            String keyWord = StringUtils.filterStringForSearch((String) reqParams.get("keyWord"));
            String citys = StringUtils.filterStringForSearch((String) reqParams.get("citys"));
            String industry = StringUtils.filterStringForSearch((String) reqParams.get("industry"));
            String scale = (String) reqParams.get("scale");
            String page = (String) reqParams.get("page");
            String pageSize = (String) reqParams.get("pageSize");
            String salaryBottom = (String) reqParams.get("salaryBottom");
            String salaryTop = (String) reqParams.get("salaryTop");
            String startTime = (String) reqParams.get("startTime");
            String endTime = (String) reqParams.get("endTime");
            String order = (String) reqParams.get("order");
            String companyId = (String) reqParams.get("companyId");
            String teamId = (String) reqParams.get("teamId");
            String motherCompanyId = (String) reqParams.get("motherCompanyId");
            String moduleId = (String) reqParams.get("moduleId");
            String candidateSource = (String) reqParams.get("candidateSource");
            if (companyId == null) {
                companyId = "0";
            }
            if (teamId == null) {
                teamId = "0";
            }
            if (motherCompanyId == null) {
                motherCompanyId = "0";
            }
            if (order == null) {
                order = "0";
            }
            if (StringUtils.isNullOrEmpty(page)) {
                page = "1";
            }
            if (StringUtils.isNullOrEmpty(pageSize)) {
                pageSize = "10";
            }
            if (StringUtils.isNullOrEmpty(moduleId)) {
                moduleId = "0";
            }
            Map<String, Integer> map = new HashMap<>();
            if (StringUtils.isNotNullOrEmpty(salaryTop) && !"0".equals(salaryTop)) {
                map.put("salaryTop", Integer.parseInt(salaryTop));
            }
            if (StringUtils.isNotNullOrEmpty(salaryBottom) && !"0".equals(salaryBottom)) {
                map.put("salaryBottom", Integer.parseInt(salaryBottom));
            }
            List<Map<String, Integer>> salary = null;
            if (map != null && !map.isEmpty()) {
                salary = new ArrayList<>();
                salary.add(map);
            }
            String salaryCode = null;
            if (salary != null) {
                salaryCode = JSON.toJSONString(salary);
            }
            if (StringUtils.isNullOrEmpty(candidateSource)) {
                candidateSource = "-1";
            }
            logger.info(keyWord, citys, industry, scale, page,
                    pageSize, companyId, teamId, salaryCode, "=============");
            Response res = searchengineServices.positionQuery(keyWord, citys, industry, salaryCode, Integer.parseInt(page),
                    Integer.parseInt(pageSize), startTime, endTime, Integer.parseInt(companyId),
                    Integer.parseInt(teamId), Integer.parseInt(motherCompanyId), Integer.parseInt(order), Integer.parseInt(moduleId),
                    candidateSource
            );
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    //pc端企业搜索的es
    @RequestMapping(value = "/api/talentpool/search", method = RequestMethod.POST)
    @ResponseBody
    public String searchUsers(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail(request, "参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                params.put(key, String.valueOf(reqParams.get(key)));
            }

            //选中内推职位的特殊处理，前端position_status传1，必须将它改回成0,设置is_referral=1
            //TODO 重构整改
            String position_status = params.get("position_status");
            if (position_status.equals("1")) {
                params.put("is_referral", "1");
                params.put("position_status", "0");
            }
            Response res = searchengineServices.userQuery(params);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    //pc端企业搜索的es
    @RequestMapping(value = "/api/talentpool/agginfo", method = RequestMethod.POST)
    @ResponseBody
    public String searchUsersAggInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail(request, "参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                params.put(key, String.valueOf(reqParams.get(key)));
            }
            Response res = searchengineServices.userAggInfo(params);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    //微信端职位index查询
    @RequestMapping(value = "/api/position/suggest", method = RequestMethod.POST)
    @ResponseBody
    public String searchPositionSuggest(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail(request, "参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                if ("flag".equals(key)) {
                    params.put(key, String.valueOf(reqParams.get(key)));
                } else {
                    params.put(key, StringUtils.filterStringForSearch(String.valueOf(reqParams.get(key))));
                }

            }
            Response res = searchengineServices.searchPositionSuggest(params);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/api/profile/pastposition", method = RequestMethod.GET)
    @ResponseBody
    public String searchProfilePosition(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail(request, "参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                params.put(key, StringUtils.filterStringForSearch((String) reqParams.get(key)));
            }
            Response res = searchengineServices.searchpastPosition(params);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/api/profile/pastcompany", method = RequestMethod.GET)
    @ResponseBody
    public String searchProfileCompany(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail(request, "参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                params.put(key, StringUtils.filterStringForSearch((String) reqParams.get(key)));
            }
            Response res = searchengineServices.searchpastCompany(params);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/api/mobot/search", method = RequestMethod.GET)
    @ResponseBody
    public String searchMobotPosition(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail(request, "参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                params.put(key, StringUtils.filterStringForSearch((String) reqParams.get(key)));
            }
            Response res = searchengineServices.mobotSearchPosition(params);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }


    /**********************--add by lcf @2018/11/12--*************************/
    /**
     * 查询企业标签的人才数量
     *
     * @return CleanJsonResponse4Alphacloud
     */
    @RequestMapping(value = "/v4/searchengine/talentSearchNum", method = RequestMethod.POST)
    @ResponseBody
    public CleanJsonResponse4Alphacloud talentSearchNum(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail4Alphacloud("参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                params.put(key, StringUtils.filterStringForSearch((String) reqParams.get(key)));
            }
            int num = searchengineServices.talentSearchNum(params);
            return ResponseLogNotification.success4Alphacloud(num);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return ResponseLogNotification.fail4Alphacloud(e.getMessage());
        }
    }

    /**
     * 获取企业标签的人才库用户id集合
     *
     * @return CleanJsonResponse4Alphacloud
     */
    @RequestMapping(value = "/v4/searchengine/getTalentUserIdList", method = RequestMethod.POST)
    @ResponseBody
    public CleanJsonResponse4Alphacloud getTalentUserIdList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail4Alphacloud("参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                params.put(key, StringUtils.filterStringForSearch((String) reqParams.get(key)));
            }
            List<Integer> talentUserIdList = searchengineServices.getTalentUserIdList(params);
            return ResponseLogNotification.success4Alphacloud(talentUserIdList);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return ResponseLogNotification.fail4Alphacloud(e.getMessage());
        }
    }

    /**
     * queryCompanyTagUserIdListCount
     *
     * @return CleanJsonResponse4Alphacloud
     */
    @RequestMapping(value = "/v4/searchengine/queryCompanyTagUserIdListCount", method = RequestMethod.POST)
    @ResponseBody
    public CleanJsonResponse4Alphacloud queryCompanyTagUserIdListCount(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail4Alphacloud("参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                params.put(key, StringUtils.filterStringForSearch((String) reqParams.get(key)));
            }
            int count = searchengineServices.queryCompanyTagUserIdListCount(params);
            return ResponseLogNotification.success4Alphacloud(count);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return ResponseLogNotification.fail4Alphacloud(e.getMessage());
        }
    }

    /**
     * queryProfileFilterUserIdList
     *
     * @return CleanJsonResponse4Alphacloud
     */
    @RequestMapping(value = "/v4/searchengine/queryProfileFilterUserIdList", method = RequestMethod.POST)
    @ResponseBody
    public CleanJsonResponse4Alphacloud queryProfileFilterUserIdList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail4Alphacloud("参数不能为空");
            }
            //解析请求体参数
            List<Map<String, String>> filterMapList = (List<Map<String, String>>) reqParams.get("filter_map_list");
            Integer pageNumber = BeanUtils.converToInteger(reqParams.get("page_number"));
            Integer pageSize = BeanUtils.converToInteger(reqParams.get("page_size"));

            Response res = searchengineServices.queryProfileFilterUserIdList(filterMapList, pageNumber, pageSize);
            return ResponseLogNotification.success4Alphacloud(JSON.parse(res.getData()));
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return ResponseLogNotification.fail4Alphacloud(e.getMessage());
        }
    }

    /**
     * queryCompanyTagUserIdList
     *
     * @return CleanJsonResponse4Alphacloud
     */
    @RequestMapping(value = "/v4/searchengine/queryCompanyTagUserIdList", method = RequestMethod.POST)
    @ResponseBody
    public CleanJsonResponse4Alphacloud queryCompanyTagUserIdList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail4Alphacloud("参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                params.put(key, StringUtils.filterStringForSearch((String) reqParams.get(key)));
            }
            List<Integer> res = searchengineServices.queryCompanyTagUserIdList(params);
            return ResponseLogNotification.success4Alphacloud(res);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return ResponseLogNotification.fail4Alphacloud(e.getMessage());
        }
    }


    /**
     * userQuery
     *
     * @return CleanJsonResponse4Alphacloud
     */
    @RequestMapping(value = "/v4/searchengine/userQuery", method = RequestMethod.POST)
    @ResponseBody
    public CleanJsonResponse4Alphacloud userQuery(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail4Alphacloud("参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                params.put(key, StringUtils.filterStringForSearch((String) reqParams.get(key)));
            }

            Response res = searchengineServices.userQuery(params);
            return ResponseLogNotification.success4Alphacloud(JSON.parse(res.getData()));
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return ResponseLogNotification.fail4Alphacloud(e.getMessage());
        }
    }

    /**
     * userQueryById
     *
     * @return CleanJsonResponse4Alphacloud
     */
    @RequestMapping(value = "/v4/searchengine/userQueryById", method = RequestMethod.POST)
    @ResponseBody
    public CleanJsonResponse4Alphacloud userQueryById(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail4Alphacloud("参数不能为空");
            }
            List<Integer> userIdList = (List<Integer>) reqParams.get("userIdList");

            Response res = searchengineServices.userQueryById(userIdList);
            return ResponseLogNotification.success4Alphacloud(JSON.parse(res.getData()));
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return ResponseLogNotification.fail4Alphacloud(e.getMessage());
        }
    }

    /**
     * 获取企业标签的人才数量
     *
     * @return CleanJsonResponse4Alphacloud
     */
    /*@RequestMapping(value = "/v4/searchengine/talentSearchNum/companyTag", method = RequestMethod.PUT)
    @ResponseBody
    public CleanJsonResponse4Alphacloud talentSearchNumByCompanyTag(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail4Alphacloud("参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                params.put(key, StringUtils.filterStringForSearch((String) reqParams.get(key)));
            }

            flag 1
            int data = searchengineServices.talentSearchNum(params);
            return ResponseLogNotification.success4Alphacloud(data);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return ResponseLogNotification.fail4Alphacloud(e.getMessage());
        }
    }*/

    /*@RequestMapping(value = "/v4/searchengine/updateCompanyTagByIdAndType", method = RequestMethod.PUT)
    @ResponseBody
    public CleanJsonResponse4Alphacloud updateCompanyTagByIdAndType(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail4Alphacloud("参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                params.put(key, StringUtils.filterStringForSearch((String) reqParams.get(key)));
            }
            talentPoolEntity.updateCompanyTagByIdAndType(params);
            return ResponseLogNotification.success4Alphacloud(1);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return ResponseLogNotification.fail4Alphacloud(e.getMessage());
        }
    }*/


    //获取企业标签的人才数量
//    @RequestMapping(value = "/v4/searchengine/talentSearchNum/companyTag", method = RequestMethod.PUT)
//    ResultObject<Integer> talentSearchNumByCompanyTag(@RequestBody SearchTalentNumVO vo);
//
//
//    @RequestMapping(value = "/v4/searchengine/updateCompanyTagByIdAndType", method = RequestMethod.PUT)
//    Result updateCompanyTagByIdAndType(@RequestBody EsUpdateCompanyTagAndTypeVO vo  );
    @RequestMapping(value = "/api/position/mini", method = RequestMethod.GET)
    @ResponseBody
    public String positionMiniQuery(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
            Map<String, String> params = new HashMap<>();
            if (reqParams == null || reqParams.isEmpty()) {
                return ResponseLogNotification.fail(request, "参数不能为空");
            }
            for (String key : reqParams.keySet()) {
                params.put(key, StringUtils.filterStringForSearch((String) reqParams.get(key)));
            }
            Response res = searchengineServices.queryPositionMini(params);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }
}
