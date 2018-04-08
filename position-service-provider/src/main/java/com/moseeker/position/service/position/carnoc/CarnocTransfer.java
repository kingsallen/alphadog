package com.moseeker.position.service.position.carnoc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.carnoc.pojo.PositionCarnoc;
import com.moseeker.position.service.position.carnoc.pojo.PositionCarnocWithAccount;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CarnocTransfer extends AbstractPositionTransfer<ThirdPartyPosition,PositionCarnocWithAccount,PositionCarnoc,EmptyExtThirdPartyPosition> {
    @Override
    public PositionCarnocWithAccount changeToThirdPartyPosition(ThirdPartyPosition positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {
        PositionCarnocWithAccount positionWithAccount=createAndInitAccountInfo(positionForm,positionDB,account);

        PositionCarnoc position=createAndInitPositionInfo(positionForm,positionDB);
        positionWithAccount.setPosition_info(position);

        return positionWithAccount;
    }

    @Override
    protected PositionCarnocWithAccount createAndInitAccountInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        PositionCarnocWithAccount positionWithAccount=new PositionCarnocWithAccount();

        positionWithAccount.setUser_name(account.getUsername());
        positionWithAccount.setPassword(account.getPassword());
        positionWithAccount.setPosition_id(String.valueOf(positionDB.getId()));
        positionWithAccount.setChannel(String.valueOf(positionForm.getChannel()));
        positionWithAccount.setAccount_id(String.valueOf(account.getId()));
        return positionWithAccount;
    }

    @Override
    protected PositionCarnoc createAndInitPositionInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB) throws Exception {
        return null;
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.CARNOC;
    }

    @Override
    public Class<ThirdPartyPosition> getFormClass() {
        return ThirdPartyPosition.class;
    }

    @Override
    public HrThirdPartyPositionDO toThirdPartyPosition(ThirdPartyPosition thirdPartyPosition, PositionCarnocWithAccount pwa) {
        return null;
    }

    @Override
    public EmptyExtThirdPartyPosition toExtThirdPartyPosition(ThirdPartyPosition thirdPartyPosition, PositionCarnocWithAccount positionCarnocWithAccount) {
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
}
