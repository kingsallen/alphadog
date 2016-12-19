# file: profile_service.thrift

include "../../common/struct/common_struct.thrift"
namespace java com.moseeker.thrift.gen.apps.profilebs.service

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
 
service ProfileBS {
   	//简历回收
	common_struct.Response retrieveProfile(1:i32 positionId, 2:i32 channel, 3:string profile);
}

