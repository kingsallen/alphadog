package com.moseeker.entity.exception;

import com.moseeker.common.exception.CommonException;

/**
 * @Author: jack
 * @Date: 2018/8/19
 */
public class EmployeeException extends CommonException {

    public static final EmployeeException EMPLOYEE_WECHAT_NOT_EXISTS = new EmployeeException(42500, "公众号信息错误!");
    public static final EmployeeException EMPLOYEE_NOT_EXISTS= new EmployeeException(42501, "员工信息不存在!");
    public static final EmployeeException EMPLOYEE_VERIFY_AWARD_NOT_EXIST= new EmployeeException(42502, "员工积分配置信息不存在!");
    public static final EmployeeException EMPLOYEE_AWARD_NOT_ENOUGH= new EmployeeException(42503, "用户积分不足!");
    public static final EmployeeException EMPLOYEE_AWARD_ADD_FAILED= new EmployeeException(42504, "积分添加失败!");
    public static final EmployeeException EMPLOYEE_AWARD_REPEAT_PLUS= new EmployeeException(42505, "重复添加积分!");
    public static final EmployeeException EMPLOYEE_REPEAT_RECOMMEND= new EmployeeException(42506, "感谢您的支持，此候选人已存在系统中，此次推荐不成功。期待您继续推荐其他候选人！");
    public static final EmployeeException EMPLOYEE_REPEAT_CLAIM= new EmployeeException(42507, "重复认领!");
    public static final EmployeeException EMPLOYEE_AWARD_ZERO= new EmployeeException(42508, "添加积分点数不能为0");


    public EmployeeException(int code, String message) {
        super(code, message);
    }
}
