package com.moseeker.position.service.position.zhilian;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.DegreeChangeUtil;
import com.moseeker.position.service.position.ExperienceChangeUtil;
import com.moseeker.position.service.position.base.PositionTransfer;
import com.moseeker.position.service.position.zhilian.pojo.PositionZhilian;
import com.moseeker.position.service.position.zhilian.pojo.PositionZhilianWithAccount;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class ZhilianPositionTransfer extends PositionTransfer<ThirdPartyPosition,PositionZhilianWithAccount,PositionZhilian> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public PositionZhilianWithAccount changeToThirdPartyPosition(ThirdPartyPosition positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {
        PositionZhilianWithAccount positionZhilianWithAccount=createAndInitAccountInfo(positionForm,positionDB,account);

        PositionZhilian positionZhilian=createAndInitPositionInfo(positionForm,positionDB);

        positionZhilianWithAccount.setPosition_info(positionZhilian);

        return positionZhilianWithAccount;
    }

    @Override
    protected PositionZhilianWithAccount createAndInitAccountInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        PositionZhilianWithAccount position51WithAccount = new PositionZhilianWithAccount();
        position51WithAccount.setUser_name(account.getUsername());
        position51WithAccount.setPassword(account.getPassword());
        position51WithAccount.setPosition_id(String.valueOf(positionDB.getId()));
        position51WithAccount.setChannel(String.valueOf(positionForm.getChannel()));
        position51WithAccount.setAccount_id(String.valueOf(account.getId()));
        return position51WithAccount;
    }

    @Override
    protected PositionZhilian createAndInitPositionInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB) throws Exception {
        PositionZhilian positionZhilian = new PositionZhilian();

        positionZhilian.setTitle(positionDB.getTitle());

        positionZhilian.setCities(getCities(positionDB));

        positionZhilian.setAddress(positionForm.getAddressName());

        setOccupation(positionForm,positionZhilian);

        positionZhilian.setSalary_low(getSalaryBottom(positionForm.getSalaryBottom())+"");
        positionZhilian.setSalary_high(getSalaryTop(positionForm.getSalaryTop())+"");

        setWorkyears(positionDB,positionZhilian);

        setDegree((int) positionDB.getDegree(),  positionZhilian);

        String description = getDescription(positionDB.getAccountabilities(), positionDB.getRequirement());
        positionZhilian.setDescription(description);

        positionZhilian.setEmail(getEmail(positionDB));
//        positionZhilian.setJob_id(positionInfo.getJob_id());
        int quantity=getQuantity(positionForm.getCount(),(int)positionDB.getCount());
        positionZhilian.setCount(quantity+"");

        if(StringUtils.isNullOrEmpty(positionForm.getCompanyName())){
            positionZhilian.setCompany(positionForm.getCompanyName());
        }else{
            positionZhilian.setCompany(getCompanyName(positionDB.getPublisher()));
        }

        return positionZhilian;
    }

    protected void setOccupation(ThirdPartyPosition positionForm, PositionZhilian position) {
        DecimalFormat df = new DecimalFormat("000");
        List<String> list=new ArrayList<>();
        if (positionForm.getOccupation() != null) {
            positionForm.getOccupation().forEach(o -> list.add(df.format(Integer.valueOf(o))));
        }
        position.setOccupation(list);
    }

    protected void setDegree(int degreeInt, PositionZhilian position) {
        Degree degree = Degree.instanceFromCode(String.valueOf(degreeInt));
        position.setDegree(DegreeChangeUtil.getZhilianDegree(degree).getValue());
    }

    protected void setWorkyears(JobPositionDO positionDB, PositionZhilian position) throws BIZException {
        //工作经验要求
        Integer experience = null;
        try {
            if (StringUtils.isNotNullOrEmpty(positionDB.getExperience())) {
                experience = Integer.valueOf(positionDB.getExperience().trim());
            }
        } catch (NumberFormatException e) {
            logger.info("zhilian parse experience error {}",positionDB.getExperience());
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"parse experience error");
        }
        position.setWorkyears(ExperienceChangeUtil.getZhilianExperience(experience).getValue());
    }



    @Override
    public ChannelType getChannel() {
        return ChannelType.ZHILIAN;
    }

    @Override
    public Class<ThirdPartyPosition> getFormClass() {
        return ThirdPartyPosition.class;
    }

}
