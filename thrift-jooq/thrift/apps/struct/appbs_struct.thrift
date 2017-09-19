namespace java com.moseeker.thrift.gen.apps.positionbs.struct

typedef string Timestamp

/*
* 申请记录实体
*
*/
struct ThirdPartyPosition {
     1: optional i32 salary_top,             // 薪资上限
     2: optional i32 salary_bottom,          // 薪资下限
     3: optional i32 salary_month,        // 薪资下限
     4: optional i32 count,            	     // 招聘人数
     5: optional string address,             // 工作地址
     6: optional bool use_company_address,   // 是否使用公司地址
     7: optional list<string> occupation,    // 倒数第二级的第三方职位职能
     8: optional i32 channel,                 // 渠道
     9: optional i32 third_party_account_id, // 使用的第三方的帐号的
     10: optional string department,         // 部门名称
     11: optional i32 feedback_period,       // 应聘反馈时常
     12: optional bool salary_discuss = false,               //是否显示为面议
     13: optional i32 practice_salary,                      //实习薪资
     14: optional i8 practice_per_week,      //每周实习天数
     15: optional i8 practice_salary_unit,   //实习薪资单位，0：元/每月，1：元/每天
}

struct ThirdPartyPositionForm {
    1: i32 position_id,				//职位编号
    2: i32 appid,				//调用方编号
    3: list<ThirdPartyPosition> channels,	//渠道参数
} 
