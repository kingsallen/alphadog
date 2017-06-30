package com.moseeker.entity;

import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Service
public class EmployeeEntity {

    @Autowired
    private UserEmployeeDao employeeDao;

    /**
     * 获取员工详情
     * @param employeeId
     * @return
     */
    public UserEmployeeDO getEmployee(int employeeId) {
        return null;
    }

    /**
     * 员工列表
     * @param pageSize 每页大小
     * @param pageNum 页码
     * @return
     */
    public List<UserEmployeeDO> employeeList(int pageSize, int pageNum, String ... str) {
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
     * @param employeeId
     * @param award
     * @return
     */
    public int plusReward(int employeeId, int award) {
        return 0;
    }

    /**
     * 员工删除
     * @param employeeId
     */
    public void removeEmployee(int employeeId) {

    }

    /**
     * 员工信息去重
     * @param data
     * @param companyId
     * @return
     */
    public List<UserEmployeeDO> distinctEmployee(List<UserEmployeeDO> data, int companyId) {
        return null;
    }
}
