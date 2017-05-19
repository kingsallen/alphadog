package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.service.HrDBService;
import com.moseeker.baseorm.service.HrDaoService;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.baseorm.util.CURDExceptionUtils;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.HrDBDao.Iface;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class HrDBThriftService implements Iface {

	@Autowired
	private HrDBService hrDBService;

	@Autowired
    private HrDaoService hrDaoService;

	@Autowired
	private HrOperationRecordDao hrOperationRecordDao;

	@Autowired
	private HrCompanyDao companyDao;

	@Autowired
	private HrWxHrChatDao chatDao;

	@Autowired
	private HrWxHrChatListDao chatRoomDao;

	@Autowired
	private HrChatUnreadCountDao hrChatUnreadCountDao;

	@Autowired
	private HrWxWechatDao hrWxWechatDao;

	@Autowired
	private HrPointsConfDao hrPointsConfDao;

	@Override
	public Response getHrHistoryOperations(List<ProcessValidationStruct> record) throws TException {
		return hrDBService.getHrHistoryOpertation(record);
	}

	@Override
	public List<HrOperationRecordDO> listHrOperationRecord(CommonQuery query) throws TException {
		return hrOperationRecordDao.getDatas(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public List<HrOperationRecordDO> listLatestOperationRecordByAppIdSet(Set<Integer> appidSet) throws TException {
		return hrOperationRecordDao.listLatestOperationRecordByAppIdSet(appidSet);
	}

	@Override
	public HrEmployeeCertConfDO getEmployeeCertConf(CommonQuery query) throws TException {
		return hrDaoService.getEmployeeCertConf(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public List<HrEmployeeCustomFieldsDO> getEmployeeCustomFields(CommonQuery query) throws TException {
		return hrDaoService.getEmployeeCustomFields(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public List<HrPointsConfDO> getPointsConfs(CommonQuery query) throws TException {
		return hrDaoService.getPointsConfs(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public HrPointsConfDO getPointsConf(CommonQuery query) throws BIZException, TException {
		return hrPointsConfDao.getData(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public HrCompanyDO getCompany(CommonQuery query) throws TException {
		return companyDao.getData(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public List<HrCompanyDO> listCompany(CommonQuery query) throws CURDException, TException {
		return companyDao.getDatas(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public List<HrWxHrChatDO> listChats(CommonQuery query) throws CURDException, TException {
		return chatDao.getDatas(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public int countChats(CommonQuery query) throws CURDException, TException {
		try {
			return chatDao.getCount(QueryConvert.commonQueryConvertToQuery(query));
		} catch (Exception e) {
			throw new CURDExceptionUtils().buildGetNothingException();
		}
	}

	@Override
	public HrWxHrChatDO getChat(CommonQuery query) throws CURDException, TException {
		try {
			return chatDao.getData(QueryConvert.commonQueryConvertToQuery(query));
		} catch (Exception e) {
			throw new CURDExceptionUtils().buildGetNothingException();
		}
	}

	@Override
	public HrWxHrChatDO saveChat(HrWxHrChatDO chatDO) throws CURDException, TException {
		chatDao.addData(chatDO);
		return chatDO;
	}

	@Override
	public HrWxHrChatDO updateChat(HrWxHrChatDO chatDO) throws CURDException, TException {
		chatDao.updateData(chatDO);
		return chatDO;
	}

	@Override
	public List<HrWxHrChatListDO> listChatRooms(CommonQuery query) throws CURDException, TException {
		return chatRoomDao.getDatas(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public int countChatRooms(CommonQuery query) throws CURDException, TException {
		try {
			return chatRoomDao.getCount(QueryConvert.commonQueryConvertToQuery(query));
		} catch (Exception e) {
			throw new CURDExceptionUtils().buildGetNothingException();
		}
	}

	@Override
	public HrWxHrChatListDO getChatRoom(CommonQuery query) throws CURDException, TException {
		return chatRoomDao.getData(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public HrWxHrChatListDO saveChatRoom(HrWxHrChatListDO chatRoom) throws CURDException, TException {
		chatRoomDao.addData(chatRoom);
		return chatRoom;
	}

	@Override
	public HrWxHrChatListDO updateChatRoom(HrWxHrChatListDO chatRoom) throws CURDException, TException {
		chatRoomDao.updateData(chatRoom);
		return chatRoom;
	}

	@Override
	public List<HrChatUnreadCountDO> listChatRoomUnreadSort(CommonQuery query) throws CURDException, TException {
		return hrChatUnreadCountDao.getDatas(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public HrChatUnreadCountDO saveChatUnreadCount(HrChatUnreadCountDO unreadCount) throws CURDException, TException {
		hrChatUnreadCountDao.addData(unreadCount);
		return unreadCount;
	}

	public HrHbConfigDO getHbConfig(CommonQuery query) throws TException {
		return hrDaoService.getHbConfig(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public List<HrHbConfigDO> getHbConfigs(CommonQuery query) throws TException {
		return hrDaoService.getHbConfigs(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public HrHbPositionBindingDO getHbPositionBinding(CommonQuery query) throws TException {
		return hrDaoService.getHbPositionBinding(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public List<HrHbPositionBindingDO> getHbPositionBindings(CommonQuery query) throws TException {
		return hrDaoService.getHbPositionBindings(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public HrHbItemsDO getHbItem(CommonQuery query) throws TException {
		return hrDaoService.getHbItem(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public List<HrHbItemsDO> getHbItems(CommonQuery query) throws TException {
		return hrDaoService.getHbItems(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public HrHbScratchCardDO getHbScratchCard(CommonQuery query) throws TException {
		return hrDaoService.getHbScratchCard(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public HrHbSendRecordDO getHbSendRecord(CommonQuery query) throws TException {
		return hrDaoService.getHbSendRecord(QueryConvert.commonQueryConvertToQuery(query));
	}

	public Response postHrOperationrecords(List<HrOperationRecordDO> record) throws TException {
		// TODO Auto-generated method stub
		return hrDBService.postHrOperations(record);
	}

	@Override
	public Response postHrOperationrecord(HrOperationRecordDO record)
			throws TException {
		return hrDBService.postHrOperation(record);
	}

	@Override
	public Response getHrWxWechat(CommonQuery query) throws TException {
		// TODO Auto-generated method stub
		return hrDBService.getHrWxWechat(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public HrWxWechatDO getHrWxWechatDO(CommonQuery query) throws CURDException, TException {
		return hrWxWechatDao.getData(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public List<HrCompanyAccountDO> listHrCompanyAccount(CommonQuery query) throws CURDException, TException {
		return hrDaoService.getHrCompanyAccounts(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public Response getHrTeam(CommonQuery query) throws TException {
		// TODO Auto-generated method stub
		return hrDBService.getHrTeam(QueryConvert.commonQueryConvertToQuery(query));
	}

}
