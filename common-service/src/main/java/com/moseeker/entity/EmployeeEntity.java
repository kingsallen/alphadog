package com.moseeker.entity;

import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
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
    private UserEmployeeDao dao;

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
     * 判断是否是员工
     * @param userId
     * @return
     */
    public boolean isEmployee(int userId) {
        return false;
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
