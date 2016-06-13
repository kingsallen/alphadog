include "../struct/dict_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.dict.service

service CityServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
}

service CollegeServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
}

/*
  获取常量字典json数据服务
*/
service DictConstanService {
    common_struct.Response getDictConstantJsonByParentCode(1:list<i32> parentCodeList);
}