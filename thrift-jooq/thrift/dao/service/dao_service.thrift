namespace java com.moseeker.thrift.gen.orm.service

include "../../common/struct/common_struct.thrift"
include "../../useraccounts/struct/useraccounts_struct.thrift"
include "../struct/dao_struct.thrift"

service UserHrAccountDao {
	common_struct.Response getAccount(1:common_struct.CommonQuery query);
	common_struct.Response getThirdPartyAccount(1:common_struct.CommonQuery query);
	common_struct.Response createThirdPartyAccount(1:useraccounts_struct.BindAccountStruct account);
	common_struct.Response upsertThirdPartyAccount(1:useraccounts_struct.BindAccountStruct account);
}

service WordpressDao {
	#查找文章
	dao_struct.WordpressPosts getPost(1:common_struct.CommonQuery query);
	#查找关系数据
	dao_struct.WordpressTermRelationships getRelationships(1:i64 objectId, 2:i64 termTaxonomyId);
	#查找这个类型下最后的文章
	dao_struct.WordpressTermRelationships getLastRelationships(1:i64 termTaxonomyId);
	#查找文章的版本号和平台字段
	dao_struct.PostExt getPostExt(1:i64 objectId);
}
