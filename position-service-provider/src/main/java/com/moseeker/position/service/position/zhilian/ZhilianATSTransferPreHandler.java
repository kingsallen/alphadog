package com.moseeker.position.service.position.zhilian;

import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.SyncRequestType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.base.sync.check.AbstractTransferPreHandler;
import com.moseeker.position.service.position.zhilian.pojo.PositionZhilianForm;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyZhilianPositionAddressDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZhilianATSTransferPreHandler extends AbstractTransferPreHandler<PositionZhilianForm> {
    @Autowired
    JobPositionCityDao positionCityDao;

    /**
     * 智联ATS需要做个预处理，
     * 因为智联网页端的address结构是
     * address:[
     *  {
     *      cityCode:110000,
     *      address:"三里屯"
     *  },
     *  {
     *      cityCode:430100,
     *      address:"长沙"
     *  }
     * ]
     * 而ATS，只传一个地址(addressName)
     * 所以构造一个
     * address.cityCode为职位对应job_position_city
     * address.addrees都为addressName
     * 的address对象
     *
     * @param positionZhilianForm 智联表单对象
     * @param moseekerPosition    仟寻职位
     */
    @Override
    public void handle(PositionZhilianForm positionZhilianForm, JobPositionDO moseekerPosition) {
        if(StringUtils.isNullOrEmpty(positionZhilianForm.getAddressName())
                || !StringUtils.isEmptyList(positionZhilianForm.getAddress())){
            return;
        }

        List<JobPositionCityDO> dictCity = positionCityDao.getPositionCitysByPid(moseekerPosition.getId());

        if (StringUtils.isEmptyList(dictCity)) {
            return;
        }

        List<ThirdpartyZhilianPositionAddressDO> address = new ArrayList<>();
        for (JobPositionCityDO c : dictCity) {
            ThirdpartyZhilianPositionAddressDO a = new ThirdpartyZhilianPositionAddressDO();
            a.setCityCode(c.getCode());
            a.setAddress(positionZhilianForm.getAddressName());
            address.add(a);
        }

        positionZhilianForm.setAddress(address);
    }

    @Override
    public SyncRequestType getSyncRequestType() {
        return SyncRequestType.ATS;
    }

    @Override
    public Class<PositionZhilianForm> getFormClass() {
        return PositionZhilianForm.class;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }
}
