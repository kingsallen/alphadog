package com.moseeker.useraccounts.thrift;

import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.service.impl.UserHrAccountService;
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

    /**
     * HR在下载行业报告是注册
     *
     * @param mobile 手机号
     * @param code   验证码
     * @param source 系统区分
     *               1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号)
     */
    @Override
    public Response sendMobileVerifiyCode(String mobile, String code, int source) throws TException {
        return service.sendMobileVerifiyCode(mobile, code, source);
    }

    /**
     * 下载行业报告，添加HR记录
     *
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
    public Response bind(BindAccountStruct account) throws TException {
        return service.bindThirdAccount(account);
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
    public Response synchronizeThirdpartyAccount(int id) throws TException {
        // TODO Auto-generated method stub
        return service.synchronizeThirdpartyAccount(id);
    }

    @Override
    public Response ifSynchronizePosition(int companyId, int channel) throws TException {
        // TODO Auto-generated method stub
        return service.ifSynchronizePosition(companyId, channel);
    }

    @Override
    public Response addThirdPartyAccount(int userId, BindAccountStruct account) throws TException {
        return service.addThirdPartyAccount(userId, account);
    }

    @Override
    public Response updateThirdPartyAccount(int accountId, BindAccountStruct account) throws TException {
        return service.updateThirdPartyAccount(accountId, account);
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
}
