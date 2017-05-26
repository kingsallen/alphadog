namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrTeamBackupChendiDO {

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
	14: optional i32 disable	//0是正常 1是删除

}
