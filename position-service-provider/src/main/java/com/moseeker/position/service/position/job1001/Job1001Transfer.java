package com.moseeker.position.service.position.job1001;

import com.moseeker.baseorm.dao.dictdb.DictJob1001OccupationDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.PositionTransfer;
import com.moseeker.position.service.position.job1001.pojo.PositionJob1001;
import com.moseeker.position.service.position.job1001.pojo.PositionJob1001Form;
import com.moseeker.position.service.position.job1001.pojo.PositionJob1001WithAccount;
import com.moseeker.position.service.position.job1001.pojo.TransferStrategy;
import com.moseeker.position.service.position.liepin.pojo.PositionLiepin;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Job1001Transfer extends PositionTransfer<PositionJob1001Form,PositionJob1001WithAccount,PositionJob1001>{
    Logger logger= LoggerFactory.getLogger(Job1001Transfer.class);

    @Autowired
    DictJob1001OccupationDao occupationDao;

    @Override
    public PositionJob1001WithAccount changeToThirdPartyPosition(PositionJob1001Form positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {
        PositionJob1001WithAccount positionJob1001WithAccount=createAndInitAccountInfo(positionForm,positionDB,account);

        PositionJob1001 positionJob1001=createAndInitPositionInfo(positionForm,positionDB);
        positionJob1001WithAccount.setPosition_info(positionJob1001);

        return null;
    }

    @Override
    protected PositionJob1001WithAccount createAndInitAccountInfo(PositionJob1001Form positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        PositionJob1001WithAccount positionWithAccount=new PositionJob1001WithAccount();

        positionWithAccount.setUser_name(account.getUsername());
        positionWithAccount.setPassword(account.getPassword());
        positionWithAccount.setPosition_id(String.valueOf(positionDB.getId()));
        positionWithAccount.setChannel(String.valueOf(positionForm.getChannel()));
        positionWithAccount.setAccount_id(String.valueOf(account.getId()));
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

    public void setOccupation(PositionJob1001Form positionForm, PositionJob1001 position) {
        DecimalFormat df = new DecimalFormat("000");
        List<String> list=new ArrayList<>();
        if (positionForm.getOccupation() != null) {
            positionForm.getOccupation().forEach(o -> list.add(df.format(Integer.valueOf(o))));
        }
        position.setOccupation(list);
    }

}
