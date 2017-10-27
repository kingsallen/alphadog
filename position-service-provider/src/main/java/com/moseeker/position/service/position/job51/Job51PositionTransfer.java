package com.moseeker.position.service.position.job51;

import com.moseeker.baseorm.dao.dictdb.Dict51OccupationDao;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyAddressDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountDepartmentDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.position.service.position.DegreeChangeUtil;
import com.moseeker.position.service.position.ExperienceChangeUtil;
import com.moseeker.position.service.position.WorkTypeChangeUtil;
import com.moseeker.position.service.position.base.PositionTransfer;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.position.qianxun.WorkType;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyAddressDO;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class Job51PositionTransfer extends PositionTransfer{
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThirdpartyAccountCompanyAddressDao addressDao;

    @Autowired
    private DictCityDao dictCityDao;

    @Autowired
    private Dict51OccupationDao dict51OccupationDao;

    @Autowired
    private ThirdpartyAccountDepartmentDao departmentDao;


    @Override
    protected void setDepartment(ThirdPartyPosition form, JobPositionDO positionDO, ThirdPartyPositionForSynchronization position) {
        /*List<ThirdpartyAccountDepartmentDO> list=departmentDao.getDepartmentByAccountId(position.getAccount_id());
        if (list != null) {
            position.setDepartment(hrTeam.getName());
        } else {
            position.setDepartment("");
        }*/
    }

    @Override
    protected void setOccupation(ThirdPartyPosition positionForm, ThirdPartyPositionForSynchronization position) {
        DecimalFormat df = new DecimalFormat("0000");
        List<String> list=new ArrayList<>();

        Query query=new Query.QueryBuilder().
                where(new Condition("code_other",positionForm.getOccupation(), ValueOp.IN))
                .and(new Condition("code_other",0,ValueOp.NEQ)).buildQuery();

        List<Dict51jobOccupationDO> occupations=dict51OccupationDao.getDatas(query);

        if (positionForm.getOccupation() != null) {
            for(String id:positionForm.getOccupation()){
                for(Dict51jobOccupationDO occupation:occupations){
                    if(id.equals(occupation.getCodeOther()+"")){
                        list.add(occupation.getName());
                    }
                }
            }
        }
        position.setOccupation(list);
    }

    @Override
    protected void setEmployeeType(byte employment_type, ThirdPartyPositionForSynchronization position) {
        WorkType workType = WorkType.instanceFromInt(employment_type);
        position.setWork_type(String.valueOf(WorkTypeChangeUtil.getJob51EmployeeType(workType).getValue()));
    }

    @Override
    protected void setDegree(int degreeInt, ThirdPartyPositionForSynchronization position) {
        Degree degree = Degree.instanceFromCode(String.valueOf(degreeInt));
        position.setDegree(DegreeChangeUtil.getJob51Degree(degree).getValue());
    }

    @Override
    protected void setExperience(Integer experience, ThirdPartyPositionForSynchronization position) {
        position.setExperience_code(ExperienceChangeUtil.getJob51Experience(experience).getValue());
        position.setExperience(ExperienceChangeUtil.getJob51Experience(experience).getValue());
    }


    @Override
    public ChannelType getChannel() {
        return ChannelType.JOB51;
    }

    @Override
    public void setMore(ThirdPartyPositionForSynchronization position,ThirdPartyPosition form, JobPositionDO positionDB) {
        setAddress(position,form);
        position.setEmail(positionDB.getHrEmail());
        //51职位需要补全到6位
        position.setCities(formateList(position.getCities(),"000000"));
    }

    public void setAddress(ThirdPartyPositionForSynchronization position,ThirdPartyPosition form){
        Query query=new Query.QueryBuilder().where("id",form.getAddressId()).buildQuery();
        ThirdpartyAccountCompanyAddressDO address=addressDao.getData(query);
        position.setAddress_city(address.getCity());
    }
}
