package com.moseeker.entity.biz;

import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;

import java.util.List;

/**
 * 员工实体业务工具
 * Created by jack on 04/08/2017.
 */
public class EmployeeEntityBiz {

    /**
     * 为每条UserEmployeeStruct生成唯一的值，该值用来判定这条数据是否插入更新或者不处理
     *
     * @param employeeStructs
     * @return
     */
    public static void getUniqueFlagsAndStatus(List<UserEmployeeStruct> employeeStructs, List<String> uniqueFlags, int[] dataStatus) {

        String flag;
        int index = 0;
        for (UserEmployeeStruct struct : employeeStructs) {
            if (struct.isSetCompany_id() && struct.isSetCustom_field()) {
                if (struct.getCustom_field() == null) {
                    struct.setCustom_field("");
                }
                flag = struct.getCompany_id() + "_custom_field_" + struct.getCustom_field().trim();
                if (uniqueFlags.contains(flag)) {
                    dataStatus[index] = 0;//重复的数据
                } else {
                    dataStatus[index] = 1;
                }
                uniqueFlags.add(flag);
            } else if (struct.isSetCompany_id() && struct.isSetCname()) {
                if (struct.getCname() == null) {
                    struct.setCname("");
                }
                flag = struct.getCompany_id() + "_cname_" + struct.getCname().trim();
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
