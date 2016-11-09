# file: function_service.thrift

include "../../../common/struct/common_struct.thrift"
include "../../../useraccounts/struct/useraccounts_struct.thrift"
namespace java com.moseeker.thrift.gen.foundation.hraccount.service

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
 
service HRAccountFoundationServices {
    common_struct.Response allowBind(1:i32 userId, 2:i32 companyId, 3:byte channelType); 
    common_struct.Response createThirdPartyAccount(1:useraccounts_struct.BindAccountStruct account); 
}

