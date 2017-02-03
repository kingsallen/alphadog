package com.moseeker.useraccounts.service.impl.biztools;

import java.util.List;

import com.moseeker.thrift.gen.position.struct.Position;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.dao.service.JobDBDao;

/**
 * 个人中心片段业务处理类
 * @author jack
 *
 */
@Component
public class UserCenterBizTools {
	
	JobDBDao.Iface jobDBDao = ServiceManager.SERVICEMANAGER.getService(JobDBDao.Iface.class);

	public List<JobApplication> getAppsForUser(int userId) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("applier_id", String.valueOf(userId));
		qu.setOrder("desc");
		qu.setSortby("submit_time");
		return jobDBDao.getApplications(qu);
	}

	public List<Position> getPositionsByApps(int... appids) throws TException {
		QueryUtil qu = new QueryUtil();
		StringBuffer sb = new StringBuffer(appids.length * 2 + 2);
		sb.append("[");
		for(int i : appids) {
			sb.append(i);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		qu.addEqualFilter("id", sb.toString());
		return jobDBDao.getPositions(qu);
	}
}
