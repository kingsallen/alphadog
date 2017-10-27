package com.moseeker.position.service.third;

import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyAccountDepartmentDao;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountDepartmentDO;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoDepartment;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThirdPartyAccountInfoDepartmentService {
    @Autowired
    ThirdpartyAccountDepartmentDao departmentDao;

    //把ThirdpartyAccountDepartmentDO转换成传给前台的类型ThirdPartyAccountInfoDepartment
    public List<ThirdPartyAccountInfoDepartment> getInfoDepartment(int accountId) throws TException {
        List<ThirdpartyAccountDepartmentDO> departmentList=departmentDao.getDepartmentByAccountId(accountId);
        List<ThirdPartyAccountInfoDepartment> infoDepartmentList=new ArrayList<>();

        departmentList.forEach(d->{
            ThirdPartyAccountInfoDepartment department=new ThirdPartyAccountInfoDepartment();
            department.setId(d.getId());
            department.setName(d.getDepartmentName());
            infoDepartmentList.add(department);
        });

        return infoDepartmentList;
    }
}
