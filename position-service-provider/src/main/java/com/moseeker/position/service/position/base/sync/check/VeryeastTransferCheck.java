package com.moseeker.position.service.position.base.sync.check;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.veryeast.pojo.PositionVeryEastForm;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VeryeastTransferCheck extends AbstractTransferCheck<PositionVeryEastForm> {

    private static String SALARY_MUST_LT_ZERO = "薪资必须大于零!";
    private static String SALARY_TOP_MUST_LT_SALARY_BOTTOM = "薪资上限需要大于下限!";
    private static String REQUIRED_OCCUPATION = "职能必填!";

    @Override
    public Class<PositionVeryEastForm> getFormClass() {
        return PositionVeryEastForm.class;
    }

    @Override
    public List<String> getError(PositionVeryEastForm positionVeryEastForm, JobPositionDO moseekerPosition) {
        List<String> errorMsg=new ArrayList<>();

        if(StringUtils.isEmptyList(positionVeryEastForm.getOccupation())){
            errorMsg.add(REQUIRED_OCCUPATION);
        }

        if(positionVeryEastForm.getSalaryTop() <= 0
                || positionVeryEastForm.getSalaryBottom() <= 0 ){
            errorMsg.add(SALARY_MUST_LT_ZERO);
        }

        if(positionVeryEastForm.getSalaryTop() <= positionVeryEastForm.getSalaryBottom() ){
            errorMsg.add(SALARY_TOP_MUST_LT_SALARY_BOTTOM);
        }

        return errorMsg;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.VERYEAST;
    }
}
