namespace java com.moseeker.thrift.gen.position.struct

typedef string Timestamp

struct HrOperationrecordStruct {
	1: optional double admin_id,
	2: optional double company_id,
	3: optional double app_id,
	4: optional double status_id,
	5: optional i32 operate_tpl_id
}

struct CandidatePositionDTO {
	1: optional i32 positionId,             //职位编号 jobdb.jop_position.id
	2: optional i32 userId,                 //用户编号 userdb.user_user.id
	3: optional Timestamp updateTime,       //修改时间 
	4: optional bool isInterested,          //是否感兴趣 0：不感兴趣，1：感兴趣
	5: optional i32 candidateCompanyId,     //候选人编号
	6: optional i32 viewNumber,             //候选人浏览该职位的次数
	7: optional bool sharedFromEmployee     //是否是员工转发
}

struct CandidateCompanyDTO {
	1: optional i32 id,                     //职位编号 jobdb.jop_position.id
	2: optional i32 companyId,              //用户编号 userdb.user_user.id
	3: optional Timestamp updateTime,       //修改时间 
	4: optional i8 status,                  //是否感兴趣 0：不感兴趣，1：感兴趣
	5: optional bool isRecommend,           //候选人编号
	6: optional string name,                //候选人浏览该职位的次数
	7: optional string email,               //候选人浏览该职位的次数
	8: optional string mobile,              //候选人浏览该职位的次数
	9: optional string nickname,            //候选人浏览该职位的次数
	10: optional string headimgurl,         //候选人浏览该职位的次数
	11: optional i32 sysUserId,             //候选人浏览该职位的次数
	12: optional i8 clickFrom               //候选人浏览该职位的次数
}
