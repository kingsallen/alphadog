package com.moseeker.servicemanager.web.controller.position;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.StructSerializer;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.position.struct.BatchHandlerJobPostion;
import com.moseeker.thrift.gen.position.struct.City;
import com.moseeker.thrift.gen.position.struct.JobPostrionObj;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionParamUtils extends ParamUtils {

    @SuppressWarnings("unchecked")
    public static ThirdPartyPositionForm parseSyncParam(HttpServletRequest request) {
        ThirdPartyPositionForm form = new ThirdPartyPositionForm();
        try {
            HashMap<String, Object> data = parseRequestParam(request);
            form.setAppid((Integer) data.get("appid"));
            form.setPositionId((Integer) data.get("positionId"));
            List<Map<String,String>> cs = new ArrayList<>();
            List<HashMap<String, Object>> channels = (List<HashMap<String, Object>>) data.get("channels");
            if (channels != null) {
                channels.forEach(channel -> {
                    try {
                        cs.add(toExtThirdPartyPosition(channel));
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
            e.printStackTrace();
            LoggerFactory.getLogger(PositionParamUtils.class).error(e.getMessage(), e);
        }
        return form;
    }


    public static BatchHandlerJobPostion parseBatchHandlerJobPostionParam(HttpServletRequest request) throws Exception {
        BatchHandlerJobPostion batchHandlerDate = new BatchHandlerJobPostion();
        HashMap<String, Object> data = parseRequestParam(request);
        batchHandlerDate.setFields_nohash((String) data.get("fields_nohash"));
        batchHandlerDate.setFields_nooverwrite((String) data.get("fields_nooverwrite"));
        if (StringUtils.isEmptyObject(data.get("nodelete"))) {
            batchHandlerDate.setNodelete(false);
        } else {
            batchHandlerDate.setNodelete((Boolean) data.get("nodelete"));
        }
        List<JobPostrionObj> cs = new ArrayList<>();
        List<HashMap<String, Object>> datas = (List<HashMap<String, Object>>) data.get("data");
        if (datas != null) {
            datas.forEach(jobPostrionObj -> {
                try {
                    JobPostrionObj c = ParamUtils.initModelForm(jobPostrionObj, JobPostrionObj.class);
                    List<HashMap<String, Object>> citys = (List<HashMap<String, Object>>) jobPostrionObj.get("city");
                    if (citys != null) {
                        List<City> cities = new ArrayList<>();
                        citys.forEach(city -> {
                            try {
                                City cityTemp = ParamUtils.initModelForm(city, City.class);
                                cities.add(cityTemp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        c.setCity(cities);
                    }
                    cs.add(c);
                } catch (Exception e) {
                    e.printStackTrace();
                    LoggerFactory.getLogger(PositionParamUtils.class).error(e.getMessage(), e);
                } finally {
                    //do nothing
                }
            });
        }
        batchHandlerDate.setData(cs);
        return batchHandlerDate;
    }

    /**
     * 解析职位刷新参数
     */
    @SuppressWarnings("unchecked")
    public static List<HashMap<Integer, Integer>> parseRefreshParam(Params<String, Object> params) {

        List<HashMap<Integer, Integer>> paramList = new ArrayList<>();
        try {
            List<Map<String, Object>> positions = (List<Map<String, Object>>) params.get("params");
            if (positions != null && positions.size() > 0) {
                positions.forEach(position -> {
                    int positionId = (Integer) position.get("position_id");
                    List<Integer> channels = (List<Integer>) position.get("channels");
                    if (channels != null && channels.size() > 0) {
                        channels.forEach(channel -> {
                            HashMap<Integer, Integer> param = new HashMap<>();
                            param.put(positionId, channel);
                            paramList.add(param);
                        });
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
