namespace java com.moseeker.thrift.gen.employee.service

include "../../common/struct/common_struct.thrift"
include "../../dao/struct/hrdb/hr_company_referral_conf_struct.thrift"
include "../struct/employee_struct.thrift"

/*
*  员工服务接口
*/
service EmployeeService {
    // 获取用户员工信息
    employee_struct.EmployeeResponse getEmployee(1: i32 userId, 2: i32 companyId);

    // 获取公司员工认证配置信息
    employee_struct.EmployeeVerificationConfResponse getEmployeeVerificationConf(1: i32 companyId);
    
    // 获取公司员工认证配置信息
    employee_struct.EmployeeVerificationConfResponse getEmployeeVerificationConfByUserId(1: i32 userId) throws (1: common_struct.BIZException e);


    // 员工绑定操作
    employee_struct.Result bind(1: employee_struct.BindingParams bindingParams,2: i32 bindSource);

    // 员工解绑操作
    employee_struct.Result unbind(1: i32 employeeId, 2: i32 companyId, 3: i32 userId, 4: i8 activationChange);

    // 获取员工认证自定义字段配置信息
    list<employee_struct.EmployeeCustomFieldsConf> getEmployeeCustomFieldsConf(1: i32 companyId);

    // 员工填写认证自定义字段
    employee_struct.Result setEmployeeCustomInfo(1: i32 employeeId, 2: string customValues);

    // 获取员工积分
    employee_struct.RewardsResponse getEmployeeRewards(1: i32 employeeId, 2: i32 companyId, 3: i32 pageNumber, 4: i32 pageSize);

    // 推荐记录
    list<employee_struct.RecomInfo> getEmployeeRecoms(1: i32 recomId);

    // 员工绑定(邮箱激活)
    employee_struct.Result emailActivation(1: string activationCodee,2: i32 bindEmailSource);

    // 积分排行榜
    employee_struct.Pagination awardRanking(1: i32 employeeId, 2: i32 companyId, 3: employee_struct.Timespan timespan, 4: i32 pageNum, 5:i32 pageSize) throws (1: common_struct.BIZException e);

    // 员工填写认证自定义字段（更新redis中员工记录）
    employee_struct.Result setCacheEmployeeCustomInfo(1: i32 userId, 2: i32 companyId, 3: string customValues);
    
    //更新员工自定义字段
    void patchEmployeeCustomFieldValues(1: i32 userId, 2: i32 companyId, 3: map<i32, list<string>> customValues);

    //插入更新公司内推政策数据
    void upsertCompanyReferralConf(1:hr_company_referral_conf_struct.HrCompanyReferralConfDO conf)throws (1: common_struct.BIZException e)
    //获取公司内推配置信息
    common_struct.Response getCompanyReferralConf(1:i32 companyId)throws (1: common_struct.BIZException e)
    //插入更新员工点击想要了解内推政策按钮次数
    void updsertCompanyReferralPocily(1:i32 companyId, 2:i32 userId)throws (1: common_struct.BIZException e)
    //计算员工被点赞的数量
    i32 countUpVote(1:i32 employeeId)throws (1: common_struct.BIZException e)
    //计算未查阅的点赞数量
    i32 countRecentUpVote(1:i32 employeeId)throws (1: common_struct.BIZException e)
    //点赞
    i32 upvote(1:i32 employeeId, 2: i32 userId)throws (1: common_struct.BIZException e)
    //取消点赞
    void removeUpvote(1:i32 employeeId, 2:i32 userId)throws (1: common_struct.BIZException e)
    //每周清空点赞
    void clearUpVoteWeekly()throws (1: common_struct.BIZException e)
    //员工榜单信息
    employee_struct.LeaderBoardInfo fetchLeaderBoardInfo(1:i32 id, 2: i32 type)throws (1: common_struct.BIZException e)
    //最后一名非指定员工的榜单信息 
    employee_struct.LeaderBoardInfo fetchLastLeaderBoardInfo(1:i32 id, 2: i32 type)throws (1: common_struct.BIZException e)
    //查找榜单信息
    employee_struct.LeaderBoardType fetchLeaderBoardType(1:i32 companyId)throws (1: common_struct.BIZException e)
    //修改榜单类型
    void updateLeaderBoardType(1:i32 companyId, 2: i8 type)throws (1: common_struct.BIZException e)
    //员工数量
    i32 countEmplyee(1:i32 companyId)throws (1: common_struct.BIZException e)
    //设置 
    void setUploadType(1:i32 employeeId, 2: i32 positionId, 3: i8 type)throws (1: common_struct.BIZException e)
    //获取设置电脑端上传配置的信息
    employee_struct.ReferralPosition getUploadType(1:i32 employeeId)throws (1: common_struct.BIZException e)
    //获取推荐名片
    employee_struct.ReferralCard getReferralCard(1: i32 referralLogId)throws (1: common_struct.BIZException e)
    //多职位获取推荐名片
    employee_struct.ReferralsCard getReferralsCard(1: list<i32> referralLogIds)throws (1: common_struct.BIZException e)
    //获取员工信息
    employee_struct.EmployeeInfo getEmployeeInfo(1: i32 userId)throws (1: common_struct.BIZException e)
    //重新发送邮件
    void retrySendVerificationMail(1: i32 userId, 2: i32 companyId, 3: i32 source)throws (1: common_struct.BIZException e)
}
