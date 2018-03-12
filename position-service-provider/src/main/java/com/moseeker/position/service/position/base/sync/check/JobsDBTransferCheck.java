package com.moseeker.position.service.position.base.sync.check;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.jobsdb.pojo.PositionJobsDBForm;

import java.util.ArrayList;
import java.util.List;

public class JobsDBTransferCheck extends AbstractTransferCheck<PositionJobsDBForm>{

    private static String ADDRESS_NOT_EMPTY="地址不能为空!";

    private static String THREE_SUMMERY_REQUIRED="必须有三条总结!";

    private static String SALARY_NOT_EMPTY="薪资不能为空!";

    @Override
    public boolean check(PositionJobsDBForm positionJobsDBForm) {
        boolean nothingWrong = true;

        List<String> errorMsg=new ArrayList<>();

        // 必须设置地址
        if(positionJobsDBForm.getAddressId()==0
                || StringUtils.isNullOrEmpty(positionJobsDBForm.getAddressName())){
            nothingWrong = false;

            errorMsg.add(ADDRESS_NOT_EMPTY);

        }

        // 必须有三条总结
        if(positionJobsDBForm.getSummery()==null || positionJobsDBForm.getSummery().size()!=3){
            nothingWrong = false;

            errorMsg.add(THREE_SUMMERY_REQUIRED);
        }

        // 薪资上下限必须都不为0
        if(positionJobsDBForm.getSalaryTop()==0 && positionJobsDBForm.getSalaryBottom()==0){
            nothingWrong = false;

            errorMsg.add(SALARY_NOT_EMPTY);
        }

        putErrorMsg(positionJobsDBForm,errorMsg);
        return nothingWrong;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOBSDB;
    }
}
