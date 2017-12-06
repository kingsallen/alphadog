package com.moseeker.position.service.position.zhilian;

import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.DegreeChangeUtil;
import com.moseeker.position.service.position.ExperienceChangeUtil;
import com.moseeker.position.service.position.base.sync.PositionTransfer;
import com.moseeker.position.service.position.zhilian.pojo.PositionZhilian;
import com.moseeker.position.service.position.zhilian.pojo.PositionZhilianWithAccount;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class ZhilianPositionTransfer extends PositionTransfer<ThirdPartyPosition,PositionZhilianWithAccount,PositionZhilian,EmptyExtThirdPartyPosition> {
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

    @Override
    public HrThirdPartyPositionDO toThirdPartyPosition(ThirdPartyPosition form,PositionZhilianWithAccount pwa) {
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();

        PositionZhilian p=pwa.position_info;

        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
        data.setSyncTime(syncTime);
        data.setUpdateTime(syncTime);

        data.setAddress(p.getAddress());
        data.setChannel(getChannel().getValue());
        data.setIsSynchronization((byte) PositionSync.binding.getValue());
        //将最后一个职能的Code存到数据库
        if (!p.getOccupation().isEmpty() && p.getOccupation().size() > 0) {
            data.setOccupation(p.getOccupation().get(p.getOccupation().size() - 1));
        }

        data.setPositionId(Integer.parseInt(pwa.getPosition_id()));
        data.setThirdPartyAccountId(Integer.parseInt(pwa.getAccount_id()));
        data.setSalaryBottom(Integer.parseInt(p.getSalary_low()));
        data.setSalaryTop(Integer.parseInt(p.getSalary_high()));
        data.setSalaryMonth(Integer.parseInt(p.getSalary_month()));

        data.setCompanyId(form.getCompanyId());
        data.setCompanyName(form.getCompanyName());
        data.setAddressId(form.getAddressId());
        data.setAddressName(form.getAddressName());

        logger.info("回写到第三方职位对象:{}",data);
        return data;
    }


    @Override
    public EmptyExtThirdPartyPosition toExtThirdPartyPosition(ThirdPartyPosition form,PositionZhilianWithAccount positionZhilianWithAccount) {
        return EmptyExtThirdPartyPosition.EMPTY;
    }

    @Override
    public EmptyExtThirdPartyPosition toExtThirdPartyPosition(Map<String, String> data) {
        return EmptyExtThirdPartyPosition.EMPTY;
    }

    @Override
    public JobPositionDO toWriteBackPosition(ThirdPartyPosition position, JobPositionDO positionDB, PositionZhilianWithAccount positionZhilianWithAccount) {
        if(positionDB==null || positionDB.getId()==0){
            return null;
        }
        JobPositionDO updateData=new JobPositionDO();
        PositionZhilian p=positionZhilianWithAccount.getPosition_info();

        boolean needWriteBackToPositin = false;
        int salay_top=Integer.parseInt(p.getSalary_high());
        int salary_bottom=Integer.parseInt(p.getSalary_low());
        int count=Integer.parseInt(p.getCount());

        if (salay_top > 0 && salay_top != positionDB.getSalaryTop() * 1000) {
            updateData.setSalaryTop(salay_top / 1000);
            needWriteBackToPositin = true;
        }
        if (salary_bottom > 0 && salary_bottom != positionDB.getSalaryBottom() * 1000) {
            updateData.setSalaryBottom(salary_bottom / 1000);
            needWriteBackToPositin = true;
        }
        if (count != positionDB.getCount()) {
            updateData.setCount(Integer.valueOf(count));
            needWriteBackToPositin = true;
        }
        if (needWriteBackToPositin) {
            logger.info("needWriteBackToPositin : {} " ,updateData);
            return updateData;
        }

        return null;
    }


}
