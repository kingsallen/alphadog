namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobPositionDO {

	1: optional i32 id,	//null
	2: optional string jobnumber,	//职位编号
	3: optional i32 companyId,	//company.id
	4: optional string title,	//职位标题
	5: optional string province,	//所在省
	6: optional string city,	//所在城市，多城市使用中文逗号分割
	7: optional string department,	//所在部门
	8: optional i32 lJobid,	//jobid from ATS or other channel
	9: optional string publishDate,	//Default: now, set by js
	10: optional string stopDate,	//截止日期
	11: optional string accountabilities,	//Job responsibilities职位描述
	12: optional string experience,	//工作经验
	13: optional string requirement,	//职位要求
	14: optional string salary,	//薪水
	15: optional string language,	//外语要求
	16: optional i32 jobGrade,	//优先级
	17: optional double status,	//0 有效, 1 删除, 2 撤下
	18: optional i32 visitnum,	//null
	19: optional string lastvisit,	//openid of last visiter
	20: optional i32 sourceId,	//职位来源 0：Moseeker
	21: optional string updateTime,	//null
	22: optional string businessGroup,	//事业群
	23: optional double employmentType,	//0:全职，1：兼职：2：合同工 3:实习 9:其他
	24: optional string hrEmail,	//HR联系人邮箱，申请通知
	25: optional string benefits,	//职位福利
	26: optional double degree,	//0:无 1:大专 2:本科 3:硕士 4:MBA 5:博士 6:中专 7:高中 8: 博士后 9:初中
	27: optional string feature,	//职位特色，多福利特色使用#分割
	28: optional i8 emailNotice,	//申请后是否给 HR 发送邮件 0:发送 1:不发送
	29: optional double candidateSource,	//0:社招 1：校招 2:定向招聘
	30: optional string occupation,	//职位职能
	31: optional i32 isRecom,	//是否需要推荐0：需要 1：不需要
	32: optional string industry,	//所属行业
	33: optional i32 hongbaoConfigId,	//null
	34: optional i32 hongbaoConfigRecomId,	//null
	35: optional i32 hongbaoConfigAppId,	//null
	36: optional double emailResumeConf,	//0:允许使用email简历进行投递；1:不允许使用email简历投递
	37: optional double lPostingtargetid,	//lumesse每一个职位会生成一个PostingTargetId,用来生成每个职位的投递邮箱地址
	38: optional double priority,	//是否置顶
	39: optional double shareTplId,	//分享分类0:无1:高大上2：小清新3：逗比
	40: optional string district,	//添加区(省市区的区)
	41: optional double count,	//添加招聘人数, 0：不限
	42: optional double salaryTop,	//薪资上限（k）
	43: optional double salaryBottom,	//薪资下限（k）
	44: optional i8 experienceAbove,	//及以上 1：需要， 0：不需要
	45: optional i8 degreeAbove,	//及以上 1：需要， 0：不需要
	46: optional double managementExperience,	//是否要求管理经验0：需要1：不需要
	47: optional double gender,	//0-> female, 1->male, 2->all
	48: optional i32 publisher,	//hr_account.id
	49: optional i32 appCvConfigId,	//职位开启并配置自定义模板 hr_app_cv_conf.id
	50: optional double source,	//0:手动创建, 1:导入, 9:ATS导入
	51: optional i8 hbStatus,	//是否正参加活动：0=未参加  1=正参加点击红包活动  2=正参加被申请红包活动  3=正参加1+2红包活动
	52: optional i32 childCompanyId,	//hr_child_company.id
	53: optional i8 age,	//年龄要求, 0：无要求
	54: optional string majorRequired,	//专业要求
	55: optional string workAddress,	//上班地址
	56: optional string keyword,	//职位关键词
	57: optional string reportingTo,	//汇报对象
	58: optional i8 isHiring,	//是否急招, 1:是 0:否
	59: optional i8 underlings,	//下属人数， 0:没有下属
	60: optional i8 languageRequired,	//语言要求，1:是 0:否
	61: optional i8 targetIndustry,	//期望人选所在行业
	62: optional i8 currentStatus,	//0:招募中, 1: 未发布, 2:暂停, 3:撤下, 4:关闭
	63: optional i32 positionCode,	//职能字典code, dict_position.code
	64: optional i32 teamId,	//职位所属团队
    65: optional i8 profile_cc_mail_enabled, //简历申请是否抄送邮箱，0 否；1 是
    66: optional i32 is_referral	//是否是内推职位，1:是 0:否
}
