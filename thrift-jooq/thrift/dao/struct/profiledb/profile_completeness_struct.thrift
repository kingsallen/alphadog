namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileCompletenessDO {

	1: optional i32 profileId,	//主key
	2: optional i32 userUser,	//用户表信息完成度
	3: optional i32 profileBasic,	//profile基础信息表完成度
	4: optional i32 profileWorkexp,	//工作经历完成度
	5: optional i32 profileEducation,	//教育经历完成度
	6: optional i32 profileProjectexp,	//项目经历完成度
	7: optional i32 profileLanguage,	//语言完成度
	8: optional i32 profileSkill,	//技能完成度
	9: optional i32 profileCredentials,	//证书完成度
	10: optional i32 profileAwards,	//获得奖项完成度
	11: optional i32 profileWorks,	//个人作品
	12: optional i32 profileIntention	//求职意愿

}
