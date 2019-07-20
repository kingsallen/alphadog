package com.moseeker.useraccounts.thrift;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrLeaderBoard;
import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyReferralConfDO;
import com.moseeker.thrift.gen.employee.service.EmployeeService.Iface;
import com.moseeker.thrift.gen.employee.struct.*;
import com.moseeker.useraccounts.service.impl.EmployeeBindByEmail;
import com.moseeker.useraccounts.service.impl.EmployeeService;
import com.moseeker.useraccounts.service.impl.pojos.ReferralPositionInfo;
import com.moseeker.useraccounts.service.impl.vo.EmployeeInfoVO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ltf
 * 员工服务thrift--实现
 * 2017年3月3日
 */
@Service
public class EmployeeServiceImpl implements Iface {
	
	@Autowired
	private EmployeeService service;

	@Autowired
    private EmployeeBindByEmail employeeBindByEmail;

	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public EmployeeServiceImpl(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

	/* 
	 * 获取用户员工信息
	 */
	@Override
	public EmployeeResponse getEmployee(int userId, int companyId) throws TException {
		try {
			return service.getEmployee(userId, companyId);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#getEmployeeVerificationConf(int)
	 * 获取公司员工认证配置信息
	 */
	@Override
	public EmployeeVerificationConfResponse getEmployeeVerificationConf(int companyId)
			throws TException {
		try {
			return service.getEmployeeVerificationConf(companyId);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public EmployeeVerificationConfResponse getEmployeeVerificationConfByUserId(int userId) throws BIZException, TException {
		try {
			return service.getEmployeeVerificationConfByUserId(userId);
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#bind(com.moseeker.thrift.gen.employee.struct.BindingParams)
	 * 员工绑定
	 */
	@Override
	public Result bind(BindingParams bindingParams,int bindSource) throws TException {
		try {
			return service.bind(bindingParams,bindSource);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#unbind(int, int, int)
	 * 员工解绑
	 */
	@Override
	public Result unbind(int employeeId, int companyId, int userId)
			throws TException {
		try {
			return service.unbind(employeeId, companyId, userId);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#getEmployeeCustomFieldsConf(int)
	 * 获取员工认证自定义字段配置信息
	 */
	@Override
	public List<EmployeeCustomFieldsConf> getEmployeeCustomFieldsConf(int companyId)
			throws TException {
		try {
			return service.getEmployeeCustomFieldsConf(companyId);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}


	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#getEmployeeRewards(int, int)
	 * 获取员工积分
	 */
	@Override
	public RewardsResponse getEmployeeRewards(int employeeId, int companyId, int pageNumber, int pageSize)
			throws TException {
		try {
			return service.getEmployeeRewards(employeeId, companyId, pageNumber, pageSize);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#getEmployeeRecoms(int)
	 * 推荐记录
	 */
	@Override
	public List<RecomInfo> getEmployeeRecoms(int recomId) throws TException {

		return null;
	}

	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#setEmployeeCustomInfo(int, java.lang.String)
	 * 员工填写认证自定义字段
	 */
	@Override
	public Result setEmployeeCustomInfo(int employeeId, String customValues)
			throws TException {
		try {
			return service.setEmployeeCustomInfo(employeeId, customValues);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public Result emailActivation(String activationCode,int bindEmailSource) throws TException {
		try {
			return employeeBindByEmail.emailActivation(activationCode,bindEmailSource);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public Pagination awardRanking(int employeeId, int companyId, Timespan timespan, int pageNum, int pageSize) throws BIZException, TException {
		String timeStr;
		if (timespan == Timespan.year) {
			timeStr = String.valueOf(LocalDate.now().getYear());
		} else if(timespan == Timespan.quarter) {
			timeStr = String.valueOf(LocalDate.now().getYear()).concat(String.valueOf((LocalDate.now().getMonthValue()+2)/3));
		} else {
			timeStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
		}
		try {
			return service.awardRanking(employeeId, companyId, timeStr, pageNum, pageSize);
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

    @Override
    public Result setCacheEmployeeCustomInfo(int userId, int companyId, String customValues) throws TException {
        return service.setCacheEmployeeCustomInfo(userId, companyId, customValues);
    }

	@Override
	public void patchEmployeeCustomFieldValues(int userId, int companyId, Map<Integer, List<String>> customValues) throws TException {
		service.patchEmployeeCustomFieldValues(userId, companyId, customValues);
	}

	@Override
    public void upsertCompanyReferralConf(HrCompanyReferralConfDO conf) throws BIZException, TException {
        try {
             service.upsertCompanyReferralConf(conf);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response getCompanyReferralConf(int companyId) throws BIZException, TException {
        try {
            HrCompanyReferralConfDO confDO = service.getCompanyReferralConf(companyId);
            String result= JSON.toJSONString(confDO,serializeConfig);
            return ResponseUtils.successWithoutStringify(result);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public void updsertCompanyReferralPocily(int companyId, int userId) throws BIZException, TException {
        try {
            service.upsertReferralPolicy(companyId, userId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

	@Override
	public int countUpVote(int employeeId) throws BIZException, TException {

		try {
			return service.countUpVote(employeeId);
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public int countRecentUpVote(int employeeId) throws BIZException, TException {
		try {
			return service.countRecentUpVote(employeeId);
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public int upvote(int employeeId, int userId) throws BIZException, TException {
		try {
			return service.upVote(employeeId, userId);
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public void removeUpvote(int employeeId, int userId) throws BIZException, TException {
		try {
			service.removeUpVote(employeeId, userId);
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public void clearUpVoteWeekly() throws BIZException, TException {
		try {
			service.clearUpVoteWeekly();
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public LeaderBoardInfo fetchLeaderBoardInfo(int id, int type) throws BIZException, TException {
		try {
			com.moseeker.useraccounts.service.impl.pojos.LeaderBoardInfo leaderBoardInfo =
                    service.fetchLeaderBoardInfo(id, type);
			LeaderBoardInfo l = new LeaderBoardInfo();
			org.springframework.beans.BeanUtils.copyProperties(leaderBoardInfo, l);
			return l;
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public LeaderBoardInfo fetchLastLeaderBoardInfo(int id, int type) throws BIZException, TException {
		try {
			com.moseeker.useraccounts.service.impl.pojos.LeaderBoardInfo leaderBoardInfo =
                    service.fetchLastLeaderBoardInfo(id, type);
			LeaderBoardInfo l = new LeaderBoardInfo();
			org.springframework.beans.BeanUtils.copyProperties(leaderBoardInfo, l);
			return l;
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public LeaderBoardType fetchLeaderBoardType(int companyId) throws BIZException, TException {
		try {
			HrLeaderBoard leaderBoard = service.fetchLeaderBoardType(companyId);
			LeaderBoardType leaderBoardType = new LeaderBoardType();
			leaderBoardType.setId(leaderBoard.getId());
			leaderBoardType.setCompany_id(leaderBoard.getCompanyId());
			leaderBoardType.setType(leaderBoard.getType());
			return leaderBoardType;
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public void updateLeaderBoardType(int companyId, byte type) throws BIZException, TException {
		try {
			service.updateLeaderBoardType(companyId, type);
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public int countEmplyee(int companyId) throws BIZException, TException {
		try {
			return service.countEmployee(companyId);
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public void setUploadType(int employeeId, int positionId, byte type) throws BIZException, TException {
		try {
			service.setUploadType(employeeId, positionId, type);
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public ReferralPosition getUploadType(int employeeId) throws BIZException, TException {
		try {
			ReferralPositionInfo referralPositionInfo = service.getUploadType(employeeId);
			ReferralPosition referralPosition = convertReferralPosition(referralPositionInfo);
			return referralPosition;
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

    @Override
    public ReferralsCard getReferralsCard(List<Integer> referralLogIds) throws BIZException, TException {
	    logger.info("EmployeeServiceImpl 开始多职位认领 {}",referralLogIds);
	    if(StringUtils.isRealEmptyList(referralLogIds)){
	        return null;
        }
        try {
            ReferralsCard referralsCard = null;
            List<String> jobTitles = new ArrayList<>(10);
            for(Integer e : referralLogIds){
                com.moseeker.useraccounts.service.impl.pojos.ReferralCard card = service.getReferralCard(e);
                if(referralsCard==null){
                    referralsCard = new ReferralsCard();
                    if(card!=null){
                        referralsCard.setCompanyName(card.getCompanyName());
                        referralsCard.setIsClaimed(card.isClaim()?1:0);
                        referralsCard.setPresenteeFirstName(card.getUserName());
                        referralsCard.setPresenteeId(card.getApplyId());
                        referralsCard.setRecomName(card.getEmployeeName());
                        jobTitles.add(card.getPosition());
                    }
                    return null;
                }
            }
            return referralsCard;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
	public ReferralCard getReferralCard(int referralLogId) throws BIZException, TException {
		try {
			com.moseeker.useraccounts.service.impl.pojos.ReferralCard card = service.getReferralCard(referralLogId);
			ReferralCard referralCard = new ReferralCard();
			BeanUtils.copyProperties(card, referralCard);
			return referralCard;
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public EmployeeInfo getEmployeeInfo(int userId) throws BIZException, TException {
		try {
			EmployeeInfoVO employeeInfoVO = service.getEmployeeInfo(userId);
			EmployeeInfo employeeInfo = new EmployeeInfo();
			BeanUtils.copyProperties(employeeInfoVO, employeeInfo);
			return employeeInfo;
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public void retrySendVerificationMail(int userId, int companyId, int source) throws BIZException, TException {
		try {
			service.retrySendVerificationMail(userId, companyId, source);
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	private ReferralPosition convertReferralPosition(ReferralPositionInfo referralPositionInfo) {
		ReferralPosition referralPosition = new ReferralPosition();

		referralPosition.setLogo(referralPositionInfo.getLogo());
		referralPosition.setCompanyAbbreviation(referralPositionInfo.getCompanyAbbreviation());
		referralPosition.setCompanyName(referralPositionInfo.getCompanyName());
		referralPosition.setExperience(referralPositionInfo.getExperience());
		referralPosition.setExperienceAbove(referralPositionInfo.isExperienceAbove());
		referralPosition.setId(referralPositionInfo.getId());
		referralPosition.setSalaryBottom(referralPositionInfo.getSalaryBottom());
		referralPosition.setSalaryTop(referralPositionInfo.getSalaryTop());
		referralPosition.setTeam(referralPositionInfo.getTeam());
		referralPosition.setTitle(referralPositionInfo.getTitle());

		if (referralPositionInfo.getCities() != null && referralPositionInfo.getCities().size() > 0) {
			List<City> cities = referralPositionInfo.getCities().stream().map(city -> {
				City city1 = new City();
				city1.setName(city.getName());
				city1.setCode(city.getCode());
				city1.setEname(city.getEname());
				return city1;
			}).collect(Collectors.toList());
			referralPosition.setCities(cities);
		}

		return referralPosition;
	}
}
