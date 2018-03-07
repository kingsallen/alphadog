package com.moseeker.position.service.position.jobsdb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.jobsdb.pojo.PositionJobsDB;
import com.moseeker.position.service.position.jobsdb.pojo.PositionJobsDBForm;
import com.moseeker.position.service.position.jobsdb.pojo.PositionJobsDBWithAccount;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyVeryEastPositionDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

@Component
public class JobsDBTransfer extends AbstractPositionTransfer<PositionJobsDBForm,PositionJobsDBWithAccount,PositionJobsDB,ThirdpartyVeryEastPositionDO> {
    Logger logger= LoggerFactory.getLogger(JobsDBTransfer.class);

    @Override
    public PositionJobsDBWithAccount changeToThirdPartyPosition(PositionJobsDBForm positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {
        PositionJobsDBWithAccount positionVeryEastWithAccount=createAndInitAccountInfo(positionForm,positionDB,account);

        PositionJobsDB veryEast=createAndInitPositionInfo(positionForm,positionDB);
        positionVeryEastWithAccount.setPosition_info(veryEast);

        return positionVeryEastWithAccount;
    }

    @Override
    protected PositionJobsDBWithAccount createAndInitAccountInfo(PositionJobsDBForm positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        PositionJobsDBWithAccount positionWithAccount=new PositionJobsDBWithAccount();

        positionWithAccount.setUser_name(account.getUsername());
        positionWithAccount.setPassword(account.getPassword());
        positionWithAccount.setPosition_id(String.valueOf(positionDB.getId()));
        positionWithAccount.setChannel(String.valueOf(positionForm.getChannel()));
        positionWithAccount.setAccount_id(String.valueOf(account.getId()));
        return positionWithAccount;
    }

    @Override
    protected PositionJobsDB createAndInitPositionInfo(PositionJobsDBForm positionForm, JobPositionDO positionDB) throws Exception {
        PositionJobsDB positionInfo=new PositionJobsDB();



        return positionInfo;
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOBSDB;
    }

    @Override
    public Class<PositionJobsDBForm> getFormClass() {
        return PositionJobsDBForm.class;
    }

    @Override
    public HrThirdPartyPositionDO toThirdPartyPosition(PositionJobsDBForm position, PositionJobsDBWithAccount pwa) {
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();

        PositionJobsDB p=pwa.getPosition_info();

        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
        data.setSyncTime(syncTime);
        data.setUpdateTime(syncTime);
        data.setPositionId(Integer.parseInt(pwa.getPosition_id()));
        data.setThirdPartyAccountId(Integer.parseInt(pwa.getAccount_id()));
        data.setChannel(getChannel().getValue());
        data.setIsSynchronization((byte) PositionSync.binding.getValue());
        data.setAddressName(position.getAddressName());
        data.setAddressId(position.getAddressId());

        logger.info("回写到第三方职位对象:{}",data);
        return data;
    }

    @Override
    public ThirdpartyVeryEastPositionDO toExtThirdPartyPosition(PositionJobsDBForm form, PositionJobsDBWithAccount positionJobsDBWithAccount) {
        return null;
    }

    @Override
    public ThirdpartyVeryEastPositionDO toExtThirdPartyPosition(Map<String, String> data) {
        return null;
    }

    @Override
    public JSONObject toThirdPartyPositionForm(HrThirdPartyPositionDO thirdPartyPosition, ThirdpartyVeryEastPositionDO extPosition) {
        return null;
    }
}
