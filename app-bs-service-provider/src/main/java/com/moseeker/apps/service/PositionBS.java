package com.moseeker.apps.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.UserHrAccountDao;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moseeker.apps.constants.ResultMessage;
import com.moseeker.apps.service.position.PositionSyncResultPojo;
import com.moseeker.common.constants.PositionRefreshType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.dao.service.CompanyDao;
import com.moseeker.thrift.gen.dao.service.PositionDao;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;

/**
 * 职位业务层
 *
 * @author wjf
 */
@Service
public class PositionBS {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    PositionDao.Iface positionDao = ServiceManager.SERVICEMANAGER.getService(PositionDao.Iface.class);

    PositionServices.Iface positionServices = ServiceManager.SERVICEMANAGER.getService(PositionServices.Iface.class);

    CompanyDao.Iface CompanyDao = ServiceManager.SERVICEMANAGER.getService(CompanyDao.Iface.class);

    ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);

    UserHrAccountDao.Iface userHrAccountDao = ServiceManager.SERVICEMANAGER
            .getService(UserHrAccountDao.Iface.class);

    /**
     * @param position
     * @return
     */
    public Response synchronizePositionToThirdPartyPlatform(ThirdPartyPositionForm position) {
        logger.info("synchronizePositionToThirdPartyPlatform:" + JSON.toJSONString(position));
        Response response;
        ;
        // 职位数据是否存在
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", String.valueOf(position.getPosition_id()));
        try {
            com.moseeker.thrift.gen.position.struct.Position positionStruct = positionDao.getPositionWithCityCode(qu);
            logger.info("position:" + JSON.toJSONString(positionStruct));
            // 如果职位数据存在，并且是在招职位
            if (positionStruct != null && positionStruct.getId() > 0 && positionStruct.getStatus() == 0) {
                // 返回结果
                List<PositionSyncResultPojo> results = new ArrayList<>();
                // 是否可以同步职位
                List<ThirdPartyPosition> positionFroms = new ArrayList<>(); // 可同步的职位

                setCompanyAddress(position.getChannels(), positionStruct.getCompany_id());
                List<ThirdPartAccountData> thirdPartyAccounts = userHrAccountDao.getThirdPartyAccountsByUserId(positionStruct.getPublisher());
                if (thirdPartyAccounts != null && thirdPartyAccounts.size() > 0) {
                    for (ThirdPartyPosition p : position.getChannels()) {
                        for (ThirdPartAccountData account : thirdPartyAccounts) {
                            if (account.getId() > 0 && account.binding == 1 && account.getRemain_num() > 0) {
                                if (p.getChannel() == account.getChannel()) {
                                    p.setThird_party_account_id(account.getId());
                                    positionFroms.add(p);
                                }
                            }
                        }
                    }

                    for (ThirdPartyPosition pp : position.getChannels()) {
                        boolean exist = true;
                        if (positionFroms.size() > 0) {
                            for (ThirdPartyPosition ppp : positionFroms) {
                                if (pp.getChannel() == ppp.getChannel()) {
                                    exist = false;
                                }
                            }
                        }
                        if (exist) {
                            PositionSyncResultPojo result = new PositionSyncResultPojo();
                            result.setChannel(pp.getChannel());
                            result.setAccount_id(pp.getThird_party_account_id());
                            result.setSync_fail_reason("未绑定或者没有可发布职位点数!");
                            results.add(result);
                        }
                    }
                }
                logger.info("positionFroms:" + JSON.toJSONString(positionFroms));
                if (positionFroms.size() > 0) {
                    // 转成第三方渠道职位
                    List<ThirdPartyPositionForSynchronization> positions = positionServices.changeToThirdPartyPosition(positionFroms, positionStruct);
                    // 提交到chaos处理
                    List<ThirdPartyPositionForSynchronizationWithAccount> PositionsForSynchronizations = new ArrayList<>();
                    if (positions != null && positions.size() > 0) {
                        positions.forEach(pos -> {
                            ThirdPartyPositionForSynchronizationWithAccount p = new ThirdPartyPositionForSynchronizationWithAccount();
                            p.setPosition_info(pos);
                            thirdPartyAccounts.forEach(account -> {
                                if (account.getId() > 0 && account.binding == 1 && account.getRemain_num() > 0
                                        && account.getChannel() == pos.getChannel()) {
                                    p.setAccount_id(String.valueOf(account.getId()));
                                    p.setChannel(String.valueOf(pos.getChannel()));
                                    p.setPassword(account.getPassword());
                                    p.setUser_name(account.getUsername());
                                    p.setPosition_id(String.valueOf(pos.getPosition_id()));
                                    p.setMember_name(account.getMembername());
                                    PositionsForSynchronizations.add(p);
                                    logger.info("ThirdPartyPositionForSynchronization:{}", JSON.toJSONString(p));
                                }
                            });
                        });
                    }
                    logger.info("chaosService.synchronizePosition:{}", JSON.toJSONString(PositionsForSynchronizations));
                    Response synchronizeResult = chaosService.synchronizePosition(PositionsForSynchronizations);
                    logger.info("synchronizeResult:" + JSON.toJSONString(synchronizeResult));
                    if (synchronizeResult.getStatus() == 0) {

                        List<ThirdPartyPositionData> pds = new ArrayList<>();

                        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
                        positions.forEach(p -> {
                            PositionSyncResultPojo result = new PositionSyncResultPojo();
                            result.setChannel(p.getChannel());
                            result.setSync_status(2);
                            result.setSync_time(syncTime);
                            result.setAccount_id(p.getAccount_id());
                            results.add(result);

                            ThirdPartyPositionData data = new ThirdPartyPositionData();
                            data.setAddress(p.getWork_place());
                            data.setChannel((byte) p.getChannel());
                            data.setIs_synchronization((byte) PositionSync.binding.getValue());
                            data.setOccupation(p.getCategory_sub_code());
                            data.setSync_time(syncTime);
                            data.setPosition_id(p.getPosition_id());
                            data.setAccount_id(String.valueOf(p.getAccount_id()));
                            pds.add(data);
                        });
                        // 回写数据到第三方职位表表
                        logger.info("write back to thirdpartyposition:" + JSON.toJSONString(pds));
                        userHrAccountDao.upsertThirdPartyPositions(pds);

                        ThirdPartyPositionForSynchronization p = positions.get(positions.size() - 1);
                        boolean needWriteBackToPositin = false;
                        if (!p.getSalary_high().equals(String.valueOf(positionStruct.getSalary_top() * 1000))) {
                            positionStruct.setSalary_top(Integer.valueOf(p.getSalary_high()) / 1000);
                            needWriteBackToPositin = true;
                        }
                        if (!p.getSalary_low().equals(String.valueOf(positionStruct.getSalary_bottom() * 1000))) {
                            positionStruct.setSalary_bottom(Integer.valueOf(p.getSalary_low()) / 1000);
                            needWriteBackToPositin = true;
                        }
                        if (!p.getQuantity().equals(String.valueOf(positionStruct.getCount()))) {
                            positionStruct.setCount(Integer.valueOf(p.getQuantity()));
                            needWriteBackToPositin = true;
                        }
                        if (needWriteBackToPositin) {
                            logger.info("needWriteBackToPositin :" + JSON.toJSONString(positionStruct));
                            positionDao.updatePosition(positionStruct);
                        }
                        response = ResultMessage.SUCCESS.toResponse(results);
                    } else {
                        response = synchronizeResult;
                    }
                } else {
                    response = ResultMessage.THIRD_PARTY_ACCOUNT_NOT_BIND.toResponse();
                }
            } else {
                response = ResultMessage.PROGRAM_PARAM_NOTEXIST.toResponse();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = ResultMessage.PROGRAM_EXCEPTION.toResponse();
        } finally {
            // do nothing
        }
        return response;
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
            try {
                QueryUtil qu = new QueryUtil();
                qu.addEqualFilter("id", String.valueOf(companyId));
                HrCompanyDO company = CompanyDao.getCompany(qu);
                for (ThirdPartyPosition channel : channels) {
                    if (channel.isUse_company_address()) {
                        channel.setAddress(company.getAddress());
                    }
                }
            } catch (TException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            } finally {
                //do nothing
            }
        }
    }

    /**
     * 刷新职位
     *
     * @param positionId 职位编号
     * @param channel    渠道编号
     * @return
     */
    public Response refreshPosition(int positionId, int channel) {
        logger.info("refreshPosition start");
        HashMap<String, Object> result = new HashMap<>();
        result.put("position_id", positionId);
        result.put("channel", channel);
        result.put("is_refresh", PositionRefreshType.notRefresh.getValue());
        Response response = ResultMessage.PROGRAM_EXHAUSTED.toResponse(result);
        try {
            //更新仟寻职位的修改时间
            writeBackToQX(positionId);
            QueryUtil queryUtil = new QueryUtil();
            queryUtil.addEqualFilter("id", positionId);
            Position position = positionDao.getPosition(queryUtil);
            boolean permission = false;
            ThirdPartAccountData thirdPartAccountData = null;
            if (position != null) {
                thirdPartAccountData = userHrAccountDao.getThirdPartyAccountByUserId(position.getPublisher(), channel);
                if (thirdPartAccountData != null && thirdPartAccountData.getId() > 0) {
                    permission = positionServices.ifAllowRefresh(positionId, thirdPartAccountData.getId());
                }
                logger.info("permission:" + permission);

                if (permission) {
                    ThirdPartyPositionForSynchronizationWithAccount refreshPosition = positionServices
                            .createRefreshPosition(positionId, thirdPartAccountData.getId());
                    if (refreshPosition.getPosition_info() != null && StringUtils.isNotNullOrEmpty(refreshPosition.getUser_name())) {
                        logger.info("refreshPosition:" + JSON.toJSONString(refreshPosition));
                        response = chaosService.refreshPosition(refreshPosition);
                        ThirdPartyPositionData account = JSON.parseObject(response.getData(), ThirdPartyPositionData.class);
                        result.put("is_refresh", PositionRefreshType.refreshing.getValue());
                        result.put("sync_time", account.getSync_time());
                        logger.info("refreshPosition:result" + JSON.toJSONString(result));
                        response = ResultMessage.SUCCESS.toResponse(result);
                    } else {
                        response = ResultMessage.PROGRAM_PARAM_NOTEXIST.toResponse(result);
                    }
                } else {
                    result.put("is_refresh", PositionRefreshType.failed.getValue());
                    response = ResultMessage.POSITION_NOT_ALLOW_REFRESH.toResponse(result);
                }
            } else {
                response = ResultMessage.POSITION_NOT_EXIST.toResponse(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            response = ResultMessage.PROGRAM_EXCEPTION.toResponse();
        } finally {
            // do nothing
        }

        return response;
    }

    private void writeBackToQX(int positionId) {
        try {
            Position job = new Position();
            job.setId(positionId);
            job.setUpdate_time((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
            positionDao.updatePosition(job);
        } catch (TException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //do nothing
        }
    }

    public PositionDao.Iface getPositionDao() {
        return positionDao;
    }

    public void setPositionDao(PositionDao.Iface positionDao) {
        this.positionDao = positionDao;
    }

    public UserHrAccountDao.Iface getUserHrAccountDao() {
        return userHrAccountDao;
    }

    public void setUserHrAccountDao(UserHrAccountDao.Iface userHrAccountDao) {
        this.userHrAccountDao = userHrAccountDao;
    }

    public PositionServices.Iface getPositionServices() {
        return positionServices;
    }

    public void setPositionServices(PositionServices.Iface positionServices) {
        this.positionServices = positionServices;
    }

    public CompanyDao.Iface getCompanyDao() {
        return CompanyDao;
    }

    public void setCompanyDao(CompanyDao.Iface companyDao) {
        CompanyDao = companyDao;
    }

    public ChaosServices.Iface getChaosService() {
        return chaosService;
    }

    public void setChaosService(ChaosServices.Iface chaosService) {
        this.chaosService = chaosService;
    }

}
