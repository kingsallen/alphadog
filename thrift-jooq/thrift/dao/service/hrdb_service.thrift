namespace java com.moseeker.thrift.gen.dao.service

include "../../common/struct/common_struct.thrift"
include "../struct/hrdb_struct.thrift"

service HrDBDao {
    hrdb_struct.HrHbConfigPojo getHbConfig(1: common_struct.CommonQuery query);
    list<hrdb_struct.HrHbConfigPojo> getHbConfigs(1: common_struct.CommonQuery query);
    
    hrdb_struct.HrHbPositionBindingPojo getHbPositionBinding(1: common_struct.CommonQuery query);
    list<hrdb_struct.HrHbPositionBindingPojo> getHbPositionBindings(1: common_struct.CommonQuery query);

    hrdb_struct.HrHbItemsPojo getHbItem(1: common_struct.CommonQuery query);
    list<hrdb_struct.HrHbItemsPojo> getHbItems(1: common_struct.CommonQuery query);
    
    hrdb_struct.HrHbScratchCardPojo getHbScratchCard(1: common_struct.CommonQuery query);
    
    hrdb_struct.HrHbSendRecordPojo getHbSendRecord(1: common_struct.CommonQuery query);
}
