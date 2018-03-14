package com.moseeker.position.service.position.jobsdb.refresh.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.position.service.position.base.refresh.handler.AbstractRedisResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JobsDBRedisResultHandler extends AbstractRedisResultHandler implements JobsDBResultHandlerAdapter {
    Logger logger= LoggerFactory.getLogger(JobsDBRedisResultHandler.class);

    private static final String EMPLOYEE_TYPE="employee_type";
    private static final String SALARY="salary";
    private static final String WORK_LOCATION="work_location";

    @Override
    protected String[] param() {
        return new String[]{"salary","work_location","employee_type"};
    }

    @Override
    protected void handle(JSONObject obj) {
        JSONObject result=new JSONObject();

        String[] params=param();
        if(params == null || params.length==0){
            logger.info("no refresh result to redis");
            return;
        }

        result.put(SALARY,obj.get(SALARY));
        result.put(EMPLOYEE_TYPE,obj.get(EMPLOYEE_TYPE));

        TypeReference<List<ChaosWorkLocation>> typeRef = new TypeReference<List<ChaosWorkLocation>>(){};

        List<ChaosWorkLocation> chaosWorkLocations = JSON.parseObject(obj.getString(WORK_LOCATION),typeRef);

        List<WorkLocationPojo> workLocationPojos = new ArrayList<>();

        for (ChaosWorkLocation chaosWorkLocation:chaosWorkLocations){
            WorkLocationPojo workLocationPojo=new WorkLocationPojo();


            WorkLocationPojo.WorkLocation workLocation=toWorkLocation(chaosWorkLocation);
            workLocationPojo.setWorkLocation(workLocation);

            List<WorkLocationPojo.ChildWorkLocation> childWorkLocations=toChildWorkLocation(chaosWorkLocation.getChildren());
            workLocationPojo.setChildWorkLocation(childWorkLocations);

            workLocationPojos.add(workLocationPojo);
        }


        result.put(WORK_LOCATION,obj.get(WORK_LOCATION));

        String json=result.toJSONString();
        logger.info("save refresh result to {} redis : {}",keyIdentifier(),json);
        redisClient.set(RefreshConstant.APP_ID,keyIdentifier(),"",json);
    }

    /**
     * 转换chaos的第一层工作地址
     * @param chaosWorkLocation chaos传过来的主工作地址
     * @return
     */
    private WorkLocationPojo.WorkLocation toWorkLocation(ChaosWorkLocation chaosWorkLocation){
        WorkLocationPojo.WorkLocation workLocation = new WorkLocationPojo.WorkLocation();
        workLocation.setCode(String.valueOf(chaosWorkLocation.getId()));
        workLocation.setText(chaosWorkLocation.getName());
        return workLocation;
    }

    /**
     * 转换chaos的第二层工作地址
     * @param childList
     * @return
     */
    private List<WorkLocationPojo.ChildWorkLocation> toChildWorkLocation(List<ChaosWorkLocation> childList){
        List<WorkLocationPojo.ChildWorkLocation> childWorkLocations=new ArrayList<>();

        for(ChaosWorkLocation childChaosWorkLocation:childList) {

            WorkLocationPojo.ChildWorkLocation childWorkLocation=new WorkLocationPojo.ChildWorkLocation();

            childWorkLocation.setCode(String.valueOf(childChaosWorkLocation.getId()));
            childWorkLocation.setText(childChaosWorkLocation.getName());

            childWorkLocations.add(childWorkLocation);
        }

        return childWorkLocations;
    }

    private static class ChaosWorkLocation{
        private int id;
        private String name;
        private List<ChaosWorkLocation> children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ChaosWorkLocation> getChildren() {
            return children;
        }

        public void setChildren(List<ChaosWorkLocation> children) {
            this.children = children;
        }
    }

    private static class WorkLocationPojo{
        private WorkLocation workLocation;
        private List<ChildWorkLocation> childWorkLocation;

        public WorkLocation getWorkLocation() {
            return workLocation;
        }

        public void setWorkLocation(WorkLocation workLocation) {
            this.workLocation = workLocation;
        }

        public List<ChildWorkLocation> getChildWorkLocation() {
            return childWorkLocation;
        }

        public void setChildWorkLocation(List<ChildWorkLocation> childWorkLocation) {
            this.childWorkLocation = childWorkLocation;
        }

        private static class ChildWorkLocation{
            private String text;
            private String code;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }
        private static class WorkLocation{
            private String text;
            private String code;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }

    }
}
