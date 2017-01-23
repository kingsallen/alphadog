package com.moseeker.useraccounts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.AwardConfigTpl;
import com.moseeker.thrift.gen.dao.struct.UserFavPositionPojo;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.useraccounts.struct.ApplicationRecordsForm;
import com.moseeker.thrift.gen.useraccounts.struct.AwardRecordForm;
import com.moseeker.thrift.gen.useraccounts.struct.FavPositionForm;
import com.moseeker.thrift.gen.useraccounts.struct.RecommendationForm;
import com.moseeker.useraccounts.service.impl.biztools.UserCenterBizTools;

/**
 * 用户个人中心功能相关接口
 * @author jack
 *
 */
@Service
@CounterIface
public class UserCenterService {
	
	@Autowired
	private UserCenterBizTools bizTools;
	
	/**
	 * 查询申请记录
	 * @param userId 用户编号
	 * @return 申请记录
	 * @throws TException
	 */
	public List<ApplicationRecordsForm> getApplication(int userId) throws TException {
		List<ApplicationRecordsForm> applications = new ArrayList<>();
		
		//参数有效性校验
		if(userId > 0) {
			//查询申请记录
			List<JobApplication> apps = bizTools.getAppsForUser(userId);
			if(apps != null && apps.size() > 0) {
				//查询申请记录对应的职位数据
				List<Position> positions = bizTools.getPositions(apps.stream().mapToInt(app -> (int)app.getPosition_id()).toArray());
				List<Hrcompany> companies = bizTools.getCompanies(apps.stream().mapToInt(app -> (int)app.getCompany_id()).toArray());
				List<AwardConfigTpl> tpls = bizTools.getAwardConfigTpls();
				
				applications = apps.stream().map(app -> {
					ApplicationRecordsForm ar = new ApplicationRecordsForm();
					ar.setId((int)app.getId());
					ar.setStatus((byte)app.getApp_tpl_id());
					ar.setTime(app.get_create_time());
					if(positions != null) {
						Optional<Position> op = positions.stream().filter(position -> position.getId() == app.getPosition_id()).findFirst();
						if(op.isPresent()) {
							ar.setTitle(op.get().getTitle());
						}
					}
					if(companies != null) {
						Optional<Hrcompany> op = companies.stream().filter(company -> company.getId() == app.getCompany_id()).findFirst();
						if(op.isPresent()) {
							ar.setDepartment(op.get().getAbbreviation());
						}
					}
					
					if(tpls != null) {
						Optional<AwardConfigTpl> op = tpls.stream().filter(tpl -> tpl.getId() == app.getApp_tpl_id()).findFirst();
						if(op.isPresent()) {
							ar.setStatus((byte)op.get().getRecruitOrder());
						}
					}
					
					return ar;
				}).collect(Collectors.toList());
			}
		} else {
			//do nothing
		}
		
		return applications;
	}
	
	/**
	 * 查询职位收藏
	 * @param userId 用户编号
	 * @param favorite 收藏类型
	 * @return
	 * @throws TException
	 */
	public List<FavPositionForm> getFavPositions(int userId) throws TException {
		List<FavPositionForm> favPositions = new ArrayList<>();
		//参数校验
		if(userId > 0) {
			//查询用户的收藏职位列表
			List<UserFavPositionPojo> favPositionRecords = bizTools.getFavPositions(userId, 0);
			if(favPositionRecords != null && favPositionRecords.size() > 0) {
				//差用用户收藏职位的职位详情
				List<Position> positions = bizTools.getPositions(favPositionRecords.stream().mapToInt(favP -> favP.getPositionId()).toArray());
				//拼装职位收藏记录
				if(positions != null && positions.size() > 0) {
					favPositions = favPositionRecords.stream()
							.filter(favP -> {			//过滤不存在职位的职位收藏记录
								boolean flag = false;
								for(Position p : positions) {
									if(p.getId() == favP.getPositionId()) {
										flag = true;
									}
								}
								return flag;
							})
							.map(record -> {
						FavPositionForm form = new FavPositionForm();
						Optional<Position> op = positions.stream().filter(p -> p.getId() == record.getPositionId()).findFirst();
						if(op.isPresent()) {
							form.setId(op.get().getId());
							form.setTitle(op.get().getTitle());
							form.setDepartment(op.get().getDepartment());
							form.setTime(record.getUpdate_time());
							form.setCity(op.get().getCity());
							form.setSalary_top(op.get().getSalary_top());
							form.setSalary_bottom(op.get().getSalary_bottom());
							form.setUpdate_time(op.get().getUpdate_time());
						}
						return form;
					}).collect(Collectors.toList());
				}
			}
		} else {
			//do nothing
		}
		
		return favPositions;
	}
	
	/**
	 * (todo)
	 * 查询历史推荐记录
	 * @param userId 用户编号
	 * @return 历史推荐记录
	 * @throws TException thrift异常类
	 */
	public List<RecommendationForm> getRecommendations(int userId) throws TException {
		List<RecommendationForm> forms = new ArrayList<>();
		
		return forms;
	}
	
	/**
	 * 查找积分
	 * @param userId
	 * @return
	 * @throws TException
	 */
	public List<AwardRecordForm> getAwardRecords(int userId) throws TException {
		List<AwardRecordForm> awards = new ArrayList<>();
		
		//参数校验
		if(userId > 0) {
			
		}
		//判断用户是否是员工
		//查找积分记录
		//查找申请
		//查找职位
		//封装数据
		
		return awards;
	}
	
	
	public boolean isEmployee(int userId) throws TException {
		boolean flag =  false;
		
		return flag;
	}
}
