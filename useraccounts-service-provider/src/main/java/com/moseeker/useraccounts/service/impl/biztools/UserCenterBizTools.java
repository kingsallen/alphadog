package com.moseeker.useraccounts.service.impl.biztools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.moseeker.common.util.StringUtils;
import com.moseeker.db.candidatedb.Candidatedb;
import com.moseeker.thrift.gen.dao.service.*;
import com.moseeker.thrift.gen.dao.struct.*;
import org.apache.thrift.TException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.position.struct.Position;

/**
 * 个人中心片段业务处理类
 * @author jack
 *
 */
@Component
public class UserCenterBizTools {

	Logger logger = LoggerFactory.getLogger(UserCenterBizTools.class);
	
	JobDBDao.Iface jobDBDao = ServiceManager.SERVICEMANAGER.getService(JobDBDao.Iface.class);
	ConfigDBDao.Iface configDBDao = ServiceManager.SERVICEMANAGER.getService(ConfigDBDao.Iface.class);
	CompanyDao.Iface companyDao = ServiceManager.SERVICEMANAGER.getService(CompanyDao.Iface.class);
	UserDBDao.Iface userDBDao = ServiceManager.SERVICEMANAGER.getService(UserDBDao.Iface.class);
	CandidateDBDao.Iface candidateDBDao = ServiceManager.SERVICEMANAGER.getService(CandidateDBDao.Iface.class);
	HrDBDao.Iface hrDBDao = ServiceManager.SERVICEMANAGER.getService(HrDBDao.Iface.class);
	
	/**
	 * 查找用户的申请记录
	 * @param userId 用户编号
	 * @return 申请记录集合
	 * @throws TException thrift异常
	 */
	public List<JobApplicationDO> getAppsForUser(int userId) throws TException {
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
	public List<ConfigSysPointConfTplDO> getAwardConfigTpls() throws TException {
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

	public List<CandidateRecomRecordDO> listCandidateRecomRecords(int userId, int pageNo, int pageSize) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addSelectAttribute("id").addSelectAttribute("app_id").addSelectAttribute("repost_user_id").addSelectAttribute("click_time").addSelectAttribute("recom_time").addSelectAttribute("is_recom").addSelectAttribute("presentee_user_id");
		qu.addEqualFilter("post_user_id", userId);
		qu.addGroup("position_id").addGroup("presentee_user_id");
		qu.setPage(pageNo);
		qu.setPer_page(pageSize);
		return candidateDBDao.listCandidateRecomRecords(qu);
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

	/**
	 * 查找职位编号和职位标题。
	 * @param positionIDSet 职位编号集合
	 * @return 浏览者信息集合
	 */
	public List<JobPositionDO> listJobPositions(Set<Integer> positionIDSet) {
		List<JobPositionDO> positionList = new ArrayList<>();
		if(positionIDSet != null && positionIDSet.size() > 0) {
			QueryUtil queryUtil = new QueryUtil();
			queryUtil.addEqualFilter("id", StringUtils.converToArrayStr(positionIDSet));
			queryUtil.addSelectAttribute("id").addSelectAttribute("title");
			try {
				positionList = jobDBDao.getPositions(queryUtil);
			} catch (TException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return positionList;
	}

	/**
	 * 查找浏览者信息
	 * @param presenteeIDSet 浏览者编号
	 * @return 浏览者信息集合
	 */
	public List<UserUserDO> listPresentees(Set<Integer> presenteeIDSet) {
		List<UserUserDO> users = new ArrayList<>();

		if(presenteeIDSet != null && presenteeIDSet.size() > 0) {
			QueryUtil queryUtil = new QueryUtil();
			queryUtil.addEqualFilter("id", StringUtils.converToArrayStr(presenteeIDSet));
			queryUtil.addSelectAttribute("id").addSelectAttribute("name").addSelectAttribute("nickname").addSelectAttribute("headimg");
			try {
				users = userDBDao.listUser(queryUtil);
			} catch (TException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return users;
	}

	/**
	 * 查询转发用户信息
	 * @param repostIDSet 转发用户编号集合
	 * @return 转发用户信息集合
	 */
	public List<UserUserDO> listReposts(Set<Integer> repostIDSet) {
		List<UserUserDO> users = new ArrayList<>();
		if(repostIDSet != null && repostIDSet.size() > 0) {
			QueryUtil queryUtil = new QueryUtil();
			queryUtil.addEqualFilter("id", StringUtils.converToArrayStr(repostIDSet));
			queryUtil.addSelectAttribute("id").addSelectAttribute("name").addSelectAttribute("nickname");
			try {
				users = userDBDao.listUser(queryUtil);
			} catch (TException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return users;
	}

	/**
	 * 查询申请信息集合
	 * @param appIDSet 申请编号集合
	 * @return 申请信息集合
	 */
	public List<JobApplicationDO> listApps(Set<Integer> appIDSet) {
		List<JobApplicationDO> apps = new ArrayList<>();

		if(appIDSet != null && appIDSet.size() > 0) {
			QueryUtil queryUtil = new QueryUtil();
			queryUtil.addEqualFilter("id", StringUtils.converToArrayStr(appIDSet));
			queryUtil.addSelectAttribute("id").addSelectAttribute("applier_name").addSelectAttribute("submit_time").addSelectAttribute("app_tpl_id");
			try {
				apps = jobDBDao.getApplications(queryUtil);
			} catch (TException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return apps;
	}

	/**
	 * 查找候选人查看职位的记录
	 * @param cps 候选人的职位编号和用户编号的集合
	 * @return 候选人浏览职位的集合
	 */
	public List<CandidatePositionDO> listCandidatePositionsByPositionIDUserID(List<Map<Integer, Integer>> cps) {
		try {
			return candidateDBDao.listCandidatePositionsByPositionIDUserID(cps);
		} catch (TException e) {
			logger.error(e.getMessage(), e);
			return new ArrayList<>();
		}

	}

	/**
	 * 根据申请编号，查找排除不合适记录的最后一条操作记录
	 * @param rejectAppIdSet 申请编号
	 * @return 最后一条操作记录
	 */
	public List<HrOperationRecordDO> listLastHrOperationRecordPassedReject(Set<Integer> rejectAppIdSet) {
		List<HrOperationRecordDO> hrOperationRecordDOList = new ArrayList<>();

		if(rejectAppIdSet != null && rejectAppIdSet.size() > 0) {
			try {
				hrOperationRecordDOList = hrDBDao.listLatestOperationRecordByAppIdSet(rejectAppIdSet);
			} catch (TException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return hrOperationRecordDOList;

	}

	/**
	 * 查找申请记录集合信息
	 * @param appId 申请记录编号
	 * @return 申请记录集合
	 * @throws TException thrift异常
	 */
	public JobApplicationDO getApplication(int appId) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", appId).addEqualFilter("disable", AbleFlag.OLDENABLE.getValueStr());

		qu.addSelectAttribute("id").addSelectAttribute("applier_id").addSelectAttribute("email_status")
				.addSelectAttribute("apply_type").addSelectAttribute("app_tpl_id").addSelectAttribute("position_id")
				.addSelectAttribute("company_id");
		return jobDBDao.getApplication(qu);
	}

	/**
	 * 查找职位名称
	 * @param positionId 职位编号
	 * @return 职位信息
	 */
	public JobPositionDO getPosition(int positionId) {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", positionId);
		qu.addSelectAttribute("id").addSelectAttribute("title");
		try {
			return jobDBDao.getPosition(qu);
		} catch (TException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 查询公司名称
	 * @param companyId
	 * @return
	 */
	public HrCompanyDO getCompany(int companyId) {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", companyId);
		qu.addSelectAttribute("id").addSelectAttribute("name").addSelectAttribute("abbreviation");
		try {
			return hrDBDao.getCompany(qu);
		} catch (TException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 根据申请编号查找该申请记录的HR操作记录
	 * @param appId 申请记录
	 * @return 操作记录
	 */
	public List<HrOperationRecordDO> listHrOperationRecord(int appId) {
		QueryUtil queryUtil = new QueryUtil();
		queryUtil.addEqualFilter("app_id", appId);
		queryUtil.addSelectAttribute("id").addSelectAttribute("app_id").addSelectAttribute("opt_time");
		queryUtil.setSortby("opt_time");
		try {
			return hrDBDao.listHrOperationRecord(queryUtil);
		} catch (TException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
