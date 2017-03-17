namespace java com.moseeker.thrift.gen.dao.service
namespace py thrift_gen.gen.dao.service.dao

include "../../common/struct/common_struct.thrift"
include "../../useraccounts/struct/useraccounts_struct.thrift"
include "../../position/struct/position_struct.thrift"
include "../../dict/struct/dict_struct.thrift"
include "../struct/dao_struct.thrift"
include "../struct/hrdb_struct.thrift"
include "../../company/struct/company_struct.thrift"
include "../../application/struct/application_struct.thrift"

service UserHrAccountDao {
	common_struct.Response getAccount(1:common_struct.CommonQuery query);
	common_struct.Response getThirdPartyAccount(1:common_struct.CommonQuery query);
	common_struct.Response createThirdPartyAccount(1:useraccounts_struct.BindAccountStruct account);
	common_struct.Response upsertThirdPartyAccount(1:useraccounts_struct.BindAccountStruct account);
	common_struct.Response getAccounts(1:common_struct.CommonQuery query);
}

service WordpressDao {
	#查找文章
	dao_struct.WordpressPosts getPost(1:common_struct.CommonQuery query);
	#查找在发布状态下的最后一篇关于版本更新的文章
        dao_struct.WordpressPosts getReleaseVersionPost();
	#查找关系数据
	dao_struct.WordpressTermRelationships getRelationships(1:i64 objectId, 2:i64 termTaxonomyId);
	#查找这个类型下最后的文章
	dao_struct.WordpressTermRelationships getLastRelationships(1:i64 termTaxonomyId);
	#查找文章的版本号和平台字段
	dao_struct.PostExt getPostExt(1:i64 objectId);

	#查找用户读过的版本更新文章
	i64 getReadedPostId(1:i32 userId);
	#更新用户读过的版本更新内容
	common_struct.Response upsertUserPost(1:i32 userId, 2:i64 postId);
}

service CompanyDao {
	dao_struct.ThirdPartAccountData getThirdPartyAccount(1:common_struct.CommonQuery query);
	list<dao_struct.ThirdPartAccountData> getThirdPartyBindingAccounts(1:common_struct.CommonQuery query);
	//获取第三方渠道职位
	list<dao_struct.ThirdPartyPositionData> getThirdPartyPositions(1:common_struct.CommonQuery query);
	//添加或者修改第三方渠道职位
	common_struct.Response upsertThirdPartyPositions(1: list<dao_struct.ThirdPartyPositionData> positions);
	common_struct.Response updatePartyAccountByCompanyIdChannel(1: dao_struct.ThirdPartAccountData account);
	//查找公司信息
	hrdb_struct.HrCompanyDO getCompany(1:common_struct.CommonQuery query);
	list<company_struct.Hrcompany> getCompanies(1:common_struct.CommonQuery query);
	common_struct.Response getHrCompanyConfig(1:common_struct.CommonQuery query);
}

service PositionDao {
	position_struct.Position getPosition(1:common_struct.CommonQuery query);
	i32 updatePosition(1:position_struct.Position position);
	position_struct.Position getPositionWithCityCode(1:common_struct.CommonQuery query);
	list<dao_struct.ThirdPartyPositionData> getPositionThirdPartyPositions(1:common_struct.CommonQuery query);
	dao_struct.ThirdPartyPositionData getThirdPartyPosition(1:i32 positionId, 2:i32 channel);
	//添加或者修改第三方职位帐号信息
	i32 upsertThirdPartyPositions(1: dao_struct.ThirdPartyPositionData position);
}

service DictDao {
        dict_struct.CityMap getDictMap(1:common_struct.CommonQuery query);
}

service SearcheConditionDao {
	list<useraccounts_struct.SearchCondition> getResources(1:common_struct.CommonQuery query);
	useraccounts_struct.SearchCondition getResource(1:common_struct.CommonQuery query);
	i32 getResourceCount(1:common_struct.CommonQuery query);
	i32 postResource(1:useraccounts_struct.SearchCondition searchCondition);
	i32 delResource(1:i32 hrAccountId, 2:i32 id);
}

service TalentpoolDao {
	list<dao_struct.Talentpool> getResources(1:common_struct.CommonQuery query);
	dao_struct.Talentpool getResource(1:common_struct.CommonQuery query);
	i32 postResource(1:dao_struct.Talentpool talentpool);
	i32 putResource(1:dao_struct.Talentpool talentpool);
}

service ApplicationDao{
		common_struct.Response getProcessAuth(1:list<i32> appIds,2:i32 companyId 3:i32 progressStatus);
		common_struct.Response putApplication(1: application_struct.JobApplication application);
		common_struct.Response postApplication(1: application_struct.JobApplication application);
		common_struct.Response putApplications(1: list<application_struct.JobApplication> application);
		common_struct.Response postApplications(1:list<application_struct.JobApplication> application);
		common_struct.Response getApplicationsByList(1:list<i32> params);
}
service ConfigDao{
        common_struct.Response getConfigSysPointsConfTpls(1:common_struct.CommonQuery query);
        common_struct.Response getConfigSysPointsConfTpl(1:common_struct.CommonQuery query);
        common_struct.Response getRecruitProcesses(1:i32 companyId);
}
service WxUserDao {
	common_struct.Response getResource(1:common_struct.CommonQuery query);
}

service ThirdPartyUserDao {
    common_struct.Response putThirdPartyUser(1: useraccounts_struct.ThirdPartyUser user);
}

//UserEmployeeDao数据库单表操作
service UserEmployeeDao {
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response getResources(1:common_struct.CommonQuery query);

    common_struct.Response getResourceCount(1:common_struct.CommonQuery query);

    common_struct.Response postResource(1:useraccounts_struct.UserEmployeeStruct record);
    common_struct.Response postResources(1:list<useraccounts_struct.UserEmployeeStruct> records);

    common_struct.Response putResource(1:useraccounts_struct.UserEmployeeStruct record);
    common_struct.Response putResources(1:list<useraccounts_struct.UserEmployeeStruct> records);

    common_struct.Response delResource(1:common_struct.CommonQuery query);

    common_struct.Response postPutUserEmployeeBatch(1:list<useraccounts_struct.UserEmployeeStruct> userEmployees);
}
