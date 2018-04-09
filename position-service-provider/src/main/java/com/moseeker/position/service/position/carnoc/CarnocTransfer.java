package com.moseeker.position.service.position.carnoc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.dao.dictdb.DictCarnocOccupationDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.carnoc.pojo.CarnocTransferStrategy;
import com.moseeker.position.service.position.carnoc.pojo.PositionCarnoc;
import com.moseeker.position.service.position.carnoc.pojo.PositionCarnocWithAccount;
import com.moseeker.position.service.position.job51.pojo.Position51;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCarnocOccupationDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CarnocTransfer extends AbstractPositionTransfer<ThirdPartyPosition, PositionCarnocWithAccount, PositionCarnoc, EmptyExtThirdPartyPosition> {

    @Autowired
    private DictCarnocOccupationDao carnocOccupationDao;

    @Override
    public PositionCarnocWithAccount changeToThirdPartyPosition(ThirdPartyPosition positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {
        PositionCarnocWithAccount positionWithAccount = createAndInitAccountInfo(positionForm, positionDB, account);

        PositionCarnoc position = createAndInitPositionInfo(positionForm, positionDB);
        positionWithAccount.setPosition_info(position);

        return positionWithAccount;
    }

    @Override
    protected PositionCarnocWithAccount createAndInitAccountInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        PositionCarnocWithAccount positionWithAccount = new PositionCarnocWithAccount();

        positionWithAccount.setUser_name(account.getUsername());
        positionWithAccount.setPassword(account.getPassword());
        positionWithAccount.setPosition_id(positionDB.getId());
        positionWithAccount.setChannel(String.valueOf(positionForm.getChannel()));
        positionWithAccount.setAccount_id(account.getId());
        return positionWithAccount;
    }

    @Override
    protected PositionCarnoc createAndInitPositionInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB) throws Exception {
        PositionCarnoc positionInfo = new PositionCarnoc();

        positionInfo.setJob_title(positionDB.getTitle());
        positionInfo.setJob_details(getDescription(positionDB.getAccountabilities(), positionDB.getRequirement()));
        setOccupation(positionForm, positionInfo);
        positionInfo.setEmail(getEmail(positionDB));
        positionInfo.setJob_number((int) positionDB.getCount());
        positionInfo.setRecruit_school(positionDB.getCandidateSource() == 1);
        positionInfo.setCity(getCities(positionDB));
        positionInfo.setDegree(CarnocTransferStrategy.CarnocDegree.moseekerToCarnoc(String.valueOf(positionDB.getDegree())));
        positionInfo.setWork_exp(CarnocTransferStrategy.WorkExp.moseekerToCarnoc(positionDB.getExperience()));

        return positionInfo;
    }

    protected void setOccupation(ThirdPartyPosition positionForm, PositionCarnoc position) {
        List<String> list = new ArrayList<>();

        Query query = new Query.QueryBuilder().
                where(new Condition("code_other", positionForm.getOccupation(), ValueOp.IN))
                .and(new Condition("code_other", 0, ValueOp.NEQ)).buildQuery();

        List<DictCarnocOccupationDO> occupations = carnocOccupationDao.getDatas(query);

        if (positionForm.getOccupation() != null) {
            for (String id : positionForm.getOccupation()) {
                for (DictCarnocOccupationDO occupation : occupations) {
                    if (id.equals(occupation.getCodeOther() + "")) {
                        list.add(occupation.getName());
                    }
                }
            }
        }
        position.setJob_type(list);
    }

    @Override
    protected String getDescription(String accounTabilities, String requirement) {
        StringBuffer descript = new StringBuffer();
        if (StringUtils.isNotNullOrEmpty(accounTabilities)) {
            descript.append("职位描述:\n").append(accounTabilities);
        }
        if (StringUtils.isNotNullOrEmpty(requirement)) {
            descript.append("\n\n任职条件:\n" + requirement);
        }
        return descript.toString();
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
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();

        String syncTime = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        data.setSyncTime(syncTime);
        data.setUpdateTime(syncTime);
        data.setPositionId(pwa.getPosition_id());
        data.setThirdPartyAccountId(pwa.getAccount_id());
        data.setChannel(getChannel().getValue());
        data.setIsSynchronization((byte) PositionSync.binding.getValue());

        //将最后一个职能的Code存到数据库
        data.setOccupation(thirdPartyPosition.getOccupation().get(thirdPartyPosition.getOccupation().size() - 1));

        return data;
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
