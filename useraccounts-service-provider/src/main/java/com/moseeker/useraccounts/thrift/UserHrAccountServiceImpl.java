package com.moseeker.useraccounts.thrift;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.exception.HRException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrAppExportFieldsDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.employee.struct.BonusVOPageVO;
import com.moseeker.thrift.gen.employee.struct.RewardVOPageVO;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.exception.ExceptionCategory;
import com.moseeker.useraccounts.exception.ExceptionFactory;
import com.moseeker.useraccounts.service.impl.UserHrAccountService;
import com.moseeker.useraccounts.service.thirdpartyaccount.ThirdPartyAccountService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    private ThirdPartyAccountService thirdPartyAccountService;

    @Autowired
    private EmployeeEntity employeeEntity;

    @Override
    public void updateMobile(int hrId, String mobile) throws TException {
        try {
            service.updateMobile(hrId, mobile);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public UserHrAccountDO addAccount(UserHrAccountDO hrAccount) throws TException {
        try {
            return service.addAccount(hrAccount);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public int addSubAccount(UserHrAccountDO hrAccount) throws BIZException, TException {
        try {
            return service.addSubAccount(hrAccount);
        } catch (CommonException e1) {
            throw ExceptionConvertUtil.convertCommonException(e1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public boolean ifAddSubAccountAllowed(int hrId) throws TException {
        try {
            return service.ifAddSubAccountAllowed(hrId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * HR在下载行业报告是注册
     *
     * @param mobile 手机号
     * @param code   验证码
     * @param source 系统区分 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号)
     */
    @Override
    public Response sendMobileVerifiyCode(String mobile, String code, int source) throws TException {
        try {
            return service.sendMobileVerifiyCode(mobile, code, source);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 下载行业报告，添加HR记录
     */
    @Override
    public Response postResource(DownloadReport downloadReport) throws TException {
        try {
            return service.postResource(downloadReport);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 更新HR账户信息
     *
     * @param userHrAccount 用户实体
     */
    @Override
    public Response putResource(UserHrAccount userHrAccount) throws TException {
        try {
            return service.putResource(userHrAccount);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public HrThirdPartyAccountDO bindThirdPartyAccount(int hrId, HrThirdPartyAccountDO account, boolean sync) throws BIZException, TException {
        try {
            return thirdPartyAccountService.bindThirdAccount(hrId, account, sync);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }

    public HrThirdPartyAccountDO syncThirdPartyAccount(int hrId, int id, boolean sync) throws BIZException, TException {
        return null;
    }

    @Override
    public HrThirdPartyAccountDO bindConfirm(int hrId, int id, boolean confirm) throws BIZException, TException {
        try {
            return thirdPartyAccountService.bindConfirm(hrId,id,confirm);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public HrThirdPartyAccountDO bindMessage(int hrId, int id, String code) throws BIZException, TException {
        try {
            return thirdPartyAccountService.bindMessage(hrId, id, code);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void unbindThirdPartyAccount(int accountId, int userId) throws BIZException, TException {
        try {
            thirdPartyAccountService.unbindingAccount(accountId, userId);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response deleteThirdPartyAccount(int accountId,int userId) throws BIZException, TException {
        try {
            return thirdPartyAccountService.deleteThirdPartyAccount(accountId,userId);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public ThirdPartyAccountInfo dispatchThirdPartyAccount(int accountId, List<Integer> hrIds) throws BIZException, TException {
        try {
            return thirdPartyAccountService.dispatch(accountId, hrIds);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public ThirdPartyAccountInfo getThirdPartyAccount(int accountId) throws BIZException, TException {
        try {
            return thirdPartyAccountService.getThridAccount(accountId);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getSearchCondition(int hrAccountId, int type)
            throws TException {
        try {
            return service.getSearchCondition(hrAccountId, type);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response postSearchCondition(SearchCondition searchCondition)
            throws TException {
        try {
            return service.postSearchCondition(searchCondition);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response delSearchCondition(int hrAccountId, int id)
            throws TException {
        try {
            return service.delSearchCondition(hrAccountId, id);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
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
        try {
            return service.shiftOutTalentpool(hrAccountId, applierIds);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response userHrAccount(int company_id, int disable, int page, int per_age) throws TException {
        try {
            return service.userHrAccount(company_id, disable, page, per_age);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
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
    public boolean permissionJudgeWithUserEmployeeIdsAndCompanyIds(List<Integer> userEmployeeIds, List<Integer> companyIds) throws BIZException, TException {
        try {
            return employeeEntity.permissionJudge(userEmployeeIds, companyIds);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public boolean permissionJudgeWithUserEmployeeIdsAndCompanyId(List<Integer> userEmployeeIds, int companyId) throws BIZException, TException {
        try {
            return employeeEntity.permissionJudge(userEmployeeIds, companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }


    @Override
    public boolean permissionJudgeWithUserEmployeeIdAndCompanyId(int userEmployeeId, int companyId) throws BIZException, TException {
        try {
            return employeeEntity.permissionJudge(userEmployeeId, companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }


    /**
     * 员工取消认证(支持批量)
     *
     * @param ids 员工ID列表
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public boolean unbindEmployee(List<Integer> ids) throws BIZException, TException {
        try {
            return employeeEntity.unbind(ids);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 删除员工 (支持批量)
     *
     * @param ids 员工ID列表
     * @return
     * @throws BIZException
     * @throws TException
     */
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


    /**
     * 积分列表
     *
     * @param employeeId 员工ID
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public RewardVOPageVO getEmployeeRewards(int employeeId, int companyId, int pageNumber, int pageSize) throws BIZException {
        try {
            return service.getEmployeeRewards(employeeId, companyId, pageNumber, pageSize);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 员工积分添加
     *
     * @param employeeId 员工ID
     * @param points     积分
     * @param reason     描述
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public int addEmployeeReward(int employeeId, int companyId, int points, String reason) throws BIZException, TException {
        try {
            UserEmployeePointsRecordDO ueprDo = new UserEmployeePointsRecordDO();
            ueprDo.setAward(points);
            ueprDo.setEmployeeId(employeeId);
            ueprDo.setReason(reason);
            return employeeEntity.addReward(employeeId, companyId, ueprDo);
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
    public UserEmployeeNumStatistic getListNum(String keyWord, int companyId) throws BIZException {
        try {
            return service.getListNum(keyWord, companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }


    /**
     * 员工列表
     *
     * @param keyword    关键字搜索
     * @param companyId  公司ID
     * @param filter     过滤条件，0：全部，1：已认证，2：未认证,默认：0
     * @param order      排序条件
     * @param asc        正序，倒序 0: 正序,1:倒序 默认
     * @param pageNumber 第几页
     * @param pageSize   每页的条数
     * @param timeSpan   月，季，年 2017 代表年积分，2017-08 代表月积分 20171 代表第一季度的积分
     */
    @Override
    public UserEmployeeVOPageVO employeeList(String keyword, int companyId, int filter, String order, String asc, int pageNumber, int pageSize, String timeSpan,String email_validate) throws BIZException, TException {
        try {
            return service.employeeList(keyword, companyId, filter, order, asc, pageNumber, pageSize, timeSpan,email_validate);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }

    }

    /**
     * 员工列表
     * @param keyword 关键词
     * @param companyId 公司编号
     * @param filter 过滤条件，0：全部，1：已认证，2：未认证， 3 撤销认证,默认：0
     * @param order 排序字段
     * @param asc 升序降序
     * @param pageNumber 页码
     * @param pageSize 每页数量
     * @param email_validate 邮箱认证条件
     * @param timeSpan 时间
     * @return 员工列表
     * @throws BIZException 业务异常
     * @throws TException rpc异常
     */
    @Override
    public UserEmployeeVOPageVO getEmployees(String keyword, int companyId, int filter, String order, String asc,
                                             int pageNumber, int pageSize, String email_validate,int balanceType,
                                             String timeSpan, String selectIds)
            throws BIZException, TException {
        try {
            return service.getEmployees(keyword, companyId, filter, order, asc, pageNumber, pageSize, email_validate,
                    balanceType, timeSpan, selectIds);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    /**
     * 员工信息导出
     *
     * @param userEmployees 员工ID列表
     * @param companyId     公司ID
     * @return
     */
    @Override
    public List<UserEmployeeVO> employeeExport(List<Integer> userEmployees, int companyId, int type) throws BIZException, TException {
        try {
            return service.employeeExport(userEmployees, companyId, type);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 员工信息
     *
     * @param userEmployeeId 员工ID
     * @param companyId      公司ID
     */
    @Override
    public UserEmployeeDetailVO userEmployeeDetail(int userEmployeeId, int companyId) throws BIZException, TException {
        try {
            return service.userEmployeeDetail(userEmployeeId, companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 编辑公司员工信息
     *
     * @param cname             姓名
     * @param mobile            手机号
     * @param email             邮箱
     * @param customField       自定义字段
     * @param userEmployeeId    user_employee.id
     * @param companyId         公司ID
     * @param customFieldValues 公司员工认证后补填字段配置信息
     * @return
     * @throws BIZException
     */
    @Override
    public Response updateUserEmployee(String cname, String mobile, String email, String customField, int userEmployeeId, int companyId, String customFieldValues) throws BIZException, TException {
        try {
            return service.updateUserEmployee(cname, mobile, email, customField, userEmployeeId, companyId, customFieldValues);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 员工信息导入
     *
     * @param userEmployeeDOMap
     * @param companyId
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public Response employeeImport(Map<Integer, UserEmployeeDO> userEmployeeDOMap, int companyId, String filePath, String fileName, int type, int hraccountId) throws BIZException, TException {
        try {
            return service.employeeImport(companyId, userEmployeeDOMap, filePath, fileName, type, hraccountId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public ImportUserEmployeeStatistic updateEmployee(List<UserEmployeeDO> userEmployeeDOS, int companyId, String filePath, String fileName, int type, int hraccountId) throws BIZException, TException {
        try {
            return service.updateEmployees(companyId, userEmployeeDOS, filePath, fileName, type, hraccountId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    /**
     * 检查员工重复(批量导入之前验证)
     *
     * @param userEmployeeDOMap
     * @param companyId
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public ImportUserEmployeeStatistic checkBatchInsert(Map<Integer, UserEmployeeDO> userEmployeeDOMap, int companyId) throws BIZException, TException {
        try {
            return service.checkBatchInsert(userEmployeeDOMap, companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public List<HrAppExportFieldsDO> getExportFields(int companyId, int userHrAccountId) throws TException {
        try {
            return service.getExportFields(companyId, userHrAccountId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response getHrCompanyInfo(int wechat_id, String unionId, int account_id) throws BIZException, TException {
        Response response = service.getHrCompanyInfo(wechat_id, unionId, account_id);
        logger.info("getHrCompanyInfo fanhuizhi:{}", response);
        return response;
    }

    @Override
    public UserHrAccountDO switchChatLeaveToMobot(int accountId, byte leaveToMobot) throws BIZException, TException {
        try {
            return service.switchChatLeaveToMobot(accountId,leaveToMobot);
        } catch (HRException e){
            throw ExceptionUtils.convertException(e);
        } catch (BIZException e){
            throw e;
        } catch (Exception e){
            logger.error("switch Chat LeaveToMobot error:{},accountId:{},leaveToMobot:{}",e,accountId,leaveToMobot);
            throw new SysBIZException();
        }

    }

    @Override
    public Response getThirdPartyAccountDO(int channel) throws BIZException, TException {
        try {
            return thirdPartyAccountService.getBoundThirdPartyAccountDO(channel);
        } catch (BIZException e){
            throw e;
        } catch (Exception e){
            logger.error("================getThirdPartyAccountDO error:{},hrId:{},channel:{}=============", e, channel);
            throw new SysBIZException();
        }
    }

    @Override
    public List<HrThirdPartyAccountDO>  getUnBindThirdPartyAccountDO(int channel) throws BIZException, TException {
        try {
            return thirdPartyAccountService.getUnBindThirdPartyAccountDO(channel);
        } catch (BIZException e){
            throw e;
        } catch (Exception e){
            logger.error("================getThirdPartyAccountDO error:{},hrId:{},channel:{}=============", e, channel);
            throw new SysBIZException();
        }
    }

    @Override
    public String bindLiepinUserAccount(String liepinToken, int liepinUserId, int hrThirdAccountId) throws BIZException, TException {
        try {
            return thirdPartyAccountService.bindLiepinUserAccount(liepinToken, liepinUserId, hrThirdAccountId);
        } catch (BIZException e){
            throw e;
        } catch (Exception e){
            logger.error("==========bindLiepinUserAccount liepinToken:{},liepinUserId:{},hrThirdAccountId:{}=========", liepinToken,liepinUserId,hrThirdAccountId,e);
            throw new SysBIZException();
        }
    }

    @Override
    public HRInfo getHR(int id) throws BIZException, TException {
        try {
            return service.getHR(id);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response setApplicationNotify(int hrAccountId, boolean flag) throws BIZException, TException {
        return service.setApplicationNotify(hrAccountId,flag);
    }

    @Override
    public Response getApplicationNotify(int hrAccountId) throws BIZException, TException {
        return service.getApplicationNotify(hrAccountId);
    }

    @Override
    public BonusVOPageVO getEmployeeBonus(int employeeId, int companyId, int pageNumber, int pageSize) throws TException {
        return service.getEmployeeBonus(employeeId,companyId,pageNumber,pageSize);
    }

    @Override
    public HrThirdPartyAccountDO getJob58BindResult(int channel, String key) throws BIZException, TException {
        try {
            return thirdPartyAccountService.getJob58BindResult(channel, key);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }

}
