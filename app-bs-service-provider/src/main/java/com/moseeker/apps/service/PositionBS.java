package com.moseeker.apps.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moseeker.apps.constants.ResultMessage;
import com.moseeker.apps.service.position.PositionSyncResultPojo;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThridPartyPosition;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThridPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.dao.service.CompanyDao;
import com.moseeker.thrift.gen.dao.service.PositionDao;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;

/**
 * 职位业务层
 * @author wjf
 *
 */
@Service
public class PositionBS {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	PositionDao.Iface positionDao = ServiceManager.SERVICEMANAGER
			.getService(PositionDao.Iface.class);
	
	UserHrAccountService.Iface userHrAccountService = ServiceManager.SERVICEMANAGER
			.getService(UserHrAccountService.Iface.class);
	
	CompanyServices.Iface companyService = ServiceManager.SERVICEMANAGER
			.getService(CompanyServices.Iface.class);
	
	PositionServices.Iface positionServices = ServiceManager.SERVICEMANAGER
			.getService(PositionServices.Iface.class);
	
	CompanyDao.Iface CompanyDao = ServiceManager.SERVICEMANAGER
			.getService(CompanyDao.Iface.class);
	
	ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);
	
	
	
	public Response synchronizePositionToThirdPartyPlatform(ThridPartyPositionForm position) {
		Response response = null;
		//职位数据是否存在
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(position.getPosition_id()));
		try {
			com.moseeker.thrift.gen.position.struct.Position positionStruct = positionDao.getPositionWithCityCode(qu);
			logger.info("position:"+JSON.toJSONString(positionStruct));
			//如果职位数据存在，并且是在招职位
			if(positionStruct.getId() > 0 && positionStruct.getStatus() == 0) {
				//返回结果
				List<PositionSyncResultPojo> results = new ArrayList<>();
				//是否可以同步职位
				List<ThridPartyPosition> positionFroms = new ArrayList<>(); //可同步的职位
				
				QueryUtil ThirdPartyBindingAccounts = new QueryUtil();
				ThirdPartyBindingAccounts.addEqualFilter("company_id", String.valueOf(positionStruct.getCompany_id()));
				List<ThirdPartAccountData> thirdPartyAccounts = CompanyDao.getThirdPartyBindingAccounts(ThirdPartyBindingAccounts);
				if(thirdPartyAccounts != null && thirdPartyAccounts.size() > 0) {
					for(ThridPartyPosition p : position.getChannels()) {
						boolean exist = false;
						for(ThirdPartAccountData account : thirdPartyAccounts) {
							if(account.getId() > 0 && account.binding == 1 && account.getRemain_num() > 0) {
								if(p.getChannel() == account.getChannel()) {
									exist = true;
									positionFroms.add(p);
								}
							}
						}
						if(exist) {
							PositionSyncResultPojo result = new PositionSyncResultPojo();
							result.setChannel(p.getChannel());
							result.setSync_fail_reason("未绑定或者没有可发布职位点数!");
							results.add(result);
						}
					}
					/*position.getChannels().forEach(p -> {
						boolean exist = false;
						for(ThirdPartAccountData account : thirdPartyAccounts) {
							if(account.getId() > 0 && account.binding == 1 && account.getRemain_num() > 0) {
								if(p.getChannel() == account.getChannel()) {
									exist = true;
									positionFroms.add(p);
								}
							}
						}
						if(exist) {
							PositionSyncResultPojo result = new PositionSyncResultPojo();
							result.setChannel(p.getChannel());
							result.setSync_fail_reason("未绑定或者没有可发布职位点数!");
							results.add(result);
						}
					});*/
					
				}
				logger.info("positionFroms:"+JSON.toJSONString(positionFroms));
				if(positionFroms.size() > 0) {
					//转成第三方渠道职位
					List<ThirdPartyPositionForSynchronization> positions = positionServices.changeToThirdPartyPosition(positionFroms, positionStruct);
					//提交到chaos处理
					List<ThirdPartyPositionForSynchronizationWithAccount> PositionsForSynchronization = new ArrayList<>();
					if(positions != null && positions.size() > 0) {
						positions.forEach(pos -> {
							ThirdPartyPositionForSynchronizationWithAccount p = new ThirdPartyPositionForSynchronizationWithAccount();
							p.setPosition_info(pos);
							thirdPartyAccounts.forEach(account -> {
								if(account.getId() > 0 && account.binding == 1 && account.getRemain_num() > 0 && account.getChannel() == pos.getChannel()) {
									p.setChannel(String.valueOf(pos.getChannel()));
									p.setPassword(account.getPassword());
									p.setUser_name(account.getUsername());
									p.setPosition_id(String.valueOf(pos.getPosition_id()));
									p.setMember_name(account.getMembername());
									PositionsForSynchronization.add(p);
								}
							});
						});
					}
					Response synchronizeResult = chaosService.synchronizePosition(positions);
					logger.info("synchronizeResult:"+JSON.toJSONString(synchronizeResult));
					if(synchronizeResult.getStatus() == 0) {
						
						List<ThirdPartyPositionData> pds = new ArrayList<>();
						
						String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");
						positions.forEach(p -> {
							PositionSyncResultPojo result = new PositionSyncResultPojo();
							result.setChannel(p.getChannel());
							result.setSync_status(2);
							result.setSync_time(syncTime);
							results.add(result);
							
							ThirdPartyPositionData data = new ThirdPartyPositionData();
							data.setAddress(p.getWork_place());
							data.setChannel((byte)p.getChannel());
							data.setIs_synchronization((byte)PositionSync.binding.getValue());
							data.setOccupation(p.getCategory_sub());
							data.setSync_time(syncTime);
							data.setPosition_id(p.getPosition_id());
							pds.add(data);
						});
						//回写数据到第三方职位表表
						CompanyDao.upsertThirdPartyPositions(pds);
						
						ThirdPartyPositionForSynchronization p = positions.get(positions.size()-1);
						boolean needWriteBackToPositin = false;
						if(!p.getSalary_high().equals(String.valueOf(positionStruct.getSalary_top()))) {
							positionStruct.setSalary_top(Integer.valueOf(p.getSalary_high()));
							needWriteBackToPositin =  true;
						}
						if(!p.getSalary_low().equals(String.valueOf(positionStruct.getSalary_bottom()))) {
							positionStruct.setSalary_bottom(Integer.valueOf(p.getSalary_low()));
							needWriteBackToPositin =  true;
						}
						if(!p.getQuantity().equals(String.valueOf(positionStruct.getCount()))) {
							positionStruct.setCount(Integer.valueOf(p.getQuantity()));
							needWriteBackToPositin =  true;
						}
						if(needWriteBackToPositin) {
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
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response = ResultMessage.POSITION_NOT_EXIST.toResponse();
		} finally {
			//do nothing
		}
		return response;
	}

}
