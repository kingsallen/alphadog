package com.moseeker.useraccounts.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.BindAccountStruct;
import com.moseeker.thrift.gen.useraccounts.struct.DownloadReport;
import com.moseeker.thrift.gen.useraccounts.struct.SearchCondition;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import com.moseeker.useraccounts.service.impl.UserHrAccountService;

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
     * @param userHrAccount hr用户实体
     * @param code          验证码
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
        return service.joinTalentpool(hrAccountId, applierIds);
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

}
