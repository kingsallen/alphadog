package com.moseeker.position.service.position.veryeast;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.veryeast.pojo.PositionVeryEast;
import com.moseeker.position.service.position.veryeast.pojo.PositionVeryEastForm;
import com.moseeker.position.service.position.veryeast.pojo.PositionVeryEastWithAccount;
import com.moseeker.position.service.position.veryeast.pojo.VeryEastTransferStrategy;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyVeryEastPositionDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class VeryEastTransfer extends AbstractPositionTransfer<PositionVeryEastForm,PositionVeryEastWithAccount,PositionVeryEast,ThirdpartyVeryEastPositionDO> {
    Logger logger= LoggerFactory.getLogger(VeryEastTransfer.class);

    @Override
    public PositionVeryEastWithAccount changeToThirdPartyPosition(PositionVeryEastForm positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {
        PositionVeryEastWithAccount positionVeryEastWithAccount=createAndInitAccountInfo(positionForm,positionDB,account);

        PositionVeryEast veryEast=createAndInitPositionInfo(positionForm,positionDB);
        positionVeryEastWithAccount.setPosition_info(veryEast);

        return positionVeryEastWithAccount;
    }

    @Override
    protected PositionVeryEastWithAccount createAndInitAccountInfo(PositionVeryEastForm positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        PositionVeryEastWithAccount positionWithAccount=new PositionVeryEastWithAccount();

        positionWithAccount.setUser_name(account.getUsername());
        positionWithAccount.setPassword(account.getPassword());
        positionWithAccount.setPosition_id(String.valueOf(positionDB.getId()));
        positionWithAccount.setChannel(String.valueOf(positionForm.getChannel()));
        positionWithAccount.setAccount_id(String.valueOf(account.getId()));
        return positionWithAccount;
    }

    @Override
    protected PositionVeryEast createAndInitPositionInfo(PositionVeryEastForm positionForm, JobPositionDO positionDB) throws Exception {
        PositionVeryEast positionInfo=new PositionVeryEast();

        positionInfo.setCompany(positionForm.getCompanyName());
        positionInfo.setTitle(positionDB.getTitle());
        positionInfo.setRegion(getCities(positionDB));
        positionInfo.setQuantity(getQuantity(positionForm.getQuantity(),(int)positionDB.getCount()));
        positionInfo.setIndate(positionForm.getIndate());
        positionInfo.setSalary((int)positionDB.getSalaryTop());
        positionInfo.setOccupation(positionForm.getOccupation());
        positionInfo.setAccommodation(positionForm.getAccommodation()+"");
        positionInfo.setDegree(VeryEastTransferStrategy.VeryEastDegree.moseekerToOther((int)positionDB.getDegree()));
        positionInfo.setExperience(positionDB.getExperience());
        positionInfo.setAge(positionForm.getAge());
        positionInfo.setLanguage(positionForm.getLanguage());
        positionInfo.setComputer_level(positionForm.getComputerLevel()+"");
        positionInfo.setDescription(getDescription(positionDB.getAccountabilities(),positionDB.getRequirement()));
        positionInfo.setEmail(getEmail(positionDB));
        positionInfo.setWork_mode(VeryEastTransferStrategy.WorkMode.moseekerToOther((int)positionDB.getEmploymentType()));

        return positionInfo;
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.VERYEAST;
    }

    @Override
    public Class<PositionVeryEastForm> getFormClass() {
        return PositionVeryEastForm.class;
    }

    @Override
    public HrThirdPartyPositionDO toThirdPartyPosition(PositionVeryEastForm position, PositionVeryEastWithAccount pwa) {
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();

        PositionVeryEast p=pwa.getPosition_info();

        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
        data.setSyncTime(syncTime);
        data.setUpdateTime(syncTime);
        data.setPositionId(Integer.parseInt(pwa.getPosition_id()));
        data.setThirdPartyAccountId(Integer.parseInt(pwa.getAccount_id()));
        data.setChannel(getChannel().getValue());
        data.setIsSynchronization((byte) PositionSync.binding.getValue());



        //将最后一个职能的Code存到数据库
        if (!p.getOccupation().isEmpty() && p.getOccupation().size() > 0) {
            data.setOccupation(p.getOccupation().get(p.getOccupation().size() - 1));
        }
        data.setCompanyName(position.getCompanyName());
        data.setCompanyId(position.getCompanyId());

        logger.info("回写到第三方职位对象:{}",data);
        return data;
    }

    @Override
    public ThirdpartyVeryEastPositionDO toExtThirdPartyPosition(PositionVeryEastForm form, PositionVeryEastWithAccount positionVeryEastWithAccount) {
        ThirdpartyVeryEastPositionDO veryEast=new ThirdpartyVeryEastPositionDO();
        veryEast.setAccommodation(form.getAccommodation());
        veryEast.setAge_top(form.getAgeTop());
        veryEast.setAge_bottom(form.getAgeBottom());
        veryEast.setLanguageType1(form.getLanguageType1());
        veryEast.setLanguageLevel1(form.getLanguageLevel1());
        veryEast.setLanguageType2(form.getLanguageType2());
        veryEast.setLanguageLevel2(form.getLanguageLevel2());
        veryEast.setLanguageType3(form.getLanguageType3());
        veryEast.setLanguageLevel3(form.getLanguageLevel3());
        veryEast.setComputerLevel(form.getComputerLevel());
        veryEast.setIndate(form.getIndate());
        veryEast.setStatus((byte) 0);
        veryEast.setCreateTime(sdf.format(new Date()));

        return veryEast;
    }

    @Override
    public ThirdpartyVeryEastPositionDO toExtThirdPartyPosition(Map<String, String> data) {
        ThirdpartyVeryEastPositionDO result= JSON.parseObject(JSON.toJSONString(data),ThirdpartyVeryEastPositionDO.class);
        return result;
    }
}
