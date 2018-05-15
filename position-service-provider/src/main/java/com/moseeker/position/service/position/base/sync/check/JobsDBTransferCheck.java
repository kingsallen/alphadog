package com.moseeker.position.service.position.base.sync.check;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.jobsdb.pojo.PositionJobsDBForm;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JobsDBTransferCheck extends AbstractTransferCheck<PositionJobsDBForm>{

    private static String ADDRESS_NOT_EMPTY = "地址不能为空!";

    private static String THREE_SUMMERY_REQUIRED = "必须有三条总结!";

    private static String THREE_SUMMERY_LENGTH_50 = "总结长度不能超过50!";

    private static String SALARY_NOT_EMPTY = "薪资不能为空!";

    private static String OCCUPATION_NOT_EMPTY = "职能不能为空!";

    private static String REQUIREMENT_REQUIRED_NO_EMAIL = "职位描述不能包含邮箱!";

    private static String ACCOUNTABILITIES_REQUIRED_NO_EMAIL = "任职条件不能包含邮箱!";

    private static String REGEX_EMAIL = "([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z]{2,6})";

    @Override
    public List<String> getError(PositionJobsDBForm positionJobsDBForm, JobPositionDO moseekerPosition) {
        List<String> errorMsg=new ArrayList<>();

        // 必须设置地址
        List<String> address=positionJobsDBForm.getAddress();
        if(StringUtils.isEmptyList(address)){
            errorMsg.add(ADDRESS_NOT_EMPTY);
        }

        // 必须有三条总结
        if(isSummaryEmpty(positionJobsDBForm.getSummary())){
            errorMsg.add(THREE_SUMMERY_REQUIRED);
        }

        if(isSummaryOutOfLength(positionJobsDBForm.getSummary())){
            errorMsg.add(THREE_SUMMERY_LENGTH_50);
        }

        // 薪资上下限必须都不为0
        if(positionJobsDBForm.getSalaryTop()==0 && positionJobsDBForm.getSalaryBottom()==0){
            errorMsg.add(SALARY_NOT_EMPTY);
        }

        // 职能必须有一条
        if(isOccupationEmpty(positionJobsDBForm.getOccupation())){
            errorMsg.add(OCCUPATION_NOT_EMPTY);
        }

        // 内容必须不包含邮箱
        Pattern p = Pattern.compile(REGEX_EMAIL);
        if(p.matcher(moseekerPosition.getAccountabilities()).find()){
            errorMsg.add(ACCOUNTABILITIES_REQUIRED_NO_EMAIL);
        }


        if(p.matcher(moseekerPosition.getRequirement()).find()){
            errorMsg.add(REQUIREMENT_REQUIRED_NO_EMAIL);
        }

        return errorMsg;
    }

    public boolean isOccupationEmpty(List<List<String>> occupation){
        return occupation==null || occupation.isEmpty()
                || (StringUtils.isEmptyList(occupation.get(0))
                && StringUtils.isEmptyList(occupation.get(1))
                & StringUtils.isEmptyList(occupation.get(2)));
    }

    public boolean isSummaryEmpty(List<String> summary){
        return summary==null || summary.size()!=3
                || StringUtils.isNullOrEmpty(summary.get(0))
                || StringUtils.isNullOrEmpty(summary.get(1))
                || StringUtils.isNullOrEmpty(summary.get(2));
    }

    public boolean isSummaryOutOfLength(List<String> summary){
        return summary.get(0).length() > 50
                || summary.get(1).length() > 50
                || summary.get(2).length() > 50;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOBSDB;
    }

    @Override
    public Class<PositionJobsDBForm> getFormClass() {
        return PositionJobsDBForm.class;
    }


}
