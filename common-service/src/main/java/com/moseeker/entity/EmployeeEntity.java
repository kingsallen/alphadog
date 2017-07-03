package com.moseeker.entity;

import com.moseeker.baseorm.dao.hrdb.HrGroupCompanyRelDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.util.query.Query;
import com.moseeker.baseorm.db.hrdb.tables.HrGroupCompanyRel;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrGroupCompanyRelDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Service
public class EmployeeEntity {

    @Autowired
    private UserEmployeeDao employeeDao;


    @Autowired
    private HrGroupCompanyRelDao hrGroupCompanyRelDao;


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 获取员工详情
     *
     * @param employeeId
     * @return
     */
    public UserEmployeeDO getEmployee(int employeeId) {
        return null;
    }

    /**
     * 员工列表
     *
     * @param pageSize 每页大小
     * @param pageNum  页码
     * @return
     */
    public List<UserEmployeeDO> employeeList(int pageSize, int pageNum, String... str) {
        return null;
    }

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

        // TODO 查找集团公司列表...
        query.where("company_id", String.valueOf(companyId)).and("sysuser_id", String.valueOf(userId))
                .and("disable", "0");

        return employeeDao.getData(query.buildQuery());
    }

    /**
     * 增加积点
     *
     * @param employeeId
     * @param award
     * @return
     */
    public int plusReward(int employeeId, int award) {
        return 0;
    }

    /**
     * 员工删除
     *
     * @param employeeId
     */
    public void removeEmployee(int employeeId) {

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
