package com.moseeker.useraccounts.thrift;

import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.employee.struct.Reward;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.exception.ExceptionCategory;
import com.moseeker.useraccounts.exception.ExceptionFactory;
import com.moseeker.useraccounts.service.impl.UserHrAccountService;

import java.util.ArrayList;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * HR账号服务
 * <p>
 * <p>
 * Created by zzh on 16/5/31.
 */
@Service
public class UserHrAccountServiceImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserHrAccountService service;

    @Autowired
    private EmployeeEntity employeeEntity;

    /**
     * HR在下载行业报告是注册
     *
     * @param mobile 手机号
     * @param code   验证码
     * @param source 系统区分 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号)
     */
    @Override
    public Response sendMobileVerifiyCode(String mobile, String code, int source) throws TException {
        return service.sendMobileVerifiyCode(mobile, code, source);
    }

    /**
     * 下载行业报告，添加HR记录
     */
    @Override
    public Response postResource(DownloadReport downloadReport) throws TException {
        return service.postResource(downloadReport);
    }

    /**
     * 更新HR账户信息
     *
     * @param userHrAccount 用户实体
     */
    @Override
    public Response putResource(UserHrAccount userHrAccount) throws TException {
        return service.putResource(userHrAccount);
    }

    @Override
    public HrThirdPartyAccountDO bindThirdpartyAccount(int hrId, HrThirdPartyAccountDO account) throws BIZException, TException {
        try {
            return service.bindThirdAccount(hrId, account);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new TException(e.getMessage());
        }
    }

    @Override
    public HrThirdPartyAccountDO syncThirdpartyAccount(int id) throws BIZException, TException {
        try {
            return service.synchronizeThirdpartyAccount(id);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new TException(e.getMessage());
        }
    }

    @Override
    public Response getSearchCondition(int hrAccountId, int type)
            throws TException {
        logger.info("UserHrAccountServiceImpl - getSearchCondition ");
        return service.getSearchCondition(hrAccountId, type);
    }

    @Override
    public Response postSearchCondition(SearchCondition searchCondition)
            throws TException {
        return service.postSearchCondition(searchCondition);
    }

    @Override
    public Response delSearchCondition(int hrAccountId, int id)
            throws TException {
        return service.delSearchCondition(hrAccountId, id);
    }

    @Override
    public Response joinTalentpool(int hrAccountId, List<Integer> applierIds)
            throws TException {
        try {
            return service.joinTalentpool(hrAccountId, applierIds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Response shiftOutTalentpool(int hrAccountId, List<Integer> applierIds)
            throws TException {
        return service.shiftOutTalentpool(hrAccountId, applierIds);
    }

    @Override
    public Response userHrAccount(int company_id, int disable, int page, int per_age) throws TException {
        return service.userHrAccount(company_id, disable, page, per_age);
    }

    @Override
    public Response ifSynchronizePosition(int companyId, int channel) throws TException {
        // TODO Auto-generated method stub
        return service.ifSynchronizePosition(companyId, channel);
    }

    @Override
    public HrNpsResult npsStatus(int userId, String startDate, String endDate) throws BIZException, TException {
        try {
            return service.npsStatus(userId, startDate, endDate);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
    }

    @Override
    public HrNpsResult npsUpdate(HrNpsUpdate npsUpdate) throws BIZException, TException {
        try {
            return service.npsUpdate(npsUpdate);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
    }

    @Override
    public HrNpsStatistic npsList(String startDate, String endDate, int page, int pageSize) throws BIZException, TException {
        try {
            return service.npsList(startDate, endDate, page, pageSize);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
    }

    @Override
    public List<HrThirdPartyAccountDO> getThirdPartyAccounts(CommonQuery query) throws TException {
        try {
            return service.getThirdPartyAccounts(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public int updateThirdPartyAccount(HrThirdPartyAccountDO account) throws BIZException, TException {
        try {
            int result = service.updateThirdPartyAccount(account);
            if (result < 1) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
            }
            return result;
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public boolean unbindEmployee(List<Integer> ids) throws BIZException, TException {
        return employeeEntity.unbind(ids);
    }

    @Override
    public boolean delEmployee(List<Integer> ids) throws BIZException, TException {
        try {
            return employeeEntity.removeEmployee(ids);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ExceptionFactory.buildException(ExceptionCategory.PROGRAM_EXCEPTION);
        }
        return false;
    }

    @Override
    public List<Reward> getEmployeeRewards(int employeeId) throws BIZException, TException {
        List<Reward> result = new ArrayList<>();
        try {
            result = employeeEntity.getEmployeePointsRecords(employeeId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ExceptionFactory.buildException(ExceptionCategory.PROGRAM_EXCEPTION);
        }
        return result;
    }

    @Override
    public int addEmployeeReward(int employeeId, int points, String reason) throws BIZException, TException {
        try {
            return employeeEntity.addReward(employeeId, points, reason);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            ExceptionFactory.buildException(ExceptionCategory.PROGRAM_EXCEPTION);
        }
        return 0;
    }


    /**
     * 获取列表number
     * 通过公司ID,查询认证员工和未认证员工数量
     *
     * @param keyWord   关键字
     * @param companyId 公司ID
     * @return
     */
    @Override
    public UserEmployeeNumStatistic getListNum(String keyWord, int companyId) throws BIZException, TException {
        return service.getListNum(keyWord, companyId);
    }

    @Override
    public UserEmployeeVOPageVO employeeList(String keword, int companyId, int filter, String order, int by, int pageNumber, int pageSize) throws BIZException, TException {
        return service.employeeList(keword, companyId, filter, order, by, pageNumber, pageSize);
    }
}
