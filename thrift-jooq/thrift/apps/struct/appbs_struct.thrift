namespace java com.moseeker.thrift.gen.apps.positionbs.struct

typedef string Timestamp

/*
* 申请记录实体
*
*/
struct ThirdPartyPosition {
     1: optional i32 salaryTop,             // 薪资上限
     2: optional i32 salaryBottom,          // 薪资下限
     3: optional i32 salaryMonth,        // 薪资下限
     4: optional i32 count,            	     // 招聘人数
     5: optional bool useCompanyAddress,   // 是否使用公司地址
     6: optional list<string> occupation,    // 倒数第二级的第三方职位职能
     7: optional i32 channel,                 // 渠道
     8: optional i32 thirdPartyAccountId, // 使用的第三方的帐号的
     9: optional i32 feedbackPeriod,       // 应聘反馈时常
     10: optional bool salaryDiscuss = false,               //是否显示为面议
     11: optional i32 practiceSalary,                      //实习薪资
     12: optional i8 practicePerWeek,      //每周实习天数
     13: optional i8 practiceSalaryUnit,   //实习薪资单位，0：元/每月，1：元/每天

     //3.5.9.3加上的字段
     14:optional i32 departmentId,         //部门id
     15:optional string departmentName,    //部门名称
     16:optional i32 companyId,            //公司id
     17:optional string companyName,       //公司名称
     18:optional i32 addressId,                //地址id
     19:optional string addressName               //地址全称

     //3.5.9.3废弃的字段
     //5: optional string address,             // 工作地址
     //10: optional string department,         // 部门名称

}

struct ThirdPartyPositionForm {
    1: i32 positionId,				//职位编号
    2: i32 appid,				//调用方编号
    3: list<ThirdPartyPosition> channels,	//渠道参数
} 
