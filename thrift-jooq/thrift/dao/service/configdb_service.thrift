namespace java com.moseeker.thrift.gen.dao.service

include "../../common/struct/common_struct.thrift"
include "../struct/configdb_struct.thrift"

service ConfigDBDao {
	list<configdb_struct.AwardConfigTpl> getAwardConfigTpls(1:common_struct.CommonQuery query);
}
