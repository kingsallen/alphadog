package com.moseeker.position.service.position.jobsdb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.jobsdb.pojo.JobsDBTransferStrategy;
import com.moseeker.position.service.position.jobsdb.pojo.PositionJobsDB;
import com.moseeker.position.service.position.jobsdb.pojo.PositionJobsDBForm;
import com.moseeker.position.service.position.jobsdb.pojo.PositionJobsDBWithAccount;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyJobsDBPositionDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JobsDBTransfer extends AbstractPositionTransfer<PositionJobsDBForm,PositionJobsDBWithAccount,PositionJobsDB,ThirdpartyJobsDBPositionDO> {
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

        positionInfo.setJob_title(positionDB.getTitle());
        positionInfo.setJob_details(getDescription(positionDB.getAccountabilities(),positionDB.getRequirement()));
        positionInfo.setEmail(getEmail(positionDB));
        positionInfo.setSummary_points(Arrays.asList(positionForm.getSummery1(),positionForm.getSummery2(),positionForm.getSummery3()));
        positionInfo.setJob_functions(Arrays.asList(positionForm.getOccupation1(),positionForm.getOccupation2(),positionForm.getOccupation3()));
        positionInfo.setWork_location(Arrays.asList(positionForm.getAddressId(),positionForm.getChildAddressId()));
        positionInfo.setEmployment_type(JobsDBTransferStrategy.EmploymentType.moseekerToOther((int)positionDB.getEmploymentType()));
        positionInfo.setSalary_bottom(positionForm.getSalaryBottom());
        positionInfo.setSalary_top(positionForm.getSalaryTop());
        positionInfo.setBenefits(getFeature(positionDB.getFeature()));


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

        //将最后一个职能的Code存到数据库
        if (!position.getOccupation1().isEmpty() && position.getOccupation1().size() > 0) {
            data.setOccupation(position.getOccupation1().get(position.getOccupation1().size() - 1));
        }
        data.setAddressName(position.getAddressName());
        data.setAddressId(position.getAddressId());

        logger.info("回写到第三方职位对象:{}",data);
        return data;
    }

    @Override
    public ThirdpartyJobsDBPositionDO toExtThirdPartyPosition(PositionJobsDBForm form, PositionJobsDBWithAccount positionJobsDBWithAccount) {
        ThirdpartyJobsDBPositionDO jobsDB=new ThirdpartyJobsDBPositionDO();
        jobsDB.setChildAddressId(form.getChildAddressId());
        jobsDB.setChildAddressName(form.getAddressName());
        if (!form.getOccupation2().isEmpty() && form.getOccupation2().size() > 0) {
            jobsDB.setOccupationExt1(form.getOccupation2().get(form.getOccupation2().size() - 1));
        }
        if (!form.getOccupation3().isEmpty() && form.getOccupation3().size() > 0) {
            jobsDB.setOccupationExt2(form.getOccupation3().get(form.getOccupation3().size() - 1));
        }
        jobsDB.setSummary1(form.getSummery1());
        jobsDB.setSummary2(form.getSummery2());
        jobsDB.setSummary3(form.getSummery3());

        jobsDB.setStatus((byte) 0);
        jobsDB.setCreateTime(sdf.format(new Date()));

        return null;
    }

    @Override
    public ThirdpartyJobsDBPositionDO toExtThirdPartyPosition(Map<String, String> data) {
        ThirdpartyJobsDBPositionDO result= JSON.parseObject(JSON.toJSONString(data),ThirdpartyJobsDBPositionDO.class);
        return result;
    }

    @Override
    public JSONObject toThirdPartyPositionForm(HrThirdPartyPositionDO thirdPartyPosition, ThirdpartyJobsDBPositionDO extPosition) {
        PositionJobsDBForm form = new PositionJobsDBForm();

        form.setSummery1(extPosition.getSummary1());
        form.setSummery2(extPosition.getSummary2());
        form.setSummery3(extPosition.getSummary3());

        form.setOccupation1(Arrays.asList(thirdPartyPosition.getOccupation()));
        form.setOccupation2(Arrays.asList(extPosition.getOccupationExt1()));
        form.setOccupation3(Arrays.asList(extPosition.getOccupationExt2()));

        form.setAddressId(thirdPartyPosition.getAddressId());
        form.setAddressName(thirdPartyPosition.getAddressName());
        form.setChildAddressId(extPosition.getChildAddressId());
        form.setChildAddressName(extPosition.getChildAddressName());

        form.setSalaryTop(thirdPartyPosition.getSalaryTop());
        form.setSalaryBottom(thirdPartyPosition.getSalaryBottom());

        JSONObject result= JSON.parseObject(JSON.toJSONString(form));

        result.putAll(JSON.parseObject(JSON.toJSONString(thirdPartyPosition)));
        result.put("occupation",form.getOccupation());

        return result;
    }

    @Override
    protected String getDescription(String accounTabilities, String requirement) {
        StringBuffer descript = new StringBuffer();
        if (StringUtils.isNotNullOrEmpty(accounTabilities)) {
            descript.append("Job description and responsibilities:\n")
                    .append(accounTabilities);
        }
        if (StringUtils.isNotNullOrEmpty(requirement)) {
            descript.append("\nJob requirements:\n").append(requirement);
        }
        return descript.toString();
    }
}
