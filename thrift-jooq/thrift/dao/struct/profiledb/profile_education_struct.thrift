namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileEducationDO {

	1: optional i32 id,	//主key
	2: optional i32 profileId,	//profile.id
	3: optional string startTime,	//起止时间-起 yyyy-mm-dd
	4: optional string endTime,	//起止时间-止 yyyy-mm-dd
	5: optional i8 endUntilNow,	//是否至今 0：否 1：是
	6: optional i32 collegeCode,	//院校字典编码
	7: optional string collegeName,	//院校名称
	8: optional string collegeLogo,	//院校LOGO, 用户上传
	9: optional string majorCode,	//专业字典编码
	10: optional string majorName,	//专业名称
	11: optional i8 degree,	//学历 0:未选择 1: 初中及以下, 2:中专, 3:高中, 4: 大专, 5: 本科, 6: 硕士, 7: 博士, 8:博士以上, 9:其他
	12: optional string description,	//教育描述
	13: optional i8 isFull,	//是否全日制 0:没填写, 1:是, 2:否
	14: optional i8 isUnified,	//是否统招 0:没填写, 1:是, 2:否
	15: optional i8 isStudyAbroad,	//是否海外学习经历 0:没填写, 1:是, 2:否
	16: optional string studyAbroadCountry,	//海外留学国家
	17: optional string createTime,	//创建时间
	18: optional string updateTime	//更新时间

}
