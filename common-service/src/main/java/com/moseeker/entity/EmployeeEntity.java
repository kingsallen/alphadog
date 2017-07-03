package com.moseeker.entity;

import com.moseeker.baseorm.dao.historydb.HistoryUserEmployeeDao;
import com.moseeker.baseorm.dao.hrdb.HrGroupCompanyRelDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.baseorm.dao.userdb.UserEmployeePointsRecordDao;
import com.moseeker.common.util.query.Query;
import com.moseeker.baseorm.db.hrdb.tables.HrGroupCompanyRel;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrGroupCompanyRelDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Service
public class EmployeeEntity {

    @Autowired
    private UserEmployeeDao employeeDao;

    @Autowired
    private HrGroupCompanyRelDao hrGroupCompanyRelDao;

    @Autowired
    private HistoryUserEmployeeDao historyUserEmployeeDao;

    @Autowired
    private UserEmployeePointsRecordDao ueprDao;


    private static final Logger logger = LoggerFactory.getLogger(EmployeeEntity.class);

    /**
     * 判断某个用户是否属于某个公司的员工
     * @param userId
     * @param companyId 如果公司属于集团公司，需验证用户是否在集团下的所有公司中认证过员工
     * @return
     */
    public boolean isEmployee(int userId, int companyId) {
        UserEmployeeDO employee = getCompanyEmployee(userId, companyId);
        if (employee != null && employee.getId() > 0 && employee.getActivation() == 0) {
            return true;
        }
        return false;
    }

    public UserEmployeeDO getCompanyEmployee(int userId, int companyId) {

        Query.QueryBuilder query = new Query.QueryBuilder();

        // 查找集团公司列表
        List<Integer> companyIds = getCompanyIds(companyId);
        companyIds.add(companyId);

        query.where(new Condition("company_id", companyIds, ValueOp.IN)).and("sysuser_id", String.valueOf(userId))
                .and("disable", "0");

        return employeeDao.getData(query.buildQuery());
    }

    /**
     * 增加员工积点
     * @param employeeId
     * @param award (注意: award可以为负数...)
     * @return 员工当前总积分
     */
    @Transactional
    public int addReward(int employeeId, int award, String reason) throws Exception {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("id", employeeId);
        UserEmployeeDO userEmployeeDO = employeeDao.getData(query.buildQuery());
        if (userEmployeeDO != null && userEmployeeDO.getId() > 0) {
            // 修改用户总积分
            userEmployeeDO.setAward(userEmployeeDO.getAward() + award);
            int row = employeeDao.updateData(userEmployeeDO);
            // 积分记录
            if (row > 0) {
                UserEmployeePointsRecordDO ueprDo = new UserEmployeePointsRecordDO();
                ueprDo.setAward(award);
                ueprDo.setEmployeeId(employeeId);
                ueprDo.setReason(reason);
                ueprDo = ueprDao.addData(ueprDo);
                if (ueprDo.getId() > 0) {
                    logger.info("增加用户积分成功：为用户{},添加积分{}点, reason:{}", employeeId, award, reason);
                    return userEmployeeDO.getAward();
                } else {
                    logger.error("增加用户积分失败：为用户{},添加积分{}点, reason:{}", employeeId, award, reason);
                    throw new RuntimeException("增加积分失败");
                }
            }
        }
        return 0;
    }

    /**
     * 员工取消认证（支持批量）
     * @param employeeIds
     * @return
     */
    public boolean unbind(List<Integer> employeeIds) {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.and(new Condition("id", employeeIds, ValueOp.IN));
        List<UserEmployeeDO> employeeDOList = employeeDao.getDatas(query.buildQuery());
        if (employeeDOList != null && employeeDOList.size() > 0) {
            employeeDOList.stream().filter(f -> f.getActivation() == 0).forEach(e -> {e.setActivation((byte)1); e.setEmailIsvalid((byte)0);});
            int[] rows = employeeDao.updateDatas(employeeDOList);
            if(Arrays.stream(rows).sum() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 员工删除(支持批量)
     * 1.将数据移入到history_user_employee中
     * 2.user_employee中做物理删除
     */
    @Transactional
    public void removeEmployee(List<Integer> employeeIds) {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where(new Condition("id", employeeIds, ValueOp.IN));
        List<UserEmployeeDO> userEmployeeDOList = employeeDao.getDatas(query.buildQuery());
        if (userEmployeeDOList != null && userEmployeeDOList.size() > 0) {
            int[] rows = employeeDao.deleteDatas(userEmployeeDOList);
            // 受影响行数大于零，说明删除成功， 将数据copy到history_user_employee中
            if(Arrays.stream(rows).sum() > 0) {
                historyUserEmployeeDao.addAllData(userEmployeeDOList);
            }
        }
    }

    /**
     * 员工信息去重
     *
     * @param data
     * @param companyId
     * @return
     */
    public List<UserEmployeeDO> distinctEmployee(List<UserEmployeeDO> data, int companyId) {
        return null;
    }


    /**
     * 通过companyId 查询集团下所有的公司ID列表
     *
     * @return
     */
    public List<Integer> getCompanyIds(Integer companyId) {
        List<Integer> list = new ArrayList<>();
        logger.info("compangId:{}", companyId);
        // 查询集团ID
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrGroupCompanyRel.HR_GROUP_COMPANY_REL.COMPANY_ID.getName(), companyId);
        HrGroupCompanyRelDO hrGroupCompanyRelDO = hrGroupCompanyRelDao.getData(queryBuilder.buildQuery());
        // 没有集团信息，返回当前companyId
        if (StringUtils.isEmptyObject(hrGroupCompanyRelDO)) {
            logger.info("未查询到该公司的集团ID");
            list.add(companyId);
            return list;
        }
        queryBuilder.clear();
        queryBuilder.where(HrGroupCompanyRel.HR_GROUP_COMPANY_REL.GROUP_ID.getName(), hrGroupCompanyRelDO.getGroupId());
        List<HrGroupCompanyRelDO> hrGroupCompanyRelDOS = hrGroupCompanyRelDao.getDatas(queryBuilder.buildQuery());
        if (StringUtils.isEmptyList(hrGroupCompanyRelDOS)) {
            return list;
        }
        // 返回CompanyList
        hrGroupCompanyRelDOS.forEach(hrGroupCompanyRelDOTemp ->
                list.add(hrGroupCompanyRelDOTemp.getCompanyId())
        );
        return list;
    }


    /**
     * 通过公司ID查集团下所有的员工列表
     *
     * @param companyId
     * @return
     */
    public List<UserEmployeeDO> getUserEmployeeDOList(Integer companyId) {
        List<UserEmployeeDO> userEmployeeDOS = new ArrayList<>();
        if (companyId != 0) {
            // 首先通过CompanyId 查询到该公司集团下所有的公司ID
            List<Integer> companyIds = getCompanyIds(companyId);
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.select(UserEmployee.USER_EMPLOYEE.ID.getName())
                    .select(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName())
                    .select(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.getName());
            Condition condition = new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyIds, ValueOp.IN);
            queryBuilder.where(condition).and(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), AbleFlag.OLDENABLE.getValue());
            userEmployeeDOS = employeeDao.getDatas(queryBuilder.buildQuery());
        }
        return userEmployeeDOS;
    }
}
