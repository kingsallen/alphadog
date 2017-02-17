package com.moseeker.useraccounts.service.impl.biztools;

import java.util.List;

import com.moseeker.thrift.gen.dao.struct.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.UserFavPositionDO;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.service.CompanyDao;
import com.moseeker.thrift.gen.dao.service.ConfigDBDao;
import com.moseeker.thrift.gen.dao.service.JobDBDao;
import com.moseeker.thrift.gen.dao.service.UserDBDao;
import com.moseeker.thrift.gen.dao.struct.AwardConfigTpl;
import com.moseeker.thrift.gen.position.struct.Position;

/**
 * 个人中心片段业务处理类
 * @author jack
 *
 */
@Component
public class UserCenterBizTools {
	
	JobDBDao.Iface jobDBDao = ServiceManager.SERVICEMANAGER.getService(JobDBDao.Iface.class);
	ConfigDBDao.Iface configDBDao = ServiceManager.SERVICEMANAGER.getService(ConfigDBDao.Iface.class);
	CompanyDao.Iface companyDao = ServiceManager.SERVICEMANAGER.getService(CompanyDao.Iface.class);
	UserDBDao.Iface userDBDao = ServiceManager.SERVICEMANAGER.getService(UserDBDao.Iface.class);
	
	/**
	 * 查找用户的申请记录
	 * @param userId 用户编号
	 * @return 申请记录集合
	 * @throws TException thrift异常
	 */
	public List<JobApplication> getAppsForUser(int userId) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("applier_id", String.valueOf(userId));
		qu.addEqualFilter("disable", AbleFlag.OLDENABLE.getValueStr());
		qu.addEqualFilter("email_status", AbleFlag.OLDENABLE.getValueStr());
		qu.setOrder("desc");
		qu.setSortby("submit_time");
		return jobDBDao.getApplications(qu);
	}

	/**
	 * 查找职位信息
	 * @param ids 职位编号数组
	 * @return 职位数据集合
	 * @throws TException thrift异常信息
	 */
	public List<JobPositionDO> getPositions(int... ids) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", arrayToString(ids));
		return jobDBDao.getPositions(qu);
	}

	/**
	 * 查找公司信息
	 * @param ids 公司信息编号
	 * @return 公司信息集合
	 * @throws TException thrift异常信息
	 */
	public List<Hrcompany> getCompanies(int... ids) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", arrayToString(ids));
		return companyDao.getCompanies(qu);
	}

	/**
	 * 查找招聘进度积分配置模板
	 * (todo 可以走缓存)
	 * @return 聘进度积分配置模板集合
	 * @throws TException
	 */
	public List<AwardConfigTpl> getAwardConfigTpls() throws TException {
		return configDBDao.getAwardConfigTpls(null);
	}
	
	/**
	 * 查找职位搜藏
	 * @param userId 用户编号
	 * @param favorite 收藏类型
	 * @return 感兴趣职位集合
	 * @throws TException thrift异常信息
	 */
	public List<UserFavPositionDO> getFavPositions(int userId, int favorite) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("sysuser_id", String.valueOf(userId));
		qu.addEqualFilter("favorite", String.valueOf(favorite));
		return userDBDao.getUserFavPositions(qu);
	}
	
	/**
	 * 数组转成逗号隔开，开始和结束用中括号括起来的字符创。如[1,2,3]
	 * @param array 整型数组
	 * @return 字符串
	 */
	private String arrayToString(int ...array) {
		StringBuffer sb = new StringBuffer(array.length * 2 + 2);
		sb.append("[");
		for(int i : array) {
			sb.append(i);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		return sb.toString();
	}
}
