namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


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
