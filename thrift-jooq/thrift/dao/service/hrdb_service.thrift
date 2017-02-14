namespace java com.moseeker.thrift.gen.dao.service

include "../../common/struct/common_struct.thrift"
include "../struct/hrdb_struct.thrift"
include "../../application/struct/application_struct.thrift"

service HrDBDao {
    hrdb_struct.HrHbConfigPojo getHbConfig(1: common_struct.CommonQuery query);
    list<hrdb_struct.HrHbConfigPojo> getHbConfigs(1: common_struct.CommonQuery query);
    
    hrdb_struct.HrHbPositionBindingPojo getHbPositionBinding(1: common_struct.CommonQuery query);
    list<hrdb_struct.HrHbPositionBindingPojo> getHbPositionBindings(1: common_struct.CommonQuery query);

    hrdb_struct.HrHbItemsPojo getHbItem(1: common_struct.CommonQuery query);
    list<hrdb_struct.HrHbItemsPojo> getHbItems(1: common_struct.CommonQuery query);
    
    hrdb_struct.HrHbScratchCardPojo getHbScratchCard(1: common_struct.CommonQuery query);
    
    hrdb_struct.HrHbSendRecordPojo getHbSendRecord(1: common_struct.CommonQuery query);
    common_struct.Response postHrOperationrecords(1:list<hrdb_struct.HrOperationrecordStruct> record);
    common_struct.Response postHrOperationrecord(1:hrdb_struct.HrOperationrecordStruct record);
    common_struct.Response getHrHistoryOperations(1:list<application_struct.ProcessValidationStruct> record);

    hrdb_struct.HrEmployeeCertConfPojo getEmployeeCertConf(1: common_struct.CommonQuery query);

    list<hrdb_struct.HrEmployeeCustomFieldsPojo> getEmployeeCustomFields(1: common_struct.CommonQuery query);
}
