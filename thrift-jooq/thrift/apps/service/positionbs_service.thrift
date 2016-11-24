# file: profile_service.thrift

include "../../common/struct/common_struct.thrift"
include "../struct/appbs_struct.thrift"
namespace java com.moseeker.thrift.gen.apps.positionbs.service

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
 
service PositionBS {
   	//职位同步
	common_struct.Response synchronizePositionToThirdPartyPlatform(1: appbs_struct.ThridPartyPositionForm position);
}

