package com.moseeker.position.service.third;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionInfo;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionInfoForm;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhangdi on 2017/7/17.
 */
@Service
public class ThirdPositionService {

    @Autowired
    HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    HRThirdPartyPositionDao thirdPartyPositionDao;

    @Autowired
    UserHrAccountDao hrAccountDao;

    @Autowired
    HrCompanyAccountDao companyAccountDao;

    @Autowired
    HrCompanyDao companyDao;

    @Autowired
    JobPositionDao positionDao;

    @CounterIface
    public ThirdPartyPositionResult getThirdPartyPositionInfo(ThirdPartyPositionInfoForm infoForm) {

        ThirdPartyPositionResult result = new ThirdPartyPositionResult();
        List<ThirdPartyPositionInfo> datas = new ArrayList<>();

        int page = infoForm.getPage();
        int pageSize = infoForm.getPageSize();
        int total = 0;

        if (page < 1) {
            page = 1;
        }

        if (pageSize < 1) {
            pageSize = 50;
        }

        if (pageSize > 500) {
            pageSize = 500;
        }

        result.setPage(page);
        result.setPageSize(pageSize);
        result.setTotal(total);
        result.setData(datas);

        List<HrThirdPartyAccountDO> formThirdAccounts = null;
        Map<Integer, HrThirdPartyAccountDO> formThirdAccountMap = new HashMap<>();

        Query.QueryBuilder queryBuilder = null;
        //采用第三方帐号的名字过滤
        if (StringUtils.isNotNullOrEmpty(infoForm.getThirdAccountName())) {
            queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(new Condition(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.USERNAME.getName(), "%" + infoForm.getThirdAccountName() + "%", ValueOp.LIKE)).buildQuery();
            formThirdAccounts = thirdPartyAccountDao.getDatas(queryBuilder.buildQuery());
            if (formThirdAccounts == null || formThirdAccounts.size() == 0) {
                return result;
            }

            for (HrThirdPartyAccountDO thirdPartyAccount : formThirdAccounts) {
                formThirdAccountMap.put(thirdPartyAccount.getId(), thirdPartyAccount);
            }
        }

        queryBuilder = new Query.QueryBuilder();

        if (formThirdAccountMap.size() > 0) {
            queryBuilder.where(new Condition(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.THIRD_PARTY_ACCOUNT_ID.getName(), formThirdAccountMap.keySet(), ValueOp.IN));
        }

        if (infoForm.getChannel() > 0) {
            queryBuilder.where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.CHANNEL.getName(), infoForm.getChannel());
        }
        if (infoForm.getStatus() > 0 && infoForm.getStatus() < 5) {
            queryBuilder.where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(), infoForm.getStatus());
        } else if (infoForm.getStatus() > 0 && infoForm.getStatus() < 9) {
            queryBuilder.where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_REFRESH.getName(), infoForm.getStatus() - 4);
        }

        if (infoForm.getPositionId() > 0) {
            queryBuilder.where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(), infoForm.getPositionId());
        }

        if (infoForm.getThirdPositionId() > 0) {
            queryBuilder.where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.ID.getName(), infoForm.getThirdPositionId());
        }

        if (StringUtils.isNotNullOrEmpty(infoForm.getThirdPositionNumber())) {
            queryBuilder.where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.THIRD_PART_POSITION_ID.getName(), infoForm.getThirdPositionId());
        }

        total = thirdPartyPositionDao.getCount(queryBuilder.buildQuery());
        result.setTotal(total);

        queryBuilder.setPageNum(page);
        queryBuilder.setPageSize(pageSize);
        queryBuilder.orderBy(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.UPDATE_TIME.getName(), Order.DESC);

        /** 找到所有的第三方职位 **/
        List<HrThirdPartyPositionDO> thirdPositions = thirdPartyPositionDao.getDatas(queryBuilder.buildQuery());

        if (thirdPositions == null || thirdPositions.size() == 0) {
            return result;
        }

        //所有的职位ID
        Set<Integer> positionIds = new HashSet<>();
        //所有的第三方帐号ID
        Set<Integer> thirdAccountIds = new HashSet<>();
        for (HrThirdPartyPositionDO thirdPartyPosition : thirdPositions) {
            positionIds.add(thirdPartyPosition.getPositionId());
            thirdAccountIds.add(thirdPartyPosition.getThirdPartyAccountId());
        }

        Map<Integer, HrThirdPartyAccountDO> thirdAccountMap = new HashMap<>();
        //所有的第三方账号，如果过滤条件中有第三方帐号的名字,直接从之前的列表中查找
        if (formThirdAccountMap.size() > 0) {
            for (Integer id : thirdAccountIds) {
                thirdAccountMap.put(id, formThirdAccountMap.get(id));
            }
        } else {
            queryBuilder = new Query.QueryBuilder().where(new Condition(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID.getName(), thirdAccountIds, ValueOp.IN));
            List<HrThirdPartyAccountDO> thirdPartyAccounts = thirdPartyAccountDao.getDatas(queryBuilder.buildQuery());
            for (HrThirdPartyAccountDO account : thirdPartyAccounts) {
                thirdAccountMap.put(account.getId(), account);
            }
        }

        /** 所有的职位 **/
        queryBuilder = new Query.QueryBuilder().where(new Condition(JobPosition.JOB_POSITION.ID.getName(), positionIds, ValueOp.IN));
        Map<Integer, JobPositionDO> positionMap = new HashMap<>();
        List<JobPositionDO> positions = positionDao.getDatas(queryBuilder.buildQuery());
        for (JobPositionDO position : positions) {
            positionMap.put(position.getId(), position);
        }

        /**　获取所有的HR **/
        Set<Integer> hrIds = new HashSet<>();
        for (JobPositionDO position : positions) {
            hrIds.add(position.getPublisher());
        }
        queryBuilder = new Query.QueryBuilder().where(new Condition(UserHrAccount.USER_HR_ACCOUNT.ID.getName(), hrIds, ValueOp.IN));
        Map<Integer, UserHrAccountDO> hrMap = new HashMap<>();
        List<UserHrAccountDO> hrAccounts = hrAccountDao.getDatas(queryBuilder.buildQuery());
        for (UserHrAccountDO account : hrAccounts) {
            hrMap.put(account.getId(), account);
        }

        /**　获取所有的HR子公司简称　**/

        //先找到HR的子公司
        queryBuilder = new Query.QueryBuilder().where(new Condition(HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID.getName(), hrIds, ValueOp.IN));
        List<HrCompanyAccountDO> hrCompanys = companyAccountDao.getDatas(queryBuilder.buildQuery());
        Map<Integer, Integer> hrCompanyMap = new HashMap<>();
        for (HrCompanyAccountDO hrCompanyAccount : hrCompanys) {
            hrCompanyMap.put(hrCompanyAccount.getAccountId(), hrCompanyAccount.getCompanyId());
        }
        //找到所有的子公司
        Set<Integer> companyIds = new HashSet<>();
        for (HrCompanyAccountDO companyAccountDO : hrCompanys) {
            companyIds.add(companyAccountDO.getCompanyId());
        }
        queryBuilder = new Query.QueryBuilder().where(new Condition(HrCompany.HR_COMPANY.ID.getName(), companyIds, ValueOp.IN));
        List<HrCompanyDO> companys = companyDao.getDatas(queryBuilder.buildQuery());
        Map<Integer, HrCompanyDO> companyMap = new HashMap<>();
        for (HrCompanyDO company : companys) {
            companyMap.put(company.getId(), company);
        }


        /** 组装结果　**/
        ThirdPartyPositionInfo data;
        UserHrAccountDO hr;
        JobPositionDO position;
        HrCompanyDO company;
        HrThirdPartyAccountDO thirdAccount;
        for (HrThirdPartyPositionDO thirdPosition : thirdPositions) {
            data = new ThirdPartyPositionInfo();
            position = positionMap.get(thirdPosition.getPositionId());
            thirdAccount = thirdAccountMap.get(thirdPosition.getThirdPartyAccountId());
            if (position != null) {
                hr = hrMap.get(position.getPublisher());
                company = companyMap.get(hrCompanyMap.get(position.getPublisher()));
            } else {
                hr = null;
                company = null;
            }

            data.setThirdPosition(thirdPosition);
            data.setThirdAccount(thirdAccount);
            data.setPosition(position);
            data.setHr(hr);
            data.setCompany(company);
            datas.add(data);
        }

        return result;
    }


    public int updateThirdPartyPosition(HrThirdPartyPositionDO data) {
        if (data.getId() == 0) {
            throw new CommonException(-1, "ID不能为空!");
        }
        int result = thirdPartyPositionDao.updateData(data);

        if (result < 1) {
            throw ExceptionUtils.getCommonException(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
        }

        return result;
    }
}
