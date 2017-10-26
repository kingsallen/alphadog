package com.moseeker.apps.service.position;

import com.alibaba.fastjson.JSON;
import com.moseeker.apps.constants.ResultMessage;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyAddressDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountCompanyDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountDepartmentDao;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyAccountCompanyAddress;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyAddressDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountDepartmentDO;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionSyncHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;
    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;

    @Autowired
    private ThirdpartyAccountCompanyDao companyDao;
    @Autowired
    private ThirdpartyAccountCompanyAddressDao companyAddressDao;
    @Autowired
    private ThirdpartyAccountDepartmentDao departmentDao;

    public void checkFormData(ThirdPartyPosition position){

    }

    public ThirdpartyAccountCompanyDO checkCompany(int companyId){
        ThirdpartyAccountCompanyDO company=companyDao.getCompanyById(companyId);
        if(company==null){

        }
        return company;
    }

    public ThirdpartyAccountCompanyAddressDO checkCompanyAddress(int addressId){
        ThirdpartyAccountCompanyAddressDO companyAddress=companyAddressDao.getAddressById(addressId);

        return companyAddress;
    }

    public ThirdpartyAccountDepartmentDO checkDepartment(int departmentId){
        ThirdpartyAccountDepartmentDO department=departmentDao.getDepartmentById(departmentId);

        return department;
    }

    //回写到jobPosition需要的字段
    public void writeBackJobPositionField(JobPositionDO moseekerPosition, List<ThirdPartyPositionForSynchronizationWithAccount> positions){
        ThirdPartyPositionForSynchronization p = null;
        for(ThirdPartyPositionForSynchronizationWithAccount positionWithAccount : positions){
            ThirdPartyPositionForSynchronization thirdPartyPositionForSynchronization=positionWithAccount.getPosition_info();
            //假如是同步到猎聘并且是面议那么不回写到数据库
            if(thirdPartyPositionForSynchronization.isSalary_discuss() && thirdPartyPositionForSynchronization.getChannel() == ChannelType.LIEPIN.getValue()){
                continue;
            }
            p = thirdPartyPositionForSynchronization;
            break;
        }
        if(p != null) {
            boolean needWriteBackToPositin = false;
            if (p.getSalary_top() > 0 && p.getSalary_top() != moseekerPosition.getSalaryTop() * 1000) {
                moseekerPosition.setSalaryTop(p.getSalary_top() / 1000);
                needWriteBackToPositin = true;
            }
            if (p.getSalary_bottom() > 0 && p.getSalary_bottom() != moseekerPosition.getSalaryBottom() * 1000) {
                moseekerPosition.setSalaryBottom(p.getSalary_bottom() / 1000);
                needWriteBackToPositin = true;
            }
            if (p.getQuantity() != moseekerPosition.getCount()) {
                moseekerPosition.setCount(Integer.valueOf(p.getQuantity()));
                needWriteBackToPositin = true;
            }
            if (needWriteBackToPositin) {
                logger.info("needWriteBackToPositin :" + JSON.toJSONString(moseekerPosition));
                jobPositionDao.updateData(moseekerPosition);
            }
        }
    }

    //根据同步职位数据，创建插入数据库第三方职位表hr_third_party_position的数据
    public HrThirdPartyPositionDO createHrThirdPartyPositionDO(ThirdPartyPositionForSynchronization p,ThirdPartyPosition position){
        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();
        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");

        data.setAddress(p.getWork_place());
        data.setChannel((byte) p.getChannel());
        data.setIsSynchronization((byte) PositionSync.binding.getValue());
        //将最后一个职能的Code存到数据库
        if (p.getOccupation().size() > 0) {
            data.setOccupation(p.getOccupation().get(p.getOccupation().size() - 1));
        }
        data.setSyncTime(syncTime);
        data.setUpdateTime(syncTime);
        data.setPositionId(p.getPosition_id());
        data.setThirdPartyAccountId(p.getAccount_id());
        data.setFeedbackPeriod(p.getFeedback_period());
        data.setDepartment(p.getDepartment());
        data.setSalaryBottom(p.getSalary_bottom());
        data.setSalaryTop(p.getSalary_top());
        data.setSalaryDiscuss(p.isSalary_discuss() ? 1 : 0);
        data.setSalaryMonth(p.getSalary_month());
        data.setPracticeSalary(p.getPractice_salary());
        data.setPracticePerWeek(p.getPractice_per_week());
        data.setPracticeSalaryUnit(p.getPractice_salary_unit());

        data.setCompanyId(position.getCompanyId());
        data.setCompanyName(position.getCompanyName());
        data.setAddressId(position.getAddressId());
        data.setAddressName(position.getAddressName());
        data.setDepartmentId(position.getDepartmentId());
        data.setDepartmentName(position.getDepartmentName());

        logger.info("回写到第三方职位对象:{}",data);
        return data;
    }



    //初始化公司
    public void initCompanyName(ThirdPartyPositionForSynchronizationWithAccount p,int publisher){
        HrCompanyDO subCompany = hrCompanyAccountDao.getHrCompany(publisher);//获取发布者对应的公司，只返回一个
        if (subCompany != null) {
            logger.info("初始化公司名称:{}",subCompany);
            p.setCompany_name(subCompany.getAbbreviation());
        } else {
            p.setCompany_name("");
        }
    }

    //创建失败结果
    public PositionSyncResultPojo createFailResult(int channel,int thirdPartyAccountId,String reason){
        PositionSyncResultPojo result=new PositionSyncResultPojo();
        result.setChannel(channel);
        result.setAccount_id(thirdPartyAccountId);
        result.setSync_fail_reason(reason);
        return result;
    }
    //创建普通结果
    public PositionSyncResultPojo createNormalResult(int channel,int thirdPartyAccountId){
        PositionSyncResultPojo result = new PositionSyncResultPojo();
        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");

        result.setChannel(channel);
        result.setSync_status(2);
        result.setSync_time(syncTime);
        result.setAccount_id(thirdPartyAccountId);

        return result;
    }


    //初始化并设置第三方账号的信息
    public ThirdPartyPositionForSynchronizationWithAccount createAndInitThirdAccount(ThirdPartyPositionForSynchronization pos,HrThirdPartyAccountDO account){
        ThirdPartyPositionForSynchronizationWithAccount p=new ThirdPartyPositionForSynchronizationWithAccount();
        p.setAccount_id(pos.getAccount_id());
        p.setChannel(pos.getChannel());
        p.setPassword(account.getPassword());
        p.setUser_name(account.getUsername());
        p.setPosition_id(pos.getPosition_id());
        p.setMember_name(account.getMembername());
        logger.info("初始化第三方账号:{}"+p);
        return p;
    }


    //获取发布者在对应渠道下的第三方账号
    public HrThirdPartyAccountDO getThirdPartAccount(int publisher,int channel){
        return hrThirdPartyAccountDao.getThirdPartyAccountByUserId(publisher,channel);
    }
    //获取可用并且remainNum>0的第三方账号
    public HrThirdPartyAccountDO getAvailableThirdAccount(int publisher,int channel){
        HrThirdPartyAccountDO account=getThirdPartAccount(publisher,channel);
        if(account!=null && ( channel==ChannelType.JOB51.getValue() && account.remainNum>0) || (channel!=ChannelType.JOB51.getValue()) ){
            logger.info("发布者：{}获取到渠道：{}第三方账号",publisher,channel,account);
            return account;
        }
        return null;
    }
    //设置可以绑定的第三方账号ID
    public ThirdPartyPosition setThirdPartyAccountId(HrThirdPartyAccountDO thirdPartyAccount,ThirdPartyPosition position) throws TException {
        if(thirdPartyAccount==null){
            logger.error("没有找到第三方账号");
            throw new TException(ResultMessage.THIRD_PARTY_ACCOUNT_NOT_EXIST.getMessage());
        }else{
            position.setThirdPartyAccountId(thirdPartyAccount.getId());
        }
        return position;
    }


    //根据职位id获取MoSeeker的职位
    public JobPositionDO getMoSeekerPosition(int positionId){
        Query qu = new Query.QueryBuilder().where("id", positionId).buildQuery();
        JobPositionDO moseekerPosition = jobPositionDao.getData(qu);
        logger.info("position:" + JSON.toJSONString(moseekerPosition));
        return moseekerPosition;
    }
    //检查ID对应的职位是否存在或可用
    public JobPositionDO getAvailableMoSeekerPosition(int positionId) throws BIZException {
        JobPositionDO moseekerPosition=getMoSeekerPosition(positionId);
        if (moseekerPosition == null || moseekerPosition.getId() == 0 || moseekerPosition.getStatus() != 0) {
            throw new BIZException(ResultMessage.POSITION_NOT_EXIST.getStatus(),ResultMessage.POSITION_NOT_EXIST.getMessage());
        }
        return moseekerPosition;
    }

    //获取公司
    public HrCompanyDO getCompany(int companyId) throws TException {
        Query query = new Query.QueryBuilder().where("id", companyId).buildQuery();
        HrCompanyDO company = hrCompanyDao.getData(query, HrCompanyDO.class);
        if(company==null){
            logger.error(companyId+"公司不存在");
            throw new TException(ResultMessage.COMPANY_NOT_EXIST.getMessage());
        }
        return company;
    }
    //检查是否需要设置公司地址
    public ThirdPartyPosition setCompanyAddress(ThirdPartyPosition position,int companyId) throws TException {
        return setCompanyAddress(position,getCompany(companyId));
    }
    public ThirdPartyPosition setCompanyAddress(ThirdPartyPosition position,HrCompanyDO company) throws TException {
        if(position==null || company == null){
            logger.error("设置公司地址时，职位或者公司为空");
            throw  new NullPointerException();
        }
        if(position.isUseCompanyAddress()){
            position.setAddressId(company.getId());
            position.setAddressName(company.getAddress());
        }
        return position;
    }

}
