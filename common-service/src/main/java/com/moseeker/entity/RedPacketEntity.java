package com.moseeker.entity;

import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.common.exception.CommonException;
import com.moseeker.entity.pojo.readpacket.RedPacketData;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: jack
 * @Date: 2018/10/22
 */
@Component
public class RedPacketEntity {

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private HrWxWechatDao hrWxWechatDao;

    public RedPacketData fetchRadPacketDataByEmployeeId(Integer id) throws CommonException {

        RedPacketData data = new RedPacketData();
        UserEmployeeDO employeeDO = employeeEntity.getEmployeeByID(id);
        if (employeeDO.getActivation() == EmployeeActiveState.Actived.getState()) {
            data.setCompanyId(employeeDO.getCompanyId());
            data.setUserId(employeeDO.getSysuserId());

            HrWxWechatDO hrWxWechatDO = hrWxWechatDao.getHrWxWechatByCompanyId(data.getCompanyId());
            if (hrWxWechatDO != null) {
                data.setWechatId(hrWxWechatDO.getId());
            }
        }
        return data;
    }
}
