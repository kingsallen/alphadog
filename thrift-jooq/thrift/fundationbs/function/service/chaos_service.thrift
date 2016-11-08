# file: function_service.thrift

include "../../../common/struct/common_struct.thrift"
include "../struct/thirdpart_struct.thrift"
namespace java com.moseeker.thrift.gen.foundation.chaos.service

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
 
service ChaosServices {
    common_struct.Response binding(1:string username, 2:string password, 3:string member_name, 4:byte channel); 
}

