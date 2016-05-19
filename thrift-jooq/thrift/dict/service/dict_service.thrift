include "../struct/dict_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.dict.service

service CityServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
}