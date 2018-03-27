package com.moseeker.function.service.chaos.position;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.common.constants.WorkType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.function.service.chaos.base.AbstractPositionEmailBuilder;
import com.moseeker.function.service.chaos.base.PositionEmailBuilder;
import com.moseeker.function.service.chaos.util.PositionSyncMailUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyJobsDBPositionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JobsDBPositionEmailBuilder extends AbstractPositionEmailBuilder<ThirdpartyJobsDBPositionDO> {
    @Autowired
    PositionSyncMailUtil positionSyncMailUtil;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Override
    public String message(JobPositionDO moseekerPosition, HrThirdPartyPositionDO thirdPartyPosition, ThirdpartyJobsDBPositionDO position) throws BIZException {
        EmailBodyBuilder emailMessgeBuilder = new EmailBodyBuilder();

        emailMessgeBuilder.name("【Job title】").value(moseekerPosition.getTitle());
        emailMessgeBuilder.name("【Job details】").value(getDetail(moseekerPosition));
        emailMessgeBuilder.name("【Summary 1】").value(position.getSummary1());
        emailMessgeBuilder.name("【Summary 2】").value(position.getSummary2());
        emailMessgeBuilder.name("【Summary 3】").value(position.getSummary3());
        emailMessgeBuilder.name("【Job functions 1】").value(positionSyncMailUtil.getOccupation(getChannelType().getValue(),thirdPartyPosition.getOccupation()));
        emailMessgeBuilder.name("【Job functions 2】").value(positionSyncMailUtil.getOccupation(getChannelType().getValue(),position.getOccupationExt1()));
        emailMessgeBuilder.name("【Job functions 3】").value(positionSyncMailUtil.getOccupation(getChannelType().getValue(),position.getOccupationExt2()));


        emailMessgeBuilder.name("【Work location】").value(thirdPartyPosition.getAddressName());
        emailMessgeBuilder.name("【Sub work location】").value(position.getChildAddressName());
        emailMessgeBuilder.name("【Employment type】").value(WorkType.instanceFromInt((int)moseekerPosition.getEmploymentType()).getName());
        emailMessgeBuilder.name("【Salary details (Monthly)】：").value(getSalary(thirdPartyPosition.getSalaryBottom(),thirdPartyPosition.getSalaryTop()));

        emailMessgeBuilder.line(email(moseekerPosition));

        return emailMessgeBuilder.toString();
    }

    public String getDetail(JobPositionDO moseekerPosition){
        String accounTabilities = moseekerPosition.getAccountabilities();
        String requirement = moseekerPosition.getRequirement();

        StringBuffer descript = new StringBuffer();
        if (StringUtils.isNotNullOrEmpty(accounTabilities)) {
            descript.append("Job description and responsibilities:\n")
                    .append(accounTabilities);
        }
        if (StringUtils.isNotNullOrEmpty(requirement)) {
            descript.append("\n\nJob requirements:\n").append(requirement);
        }
        return descript.toString();
    }

    public String getSalary(int bottom,int top){
        String json = redisClient.get(RefreshConstant.APP_ID, KeyIdentifier.THIRD_PARTY_ENVIRON_PARAM.toString(),String.valueOf(getChannelType().getValue()));

        JSONObject obj = JSON.parseObject(json);

        TypeReference<List<Salary>> typeRef = new TypeReference<List<Salary>>(){};

        List<Salary> array = JSON.parseObject(obj.getString("salary"),typeRef);

        for(Salary salary:array){
            if(salary.getSalary_bottom().code.equals(String.valueOf(bottom))){
                for(Salary.SalaryStruct salaryTop:salary.getSalary_top()){
                    if(salaryTop.getCode().equals(String.valueOf(top))){
                        return salary.getSalary_bottom().getText() + "-" + salaryTop.getText();
                    }
                }
            }
        }
        return "";
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOBSDB;
    }

    private static class Salary{
        private SalaryStruct salary_bottom;
        private List<SalaryStruct> salary_top;

        public SalaryStruct getSalary_bottom() {
            return salary_bottom;
        }

        public void setSalary_bottom(SalaryStruct salary_bottom) {
            this.salary_bottom = salary_bottom;
        }

        public List<SalaryStruct> getSalary_top() {
            return salary_top;
        }

        public void setSalary_top(List<SalaryStruct> salary_top) {
            this.salary_top = salary_top;
        }

        private static class SalaryStruct{
            private String code;
            private String text;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
