namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrThirdPartyPositionDO {

	1: optional i32 id,	//null
    2: optional i32 positionId,	//jobdb.job_position.id
    3: optional string thirdPartPositionId,	//第三方渠道编号
    4: optional i32 isSynchronization,	//是否同步:0=未同步,1=同步,2=同步中，3=同步失败
    5: optional i32 isRefresh,	//是否刷新:0=未刷新,1=刷新,2=刷新中
    6: optional string syncTime,	//职位同步时间
    7: optional string refreshTime,	//职位刷新时间
    8: optional string updateTime,	//数据更新时间
    9: optional string address,	//详细地址
    10: optional string occupation,	//同步时选中的第三方职位职能
    11: optional string syncFailReason,	//失败原因
    12: optional i32 useCompanyAddress,	//使用企业地址
    13: optional i32 thirdPartyAccountId,	//第三方账号ID
    14: optional i32 channel,	//1=51job,2=猎聘,3=智联,4=linkedin
    15: optional string department,	//同步时自定义或者匹配的部门名
    16: optional i32 salaryMonth,	//发放月薪数
    17: optional i32 feedbackPeriod,	//招聘反馈时长
    18: optional i32 salaryDiscuss,	//是否显示为面议0否1是
    19: optional i32 salaryBottom,	//薪资底线
    20: optional i32 salaryTop,	//薪资封顶
    21: optional i32 practiceSalary, //实习薪资
    22: optional i8 practicePerWeek, //每周实习天数
    23: optional i8 practiceSalaryUnit, //实习薪资单位，0：元/月；1：元/天
    24: optional i32 companyId,
    25: optional string companyName,
    26: optional i32 addressId,
    27: optional string addressName,
    28: optional i32 departmentId,
    29: optional string departmentName,
    30: optional i32 count,  //招聘人数
    31: optional string feature, //福利特色
    32: optional bool internship //是否使用实习职位的额度
}