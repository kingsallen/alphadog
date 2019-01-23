package com.moseeker.useraccounts.thrift;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee;
import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.exception.Category;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.PaginationUtil;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;
import com.moseeker.thrift.gen.useraccounts.service.UserEmployeeService;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.exception.ExceptionFactory;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.constant.AwardEvent;
import com.moseeker.useraccounts.service.impl.UserEmployeeServiceImpl;
import com.moseeker.useraccounts.service.impl.pojos.ContributionDetail;
import com.moseeker.useraccounts.service.impl.pojos.EmployeeForwardViewVO;
import com.moseeker.useraccounts.service.impl.pojos.RadarInfoVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by eddie on 2017/3/9.
 */
@Service
public class UserEmployeeThriftService implements UserEmployeeService.Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserEmployeeServiceImpl employeeService;

    @Autowired
    private EmployeeEntity employeeEntity;


    @Override
    public Response getUserEmployee(CommonQuery query) throws TException {
        try {
            return employeeService.getUserEmployee(query);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response getUserEmployees(CommonQuery query) throws TException {
        try {
            return employeeService.getUserEmployees(query);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response delUserEmployee(CommonQuery query) throws TException {
        try {
            return employeeService.delUserEmployee(query);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }


    @Override
    public Response postPutUserEmployeeBatch(UserEmployeeBatchForm batchForm) throws TException {
        try {
            return employeeService.postPutUserEmployeeBatch(batchForm);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public boolean isEmployee(int userId, int companyId) throws BIZException, TException {
        try {
            return employeeEntity.isEmployee(userId, companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response putUserEmployee(UserEmployeeStruct userEmployee) throws BIZException, TException {
        try {
            return employeeService.putResource(userEmployee);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public void addEmployeeAward(List<Integer> applicationIdList, int eventType) throws BIZException, TException {
        /** 初始化业务编号 */
        AwardEvent awardEvent = AwardEvent.initFromSate(eventType);
        ValidateUtil vu = new ValidateUtil();
        vu.addRequiredOneValidate("申请编号", applicationIdList, null, null);
        vu.addRequiredValidate("积分事件", awardEvent, null, null);
        String result = vu.validate();
        if (StringUtils.isNotBlank(result)) {
            throw ExceptionConvertUtil.convertCommonException(UserAccountException.validateFailed(result));
        }
        employeeService.addEmployeeAward(applicationIdList, awardEvent);
    }

    @Override
    public Response getValidateUserEmployee(int company_id, String email, int pageNum, int pageSize) throws BIZException, TException {
        try{
            UserEmployeeVOPageVO VO=employeeService.getUserEmployeeEmailValidate(company_id,email,pageNum,pageSize);
            return ResponseUtils.success(VO);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }


    /*
     注意这里的companyId是hrId,积重难返
     */
    @Override
    public Response getPastUserEmployee(int company_id) throws BIZException, TException {
        try{
            List<Map<String,Object>> list=employeeService.getPastUserEmployeeEmail(company_id);
            if(list!=null&&list.size()>0){
                return ResponseUtils.success(list);
            }
            return ResponseUtils.successWithoutStringify(null);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Pagination getContributions(int companyId, int pageNum, int pageSize) throws BIZException, TException {
        try {
            PaginationUtil<ContributionDetail> paginationUtil = employeeService.getContributions(companyId, pageNum, pageSize);
            Pagination pagination = new Pagination();
            pagination.setPageSize(paginationUtil.getPageSize());
            pagination.setPageNum(paginationUtil.getPageNum());
            pagination.setTotalRow(paginationUtil.getTotalRow());
            if (paginationUtil.getList() != null && paginationUtil.getList().size() > 0) {
                List<EmployeeReferralContribution> list = paginationUtil.getList()
                        .stream()
                        .map(detail -> {
                            EmployeeReferralContribution employeeReferralContribution = new EmployeeReferralContribution();
                            BeanUtils.copyProperties(detail, employeeReferralContribution);
                            return employeeReferralContribution;
                        })
                        .collect(Collectors.toList());
                pagination.setDetails(list);
            }
            return pagination;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }


    @Override
    public Response getUserEmployeeList(int companyId, List<Integer> userIdList) throws TException {
        try {
            List<UserEmployee> result=employeeService.getuserEmployeeList(companyId,userIdList);
            return ResponseUtils.success(result);
        }catch (Exception e){
            throw ExceptionUtils.convertException(e);
        }
    }


    @Override
    public PositionReferralInfo getPositionReferralInfo(int userId, int positionId) throws BIZException, TException {
        try {
            com.moseeker.useraccounts.pojo.PositionReferralInfo info = employeeService.getPositionReferralInfo(userId, positionId);
            logger.info("getPositionReferralInfo info:{}",JSON.toJSONString(info));
            PositionReferralInfo referralInfo = new PositionReferralInfo();
            BeanUtils.copyProperties(info, referralInfo);
            logger.info("getPositionReferralInfo referralInfo:{}", JSON.toJSONString(referralInfo));
            return referralInfo;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public RadarInfo fetchRadarIndex(int userId, int companyId, int page, int size) throws BIZException, TException {
        RadarInfoVO infoVO = employeeService.fetchRadarIndex(companyId, userId, page, size);
        return copyRadarInfoVO(infoVO);
    }

    @Override
    public EmployeeForwardViewPage fetchEmployeeForwardView(int userId, int companyId, String positionTitle,
                                                            String order, int page, int size) throws BIZException, TException {
        EmployeeForwardViewVO viewVO = employeeService.fetchEmployeeForwardView(companyId,userId, positionTitle, order, page, size);
        EmployeeForwardViewPage result = new EmployeeForwardViewPage();
        result.setPage(viewVO.getPage());
        result.setTotalCount(viewVO.getTotalCount());
        if(!com.moseeker.common.util.StringUtils.isEmptyList(viewVO.getUserList())){
            List<EmployeeForwardView> forwardViews = new ArrayList<>();
            viewVO.getUserList().forEach(view -> {
                EmployeeForwardView forwardView = new EmployeeForwardView();
                BeanUtils.copyProperties(view, forwardView);
                if(!com.moseeker.common.util.StringUtils.isEmptyList(view.getChain())){
                    List<Connection> connectionList = new ArrayList<>();
                    view.getChain().forEach(chain -> {
                        Connection connection = new Connection();
                        BeanUtils.copyProperties(chain, connection);
                        connectionList.add(connection);
                    });
                    forwardView.setChain(connectionList);
                }
                forwardViews.add(forwardView);
            });
            result.setUserList(forwardViews);
        }
        return result;
    }

    @Override
    public RadarInfo fetchEmployeeSeekRecommendPage(int userId, int companyId, int page, int size) throws BIZException, TException {
        RadarInfoVO infoVO = employeeService.fetchEmployeeSeekRecommend(companyId, userId, page, size);
        return copyRadarInfoVO(infoVO);
    }

    private RadarInfo copyRadarInfoVO(RadarInfoVO infoVO){
        RadarInfo radarInfo = new RadarInfo();
        if(!com.moseeker.common.util.StringUtils.isEmptyList(infoVO.getUserList())){
            radarInfo.setUserList( infoVO.getUserList().stream().map(m ->{
                RadarUserInfo userInfo = new RadarUserInfo();
                BeanUtils.copyProperties(m, userInfo);
                return userInfo;
            }).collect(Collectors.toList()));
            radarInfo.setPage(infoVO.getPage());
            radarInfo.setTotalCount(infoVO.getTotalCount());
        }
        return radarInfo;
    }

    @Override
    public Response getUserEmployeeByUserIdListAndCompanyList(List<Integer> userIdList, List<Integer> companyIdList) throws TException {
        try{
            List<UserEmployee> result=employeeService.getEmployeeByUserIdListAndCompanyList(userIdList,companyIdList);
            return ResponseUtils.success(result);
        }catch(Exception e){
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response addUserEmployeePointRecord(int employeeId, int companyId, UserEmployeePointsRecordDO record) throws TException {
        try {
            employeeEntity.addReward(employeeId, companyId, record);
            return ResponseUtils.success(true);
        }catch (Exception e){
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getUserEmployeeByuserId(int userId) throws TException {
        try{
            UserEmployee result=employeeService.getSingleUserEmployee(userId);
            return ResponseUtils.success(result);
        }catch(Exception e){
            throw ExceptionUtils.convertException(e);
        }
    }

}
