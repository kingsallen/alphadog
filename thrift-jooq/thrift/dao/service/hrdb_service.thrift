namespace java com.moseeker.thrift.gen.dao.service
namespace py thrift_gen.gen.dao.service.hrdb

include "../../common/struct/common_struct.thrift"
include "../struct/hrdb_struct.thrift"
include "../struct/dao_struct.thrift"
include "../../application/struct/application_struct.thrift"

service HrDBDao {
    hrdb_struct.HrHbConfigDO getHbConfig(1: common_struct.CommonQuery query);
    list<hrdb_struct.HrHbConfigDO> getHbConfigs(1: common_struct.CommonQuery query);
    
    hrdb_struct.HrHbPositionBindingDO getHbPositionBinding(1: common_struct.CommonQuery query);
    list<hrdb_struct.HrHbPositionBindingDO> getHbPositionBindings(1: common_struct.CommonQuery query);

    hrdb_struct.HrHbItemsDO getHbItem(1: common_struct.CommonQuery query);
    list<hrdb_struct.HrHbItemsDO> getHbItems(1: common_struct.CommonQuery query);
    
    hrdb_struct.HrHbScratchCardDO getHbScratchCard(1: common_struct.CommonQuery query);
    
    hrdb_struct.HrHbSendRecordDO getHbSendRecord(1: common_struct.CommonQuery query);
    
    common_struct.Response postHrOperationrecords(1:list<hrdb_struct.HrOperationRecordDO> record);
    common_struct.Response postHrOperationrecord(1:hrdb_struct.HrOperationRecordDO record);
    common_struct.Response getHrHistoryOperations(1:list<application_struct.ProcessValidationStruct> record);
    list<hrdb_struct.HrOperationRecordDO> listHrOperationRecord(1: common_struct.CommonQuery query);
    list<hrdb_struct.HrOperationRecordDO> listLatestOperationRecordByAppIdSet(1: set<i32> appidSet);

    hrdb_struct.HrEmployeeCertConfDO getEmployeeCertConf(1: common_struct.CommonQuery query);

    list<hrdb_struct.HrEmployeeCustomFieldsDO> getEmployeeCustomFields(1: common_struct.CommonQuery query);
    list<hrdb_struct.HrPointsConfDO> getPointsConfs(1: common_struct.CommonQuery query);
    hrdb_struct.HrPointsConfDO getPointsConf(1: common_struct.CommonQuery query) throws (1:common_struct.BIZException e);
    
    hrdb_struct.HrCompanyDO getCompany(1: common_struct.CommonQuery query);

    list<hrdb_struct.HrCompanyDO> listCompany(1: common_struct.CommonQuery query) throws (1: common_struct.CURDException e);
    
    list<hrdb_struct.HrWxHrChatDO> listChats(1: common_struct.CommonQuery query) throws (1:common_struct.CURDException e);
    i32 countChats(1: common_struct.CommonQuery query) throws (1:common_struct.CURDException e);
    hrdb_struct.HrWxHrChatDO getChat(1: common_struct.CommonQuery query) throws (1:common_struct.CURDException e);
    hrdb_struct.HrWxHrChatDO saveChat(1: hrdb_struct.HrWxHrChatDO chatDO) throws (1:common_struct.CURDException e);
    hrdb_struct.HrWxHrChatDO updateChat(1: hrdb_struct.HrWxHrChatDO chatDO) throws (1:common_struct.CURDException e);
    
    list<hrdb_struct.HrWxHrChatListDO> listChatRooms(1: common_struct.CommonQuery query) throws (1:common_struct.CURDException e);
    i32 countChatRooms(1: common_struct.CommonQuery query) throws (1:common_struct.CURDException e);
    hrdb_struct.HrWxHrChatListDO getChatRoom(1: common_struct.CommonQuery query) throws (1:common_struct.CURDException e);
    hrdb_struct.HrWxHrChatListDO saveChatRoom(1: hrdb_struct.HrWxHrChatListDO chatRoom) throws (1:common_struct.CURDException e);
    hrdb_struct.HrWxHrChatListDO updateChatRoom(1: hrdb_struct.HrWxHrChatListDO chatRoom) throws (1:common_struct.CURDException e);
    
    list<hrdb_struct.HrChatUnreadCountDO> listChatRoomUnreadSort(1: common_struct.CommonQuery query) throws (1:common_struct.CURDException e);
    
    hrdb_struct.HrChatUnreadCountDO saveChatUnreadCount(1: hrdb_struct.HrChatUnreadCountDO unreadCount) throws (1:common_struct.CURDException e);
	
	common_struct.Response getHrWxWechat(1: common_struct.CommonQuery query);
	hrdb_struct.HrWxWechatDO getHrWxWechatDO(1: common_struct.CommonQuery query) throws (1:common_struct.CURDException e);

    common_struct.Response getHrTeam(1:common_struct.CommonQuery query);
}
