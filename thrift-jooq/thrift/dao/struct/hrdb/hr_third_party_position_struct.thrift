namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrThirdPartyPositionDO {

	1: optional i32 id,	//null
	2: optional i32 positionId,	//jobdb.job_position.id
	3: optional string thirdPartPositionId,	//第三方渠道编号
	4: optional i32 isSynchronization,	//是否同步:0=未同步,1=同步,2=同步中，3=同步失败
	5: optional i32 isRefresh,	//是否刷新:0=未刷新,1=刷新,2=刷新中
	6: optional string syncTime,	//职位同步时间
	7: optional string refreshTime,	//职位刷新时间
	8: optional string updateTime,	//数据更新时间
	9: optional string address,	//详细地址
	10: optional string occupation,	//同步时选中的第三方职位职能
	11: optional string syncFailReason,	//失败原因
	12: optional i32 useCompanyAddress,	//使用企业地址
	13: optional i32 thirdPartyAccountId	//第三方账号ID

}
