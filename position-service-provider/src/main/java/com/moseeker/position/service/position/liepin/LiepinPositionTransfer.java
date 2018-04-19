package com.moseeker.position.service.position.liepin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.fundationbs.PositionQxService;
import com.moseeker.position.service.position.DegreeChangeUtil;
import com.moseeker.position.service.position.WorkTypeChangeUtil;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.liepin.pojo.PositionLiepin;
import com.moseeker.position.service.position.liepin.pojo.PositionLiepinWithAccount;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.common.constants.WorkType;
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
public class LiepinPositionTransfer extends AbstractPositionTransfer<ThirdPartyPosition,PositionLiepinWithAccount,PositionLiepin,EmptyExtThirdPartyPosition> {
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
        positionLiepin.setAddress("");
        setOccupation(positionForm,positionLiepin);
        positionLiepin.setDepartment(positionForm.getDepartmentName());
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

        positionLiepin.setWelfare(getFeature(positionDB));

        return positionLiepin;
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
        data.setSalaryBottom(getSalaryBottom(Integer.parseInt(p.getSalary_low())));
        data.setSalaryTop(getSalaryTop(Integer.parseInt(p.getSalary_high())));
        data.setSalaryMonth(Integer.parseInt(p.getSalary_month()));
        data.setFeedbackPeriod(position.getFeedbackPeriod());

        //校招职位
        data.setPracticeSalary(position.getPracticeSalary());
        data.setPracticeSalaryUnit(position.getPracticeSalaryUnit());
        data.setPracticePerWeek(position.getPracticePerWeek());
        data.setSalaryDiscuss(position.isSalaryDiscuss() ? 1: 0);

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
    public JSONObject toThirdPartyPositionForm(HrThirdPartyPositionDO thirdPartyPosition, EmptyExtThirdPartyPosition extPosition) {
        return JSONObject.parseObject(JSON.toJSONString(thirdPartyPosition));
    }
}
