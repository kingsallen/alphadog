package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.util.CURDExceptionUtils;
import com.moseeker.thrift.gen.common.struct.CURDException;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.baseorm.service.HrDBService;
import com.moseeker.baseorm.service.HrDaoService;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.HrDBDao.Iface;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
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
    private CompanyDao companyDao;

    @Autowired
    private ChatDao chatDao;

    @Autowired
    private ChatRoomDao chatRoomDao;

    @Autowired
    private HrChatUnreadCountDao hrChatUnreadCountDao;

    @Autowired
    private HrWxWechatDao hrWxWechatDao;

    @Autowired
    private HrPointsConfDao hrPointsConfDao;

    @Autowired
    private HrTeamDao hrTeamDao;

    @Override
    public Response getHrHistoryOperations(List<ProcessValidationStruct> record) throws TException {
        return hrDBService.getHrHistoryOpertation(record);
    }

    @Override
    public List<HrOperationRecordDO> listHrOperationRecord(CommonQuery query) throws TException {
        return hrOperationRecordDao.listResources(query);
    }

    @Override
    public List<HrOperationRecordDO> listLatestOperationRecordByAppIdSet(Set<Integer> appidSet) throws TException {
        return hrOperationRecordDao.listLatestOperationRecordByAppIdSet(appidSet);
    }

    @Override
    public HrEmployeeCertConfDO getEmployeeCertConf(CommonQuery query) throws TException {
        return hrDaoService.getEmployeeCertConf(query);
    }

    @Override
    public List<HrEmployeeCustomFieldsDO> getEmployeeCustomFields(CommonQuery query) throws TException {
        return hrDaoService.getEmployeeCustomFields(query);
    }

    @Override
    public List<HrPointsConfDO> getPointsConfs(CommonQuery query) throws TException {
        return hrDaoService.getPointsConfs(query);
    }

	@Override
	public HrPointsConfDO getPointsConf(CommonQuery query) throws BIZException, TException {
		return hrPointsConfDao.findResource(query);
	}

    @Override
    public HrCompanyDO getCompany(CommonQuery query) throws TException {
        return companyDao.getCompany(query);
    }

    @Override
    public List<HrCompanyDO> listCompany(CommonQuery query) throws CURDException, TException {
        return companyDao.listResources(query);
    }

    @Override
    public List<HrWxHrChatDO> listChats(CommonQuery query) throws CURDException, TException {
        return chatDao.listResources(query);
    }

	@Override
	public int countChats(CommonQuery query) throws CURDException, TException {
		try {
			return chatDao.getResourceCount(query);
		} catch (Exception e) {
			throw new CURDExceptionUtils().buildGetNothingException();
		}
	}

	@Override
	public HrWxHrChatDO getChat(CommonQuery query) throws CURDException, TException {
		try {
			return chatDao.findResource(query);
		} catch (Exception e) {
			throw new CURDExceptionUtils().buildGetNothingException();
		}
	}

	@Override
	public HrWxHrChatDO saveChat(HrWxHrChatDO chatDO) throws CURDException, TException {
		return chatDao.saveResource(chatDO);
	}

	@Override
	public HrWxHrChatDO updateChat(HrWxHrChatDO chatDO) throws CURDException, TException {
		return chatDao.updateResource(chatDO);
	}

	@Override
	public List<HrWxHrChatListDO> listChatRooms(CommonQuery query) throws CURDException, TException {
		return chatRoomDao.listResources(query);
	}

	@Override
	public int countChatRooms(CommonQuery query) throws CURDException, TException {
		try {
			return chatRoomDao.getResourceCount(query);
		} catch (Exception e) {
			throw new CURDExceptionUtils().buildGetNothingException();
		}
	}

	@Override
	public HrWxHrChatListDO getChatRoom(CommonQuery query) throws CURDException, TException {
		return chatRoomDao.findResource(query);
	}

	@Override
	public HrWxHrChatListDO saveChatRoom(HrWxHrChatListDO chatRoom) throws CURDException, TException {
		return chatRoomDao.saveResource(chatRoom);
	}

	@Override
	public HrWxHrChatListDO updateChatRoom(HrWxHrChatListDO chatRoom) throws CURDException, TException {
		return chatRoomDao.updateResource(chatRoom);
	}

	@Override
	public List<HrChatUnreadCountDO> listChatRoomUnreadSort(CommonQuery query) throws CURDException, TException {
		return hrChatUnreadCountDao.listResources(query);
	}

	@Override
	public HrChatUnreadCountDO saveChatUnreadCount(HrChatUnreadCountDO unreadCount) throws CURDException, TException {
		return hrChatUnreadCountDao.saveResource(unreadCount);
	}

	public HrHbConfigDO getHbConfig(CommonQuery query) throws TException {
		return hrDaoService.getHbConfig(query);
	}

	@Override
	public List<HrHbConfigDO> getHbConfigs(CommonQuery query) throws TException {
		return hrDaoService.getHbConfigs(query);
	}

	@Override
	public HrHbPositionBindingDO getHbPositionBinding(CommonQuery query) throws TException {
		return hrDaoService.getHbPositionBinding(query);
	}

	@Override
	public List<HrHbPositionBindingDO> getHbPositionBindings(CommonQuery query) throws TException {
		return hrDaoService.getHbPositionBindings(query);
	}

	@Override
	public HrHbItemsDO getHbItem(CommonQuery query) throws TException {
		return hrDaoService.getHbItem(query);
	}

	@Override
	public List<HrHbItemsDO> getHbItems(CommonQuery query) throws TException {
		return hrDaoService.getHbItems(query);
	}

	@Override
	public HrHbScratchCardDO getHbScratchCard(CommonQuery query) throws TException {
		return hrDaoService.getHbScratchCard(query);
	}

	@Override
	public HrHbSendRecordDO getHbSendRecord(CommonQuery query) throws TException {
		return hrDaoService.getHbSendRecord(query);
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
		return hrDBService.getHrWxWechat(query);
	}

	@Override
	public HrWxWechatDO getHrWxWechatDO(CommonQuery query) throws CURDException, TException {
		return hrWxWechatDao.findResource(query);
	}


	@Override
	public Response getHrTeam(CommonQuery query) throws TException {
		// TODO Auto-generated method stub
		return hrDBService.getHrTeam(query);
	}

    @Override
    public HrTeamDO hrTeamDo(CommonQuery query) throws TException {
        return hrTeamDao.findResource(query);
    }


}
