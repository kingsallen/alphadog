# file: company.struct

namespace java com.moseeker.thrift.gen.company.struct
include "../../dao/struct/hrdb_struct.thrift"


typedef string Timestamp


struct Hrcompany { 
   1: optional i32 id,
   2: optional i32 type,
   3: optional string name,
   4: optional string introduction,
   5: optional string scale,
   6: optional string address,
   7: optional i32 property,
   8: optional string industry,
   9: optional string homepage,
   10: optional string logo,
   11: optional string abbreviation,
   12: optional string impression,
   13: optional string banner,
   14: optional i32 parent_id,
   15: optional i32 hraccount_id,
   16: optional i32 disable,
   17: optional Timestamp create_time,
   18: optional Timestamp update_time,
   19: optional i32 source,
   20: optional string feature
}

struct CompanyForVerifyEmployee{
    1: optional i32 id,                 //公司编号
    2: optional string name,            //公司名称
    3: optional string abbreviation,    //公司简称
    4: optional string signature        //公司简称
}

// 获取公司部门与职能信息(员工认证补填字段显示)
struct CompanyOptions{
    1:list <hrdb_struct.HrEmployeePositionDO> hrEmployeePosition,
    2:list <hrdb_struct.HrEmployeeSectionDO> hrEmployeeSection
}

// 公司认证配置
struct CompanyCertConf{
    1:list<hrdb_struct.HrEmployeeCertConfDO> hrEmployeeCertConf,
    2:HrImporterMonitorVO hrImporterMonitor
}


struct HrImporterMonitorVO{
	1: optional i32 id,	//null
	2: optional i32 companyId,	//公司ID，hr_company.id
	3: optional i32 hraccountId,	//hr_account.id 账号编号
	4: optional double type,	//要导入的表：0：user_employee 1: job_position 2:hr_company
	5: optional string file,	//导入文件的绝对路径
	6: optional double status,	//0：待处理 1：处理失败 2：处理成功
	7: optional string message,	//操作提示信息
	8: optional string createTime,	//null
	9: optional string updateTime,	//null
	10: optional string fileName,	//导入的文件名
	11: optional double sys	//1:mp, 2:hr

}

struct HrEmployeeCustomFieldsVO{
    1: optional i32 id,	//null
    2: optional string fname,
    3: optional list<string> fvalues,
    4: optional i32 option_type //选项类型  0:下拉选项, 1:文本
}

struct HrCompanyConf {

	1: optional i32 company_id,	//null
	2: optional i32 theme_id,	//config_sys_theme.id
	3: optional i32 hb_throttle,	//全局每人每次红包活动可以获得的红包金额上限
	4: optional string app_reply,	//申请提交成功回复信息
	5: optional string create_time,	//创建时间
	6: optional string update_time,	//更新时间
	7: optional string employee_inding,	//员工认证自定义文案
	8: optional string recommend_presentee,	//推荐候选人自定义文案
	9: optional string recommend_success,	//推荐成功自定义文案
	10: optional string forward_message,	//转发职位自定义文案
	11: optional i16 application_count_limit,	//一个人在一个公司下每月申请次数限制
	12: optional string job_custom_title,	//职位自定义字段标题
	13: optional string search_seq,	//搜索页页面设置顺序,3#1#2
	14: optional string search_img,	//搜索页页面设置背景图
	15: optional string job_occupation,	//自定义字段名称
	16: optional string teamname_custom,	//自定义部门别名
	17: optional string application_time,	//newjd_status即新的jd页的生效时间，
	18: optional i32 newjd_status,	//新jd页去设置状态 0是为开启，1是用户开启，2是审核通过（使用新jd），3撤销（返回基础版） 默认是0
	19: optional double hr_chat,	//IM聊天开关，0：不开启，1：开启
	20: optional double show_in_qx,	//公司信息、团队信息、职位信息在仟寻展示，0: 否， 1: 是
	21: optional string employee_slug,	//员工自定义称谓
	22: optional string display_locale,	//员工自定义称谓
	23: optional i8 talentpool_status,
	24: optional i16 school_application_count_limit, //一个人在一个公司下每月校招职位申请次数限制
	25: optional i16 job51_salary_discuss,    //51薪资面议开关 0：未开启，1：开启
    26: optional i16 veryeast_switch         //最佳东方c端简历导入开关 0：未开启，1：开启
    27: optional i16 is_open_gdpr

}

struct HrCompanyFeatureDO {
    1: optional  i32    id,
    2: optional  i32    company_id,
    3: optional  string feature,
    4: optional  string create_time,
    5: optional  string update_time,
    6: optional  i32    disable
}

struct HrCompanyWechatDO {
    1: optional  i32    companyId,
    2: optional  i32    wechatId,
    3: optional  string signature,
    4: optional  string accessToken,
    5: optional  string templateId,
    6: optional  string topcolor,
    7: optional  i32    employeeCount
}

struct GDPRProtectedInfo {
    1 : optional i32 userId,
    2 : optional bool trigger,
}

struct CompanySwitchVO{
    1: optional i32 id;
    2: optional i32 companyId;
    3: optional string keyword;
    4: optional string fieldValue;
    5: optional i8 valid;
}

// 企业微信配置
struct WorkWxCertConf{
    1: optional i32 companyId,
    2: optional string corpid;
    3: optional string secret;
    4: optional i32 errCode;
    5: optional string errMsg;
    6: optional string accessToken;
    7: optional string jsapiTicket;
}