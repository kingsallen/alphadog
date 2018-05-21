package com.moseeker.position.service.position.base.sync.check;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.zhilian.pojo.PositionZhilianForm;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyZhilianPositionAddressDO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZhilianTransferCheck extends AbstractTransferCheck<PositionZhilianForm> {

    private String ADDRESS_REQUIRED = "必须填写地址！";

    private String ADDRESS_ADDRESS_NOT_EMPTY = "地址内容不能为空！";

    private String ADDRESS_ADDRESS_LENGTH_LIMIT = "地址字数不超过50个中文，100个英文字符！";

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

        for(ThirdpartyZhilianPositionAddressDO address:positionZhilianForm.getAddress()){
            if(StringUtils.isNullOrEmpty(address.getAddress())){
                errorMsg.add(ADDRESS_ADDRESS_NOT_EMPTY);
                continue;
            }

            if(address.getAddress().replaceAll("[^\\x00-\\xff]", "**").length()>100){
                errorMsg.add(ADDRESS_ADDRESS_LENGTH_LIMIT);
            }
        }

        return errorMsg;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }
}
