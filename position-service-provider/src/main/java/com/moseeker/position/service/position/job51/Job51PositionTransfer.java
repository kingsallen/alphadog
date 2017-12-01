package com.moseeker.position.service.position.job51;

import com.moseeker.baseorm.dao.dictdb.Dict51OccupationDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyAddressDao;
import com.moseeker.baseorm.db.thirdpartydb.tables.pojos.ThirdpartyAccountCompanyAddress;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.position.service.position.DegreeChangeUtil;
import com.moseeker.position.service.position.ExperienceChangeUtil;
import com.moseeker.position.service.position.WorkTypeChangeUtil;
import com.moseeker.position.service.position.base.PositionTransfer;
import com.moseeker.position.service.position.job51.pojo.Position51;
import com.moseeker.position.service.position.job51.pojo.Position51WithAccount;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.position.service.position.qianxun.WorkType;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyAddressDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class Job51PositionTransfer extends PositionTransfer<ThirdPartyPosition,Position51WithAccount,Position51>{
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ThirdpartyAccountCompanyAddressDao addressDao;

    @Autowired
    private Dict51OccupationDao dict51OccupationDao;

    @Override
    public Position51WithAccount changeToThirdPartyPosition(ThirdPartyPosition positionForm, JobPositionDO positionDB,HrThirdPartyAccountDO account) throws Exception{
        Position51WithAccount positionWithAccount=createAndInitAccountInfo(positionForm,positionDB,account);

        Position51 position=createAndInitPositionInfo(positionForm,positionDB);
        positionWithAccount.setPosition_info(position);

        return positionWithAccount;
    }

    @Override
    protected Position51WithAccount createAndInitAccountInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB,HrThirdPartyAccountDO account){
        Position51WithAccount positionWithAccount=new Position51WithAccount();
        positionWithAccount.setUser_name(account.getUsername());
        positionWithAccount.setPassword(account.getPassword());
        positionWithAccount.setMember_name(account.getMembername());
        positionWithAccount.setPosition_id(String.valueOf(positionDB.getId()));
        positionWithAccount.setChannel(String.valueOf(positionForm.getChannel()));
        positionWithAccount.setAccount_id(String.valueOf(account.getId()));

        return positionWithAccount;
    }

    @Override
    protected Position51 createAndInitPositionInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB) throws Exception {
        Position51 position=new Position51();
        position.setTitle(positionDB.getTitle());

        setOccupation(positionForm,position);

        int quantity=getQuantity(positionForm.getCount(),(int)positionDB.getCount());
        position.setQuantity(quantity+"");

        setDegree((int) positionDB.getDegree(),  position);

        //工作经验要求
        int experience=experienceToInt(positionDB.getExperience());
        position.setExperience(ExperienceChangeUtil.getJob51Experience(experience).getValue());

        //薪资要求
        position.setSalary_low(getSalaryBottom(positionForm.getSalaryBottom())+"");
        position.setSalary_high(getSalaryTop(positionForm.getSalaryTop())+"");


        //职位详情
        String description = getDescription(positionDB.getAccountabilities(), positionDB.getRequirement());
        position.setDescription(description);

        position.setCities(getCities(positionDB));
        //51职位需要补全到6位
        position.setCities(formateList(position.getCities(),"000000"));

        position.setEmail(getEmail(positionDB));

        setEmployeeType((byte) positionDB.getEmploymentType(),position);

        if(StringUtils.isNullOrEmpty(positionForm.getCompanyName())){
            position.setCompany(positionForm.getCompanyName());
        }else{
            position.setCompany(getCompanyName(positionDB.getPublisher()));
        }


        setAddress(position,positionForm);

        return position;
    }

    protected void setOccupation(ThirdPartyPosition positionForm, Position51 position) {
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

    public void setAddress(Position51 position,ThirdPartyPosition form){
        Query query=new Query.QueryBuilder().where("id",form.getAddressId()).buildQuery();
        ThirdpartyAccountCompanyAddressDO addressDO=addressDao.getData(query);

        ThirdpartyAccountCompanyAddress address=new ThirdpartyAccountCompanyAddress();
        address.setCity(addressDO.getCity());
        address.setAddress(addressDO.getAddress());

        position.setAddress(address);
    }

    protected void setDegree(int degreeInt, Position51 position) {
        Degree degree = Degree.instanceFromCode(String.valueOf(degreeInt));
        position.setDegree(DegreeChangeUtil.getJob51Degree(degree).getValue());
    }


    protected void setEmployeeType(byte employment_type,Position51 position51) {
        WorkType workType = WorkType.instanceFromInt(employment_type);
        position51.setType_code(WorkTypeChangeUtil.getJob51EmployeeType(workType).getValue());
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOB51;
    }

    @Override
    public Class<ThirdPartyPosition> getFormClass() {
        return ThirdPartyPosition.class;
    }


}
