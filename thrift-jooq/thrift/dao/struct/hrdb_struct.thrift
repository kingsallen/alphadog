namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrAppCvConfDO {

	1: optional i32 id,	//null
	2: optional string name,	//属性含义
	3: optional i32 priority,	//排序字段
	4: optional string createTime,	//null
	5: optional string updateTime,	//null
	6: optional i32 disable,	//是否禁用 0：可用1：不可用
	7: optional i32 companyId,	//所属部门。如果是私有属性，则存在所属公司部门编号；如果不是则为0 hr_company.id
	8: optional i32 hraccountId,	//账号编号 hr_account.id
	9: optional i32 needed,	//是否必填项 0：必填项 1：选填项
	10: optional string fieldValue	//微信端页面标签默认值

}


struct HrChatUnreadCountDO {

	1: optional i32 roomId,	//聊天室编号
	2: optional i32 hrId,	//HR编号 userdb.user_hr_account
	3: optional i32 userId,	//用户编号 userdb.user_user.id
	4: optional i32 hrUnreadCount,	//hr未读消息数量
	5: optional i32 userUnreadCount	//员工未读消息数量

}


struct HrChildCompanyDO {

	1: optional i32 id,	//null
	2: optional string name,	//null
	3: optional string ename,	//null
	4: optional i8 status,	//0:onuse 1:unused
	5: optional string ceo,	//CEO
	6: optional string introduction,	//introduction
	7: optional string scale,	//people number of the company
	8: optional string province,	//province
	9: optional string city,	//city
	10: optional string address,	//address
	11: optional double property,	//0:国有1:三资2:集体3:私有
	12: optional string business,	//business
	13: optional string establishTime,	//公司成立时间
	14: optional i32 parentId,	//上级公司
	15: optional string homepage,	//company home page
	16: optional i32 companyId	//hr_company.id

}


struct HrCmsMediaDO {

	1: optional i32 id,	//null
	2: optional i32 moduleId,	//模块id即hr_cms_module.id
	3: optional string longtext,	//描述
	4: optional string attrs,	//扩展字段，地图存json
	5: optional string title,	//模板名称
	6: optional string subTitle,	//模板子名称
	7: optional string link,	//模板链接
	8: optional i32 resId,	//资源hr_resource.id
	9: optional i32 order,	//顺序
	10: optional i32 isShow,	//是否显示
	11: optional i32 disable,	//状态 0 是有效 1是无效
	12: optional string createTime,	//null
	13: optional string updateTime	//null

}


struct HrCmsModuleDO {

	1: optional i32 id,	//null
	2: optional i32 pageId,	//hr_cms_pages.id
	3: optional string moduleName,	//模块名称
	4: optional i32 type,	//1,企业模块A 2，企业模块B，3企业模块C，4，企业模块D，5，企业模块E，6地图，7，二维码 8,团队详情9，职位详情10，动态
	5: optional i32 order,	//顺序
	6: optional string link,	//模板链接
	7: optional i32 limit,	//限制显示数量，0为不限制
	8: optional i32 disable,	//状态 0 是有效 1是无效
	9: optional string createTime,	//null
	10: optional string updateTime	//null

}


struct HrCmsPagesDO {

	1: optional i32 id,	//null
	2: optional i32 configId,	//null
	3: optional i32 type,	//0,默认值暂无意义，1为企业首页(config_id为company_id)2，团队详情（config_id为team_id） ，3，详情职位详情(config_id为team_id)
	4: optional i32 disable,	//状态 0 是有效 1是无效
	5: optional string createTime,	//null
	6: optional string updateTime	//null

}


struct HrCompanyDO {

	1: optional i32 id,	//null
	2: optional i8 type,	//公司区分(其它:2,免费用户:1,企业用户:0)
	3: optional string name,	//公司注册名称
	4: optional string introduction,	//公司介绍
	5: optional i8 scale,	//公司规模, dict_constant.parent_code=1102
	6: optional string address,	//公司地址
	7: optional i8 property,	//公司性质 0:未填写 1:外商独资 3:国企 4:合资 5:民营公司 6:事业单位 7:上市公司 8:政府机关/非盈利机构 10:代表处 11:股份制企业 12:创业公司 13:其它
	8: optional string industry,	//所属行业
	9: optional string homepage,	//公司主页
	10: optional string logo,	//公司logo的网络cdn地址
	11: optional string abbreviation,	//公司简称
	12: optional string impression,	//json格式的企业印象
	13: optional string banner,	//json格式的企业banner
	14: optional i32 parentId,	//上级公司
	15: optional i32 hraccountId,	//公司联系人, hr_account.id
	16: optional i8 disable,	//0:无效 1:有效, 删除子公司使用， 母公司目前没有禁用功能
	17: optional string createTime,	//创建时间
	18: optional string updateTime,	//更新时间
	19: optional i8 source,	//添加来源 0:hr系统, 1:官网下载行业报告, 6:无线官网添加, 7:PC端 添加, 8:微信端添加, 9:PC导入, 10:微信端导入
	20: optional string slogan	//公司口号

}


struct HrCompanyAccountDO {

	1: optional i32 companyId,	//hr_company.id
	2: optional i32 accountId	//user_hr_account.id

}


struct HrCompanyConfDO {

	1: optional i32 companyId,	//null
	2: optional i32 themeId,	//config_sys_theme.id
	3: optional i32 hbThrottle,	//全局每人每次红包活动可以获得的红包金额上限
	4: optional string appReply,	//申请提交成功回复信息
	5: optional string createTime,	//创建时间
	6: optional string updateTime,	//更新时间
	7: optional string employeeBinding,	//员工认证自定义文案
	8: optional string recommendPresentee,	//推荐候选人自定义文案
	9: optional string recommendSuccess,	//推荐成功自定义文案
	10: optional string forwardMessage,	//转发职位自定义文案
	11: optional i32 applicationCountLimit,	//一个人在一个公司下每月申请次数限制
	12: optional string jobCustomTitle,	//职位自定义字段标题
	13: optional string searchSeq,	//搜索页页面设置顺序,3#1#2
	14: optional string searchImg,	//搜索页页面设置背景图
	15: optional string jobOccupation,	//自定义字段名称
	16: optional string teamnameCustom,	//自定义部门别名
	17: optional string applicationTime,	//newjd_status即新的jd页的生效时间，
	18: optional i32 newjdStatus	//新jd页去设置状态0是未开启，1是用户申请开启，2是审核通过（使用新jd），3撤销（返回基础版） 默认是0

}


struct HrEmployeeCertConfDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//所属公司 hr_company.id
	3: optional double isStrict,	//是否严格0：严格，1：不严格
	4: optional string emailSuffix,	//邮箱后缀
	5: optional string createTime,	//创建时间
	6: optional string updateTime,	//修改时间
	7: optional double disable,	//是否启用 0：启用1：禁用
	8: optional double bdAddGroup,	//用户绑定时需要加入员工组Flag, 0:需要添加到员工组 1:不需要添加到员工组
	9: optional double bdUseGroupId,	//用户绑定时需要加入员工组的分组编号
	10: optional double authMode,	//认证方式，0:不启用员工认证, 1:邮箱认证, 2:自定义认证, 3:姓名手机号认证, 4:邮箱自定义两种认证
	11: optional string authCode,	//认证码（6到20位， 字母和数字组成，区分大小写）  默认为空
	12: optional string custom,	//配置的自定义认证名称
	13: optional string questions,	//问答列表(json)
	14: optional string customHint	//自定义认证提示

}


struct HrEmployeeCustomFieldsDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//sys_company.id, 部门ID
	3: optional string fname,	//自定义字段名
	4: optional string fvalues,	//自定义字段可选值
	5: optional i32 forder,	//排序优先级，0:不显示, 正整数由小到大排序
	6: optional i8 disable,	//是否停用 0:不停用(有效)， 1:停用(无效)
	7: optional i32 mandatory,	//是否必填 0:不是必填, 1:必填
	8: optional i32 status	//0: 正常 1: 被删除

}


struct HrEmployeePositionDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//sys_company.id, 部门ID
	3: optional string name,	//职能名称
	4: optional i32 priority,	//排序优先级
	5: optional i8 status	//1:有效, 0:无效

}


struct HrEmployeeSectionDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//sys_company.id, 部门ID
	3: optional string name,	//部门名称
	4: optional i32 priority,	//排序优先级
	5: optional i8 status	//1:有效, 0:无效

}


struct HrFeedbackDO {

	1: optional i32 id,	//ID
	2: optional i32 hraccountId,	//hr_account.id 账号编号
	3: optional string name,	//姓名
	4: optional string email,	//邮箱
	5: optional string images,	//反馈图片
	6: optional string content,	//反馈内容
	7: optional string createTime,	//null
	8: optional string updateTime	//null

}


struct HrHbConfigDO {

	1: optional i32 id,	//null
	2: optional i8 type,	//0:员工认证红包，1:推荐评价红包，2:转发被点击红包，3:转发被申请红包
	3: optional i8 target,	//0:员工，1:员工及员工二度，2:粉丝
	4: optional i32 companyId,	//company.id
	5: optional string startTime,	//红包活动开始时间
	6: optional string endTime,	//红包活动结束时间，直到红包用尽：2037-12-31 23:59:59）
	7: optional i32 totalAmount,	//总预算
	8: optional double rangeMin,	//红包最小金额
	9: optional double rangeMax,	//红包最大金额
	10: optional double probability,	//中奖几率: 0 < x <= 100
	11: optional i8 dType,	//分布类型 0:平均分布，1:指数分布
	12: optional string headline,	//抽奖页面标题
	13: optional string headlineFailure,	//抽奖失败页面标题
	14: optional string shareTitle,	//转发消息标题
	15: optional string shareDesc,	//转发消息摘要
	16: optional string shareImg,	//转发消息背景图地址
	17: optional i8 status,	//0:待审核，1:已审核，2:未开始，3:进行中：4:暂停中，5：已完成， －1: 已删除
	18: optional i8 checked,	//0:未审核，1:审核通过，2:审核不通过
	19: optional i32 estimatedTotal,	//预估红包总数
	20: optional string createTime,	//创建时间
	21: optional string updateTime,	//更新时间
	22: optional i32 actualTotal	//实际红包总数

}


struct HrHbItemsDO {

	1: optional i32 id,	//null
	2: optional i32 hbConfigId,	//hr_hb_config.id
	3: optional i32 bindingId,	//position_hb_binding.id
	4: optional i32 index,	//这条数据是第几个红包 0 < x <= 总红包数, 如果是 NULL 表示这是一个空红包
	5: optional double amount,	//红包金额
	6: optional i8 status,	//0:初始状态,1:发送了消息模成功,2:发送消息模板失败,尝试直接发送有金额的红包,3:打开刮刮卡,点击红包数字前,4:点击刮刮卡上红包数字后,5:发送红包前,校验 current_user.qxuser 不通过,红包停发,6:发送红包前,校验刮刮卡中的 hb_item 不通过,红包停发,7:跳过模版消息直接发送红包失败,100: 发送消息模板后成功发送了红包,101: 跳过发送消息模板后成功发送了红包,-1: 发送了 0 元红包的消息模板
	7: optional i32 wxuserId,	//获取红包的用户
	8: optional string openTime,	//红包打开时间
	9: optional string createTime,	//创建时间
	10: optional string updateTime,	//更新时间
	11: optional i32 triggerWxuserId	//触发发送红包行为时的当前用户, 即 JD 页点击者或职位申请者 wx_group_user.id

}


struct HrHbPositionBindingDO {

	1: optional i32 id,	//null
	2: optional i32 hbConfigId,	//hr_hb_config.id
	3: optional i32 positionId,	//hr_position.id
	4: optional i8 triggerWay,	//领取条件：1=职位被转发 2=职位被申请
	5: optional double totalAmount,	//总金额
	6: optional i32 totalNum,	//红包总数
	7: optional string createTime,	//创建时间
	8: optional string updateTime	//更新时间

}


struct HrHbScratchCardDO {

	1: optional i32 id,	//null
	2: optional i32 wechatId,	//null
	3: optional string cardno,	//随机字符串
	4: optional i32 status,	//状态: 0：未拆开 1：已拆开
	5: optional double amount,	//红包金额： 0.00 表示不发红包
	6: optional i32 hbConfigId,	//null
	7: optional string baggingOpenid,	//聚合号的 openid
	8: optional string createTime,	//null
	9: optional i32 hbItemId,	//null
	10: optional i8 tips	//是否是小费 0:不是，1:是

}


struct HrHbSendRecordDO {

	1: optional i32 id,	//null
	2: optional string returnCode,	//SUCCESS/FAIL 此字段是通信标识,非交易标识,交易是否成功需要查看result_code来判断
	3: optional string returnMsg,	//返回信息,如非空,为错误原因
	4: optional string sign,	//生成签名
	5: optional string resultCode,	//SUCCESS/FAIL
	6: optional string errCode,	//错误码信息
	7: optional string errCodeDes,	//结果信息描述
	8: optional string mchBillno,	//商户订单号
	9: optional string mchId,	//微信支付分配的商户号
	10: optional string wxappid,	//商户appid
	11: optional string reOpenid,	//接受收红包的用户,用户在wxappid下的openid
	12: optional i32 totalAmount,	//付款金额,单位分
	13: optional string sendTime,	//红包发送时间
	14: optional string sendListid,	//红包订单的微信单号
	15: optional string createTime,	//null
	16: optional i32 hbItemId	//hr_hb_items.id 该红包 api 调用所对应的红包记录

}


struct HrHtml5StatisticsDO {

	1: optional i32 id,	//primary key
	2: optional i32 topicId,	//wx_topic.id
	3: optional i32 companyId,	//company.id
	4: optional i32 viewNumPv,	//浏览次数
	5: optional i32 recomViewNumPv,	//推荐浏览次数
	6: optional string createDate	//创建日期

}


struct HrHtml5UniqueStatisticsDO {

	1: optional i32 id,	//primary key
	2: optional i32 topicId,	//wx_topic.id
	3: optional i32 companyId,	//company.id
	4: optional i32 viewNumUv,	//浏览人数
	5: optional i32 recomViewNumUv,	//推荐浏览人数
	6: optional string createDate,	//创建日期
	7: optional i32 infoType	//0: 日数据，1：周数据，2：月数据

}


struct HrImporterMonitorDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//公司ID，hr_company.id
	3: optional i32 hraccountId,	//hr_account.id 账号编号
	4: optional double type,	//要导入的表：0：user_employee 1: job_position 2:hr_company
	5: optional string file,	//导入文件的绝对路径
	6: optional double status,	//0：待处理 1：处理失败 2：处理成功
	7: optional string message,	//操作提示信息
	8: optional string createTime,	//null
	9: optional string updateTime,	//null
	10: optional string name,	//导入的文件名
	11: optional double sys	//1:mp, 2:hr

}


struct HrMediaDO {

	1: optional i32 id,	//null
	2: optional string longtext,	//描述
	3: optional string attrs,	//客户属性，可选 字段
	4: optional string title,	//模板名称
	5: optional string subTitle,	//模板子名称
	6: optional string link,	//模板链接
	7: optional i32 resId	//资源hr_resource.id

}


struct HrOperationRecordDO {

	1: optional i32 id,	//null
	2: optional double adminId,	//hr_account.id
	3: optional double companyId,	//company.id
	4: optional double appId,	//job_application.id
	5: optional double statusId,	//hr_award_config.id
	6: optional string optTime,	//操作时间
	7: optional i32 operateTplId	//hr_award_config_template.id

}


struct HrPointsConfDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//null
	3: optional string statusName,	//name of status defined, if using ATS, please set it the same as ATS
	4: optional double reward,	//积分数量
	5: optional string description,	//null
	6: optional double isUsing,	//是否启用0：启用1：禁用
	7: optional double orderNum,	//优先级
	8: optional string updateTime,	//null
	9: optional string tag,	//null
	10: optional double isApplierSend,	//申请者是否发送消息模板0:发送1:不发送
	11: optional string applierFirst,	//申请状态模板发送问候语
	12: optional string applierRemark,	//申请状态模板发送结束语
	13: optional double isRecomSend,	//推荐者是否发送消息模板0:发送1:不发送
	14: optional string recomFirst,	//推荐者申请状态模板发送问候语
	15: optional string recomRemark,	//推荐者申请状态模板发送结束语
	16: optional i32 templateId	//申请状态模板ID，hr_award_config_template.id

}


struct HrRecruitStatisticsDO {

	1: optional i32 id,	//primary key
	2: optional i32 positionId,	//hr_position.id
	3: optional i32 companyId,	//company.id
	4: optional i32 jdPv,	//JD 页浏览次数
	5: optional i32 recomJdPv,	//JD 页推荐浏览次数
	6: optional i32 favNum,	//感兴趣的次数
	7: optional i32 recomFavNum,	//推荐感兴趣的次数
	8: optional i32 applyNum,	//投递次数
	9: optional i32 recomApplyNum,	//推荐投递次数
	10: optional string createDate,	//创建日期
	11: optional i32 afterReviewNum,	//评审通过人数
	12: optional i32 recomAfterReviewNum,	//推荐评审通过人数
	13: optional i32 afterInterviewNum,	//面试通过人数
	14: optional i32 recomAfterInterviewNum,	//推荐面试通过人数
	15: optional i32 onBoardNum,	//入职人数
	16: optional i32 recomOnBoardNum,	//推荐入职人数
	17: optional i32 notViewedNum,	//简历未查阅人数
	18: optional i32 recomNotViewedNum,	//推荐未查阅人数
	19: optional i32 notQualifiedNum,	//简历不匹配人数
	20: optional i32 recomNotQualifiedNum	//推荐简历不匹配人数

}


struct HrRecruitUniqueStatisticsDO {

	1: optional i32 id,	//primary key
	2: optional i32 positionId,	//hr_position.id
	3: optional i32 companyId,	//company.id
	4: optional i32 jdUv,	//JD 页浏览人数
	5: optional i32 recomJdUv,	//JD 页推荐浏览人数
	6: optional i32 favNum,	//感兴趣的人数
	7: optional i32 recomFavNum,	//推荐感兴趣的人数
	8: optional i32 mobileNum,	//留手机的人数
	9: optional i32 recomMobileNum,	//推荐感兴趣留手机的人数
	10: optional i32 applyNum,	//投递人数
	11: optional i32 recomApplyNum,	//推荐投递人数
	12: optional string createDate,	//创建日期
	13: optional i32 infoType	//0: 日数据，1：周数据，2：月数据

}


struct HrReferralStatisticsDO {

	1: optional i32 id,	//primary key
	2: optional string positionTitle,	//hr_position.title
	3: optional string employeeName,	//sys_employee.cname
	4: optional i32 employeeId,	//推荐员工 sys.employee.id
	5: optional i32 companyId,	//sys_wechat.id
	6: optional string createDate,	//创建时间
	7: optional i32 recomNum,	//推荐浏览人次
	8: optional i32 recomFavNum,	//推荐感兴趣人次
	9: optional i32 recomMobileNum,	//推荐留手机人次
	10: optional i32 recomApplyNum,	//推荐投递人次
	11: optional i32 recomAfterReviewNum,	//推荐评审通过人数
	12: optional i32 recomAfterInterviewNum,	//推荐面试通过人数
	13: optional i32 recomOnBoardNum,	//推荐入职人数
	14: optional i32 infoType,	//0: 日数据，1：周数据，2：月数据
	15: optional i32 publisher,	//null
	16: optional i32 positionId	//job.position_id

}


struct HrResourceDO {

	1: optional i32 id,	//null
	2: optional string resUrl,	//资源链接
	3: optional i32 resType,	//0: image  1: video
	4: optional string remark,	//备注资源
	5: optional i32 companyId,	//企业id
	6: optional string title,	//资源名称
	7: optional i32 disable,	//0是正常1是删除
	8: optional string updateTime,	//资源修改时间
	9: optional string createTime	//资源创建时间

}


struct HrRuleStatisticsDO {

	1: optional i32 id,	//primary key
	2: optional i32 wxruleId,	//wx_rule.id
	3: optional string menuName,	//菜单名称
	4: optional i32 type,	//0: wx_rule, 1: menu
	5: optional i32 companyId,	//company.id
	6: optional i32 viewNumPv,	//浏览人数
	7: optional string createDate	//创建日期

}


struct HrRuleUniqueStatisticsDO {

	1: optional i32 id,	//primary key
	2: optional i32 wxruleId,	//wx_rule.id
	3: optional string menuName,	//菜单名称
	4: optional i32 type,	//0: wx_rule, 1: menu
	5: optional i32 companyId,	//company.id
	6: optional i32 viewNumUv,	//浏览人数
	7: optional string createDate,	//创建日期
	8: optional i32 infoType	//0: 日数据，1：周数据，2：月数据

}


struct HrSearchConditionDO {

	1: optional i32 id,	//null
	2: optional string name,	//常用搜索条件名称，长度不超过12个字符
	3: optional i32 publisher,	//发布人id(user_hr_account.id)
	4: optional i32 positionId,	//职位id
	5: optional string keyword,	//关键字
	6: optional string submitTime,	//投递时间
	7: optional string workYears,	//工作年限、工龄
	8: optional string cityName,	//现居住地
	9: optional string degree,	//学历
	10: optional string pastPosition,	//曾任职务
	11: optional i32 inLastJobSearchPosition,	//是否只在最近一份工作中搜索曾任职务(0:否，1:是)
	12: optional i32 minAge,	//最小年龄
	13: optional i32 maxAge,	//最大年龄
	14: optional string intentionCityName,	//期望工作地
	15: optional i32 sex,	//性别
	16: optional string intentionSalaryCode,	//期望薪资
	17: optional string companyName,	//公司名称
	18: optional i32 inLastJobSearchCompany,	//是否只在最近一份工作中搜索公司名称（0:否，1:是）
	19: optional i32 hrAccountId,	//创建人id(user_hr_account.id)
	20: optional string createTime,	//创建时间
	21: optional i32 updateTime,	//简历更新时间选项（0：不限，1：最近一周，2：最近两周，3：最近一个月）
	22: optional i32 type	//类型（0：候选人列表筛选条件，1：人才库列表筛选条件）

}


struct HrSuperaccountApplyDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//hr_company.id
	3: optional string licence,	//营业执照
	4: optional string createTime,	//创建时间
	5: optional string updateTime,	//修改时间
	6: optional i32 operate,	//config_sys_administrator.id
	7: optional i32 status,	//申请状态 0表示已经通过，1表示未处理，2表示未通过
	8: optional string message,	//审核留言
	9: optional string childCompanyId,	//合并的其他公司的编号：[1,2,3]
	10: optional string migrateTime,	//迁移时间
	11: optional i32 accountLimit	//子账号数量限制

}


struct HrTalentpoolDO {

	1: optional i32 id,	//null
	2: optional i32 hrAccountId,	//创建人id(user_hr_account.id)
	3: optional i32 applierId,	//候选人id（user_user.id）
	4: optional string createTime,	//null
	5: optional string updateTime,	//null
	6: optional i32 status	//状态(0：正常，1：删除)

}


struct HrTeamDO {

	1: optional i32 id,	//null
	2: optional string name,	//团队/部门名称
	3: optional string summary,	//职能概述
	4: optional string description,	//团队介绍
	5: optional i32 showOrder,	//团队显示顺序
	6: optional string jdMedia,	//成员一天信息hr_media.id: [1, 23, 32]
	7: optional i32 companyId,	//团队所在母公司
	8: optional string createTime,	//创建时间
	9: optional string updateTime,	//更新时间
	10: optional i32 isShow,	//当前团队在列表等处是否显示, 0:不显示, 1:显示
	11: optional string slogan,	//团队标语
	12: optional i32 resId,	//团队主图片hr_resource.id
	13: optional string teamDetail,	//团队详情页配置hr_media.id: [1, 23, 32]
	14: optional i32 disable,	//0是正常 1是删除
	15: optional string subTitle	//团队小标题

}


struct HrTeamMemberDO {

	1: optional i32 id,	//null
	2: optional string name,	//成员名称
	3: optional string title,	//成员职称
	4: optional string description,	//成员描述
	5: optional i32 teamId,	//成员所属团队
	6: optional i32 userId,	//成员对应用户
	7: optional string createTime,	//创建时间
	8: optional string updateTime,	//更新时间
	9: optional i32 resId,	//成员头像hr_resource.id
	10: optional i32 disable	//0是正常1是删除

}


struct HrThirdPartyAccountDO {

	1: optional i32 id,	//编号
	2: optional i32 channel,	//1=51job,2=猎聘,3=智联,4=linkedin
	3: optional string username,	//帐号
	4: optional string password,	//密码
	5: optional string membername,	//会员名称
	6: optional i32 binding,	//0=未绑定,1=绑定
	7: optional i32 companyId,	//hrdb.hr_company.id
	8: optional i32 remainNum,	//点数
	9: optional string syncTime,	//同步时间
	10: optional string updateTime,	//数据更新时间
	11: optional string createTime	//创建时间

}


struct HrThirdPartyPositionDO {

	1: optional i32 id,	//null
	2: optional i32 positionId,	//jobdb.job_position.id
	3: optional string thirdPartPositionId,	//第三方渠道编号
	4: optional i32 channel,	//1=51job,2=猎聘,3=智联,4=linkedin
	5: optional i32 isSynchronization,	//是否同步:0=未同步,1=同步,2=同步中，3=同步失败
	6: optional i32 isRefresh,	//是否刷新:0=未刷新,1=刷新,2=刷新中
	7: optional string syncTime,	//职位同步时间
	8: optional string refreshTime,	//职位刷新时间
	9: optional string updateTime,	//数据更新时间
	10: optional string address,	//详细地址
	11: optional string occupation,	//同步时选中的第三方职位职能
	12: optional string syncFailReason,	//失败原因
	13: optional i32 useCompanyAddress	//使用企业地址

}


struct HrTopicDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//company.id, 部门ID
	3: optional string shareTitle,	//分享标题
	4: optional string shareLogo,	//分享LOGO的相对路径
	5: optional string shareDescription,	//分享描述
	6: optional i32 styleId,	//wx_group_user.id， 推荐者微信ID
	7: optional i32 creator,	//hr_account.id
	8: optional double disable,	//是否有效  0：有效 1：无效
	9: optional string createTime,	//null
	10: optional string updateTime	//null

}


struct HrWxBasicReplyDO {

	1: optional i32 id,	//null
	2: optional i32 rid,	//null
	3: optional string content	//文本回复内容

}


struct HrWxHrChatDO {

	1: optional i32 id,	//ID
	2: optional i32 chatlistId,	//wx_hr_chat_list.id
	3: optional string content,	//聊天内容
	4: optional i32 pid,	//hr_position.id
	5: optional i8 speaker,	//状态，0：求职者，1：HR
	6: optional i8 status,	//状态，0：有效，1：无效
	7: optional string createTime	//创建时间

}


struct HrWxHrChatListDO {

	1: optional i32 id,	//ID
	2: optional i32 sysuserId,	//sysuser.id
	3: optional i32 hraccountId,	//hr_account.id
	4: optional i8 status,	//状态，0：有效，1：无效
	5: optional string createTime,	//创建时间
	6: optional string wxChatTime,	//sysuser最近一次聊天时间
	7: optional string hrChatTime,	//HR最近一次聊天时间
	8: optional string updateTime	//创建时间

}


struct HrWxImageReplyDO {

	1: optional i32 id,	//null
	2: optional i32 rid,	//wx_rule.id, 规则ID
	3: optional string image,	//回复图片的相对路径
	4: optional string createTime,	//null
	5: optional string updateTime	//null

}


struct HrWxModuleDO {

	1: optional i8 id,	//null
	2: optional string name,	//标识
	3: optional string title,	//名称
	4: optional string version,	//版本
	5: optional string ability,	//功能描述
	6: optional string description,	//介绍
	7: optional string author,	//作者
	8: optional string url,	//发布页面
	9: optional i8 settings,	//扩展设置项
	10: optional string subscribes,	//订阅的消息类型
	11: optional string handles,	//能够直接处理的消息类型
	12: optional i8 isrulefields,	//是否有规则嵌入项
	13: optional i8 home,	//是否微站首页嵌入
	14: optional i8 issystem,	//是否是系统模块
	15: optional string options,	//扩展功能导航项
	16: optional i8 profile,	//是否个人中心嵌入
	17: optional string siteMenus,	//微站功能扩展导航项
	18: optional string platformMenus	//微站功能扩展导航项

}


struct HrWxNewsReplyDO {

	1: optional i32 id,	//null
	2: optional i32 rid,	//null
	3: optional i32 parentid,	//null
	4: optional string title,	//null
	5: optional string description,	//null
	6: optional string thumb,	//null
	7: optional string content,	//null
	8: optional string url	//null

}


struct HrWxNoticeMessageDO {

	1: optional i32 id,	//主key
	2: optional i32 wechatId,	//所属公众号
	3: optional i32 noticeId,	//sys_notice_message.id
	4: optional string first,	//消息模板first文案
	5: optional string remark,	//消息模板remark文案
	6: optional double status	//是否开启, 1:开启, 0:关闭

}


struct HrWxRuleDO {

	1: optional i32 id,	//null
	2: optional i32 wechatId,	//null
	3: optional i32 cid,	//null
	4: optional string name,	//null
	5: optional string component,	//模块名称
	6: optional i32 displayorder,	//排序
	7: optional double status,	//规则状态，0禁用，1启用，2置顶
	8: optional i32 accessLevel,	//规则获取权限，0：所有，1：员工
	9: optional string keywords	//关键字

}


struct HrWxTemplateMessageDO {

	1: optional i32 id,	//主key
	2: optional i32 sysTemplateId,	//模板ID
	3: optional string wxTemplateId,	//微信模板ID
	4: optional i32 display,	//是否显示
	5: optional i32 priority,	//排序
	6: optional i32 wechatId,	//所属公众号
	7: optional i32 disable,	//是否可用
	8: optional string url,	//跳转URL
	9: optional string topcolor,	//消息头部颜色
	10: optional string first,	//问候语
	11: optional string remark	//结束语

}


struct HrWxWechatDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//所属公司id, company.id
	3: optional i8 type,	//公众号类型, 0:订阅号, 1:服务号
	4: optional string signature,	//公众号ID匿名化
	5: optional string name,	//名称
	6: optional string allonym,	//别名
	7: optional string username,	//用户名
	8: optional string password,	//密码
	9: optional string token,	//开发者token
	10: optional string appid,	//开发者appid
	11: optional string secret,	//开发者secret
	12: optional i32 welcome,	//welcome message
	13: optional i32 defMsg,	//default message
	14: optional string qrcode,	//关注公众号的二维码
	15: optional double passiveSeeker,	//被动求职者开关，0：开启，1：不开启
	16: optional double thirdOauth,	//授权大岂第三方平台0：未授权 1：授权了
	17: optional double hrRegister,	//是否启用免费雇主注册，0：不启用，1：启用
	18: optional i32 accessTokenCreateTime,	//access_token最新更新时间
	19: optional i32 accessTokenExpired,	//access_token过期时间
	20: optional string accessToken,	//access_token
	21: optional string jsapiTicket,	//jsapi_ticket
	22: optional double authorized,	//是否授权0：无关，1：授权2：解除授权
	23: optional i32 unauthorizedTime,	//解除授权的时间戳
	24: optional string authorizerRefreshToken,	//第三方授权的刷新token，用来刷access_token
	25: optional string createTime,	//创建时间
	26: optional string updateTime,	//修改时间
	27: optional double hrChat,	//IM聊天开关，0：不开启，1：开启
	28: optional i32 showQxQrcode,	//显示仟寻聚合号二维码, 0:不允许，1:允许
	29: optional i32 showNewJd,	//显示新JD样式, 0:不启用, 1:启用
	30: optional i32 showCustomTheme	//show_custom_theme, 用于表示是否可以开启企业自定义颜色配置 0是否 1是开启

}


struct HrWxWechatNoticeSyncStatusDO {

	1: optional i32 id,	//主key
	2: optional i32 wechatId,	//所属公众号
	3: optional i32 status,	//同步状态 0:成功, 1:行业修改失败, 2:模板数量超出上限, 3:接口调用失败
	4: optional i32 count,	//同步状态提示应该删除信息的数量
	5: optional string updateTime	//null

}
