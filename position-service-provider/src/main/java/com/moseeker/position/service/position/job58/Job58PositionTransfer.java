package com.moseeker.position.service.position.job58;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.job58.dto.Job58PositionDTO;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author cjm
 * @date 2018-10-26 14:00
 **/
@Service
public class Job58PositionTransfer extends AbstractPositionTransfer<ThirdPartyPosition,Job58PositionDTO,Job58PositionDTO,EmptyExtThirdPartyPosition> {

    Logger logger = LoggerFactory.getLogger(Job58PositionTransfer.class);

    @Override
    public Job58PositionDTO changeToThirdPartyPosition(ThirdPartyPosition positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {

        return null;
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOB58;
    }

    @Override
    public Class<ThirdPartyPosition> getFormClass() {
        return ThirdPartyPosition.class;
    }

    @Override
    public HrThirdPartyPositionDO toThirdPartyPosition(ThirdPartyPosition thirdPartyPosition, Job58PositionDTO pwa) {
        return null;
    }

    @Override
    public TwoParam<HrThirdPartyPositionDO,EmptyExtThirdPartyPosition> sendSyncRequest(TransferResult<Job58PositionDTO,EmptyExtThirdPartyPosition> result)
            throws TException {
        Job58PositionDTO job58PositionDTO = result.getPositionWithAccount();
        HrThirdPartyPositionDO hrThirdPartyPositionDO = result.getThirdPartyPositionDO();
        return null;
    }

    @Override
    public EmptyExtThirdPartyPosition toExtThirdPartyPosition(ThirdPartyPosition position, Job58PositionDTO r) {
        return EmptyExtThirdPartyPosition.EMPTY;
    }

    @Override
    public EmptyExtThirdPartyPosition toExtThirdPartyPosition(Map<String, String> data) {
        return EmptyExtThirdPartyPosition.EMPTY;
    }
    @Override
    public JSONObject toThirdPartyPositionForm(HrThirdPartyPositionDO thirdPartyPosition, EmptyExtThirdPartyPosition extPosition) {
        return JSONObject.parseObject(JSON.toJSONString(thirdPartyPosition));
    }
    @Override
    protected Job58PositionDTO createAndInitAccountInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        return null;
    }

    @Override
    protected Job58PositionDTO createAndInitPositionInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB) throws Exception {
        return null;
    }
}
