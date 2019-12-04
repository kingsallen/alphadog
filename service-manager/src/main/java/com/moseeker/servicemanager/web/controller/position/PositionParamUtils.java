package com.moseeker.servicemanager.web.controller.position;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.StructSerializer;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.position.struct.BatchHandlerJobPostion;
import com.moseeker.thrift.gen.position.struct.City;
import com.moseeker.thrift.gen.position.struct.JobPostrionObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionParamUtils extends ParamUtils {


    private static Logger logger = LoggerFactory.getLogger(PositionParamUtils.class);


    /**
     * 解析一键同步参数
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static ThirdPartyPositionForm parseSyncParam(HttpServletRequest request) {
        ThirdPartyPositionForm form = new ThirdPartyPositionForm();
        try {
            HashMap<String, Object> data = parseRequestParam(request);
            form.setAppid((Integer) data.get("appid"));
            form.setPositionId((Integer) data.get("positionId"));
            List<String> cs = new ArrayList<>();
            List<HashMap<String, Object>> channels = (List<HashMap<String, Object>>) data.get("channels");
            if (channels != null) {
                channels.forEach(channel -> {
                    try {
                        cs.add(JSON.toJSONString(channel));
                    } catch (Exception e) {
                        e.printStackTrace();
                        LoggerFactory.getLogger(PositionParamUtils.class).error(e.getMessage(), e);
                    } finally {
                        //do nothing
                    }
                });
            }
            form.setChannels(cs);
        } catch (Exception e) {
            LoggerFactory.getLogger(PositionParamUtils.class).error(e.getMessage(), e);
        }
        return form;
    }

    /**
     * 解析谷露同步参数
     * @param request
     * @return
     * @throws Exception
     */
    public static BatchHandlerJobPostion parseGlluePostionParam(HttpServletRequest request) throws Exception {
        HashMap<String, Object> data = parseRequestParam(request);

        logger.info("batchHandlerJobPostion param :{}",new JSONObject(data).toJSONString());

        BatchHandlerJobPostion batchHandlerDate = initBatchHandlerJobPostion(data);

        List<JobPostrionObj> cs = new ArrayList<>();
        HashMap<String, Object> jobPostrionObj = (HashMap<String, Object>)data.get("data");
        if (data != null && !data.isEmpty()) {
            try {
                JobPostrionObj c = ParamUtils.initModelForm(jobPostrionObj, JobPostrionObj.class);
                c.setCity(parseCitys(jobPostrionObj));
                cs.add(c);
            } catch (Exception e) {
                e.printStackTrace();
                LoggerFactory.getLogger(PositionParamUtils.class).error(e.getMessage(), e);
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
            }
        }else{
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        batchHandlerDate.setData(cs);
        return batchHandlerDate;
    }

    /**
     * 解析批量职位同步参数
     * @param request
     * @return
     * @throws Exception
     */
    public static BatchHandlerJobPostion parseBatchHandlerJobPostionParam(HttpServletRequest request) throws Exception {
        HashMap<String, Object> data = parseRequestParam(request);

        logger.info("batchHandlerJobPostion param :{}",new JSONObject(data).toJSONString());

        BatchHandlerJobPostion batchHandlerDate = initBatchHandlerJobPostion(data);

        List<JobPostrionObj> cs = new ArrayList<>();
        List<HashMap<String, Object>> datas = (List<HashMap<String, Object>>) data.get("data");
        if (datas != null) {
            datas.forEach(jobPostrionObj -> {
                try {
                    JobPostrionObj c = ParamUtils.initModelForm(jobPostrionObj, JobPostrionObj.class);
                    c.setCity(parseCitys(jobPostrionObj));
                    cs.add(c);
                } catch (Exception e) {
                    e.printStackTrace();
                    LoggerFactory.getLogger(PositionParamUtils.class).error(e.getMessage(), e);
                }
            });
        }
        batchHandlerDate.setData(cs);
        return batchHandlerDate;
    }

    public static BatchHandlerJobPostion parseSyncBatchHandlerJobPostionParam(HttpServletRequest request) throws Exception {
        HashMap<String, Object> data = parseRequestParam(request);

        BatchHandlerJobPostion batchHandlerDate = initBatchHandlerJobPostion(data);

        List<JobPostrionObj> cs = new ArrayList<>();
        List<HashMap<String, Object>> datas = (List<HashMap<String, Object>>) data.get("data");
        if (datas != null) {
            datas.forEach(temp -> {
                try {
                    logger.info("parseSyncBatchHandlerJobPostionParam For PlusPy : {}", temp);
                    Map<String, Object> jobPostrionObj = (Map<String, Object>) temp.get("position");
                    JobPostrionObj c = ParamUtils.initModelForm(jobPostrionObj, JobPostrionObj.class);
                    c.setThirdParty_position(JSON.toJSONString(temp.get("thirdParty_position")));
                    c.setCity(parseCitys(jobPostrionObj));
                    cs.add(c);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("parseSyncBatchHandlerJobPostionParam For PlusPy ParseError : {}, params : {}", e, temp);
                    LoggerFactory.getLogger(PositionParamUtils.class).error(e.getMessage(), e);
                } finally {
                    //do nothing
                }
            });
        }
        batchHandlerDate.setData(cs);
        return batchHandlerDate;
    }

    private static BatchHandlerJobPostion initBatchHandlerJobPostion(HashMap<String, Object> data){
        BatchHandlerJobPostion batchHandlerDate = new BatchHandlerJobPostion();
        batchHandlerDate.setFields_nohash((String) data.get("fields_nohash"));
        batchHandlerDate.setFields_nooverwrite((String) data.get("fields_nooverwrite"));
        if (StringUtils.isEmptyObject(data.get("nodelete"))) {
            batchHandlerDate.setNodelete(false);
        } else {
            batchHandlerDate.setNodelete((Boolean) data.get("nodelete"));
        }
        if (StringUtils.isEmptyObject(data.get("isCreateDepartment"))) {
            batchHandlerDate.setIsCreateDeparment(false);
        } else {
            batchHandlerDate.setIsCreateDeparment((Boolean) data.get("isCreateDepartment"));
        }
        return batchHandlerDate;
    }

    private static List<City> parseCitys(Map<String, Object> jobPostrionObj) {
        List<HashMap<String, Object>> citys = (List<HashMap<String, Object>>) jobPostrionObj.get("city");
        List<City> cities = new ArrayList<>();
        if (citys != null) {
            citys.forEach(city -> {
                try {
                    City cityTemp = ParamUtils.initModelForm(city, City.class);
                    cities.add(cityTemp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return cities;
    }

    @SuppressWarnings("unchecked")
    public static List<Integer> parseRefreshParamQX(Params<String, Object> params) {

        List<Integer> paramList = new ArrayList<Integer>();
        try {
            List<Map<String, Object>> positions = (List<Map<String, Object>>) params.get("params");
            if (positions != null && positions.size() > 0) {
                positions.forEach(position -> {
                    int positionId = (Integer) position.get("position_id");
                    if(positionId!=0){
                        paramList.add(positionId);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            LoggerFactory.getLogger(PositionParamUtils.class).error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        return paramList;
    }

    public static Map<String,String> toExtThirdPartyPosition(HashMap<String, Object> params) throws Exception {
        String json= JSON.toJSONString(params);
        TypeReference<HashMap<String,String>> typeRef
                = new TypeReference<HashMap<String,String>>() {};
        HashMap<String,String> result=JSON.parseObject(json,typeRef);

        return result;
    }

}
