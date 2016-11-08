namespace java com.moseeker.thrift.gen.orm.service

include "../../common/struct/common_struct.thrift"

service UserHrAccountDao {
	common_struct.Response getAccount(1:common_struct.CommonQuery query);
}
