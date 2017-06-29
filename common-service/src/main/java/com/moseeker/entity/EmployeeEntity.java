package com.moseeker.entity;

import com.moseeker.baseorm.dao.hrdb.HrGroupCompanyRelDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.hrdb.tables.HrGroupCompanyRel;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrGroupCompanyRelDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Service
public class EmployeeEntity {

    @Autowired
    private UserEmployeeDao userEmployeeDao;

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
     * 判断是否是员工
     *
     * @param userId
     * @return
     */
    public boolean isEmployee(int userId) {
        return false;
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
        queryBuilder.where(HrGroupCompanyRel.HR_GROUP_COMPANY_REL.COMPANY_ID + "", companyId);
        HrGroupCompanyRelDO hrGroupCompanyRelDO = hrGroupCompanyRelDao.getData(queryBuilder.buildQuery());
        // 没有集团信息，返回当前companyId
        if (StringUtils.isEmptyObject(hrGroupCompanyRelDO)) {
            logger.info("未查询到该公司的集团ID");
            list.add(companyId);
            return list;
        }
        queryBuilder.clear();
        queryBuilder.where(HrGroupCompanyRel.HR_GROUP_COMPANY_REL.GROUP_ID + "", hrGroupCompanyRelDO.getGroupId());
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
            Condition condition = new Condition(UserEmployee.USER_EMPLOYEE.COMPANY_ID + "", companyIds, ValueOp.IN);
            queryBuilder.where(condition);
            userEmployeeDOS = userEmployeeDao.getDatas(queryBuilder.buildQuery());
        }
        return userEmployeeDOS;
    }
}
