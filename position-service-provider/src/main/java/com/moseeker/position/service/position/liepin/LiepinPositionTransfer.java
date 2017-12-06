package com.moseeker.position.service.position.liepin;

import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.DegreeChangeUtil;
import com.moseeker.position.service.position.WorkTypeChangeUtil;
import com.moseeker.position.service.position.base.sync.PositionTransfer;
import com.moseeker.position.service.position.liepin.pojo.PositionLiepin;
import com.moseeker.position.service.position.liepin.pojo.PositionLiepinWithAccount;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.position.qianxun.WorkType;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LiepinPositionTransfer extends PositionTransfer<ThirdPartyPosition,PositionLiepinWithAccount,PositionLiepin,EmptyExtThirdPartyPosition> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DictLiepinOccupationDao occupationDao;


    @Override
    public PositionLiepinWithAccount changeToThirdPartyPosition(ThirdPartyPosition positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) throws Exception {
        PositionLiepinWithAccount positionLiepinWithAccount = createAndInitAccountInfo(positionForm,positionDB,account);

        PositionLiepin positionLiepin = createAndInitPositionInfo(positionForm,positionDB);
        positionLiepinWithAccount.setPosition_info(positionLiepin);

        return positionLiepinWithAccount;
    }

    @Override
    protected PositionLiepinWithAccount createAndInitAccountInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        PositionLiepinWithAccount positionLiepinWithAccount = new PositionLiepinWithAccount();

        positionLiepinWithAccount.setAccount_id(account.getId());
        positionLiepinWithAccount.setUser_name(account.getUsername());
        positionLiepinWithAccount.setPassword(account.getPassword());
        positionLiepinWithAccount.setChannel(String.valueOf(positionForm.getChannel()));
        positionLiepinWithAccount.setPosition_id(positionDB.getId());

        return positionLiepinWithAccount;
    }

    @Override
    protected PositionLiepin createAndInitPositionInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB) throws Exception {
        PositionLiepin positionLiepin = new PositionLiepin();
        positionLiepin.setTitle(positionDB.getTitle());
        positionLiepin.setCities(getCities(positionDB));
        positionLiepin.setAddress(positionForm.getAddressName());
        setOccupation(positionForm,positionLiepin);
        setDepartment(positionForm,positionLiepin);
        positionLiepin.setSalary_low(positionForm.getSalaryBottom()+"");
        positionLiepin.setSalary_high(positionForm.getSalaryTop()+"");
        positionLiepin.setSalary_discuss(positionForm.isSalaryDiscuss() ? "1" : "0");
        positionLiepin.setSalary_month(String.valueOf(positionForm.getSalaryMonth()));

        //工作经验要求
        setWorkyears(positionDB,positionLiepin);
        setDegree((int) positionDB.getDegree(),  positionLiepin);

        String description = getDescription(positionDB.getAccountabilities(), positionDB.getRequirement());
        positionLiepin.setDescription(description);

        positionLiepin.setFeedback_period(String.valueOf(positionForm.getFeedbackPeriod()));

        positionLiepin.setEmail(getEmail(positionDB));

        positionLiepin.setRecruit_type(String.valueOf(Double.valueOf(positionDB.getCandidateSource()).intValue()));

        setEmployeeType((byte) positionDB.getEmploymentType(),positionLiepin);

        positionLiepin.setPractice_salary(String.valueOf(positionForm.getPracticeSalary()));
        positionLiepin.setPractice_salary_unit(String.valueOf(positionForm.getPracticeSalaryUnit()));
        positionLiepin.setPractice_per_week(String.valueOf(positionForm.getPracticePerWeek()));

        setWelfare(positionLiepin,positionDB);

        return positionLiepin;
    }


    protected void setDepartment(ThirdPartyPosition form, PositionLiepin position) {
        position.setDepartment(form.getDepartmentName());
    }

    public void setOccupation(ThirdPartyPosition positionForm, PositionLiepin position) {
        List<String> occupations=positionForm.getOccupation();
        if (occupations != null && !occupations.isEmpty()) {
            occupations=occupationDao.getFullOccupations(occupations.get(occupations.size()-1)).stream().map(o->o.otherCode).collect(Collectors.toList());
            position.setOccupation(occupations);
        }
        logger.info("lieping position sync occupation {}",occupations);
    }

    protected void setEmployeeType(byte employment_type, PositionLiepin position) {
        WorkType workType = WorkType.instanceFromInt(employment_type);
        position.setWork_type(String.valueOf(WorkTypeChangeUtil.getLiepinWorkType(workType).getValue()));
    }

    protected void setDegree(int degreeInt, PositionLiepin position) {
        Degree degree = Degree.instanceFromCode(String.valueOf(degreeInt));
        position.setDegree(DegreeChangeUtil.getLiepinDegree(degree).getValue());
    }

    protected void setWorkyears(JobPositionDO positionDB, PositionLiepin position) throws BIZException {
        Integer experience = null;
        try {
            if (StringUtils.isNotNullOrEmpty(positionDB.getExperience())) {
                experience = Integer.valueOf(positionDB.getExperience().trim());
            }
        } catch (NumberFormatException e) {
            logger.info("liepin parse experience error {}",positionDB.getExperience());
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"parse experience error");
        }
        position.setWorkyears((experience == null || experience == 0) ? "不限" : String.valueOf(experience));
    }


    public void setWelfare(PositionLiepin position, JobPositionDO positionDB){
        if(positionDB.getFeature() == null || positionDB.getFeature().isEmpty()){
            //爬虫需要即使数据库这个字段为空，也需要要一个空列表
            position.setWelfare(new ArrayList<>());
        }else {
            position.setWelfare(Arrays.asList(positionDB.getFeature().split("#")));
        }
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.LIEPIN;
    }

    @Override
    public Class<ThirdPartyPosition> getFormClass() {
        return ThirdPartyPosition.class;
    }

    @Override
    public HrThirdPartyPositionDO toThirdPartyPosition(ThirdPartyPosition position, PositionLiepinWithAccount pwa) {
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();

        PositionLiepin p=pwa.getPosition_info();

        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
        data.setSyncTime(syncTime);
        data.setUpdateTime(syncTime);
        data.setPositionId(pwa.getPosition_id());
        data.setThirdPartyAccountId(pwa.getAccount_id());
        data.setChannel(getChannel().getValue());
        data.setIsSynchronization((byte) PositionSync.binding.getValue());


        //将最后一个职能的Code存到数据库
        if (!p.getOccupation().isEmpty() && p.getOccupation().size() > 0) {
            data.setOccupation(p.getOccupation().get(p.getOccupation().size() - 1));
        }
        data.setDepartmentName(position.getDepartmentName());
        data.setDepartmentId(position.getDepartmentId());
        data.setSalaryBottom(Integer.parseInt(p.getSalary_low()));
        data.setSalaryTop(Integer.parseInt(p.getSalary_high()));
        data.setSalaryMonth(Integer.parseInt(p.getSalary_month()));

        logger.info("回写到第三方职位对象:{}",data);
        return data;
    }

    @Override
    public EmptyExtThirdPartyPosition toExtThirdPartyPosition(ThirdPartyPosition position, PositionLiepinWithAccount positionLiepinWithAccount) {
        return EmptyExtThirdPartyPosition.EMPTY;
    }

    @Override
    public EmptyExtThirdPartyPosition toExtThirdPartyPosition(Map<String, String> data) {
        return EmptyExtThirdPartyPosition.EMPTY;
    }

    @Override
    public JobPositionDO toWriteBackPosition(ThirdPartyPosition position, JobPositionDO positionDB, PositionLiepinWithAccount positionLiepinWithAccount) {
        if(positionDB==null || positionDB.getId()==0){
            return null;
        }
        //假如是同步到猎聘并且是面议那么不回写到数据库
        if(position.isSalaryDiscuss()){
            return null;
        }

        JobPositionDO updateData=new JobPositionDO();
        PositionLiepin p=positionLiepinWithAccount.getPosition_info();

        boolean needWriteBackToPositin = false;
        int salay_top=Integer.parseInt(p.getSalary_high());
        int salary_bottom=Integer.parseInt(p.getSalary_low());

        if (salay_top > 0 && salay_top != positionDB.getSalaryTop() * 1000) {
            updateData.setSalaryTop(salay_top / 1000);
            needWriteBackToPositin = true;
        }
        if (salary_bottom > 0 && salary_bottom != positionDB.getSalaryBottom() * 1000) {
            updateData.setSalaryBottom(salary_bottom / 1000);
            needWriteBackToPositin = true;
        }
        if (needWriteBackToPositin) {
            logger.info("needWriteBackToPositin : {} " ,updateData);
            return updateData;
        }

        return null;
    }
}
