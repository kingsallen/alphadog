package com.moseeker.position.service.position.base.sync.check;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.zhilian.pojo.PositionZhilianForm;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZhilianTransferCheck extends AbstractTransferCheck<PositionZhilianForm> {

    private String ADDRESS_REQUIRED = "必须填写地址！";

    @Override
    public Class<PositionZhilianForm> getFormClass() {
        return PositionZhilianForm.class;
    }

    /**
     * 智联验证下地址必填
     * @param positionZhilianForm
     * @param moseekerPosition
     * @return
     */
    @Override
    public List<String> getError(PositionZhilianForm positionZhilianForm, JobPositionDO moseekerPosition) {
        List<String> errorMsg=new ArrayList<>();

        if(StringUtils.isEmptyList(positionZhilianForm.getAddress())){
            errorMsg.add(ADDRESS_REQUIRED);
        }
        return errorMsg;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }
}
