package com.moseeker.position.service.position.job1001;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictJob1001OccupationDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.position.service.position.base.sync.PositionTransfer;
import com.moseeker.position.service.position.job1001.pojo.PositionJob1001;
import com.moseeker.position.service.position.job1001.pojo.PositionJob1001Form;
import com.moseeker.position.service.position.job1001.pojo.PositionJob1001WithAccount;
import com.moseeker.position.service.position.job1001.pojo.TransferStrategy;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyJob1001PositionDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Job1001Transfer extends PositionTransfer<PositionJob1001Form,PositionJob1001WithAccount,PositionJob1001,ThirdpartyJob1001PositionDO>{
    Logger logger= LoggerFactory.getLogger(Job1001Transfer.class);

    @Autowired
    DictJob1001OccupationDao occupationDao;

    @Override
    public PositionJob1001WithAccount changeToThirdPartyPosition(PositionJob1001Form positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {
        PositionJob1001WithAccount positionJob1001WithAccount=createAndInitAccountInfo(positionForm,positionDB,account);

        PositionJob1001 positionJob1001=createAndInitPositionInfo(positionForm,positionDB);
        positionJob1001WithAccount.setPosition_info(positionJob1001);

        return positionJob1001WithAccount;
    }

    @Override
    protected PositionJob1001WithAccount createAndInitAccountInfo(PositionJob1001Form positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        PositionJob1001WithAccount positionWithAccount=new PositionJob1001WithAccount();

        positionWithAccount.setUser_name(account.getUsername());
        positionWithAccount.setPassword(account.getPassword());
        positionWithAccount.setPosition_id(String.valueOf(positionDB.getId()));
        positionWithAccount.setChannel(String.valueOf(positionForm.getChannel()));
        positionWithAccount.setAccount_id(String.valueOf(account.getId()));
        positionWithAccount.setSafe_code(account.getExt());
        positionWithAccount.setSubsite(positionForm.getSubsite());

        return positionWithAccount;
    }

    @Override
    protected PositionJob1001 createAndInitPositionInfo(PositionJob1001Form positionForm, JobPositionDO positionDB) throws Exception {
        PositionJob1001 positionInfo=new PositionJob1001();



        positionInfo.setTitle(positionDB.getTitle());
        positionInfo.setDepartment(positionDB.getDepartment());
        positionInfo.setLanguage(positionDB.getLanguage());

        positionInfo.setQuantity(getQuantity(0,(int)positionDB.getCount()));
        positionInfo.setExperience(experienceToInt(positionDB.getExperience()));
        positionInfo.setEmail(getEmail(positionDB));
        positionInfo.setRegion(getCities(positionDB));

        positionInfo.setDegree(TransferStrategy.Job1001Degree.moseekerToJob1001((int)positionDB.getDegree()));
        positionInfo.setTarget(TransferStrategy.Target.moseekerToJob1001((int)positionDB.getCandidateSource()));
        positionInfo.setJob_type(TransferStrategy.JobType.moseekerToJob1001((int)positionDB.getEmploymentType()));
        positionInfo.setSex(TransferStrategy.Sex.moseekerToJob1001((int)positionDB.gender));

        positionInfo.setCompany(positionForm.getCompanyName());
        setOccupation(positionForm,positionInfo);
        positionInfo.setMin_salary(getSalaryBottom(positionForm.getSalaryBottom()));
        positionInfo.setMax_salary(getSalaryTop(positionForm.getSalaryTop()));

        positionInfo.setDescription(getDescription(positionDB.getAccountabilities(),positionDB.getRequirement()));


        return positionInfo;
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOB1001;
    }

    @Override
    public Class<PositionJob1001Form> getFormClass() {
        return PositionJob1001Form.class;
    }

    @Override
    public HrThirdPartyPositionDO toThirdPartyPosition(PositionJob1001Form position, PositionJob1001WithAccount pwa) {
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();

        PositionJob1001 p=pwa.getPosition_info();

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
        data.setCompanyId(Integer.parseInt(position.getCompanyId()));
        data.setSalaryBottom(position.getSalaryBottom());
        data.setSalaryTop(position.getSalaryTop());

        logger.info("回写到第三方职位对象:{}",data);
        return data;
    }

    @Override
    public ThirdpartyJob1001PositionDO toExtThirdPartyPosition(PositionJob1001Form positionJob1001Form, PositionJob1001WithAccount positionJob1001WithAccount) {
        ThirdpartyJob1001PositionDO thirdpartyJob1001PositionDO=new ThirdpartyJob1001PositionDO();
        thirdpartyJob1001PositionDO.setSubsite(positionJob1001Form.getSubsite());
        thirdpartyJob1001PositionDO.setCreateTime(sdf.format(new Date()));
        thirdpartyJob1001PositionDO.setStatus((byte) 0);
        return thirdpartyJob1001PositionDO;
    }

    @Override
    public ThirdpartyJob1001PositionDO toExtThirdPartyPosition(Map<String, String> data) {
        ThirdpartyJob1001PositionDO result=JSON.parseObject(JSON.toJSONString(data),ThirdpartyJob1001PositionDO.class);
        return result;
    }

    public void setOccupation(PositionJob1001Form positionForm, PositionJob1001 position) {
        List<String> occupations=positionForm.getOccupation();
        if (occupations != null && !occupations.isEmpty()) {
            occupations=occupationDao.getFullOccupations(occupations.get(occupations.size()-1)).stream().map(o->o.getName()).collect(Collectors.toList());
            position.setOccupation(occupations);
        }
        logger.info("job1001 position sync occupation {}",occupations);
    }
}
