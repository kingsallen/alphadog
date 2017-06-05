package com.moseeker.apps.service;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
=======
import java.util.*;

import com.alibaba.fastjson.JSONObject;
>>>>>>> master
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
<<<<<<< HEAD
=======
import com.moseeker.thrift.gen.company.service.HrTeamServices;
import com.moseeker.thrift.gen.dao.service.HrDBDao;
import com.moseeker.thrift.gen.dao.service.UserHrAccountDao;
>>>>>>> master
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.moseeker.apps.constants.ResultMessage;
import com.moseeker.apps.service.position.PositionSyncResultPojo;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.constants.PositionRefreshType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Query.QueryBuilder;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
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
@Transactional
public class PositionBS {
<<<<<<< HEAD
	Logger logger = LoggerFactory.getLogger(this.getClass());
	UserHrAccountService.Iface userHrAccountService = ServiceManager.SERVICEMANAGER
			.getService(UserHrAccountService.Iface.class);
	CompanyServices.Iface companyService = ServiceManager.SERVICEMANAGER.getService(CompanyServices.Iface.class);
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
	private HrCompanyAccountDao hrCompanyAccountDao;
	/**
	 * 
	 * @param position
	 * @return
	 * @throws Exception 
	 */
	 @CounterIface
	    public Response synchronizePositionToThirdPartyPlatform(ThirdPartyPositionForm position) throws TException {
	        logger.info("synchronizePositionToThirdPartyPlatform:" + JSON.toJSONString(position));
	        Response response;
	        ;
	        // 职位数据是否存在
	        Query.QueryBuilder qu = new Query.QueryBuilder();
	        qu.where("id", position.getPosition_id());
            com.moseeker.thrift.gen.position.struct.Position positionStruct = jobPositionDao.getPositionWithCityCode(qu.buildQuery());
=======

    Logger logger = LoggerFactory.getLogger(this.getClass());

    PositionDao.Iface positionDao = ServiceManager.SERVICEMANAGER.getService(PositionDao.Iface.class);

    PositionServices.Iface positionServices = ServiceManager.SERVICEMANAGER.getService(PositionServices.Iface.class);

    CompanyDao.Iface CompanyDao = ServiceManager.SERVICEMANAGER.getService(CompanyDao.Iface.class);

    ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);

    UserHrAccountDao.Iface userHrAccountDao = ServiceManager.SERVICEMANAGER
            .getService(UserHrAccountDao.Iface.class);

    HrDBDao.Iface hrdbDao = ServiceManager.SERVICEMANAGER.getService(HrDBDao.Iface.class);

    HrTeamServices.Iface hrTeamService = ServiceManager.SERVICEMANAGER.getService(HrTeamServices.Iface.class);


    /**
     * @param position
     * @return
     */
    @CounterIface
    public Response synchronizePositionToThirdPartyPlatform(ThirdPartyPositionForm position) {
        logger.info("synchronizePositionToThirdPartyPlatform:" + JSON.toJSONString(position));
        Response response;
        ;
        // 职位数据是否存在
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", String.valueOf(position.getPosition_id()));
        try {
            com.moseeker.thrift.gen.position.struct.Position positionStruct = positionDao.getPositionWithCityCode(qu);
>>>>>>> master
            logger.info("position:" + JSON.toJSONString(positionStruct));
            // 如果职位数据存在，并且是在招职位
            if (positionStruct != null && positionStruct.getId() > 0 && positionStruct.getStatus() == 0) {
                // 返回结果
                List<PositionSyncResultPojo> results = new ArrayList<>();
                // 是否可以同步职位
                List<ThirdPartyPosition> positionFroms = new ArrayList<>(); // 可同步的职位

                setCompanyAddress(position.getChannels(), positionStruct.getCompany_id());
                List<ThirdPartAccountData> thirdPartyAccounts = hRThirdPartyAccountDao.getThirdPartyAccountsByUserId(positionStruct.getPublisher());
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
<<<<<<< HEAD
                        Query.QueryBuilder subCompanyQuery = new Query.QueryBuilder();
                        subCompanyQuery.where("account_id", positionStruct.getPublisher());
                        List<HrCompanyAccountDO> hrCompanyAccounts = hrCompanyAccountDao.getDatas(subCompanyQuery.buildQuery());//listHrCompanyAccount(subCompanyQuery);
                        HrCompanyDO subCompany = null;
                        if (hrCompanyAccounts != null && hrCompanyAccounts.size() > 0) {
                            Query.QueryBuilder companyQuery = new Query.QueryBuilder();
                            companyQuery.where("id",hrCompanyAccounts.get(0).getCompanyId());
                            subCompany =hrCompanyDao.getData(companyQuery.buildQuery());// CompanyDao.getCompany(companyQuery);
=======
                        QueryUtil subCompanyQuery = new QueryUtil();
                        subCompanyQuery.addEqualFilter("account_id", String.valueOf(positionStruct.getPublisher()));
                        List<HrCompanyAccountDO> hrCompanyAccounts = hrdbDao.listHrCompanyAccount(subCompanyQuery);
                        HrCompanyDO subCompany = null;
                        if (hrCompanyAccounts != null && hrCompanyAccounts.size() > 0) {
                            QueryUtil companyQuery = new QueryUtil();
                            companyQuery.addEqualFilter("id", String.valueOf(hrCompanyAccounts.get(0).getCompanyId()));
                            subCompany = CompanyDao.getCompany(companyQuery);
                        }

                        List<HrTeamDO> hrTeams = new ArrayList<>();
                        if (positionStruct.getTeamId() > 0) {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", String.valueOf(positionStruct.getTeamId()));
                            hrTeams = hrTeamService.getHrTeams(params);
>>>>>>> master
                        }

                        for (ThirdPartyPositionForSynchronization pos : positions) {
                            ThirdPartyPositionForSynchronizationWithAccount p = new ThirdPartyPositionForSynchronizationWithAccount();
                            p.setPosition_info(pos);
                            for (ThirdPartAccountData account : thirdPartyAccounts) {
                                if (account.getId() > 0 && account.binding == 1 && account.getRemain_num() > 0
                                        && account.getChannel() == pos.getChannel()) {
                                    if (subCompany != null) {
                                        p.setCompany_name(subCompany.getAbbreviation());
                                    }
<<<<<<< HEAD
=======
                                    if (hrTeams != null && hrTeams.size() > 0) {
                                        p.getPosition_info().setDepartment(hrTeams.get(0).getName());
                                    }
>>>>>>> master
                                    p.setAccount_id(String.valueOf(account.getId()));
                                    p.setChannel(String.valueOf(pos.getChannel()));
                                    p.setPassword(account.getPassword());
                                    p.setUser_name(account.getUsername());
                                    p.setPosition_id(String.valueOf(pos.getPosition_id()));
                                    p.setMember_name(account.getMembername());
                                    PositionsForSynchronizations.add(p);
                                    logger.info("ThirdPartyPositionForSynchronization:{}", JSON.toJSONString(p));
                                }
                            }
                        }
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
                        hRThirdPartyPositionDao.upsertThirdPartyPositions(pds);

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
//                            positionDao.updatePosition(positionStruct);
                            jobPositionDao.updatePosition(positionStruct);
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
        return response;
	    }

	/**
	 * 对使用公司地址的职位设置公司地址
	 * @param channels 渠道职位
	 * @param companyId 公司编号
	 */
	private void setCompanyAddress(List<ThirdPartyPosition> channels, int companyId) {
		boolean useCompanyAddress = false;
		for(ThirdPartyPosition channel : channels) {
			if(channel.isUse_company_address()) {
				useCompanyAddress = true;
				break;
			}
		}
		if(useCompanyAddress) {
			try {
				Query query=new QueryBuilder().where("id",companyId).buildQuery();
				HrCompanyDO company =hrCompanyDao.getData(query,HrCompanyDO.class);
				for(ThirdPartyPosition channel : channels) {
					if(channel.isUse_company_address()) {
						channel.setAddress(company.getAddress());
					}
				}
			} catch (Exception e) {
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
	 * @param positionId 职位编号
	 * @param channel 渠道编号
	 * @return
	 */
	 @CounterIface
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
	            Query.QueryBuilder queryUtil = new Query.QueryBuilder();
	            queryUtil.where("id", positionId);
	            Position position = jobPositionDao.getData(queryUtil.buildQuery(),Position.class);
	            boolean permission = false;
	            ThirdPartAccountData thirdPartAccountData = null;
	            if (position != null) {
	                thirdPartAccountData = hRThirdPartyAccountDao.getThirdPartyAccountByUserId(position.getPublisher(), channel);
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
			jobPositionDao.updatePosition(job);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//do nothing
		}
	}
	
	public UserHrAccountService.Iface getUserHrAccountService() {
		return userHrAccountService;
	}

	public void setUserHrAccountService(UserHrAccountService.Iface userHrAccountService) {
		this.userHrAccountService = userHrAccountService;
	}


	public PositionServices.Iface getPositionServices() {
		return positionServices;
	}

	public void setPositionServices(PositionServices.Iface positionServices) {
		this.positionServices = positionServices;
	}


	public ChaosServices.Iface getChaosService() {
		return chaosService;
	}

	public void setChaosService(ChaosServices.Iface chaosService) {
		this.chaosService = chaosService;
	}

}
