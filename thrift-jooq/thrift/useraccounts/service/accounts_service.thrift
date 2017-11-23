# file: useraccounts.thrift

include "../struct/useraccounts_struct.thrift"
include "../../common/struct/common_struct.thrift"
include "../../foundataionbs/wordpress/struct/wordpress_foundation_strcut.thrift"
include "../struct/bindtype_struct.thrift"
include "../../dao/struct/userdb/user_user_struct.thrift"
include "../../dao/struct/userdb/user_hr_account_struct.thrift"
include "../../dao/struct/userdb/user_employee_struct.thrift"
include "../../dao/struct/hrdb/hr_third_party_account_struct.thrift"
include "../../employee/struct/employee_struct.thrift"
include "../../dao/struct/hrdb/hr_app_export_fields_struct.thrift"



namespace java com.moseeker.thrift.gen.useraccounts.service

/**
* 用户服务
**/
service UseraccountsServices {

    // 用户用户数据
    common_struct.Response getUserById(1: i64 userId);
    common_struct.Response getUsers(1: common_struct.CommonQuery query);

    // 更新用户数据
    common_struct.Response updateUser(1: useraccounts_struct.User user);

    common_struct.Response getismobileregisted(1: string countryCode, 2: string mobile);
    common_struct.Response postuserlogin(1: useraccounts_struct.Userloginreq userloginreq);
    common_struct.Response postuserlogout(1: i32 userid);
    common_struct.Response postsendsignupcode(1: string countryCode, 2: string mobile);
    common_struct.Response postsendsignupcodeVoice(1: string countryCode, 2: string mobile);
    // 用户注册
    //common_struct.Response postusermobilesignup(1: string mobile, 2: string code, 3: string password);
    common_struct.Response postusermobilesignup(1: useraccounts_struct.User user, 2: string code);

    common_struct.Response postuserwxbindmobile(1: i32 appid, 2: string unionid, 3: string code,4: string countryCode, 5: string mobile);
    common_struct.Response postuserbindmobile(1: i32 appid, 2: string unionid, 3: string code, 4: string countryCode, 5: string mobile, 6: bindtype_struct.BindType bindType);
    common_struct.Response postuserchangepassword(1: i32 user_id, 2: string old_password,  3: string password);
    common_struct.Response postusersendpasswordforgotcode(1: string countryCode, 2: string mobile);
    common_struct.Response postvalidatepasswordforgotcode(1: string countryCode, 2: string mobile, 3:string code);
    common_struct.Response validateVerifyCode(1: string countryCode, 2: string mobile, 3:string code, 4:i32 type);
    common_struct.Response sendVerifyCode(1: string countryCode, 2: string mobile, 3:i32 type);
    common_struct.Response checkEmail(1: string email);
    common_struct.Response postuserresetpassword(1: string countryCode, 2: string mobile, 3: string code, 4: string password);
    common_struct.Response postusermergebymobile(1: i32 appid, 2: string countryCode, 3: string mobile);
    common_struct.Response postsendchangemobilecode(1: string countryCode, 2: string oldmobile);
    common_struct.Response postvalidatechangemobilecode(1: string countryCode, 2: string oldmobile, 3:string code);
    common_struct.Response postsendresetmobilecode(1: string countryCode, 2:string newmobile);
    common_struct.Response postresetmobile(1: i32 user_id, 2: string countryCode, 3: string newmobile, 4:string code);

    // 用户是否对该职位已经感兴趣
    common_struct.Response getUserFavPositionCountByUserIdAndPositionId(1: i32 userId, 2: i32 positionId);
    // 用户感兴趣账职位
    common_struct.Response postUserFavoritePosition(1: useraccounts_struct.UserFavoritePosition userFavoritePosition);

    //创建微信二维码
    common_struct.Response cerateQrcode(1: i32 wechatId, 2: i64 sceneId, 3: i32 expireSeconds, 4:i32 action_name);
    //获取qrcode
    common_struct.Response getQrcode(1: string ticket);
    //查询二维码是否被用户扫描
    common_struct.Response getScanResult(1: i32 wechatId, 2: i64 sceneId);
    //设置二维码是否查看
    common_struct.Response setScanResult(1: i32 wechatId, 2: i64 sceneId, 3:string value);

    //根据手机号码获取用户数据
    user_user_struct.UserUserDO ifExistUser(1: string countryCode, 2: string mobile);
    //简历回收的自动生成帐号
    i32 createRetrieveProfileUser(1: user_user_struct.UserUserDO user);
    //查询用户是否存在简历
    bool ifExistProfile(1: string countryCode, 2:string mobile);
    // 换绑操作
    common_struct.Response userChangeBind(1:string unionid, 2: string countryCode, 3:string mobile);


}

/**
* 用户配置服务
**/
service UsersettingServices {
    common_struct.Response getResource(1: common_struct.CommonQuery query);
    common_struct.Response putResource(1: useraccounts_struct.Usersetting usersetting);
    common_struct.Response postResource(1: useraccounts_struct.Usersetting usersetting);
}

/**
* HR账户服务
**/
service UserHrAccountService {
    //更新手机号
    void updateMobile(1:i32 hrId,2:string mobile) throws (1: common_struct.BIZException e);
    //添加帐号
    user_hr_account_struct.UserHrAccountDO addAccount(1:user_hr_account_struct.UserHrAccountDO hrAccount) throws (1: common_struct.BIZException e);
    //是否可以添加子帐号
    bool ifAddSubAccountAllowed(1:i32 hrId) throws (1: common_struct.BIZException e);

    // 发送手机验证码
    common_struct.Response sendMobileVerifiyCode(1: string mobile, 2: string code,  3: i32 source);
    common_struct.Response postResource(1: useraccounts_struct.DownloadReport downloadReport);
    common_struct.Response putResource(1: useraccounts_struct.UserHrAccount userHrAccount);
    //绑定第三方帐号
    hr_third_party_account_struct.HrThirdPartyAccountDO bindThirdPartyAccount(1:i32 hrId,2:hr_third_party_account_struct.HrThirdPartyAccountDO account,3:bool sync) throws (1: common_struct.BIZException e);
    //猎聘确认发送验证码
    hr_third_party_account_struct.HrThirdPartyAccountDO bindConfirm(1:i32 hrId,2:i32 id,3:bool confirm) throws (1: common_struct.BIZException e);
    //猎聘发送验证码
    hr_third_party_account_struct.HrThirdPartyAccountDO bindMessage(1:i32 hrId,2:i32 id,3:string code) throws (1: common_struct.BIZException e);

    //解绑第三方nag帐号
    void unbindThirdPartyAccount(1:i32 accountId,2:i32 userId) throws (1: common_struct.BIZException e);
    //分配第三方帐号
    useraccounts_struct.ThirdPartyAccountInfo dispatchThirdPartyAccount(1:i32 accountId,2:list<i32> hrIds) throws (1: common_struct.BIZException e);
    //获取第三方帐号信息
    useraccounts_struct.ThirdPartyAccountInfo getThirdPartyAccount(1:i32 accountId) throws (1: common_struct.BIZException e);


    // 获取常用筛选项
    common_struct.Response getSearchCondition(1: i32 hrAccountId, 2: i32 type);
    // 保存常用筛选项
    common_struct.Response postSearchCondition(1: useraccounts_struct.SearchCondition searchCondition)
    // 删除常用筛选项
    common_struct.Response delSearchCondition(1: i32 hrAccountId, 2: i32 id);
    // 加入人才库
    common_struct.Response joinTalentpool(1: i32 hrAccountId, 2: list<i32> applierIds)
    // 移出人才库
    common_struct.Response shiftOutTalentpool(1: i32 hrAccountId, 2: list<i32> applierIds)

    // 获取userHrAccount
    common_struct.Response userHrAccount(1: i32 company_id, 2: i32 disable,3:i32 page ,4:i32 per_age)

    //nps调研接口
    useraccounts_struct.HrNpsResult npsStatus(1:i32 userId,2:string startDate,3:string endDate) throws (1: common_struct.BIZException e);

    useraccounts_struct.HrNpsResult npsUpdate(1:useraccounts_struct.HrNpsUpdate npsUpdate)  throws (1: common_struct.BIZException e);

    useraccounts_struct.HrNpsStatistic npsList(1:string startDate,2:string endDate,3:i32 page,4:i32 pageSize)  throws (1: common_struct.BIZException e);

    list<hr_third_party_account_struct.HrThirdPartyAccountDO> getThirdPartyAccounts(1: common_struct.CommonQuery query);

    i32 updateThirdPartyAccount(1: hr_third_party_account_struct.HrThirdPartyAccountDO account)  throws (1: common_struct.BIZException e);

    bool permissionJudgeWithUserEmployeeIdsAndCompanyIds(1: list<i32> userEmployeeIds,2:list<i32> companyIds) throws (1: common_struct.BIZException e);

    bool permissionJudgeWithUserEmployeeIdsAndCompanyId(1:  list<i32> userEmployeeIds,2:i32 companyId) throws (1: common_struct.BIZException e);

    bool permissionJudgeWithUserEmployeeIdAndCompanyId(1: i32 userEmployeeId,2:i32 companyId) throws (1: common_struct.BIZException e);

    // 员工取消认证
    bool unbindEmployee(1: list<i32> ids) throws (1: common_struct.BIZException e);
    // 删除员工
    bool delEmployee(1: list<i32> ids) throws (1: common_struct.BIZException e);
    // 获取员工积分列表
    employee_struct.RewardVOPageVO getEmployeeRewards(1: i32 employeeId, 2:i32 companyId, 3:i32 pageNumber, 4:i32 pageSize) throws (1: common_struct.BIZException e);
    // 员工添加积分
    i32 addEmployeeReward(1: i32 employeeId, 2: i32 companyId, 3: i32 points, 4: string reason) throws (1: common_struct.BIZException e);
    // 通过公司ID和关键字,查询认证员工和未认证员工数量
    useraccounts_struct.UserEmployeeNumStatistic getListNum(1:string keyWord, 2:i32 companyId) throws (1: common_struct.BIZException e);
    // 员工列表
    useraccounts_struct.UserEmployeeVOPageVO employeeList(1:string keword, 2:i32 companyId, 3:i32 filter, 4:string order, 5:string asc, 6:i32 pageNumber, 7:i32 pageSize,8:string timespan) throws (1: common_struct.BIZException e);
    // 员工信息导出
    list<useraccounts_struct.UserEmployeeVO> employeeExport(1:list<i32> userEmployees,2:i32 companyId,3:i32 type) throws (1: common_struct.BIZException e);
    // 员工信息
    useraccounts_struct.UserEmployeeDetailVO userEmployeeDetail(1:i32 userEmployeeId,2:i32 companyId) throws (1: common_struct.BIZException e)
    // 更新公司员工信息
    common_struct.Response updateUserEmployee(1:string cname, 2:string mobile, 3:string email, 4:string customField, 5:i32 userEmployeeId,6:i32 companyId,7:string customFieldValues) throws (1: common_struct.BIZException e)
    // 员工信息导入
    common_struct.Response employeeImport(1:map<i32,user_employee_struct.UserEmployeeDO> userEmployeeDOS, 2:i32 companyId,3:string filePath,4:string fileName,5:i32 type,6:i32 hraccountId) throws (1: common_struct.BIZException e)
    // 检查员工重复
    useraccounts_struct.ImportUserEmployeeStatistic checkBatchInsert(1:map<i32,user_employee_struct.UserEmployeeDO> userEmployeeDOS, 2:i32 companyId) throws (1: common_struct.BIZException e)
    //查询自定义导出字段
    list<hr_app_export_fields_struct.HrAppExportFieldsDO> getExportFields(1: i32 companyId, 2: i32 userHrAccountId) throws (1: common_struct.BIZException e)
}



/**
* 所有用户通用的服务
**/
service UserCommonService {

    // 获取新版本内容
    common_struct.Response newsletter(1: wordpress_foundation_strcut.NewsletterForm form);
}

service UserCenterService {
    //查询用户的申请记录
    list<useraccounts_struct.ApplicationRecordsForm> getApplications(1: i32 userId);
    useraccounts_struct.ApplicationDetailVO getApplicationDetail(1: i32 userId, 2: i32 appId);
    //查询用户的只为收藏记录
    list<useraccounts_struct.FavPositionForm> getFavPositions(1: i32 userId);
    //查询推荐记录
    useraccounts_struct.RecommendationVO getRecommendation(1: i32 userId, 2:i8 type, 3: i32 pageNo, 4: i32 pageSize);
}

//user thirdparty user 服务
service ThirdPartyUserService {
    //更新账号
    common_struct.Response updateUser(1: useraccounts_struct.ThirdPartyUser user);

}

//UserEmployeeDao数据库单表操作
service UserEmployeeService {

    common_struct.Response getUserEmployee(1: common_struct.CommonQuery query);

    common_struct.Response getUserEmployees(1: common_struct.CommonQuery query);

    common_struct.Response delUserEmployee(1: common_struct.CommonQuery query);

    common_struct.Response postPutUserEmployeeBatch(1:useraccounts_struct.UserEmployeeBatchForm batchForm);

    bool isEmployee(1: i32 userId, 2: i32 companyId) throws (1: common_struct.BIZException e);

    common_struct.Response putUserEmployee(1:useraccounts_struct.UserEmployeeStruct userEmployee) throws (1: common_struct.BIZException e);
}
