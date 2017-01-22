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
import com.moseeker.thrift.gen.useraccounts.struct.ApplicationRecordsForm;
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
	
	public List<ApplicationRecordsForm> getApplication(int userId) throws TException {
		List<ApplicationRecordsForm> applications = new ArrayList<>();
		
		//参数有效性校验
		if(userId > 0) {
			//查询申请记录
			List<JobApplication> apps = bizTools.getAppsForUser(userId);
			if(apps != null && apps.size() > 0) {
				//查询申请记录对应的职位数据
				List<Position> positions = bizTools.getPositionsByApps(apps.stream().mapToInt(app -> (int)app.getId()).toArray());
				
				applications = apps.stream().map(app -> {
					ApplicationRecordsForm ar = new ApplicationRecordsForm();
					ar.setId((int)app.getId());
					ar.setStatus((byte)app.getApp_tpl_id());
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
