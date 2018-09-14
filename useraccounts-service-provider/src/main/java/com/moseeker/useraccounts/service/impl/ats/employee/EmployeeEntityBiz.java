package com.moseeker.useraccounts.service.impl.ats.employee;

import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeBatchForm;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import com.moseeker.useraccounts.constant.EmployeeAuthMethod;

import java.util.List;

/**
 * 员工实体业务工具
 * Created by jack on 04/08/2017.
 */
public class EmployeeEntityBiz {

    /**
     * 为每条UserEmployeeStruct生成唯一的值，该值用来判定这条数据是否插入更新或者不处理
     *
     * @param batchForm
     * @return
     */
    public static void getUniqueFlagsAndStatus(UserEmployeeBatchForm batchForm, List<String> uniqueFlags, int[] dataStatus) throws BIZException {
        List<UserEmployeeStruct> employeeStructs = batchForm.getData();

        EmployeeAuthMethod authMethod = EmployeeAuthMethod.getAuthMethod(batchForm.getAuth_method());

        String flag;
        int index = 0;
        for (UserEmployeeStruct struct : employeeStructs) {
            if (authMethod.checkDataValid(struct)) {
                flag = authMethod.uniqueKey(struct);
                if (uniqueFlags.contains(flag)) {
                    dataStatus[index] = 0;//重复的数据
                } else {
                    dataStatus[index] = 1;
                }
                uniqueFlags.add(flag);
            } else {
                //无效
                dataStatus[index] = 0;
            }
            index++;
        }
    }
}
