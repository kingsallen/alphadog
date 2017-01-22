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
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.useraccounts.service.impl.biztools.UserCenterBizTools;
import com.moseeker.useraccounts.service.impl.pojos.ApplicationRecords;

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
	
	public List<ApplicationRecords> getApplication(int userId) throws TException {
		List<ApplicationRecords> applications = new ArrayList<>();
		
		//参数有效性校验
		if(userId > 0) {
			//查询申请记录
			List<JobApplication> apps = bizTools.getAppsForUser(userId);
			if(apps != null && apps.size() > 0) {
				//查询申请记录对应的职位数据
				List<Position> positions = bizTools.getPositionsByApps(apps.stream().mapToInt(app -> (int)app.getId()).toArray());
				
				applications = apps.stream().map(app -> {
					ApplicationRecords ar = new ApplicationRecords();
					ar.setId((int)app.getId());
					ar.setStatus(String.valueOf(app.getApp_tpl_id()));
					ar.setTime(app.get_create_time());
					Optional<Position> op = positions.stream().filter(position -> position.getId() == app.getId()).findFirst();
					if(op.isPresent()) {
						ar.setTitle(op.get().getTitle());
						ar.setDepartment(op.get().getDepartment());
					}
					return ar;
				}).collect(Collectors.toList());
			}
		} else {
			//do nothing
		}
		
		return applications;
	}
}
