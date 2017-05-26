namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrThirdPartyPositionDO {

	1: optional i32 id,	//null
	2: optional i32 positionId,	//jobdb.job_position.id
	3: optional string thirdPartPositionId,	//第三方渠道编号
	4: optional i16 channel,	//1=51job,2=猎聘,3=智联,4=linkedin
	5: optional i16 isSynchronization,	//是否同步:0=未同步,1=同步,2=同步中，3=同步失败
	6: optional i16 isRefresh,	//是否刷新:0=未刷新,1=刷新,2=刷新中
	7: optional string syncTime,	//职位同步时间
	8: optional string refreshTime,	//职位刷新时间
	9: optional string updateTime,	//数据更新时间
	10: optional string address,	//详细地址
	11: optional string occupation,	//同步时选中的第三方职位职能
	12: optional string syncFailReason,	//失败原因
	13: optional i16 useCompanyAddress,	//使用企业地址
	14: optional i32 thirdPartyAccountId	//第三方账号ID

}
