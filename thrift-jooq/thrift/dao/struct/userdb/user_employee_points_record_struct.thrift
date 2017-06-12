namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserEmployeePointsRecordDO {

	1: optional i32 id,	//null
	2: optional double employeeId,	//user_employee.id
	3: optional string reason,	//积分变更说明
	4: optional i32 award,	//could use positive number to add rewards to user or nagetive number to minus
	5: optional string createTime,	//time stamp when record created
	6: optional double applicationId,	//job_application.id
	7: optional double recomWxuser,	//user_wx_user.id，推荐人的微信 id
	8: optional string updateTime,	//修改时间
	9: optional double positionId,	//job_position.id
	10: optional double berecomWxuserId,	//user_wx_user.id，被推荐人的微信 id
	11: optional double awardConfigId,	//积分记录来源hr_points_conf.id
	12: optional i32 recomUserId,	//userdb.user_user.id 推荐者的C端账号
	13: optional i32 berecomUserId	//userdb.user_user.id 被推荐者的C端账号

}
