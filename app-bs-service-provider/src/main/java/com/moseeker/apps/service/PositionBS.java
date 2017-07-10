package com.moseeker.apps.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.apps.constants.ResultMessage;
import com.moseeker.apps.service.position.PositionSyncResultPojo;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.PositionRefreshType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Query.QueryBuilder;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 职位业务层
 *
 * @author wjf
 */
@Service
@Transactional
public class PositionBS {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    PositionServices.Iface positionServices = ServiceManager.SERVICEMANAGER.getService(PositionServices.Iface.class);
    ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private HRThirdPartyAccountDao hRThirdPartyAccountDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private HRThirdPartyPositionDao hRThirdPartyPositionDao;
    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;
    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;

    /**
     * @param position
     * @return
     */
    @CounterIface
    public Response synchronizePositionToThirdPartyPlatform(ThirdPartyPositionForm position) throws Exception {
        logger.info("synchronizePositionToThirdPartyPlatform:" + JSON.toJSONString(position));
        // 职位数据是否存在
        Query qu = new Query.QueryBuilder().where("id", position.getPosition_id()).buildQuery();
        JobPositionDO moseekerPosition = jobPositionDao.getData(qu);
        logger.info("position:" + JSON.toJSONString(moseekerPosition));

        // 如果职位数据不存在，并且是不是在招职位
        if (moseekerPosition == null || moseekerPosition.getId() == 0 || moseekerPosition.getStatus() != 0) {
            return ResultMessage.POSITION_NOT_EXIST.toResponse();
        }

        // 返回结果
        List<PositionSyncResultPojo> results = new ArrayList<>();

        // 可同步的职位
        List<ThirdPartyPosition> positionFroms = new ArrayList<>();

        //根据是否使用公司地址来设置工作地址
        setCompanyAddress(position.getChannels(), moseekerPosition.getCompanyId());

        //获取发布人绑定的第三方帐号
        List<HrThirdPartyAccountDO> thirdPartyAccounts = hRThirdPartyAccountDao.getThirdPartyAccountsByUserId(moseekerPosition.getPublisher());

        if (thirdPartyAccounts == null || thirdPartyAccounts.size() == 0) {
            //没有找到有效的第三方帐号
            return ResultMessage.THIRD_PARTY_ACCOUNT_NOT_BIND.toResponse();
        }

        for (ThirdPartyPosition p : position.getChannels()) {
            HrThirdPartyAccountDO avaliableAccount = null;
            for (HrThirdPartyAccountDO account : thirdPartyAccounts) {
                if (p.getChannel() == account.getChannel()) {
                    if (account.getRemainNum() > 0) {
                        avaliableAccount = account;
                    }
                }
            }

            if (avaliableAccount == null) {
                //没有有效的对应渠道的第三方帐号
                PositionSyncResultPojo result = new PositionSyncResultPojo();
                result.setChannel(p.getChannel());
                result.setAccount_id(p.getThird_party_account_id());
                result.setSync_fail_reason("未绑定或者没有可发布职位点数!");
                results.add(result);
            } else {
                p.setThird_party_account_id(avaliableAccount.getId());
                positionFroms.add(p);
            }
        }

        logger.info("positionFroms:" + JSON.toJSONString(positionFroms));

        if (positionFroms.size() == 0) {
            //没有找到有效的第三方帐号
            return ResultMessage.THIRD_PARTY_ACCOUNT_NOT_BIND.toResponse();
        }

        // 转成第三方渠道职位
        List<ThirdPartyPositionForSynchronization> positions = positionServices.changeToThirdPartyPosition(positionFroms, moseekerPosition);
        // 提交到chaos处理
        HrCompanyDO subCompany = hrCompanyAccountDao.getHrCompany(moseekerPosition.getPublisher());
        List<ThirdPartyPositionForSynchronizationWithAccount> PositionsForSynchronizations = new ArrayList<>();
        for (ThirdPartyPositionForSynchronization pos : positions) {
            ThirdPartyPositionForSynchronizationWithAccount p = new ThirdPartyPositionForSynchronizationWithAccount();
            if(subCompany!=null){
                p.setCompany_name(subCompany.getAbbreviation());
            }else{
                p.setCompany_name("");
            }
            p.setPosition_info(pos);
            for (HrThirdPartyAccountDO account : thirdPartyAccounts) {
                if (pos.getAccount_id() == account.getId()) {
                    p.setAccount_id(pos.getAccount_id());
                    p.setChannel(pos.getChannel());
                    p.setPassword(account.getPassword());
                    p.setUser_name(account.getUsername());
                    p.setPosition_id(pos.getPosition_id());
                    p.setMember_name(account.getMembername());
                    PositionsForSynchronizations.add(p);
                    logger.info("ThirdPartyPositionForSynchronization:{}", JSON.toJSONString(p));
                }
            }
        }
        logger.info("chaosService.synchronizePosition:{}", JSON.toJSONString(PositionsForSynchronizations));
        chaosService.synchronizePosition(PositionsForSynchronizations);

        List<HrThirdPartyPositionDO> pds = new ArrayList<>();

        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
        positions.forEach(p -> {
            PositionSyncResultPojo result = new PositionSyncResultPojo();
            result.setChannel(p.getChannel());
            result.setSync_status(2);
            result.setSync_time(syncTime);
            result.setAccount_id(p.getAccount_id());
            results.add(result);

            HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();
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
            pds.add(data);
        });
        // 回写数据到第三方职位表表
        logger.info("write back to thirdpartyposition:" + JSON.toJSONString(pds));
        hRThirdPartyPositionDao.upsertThirdPartyPositions(pds);

        ThirdPartyPositionForSynchronization p = positions.get(positions.size() - 1);
        boolean needWriteBackToPositin = false;
        if (p.getSalary_top() != moseekerPosition.getSalaryTop() * 1000) {
            moseekerPosition.setSalaryTop(p.getSalary_top() / 1000);
            needWriteBackToPositin = true;
        }
        if (p.getSalary_bottom() != moseekerPosition.getSalaryBottom() * 1000) {
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

        return ResultMessage.SUCCESS.toResponse(results);
    }

    /**
     * 对使用公司地址的职位设置公司地址
     *
     * @param channels  渠道职位
     * @param companyId 公司编号
     */

    private void setCompanyAddress(List<ThirdPartyPosition> channels, int companyId) {
        boolean useCompanyAddress = false;
        for (ThirdPartyPosition channel : channels) {
            if (channel.isUse_company_address()) {
                useCompanyAddress = true;
                break;
            }
        }
        if (useCompanyAddress) {
            Query query = new QueryBuilder().where("id", companyId).buildQuery();
            HrCompanyDO company = hrCompanyDao.getData(query, HrCompanyDO.class);
            for (ThirdPartyPosition channel : channels) {
                if (channel.isUse_company_address()) {
                    channel.setAddress(company.getAddress());
                }
            }
        }
    }

    /**
     * 刷新职位
     *
     * @param positionId 职位编号
     * @param channel    渠道编号
     * @return
     * @throws TException
     */
    @CounterIface
    public Response refreshPosition(int positionId, int channel) throws TException {
        logger.info("refreshPosition start");
        HashMap<String, Object> result = new HashMap<>();
        result.put("position_id", positionId);
        result.put("channel", channel);
        result.put("is_refresh", PositionRefreshType.notRefresh.getValue());
        //更新仟寻职位的修改时间
        writeBackToQX(positionId);
        Query.QueryBuilder queryUtil = new Query.QueryBuilder();
        queryUtil.where("id", positionId);
        Position position = jobPositionDao.getData(queryUtil.buildQuery(), Position.class);
        HrThirdPartyAccountDO thirdPartAccountData = null;
        if (position == null) {
            logger.warn("职位不存在:{}", positionId);
            return ResultMessage.POSITION_NOT_EXIST.toResponse();
        }
        boolean permission = false;
        thirdPartAccountData = hRThirdPartyAccountDao.getThirdPartyAccountByUserId(position.getPublisher(), channel);
        if (thirdPartAccountData != null && thirdPartAccountData.getId() > 0) {
            permission = positionServices.ifAllowRefresh(positionId, thirdPartAccountData.getId());
            logger.info("check permission:{}:{}", positionId, permission);
        }
        if (!permission) {
            logger.info("position not allow refresh:{}:{}", positionId, permission);
            return ResultMessage.POSITION_NOT_ALLOW_REFRESH.toResponse();
        }
        ThirdPartyPositionForSynchronizationWithAccount refreshPosition = positionServices.createRefreshPosition(positionId, thirdPartAccountData.getId());

        if (refreshPosition == null || refreshPosition.getPosition_info() == null || StringUtils.isNullOrEmpty(refreshPosition.getUser_name())) {
            logger.warn("position thirdpartyposition record:{}:{}", refreshPosition);
            return ResultMessage.PROGRAM_PARAM_NOTEXIST.toResponse();
        }

        logger.info("refreshPosition:" + JSON.toJSONString(refreshPosition));

        chaosService.refreshPosition(refreshPosition);


        HrThirdPartyPositionDO p = new HrThirdPartyPositionDO();
        p.setChannel(refreshPosition.getChannel());
        p.setPositionId(refreshPosition.getPosition_id());
        p.setIsRefresh((byte) PositionRefreshType.refreshing.getValue());
        p.setRefreshTime((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
        p.setThirdPartyAccountId(refreshPosition.getAccount_id());

        thirdPartyPositionDao.upsertThirdPartyPosition(p);
        result.put("is_refresh", PositionRefreshType.refreshing.getValue());
        result.put("sync_time", p.getSyncTime());
        return ResultMessage.SUCCESS.toResponse(result);
    }

    @CounterIface
    public Response refreshPositionQX(List<Integer> list) throws TException {
        List<Position> positionList = new ArrayList<Position>();
        for (int i = 0; i < list.size(); i++) {
            Position position = new Position();
            position.setId(list.get(i));
            position.setUpdate_time((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
            positionList.add(position);
        }
        jobPositionDao.updatePositionList(positionList);
        return ResultMessage.SUCCESS.toResponse(null);
    }

    private void writeBackToQX(int positionId) {
        JobPositionDO positionDO = new JobPositionDO();
        positionDO.setId(positionId);
        positionDO.setUpdateTime((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
        jobPositionDao.updateData(positionDO);
    }

}
