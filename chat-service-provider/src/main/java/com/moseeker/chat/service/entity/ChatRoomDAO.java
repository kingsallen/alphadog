package com.moseeker.chat.service.entity;

import com.moseeker.chat.constant.ChatSpeakerType;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.service.HrDBDao;
import com.moseeker.thrift.gen.dao.struct.HrChatUnreadCountDO;
import com.moseeker.thrift.gen.dao.struct.HrWxHrChatListDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by jack on 09/03/2017.
 */
public class ChatRoomDAO {

    Logger logger = LoggerFactory.getLogger(ChatRoomDAO.class);

    HrDBDao.Iface hrDBDao = ServiceManager.SERVICEMANAGER.getService(HrDBDao.Iface.class);

    /**
     * 按照未阅读对聊天室排序
     * @param type 聊天对象类型
     * @param id 编号
     * @param pageNo 页码
     * @param pageSize 每页显示的数量
     * @return 聊天室列表
     */
    public List<HrChatUnreadCountDO> listChatRoomUnreadCount(ChatSpeakerType type, int id, int pageNo, int pageSize) {
        return null;
    }

    /**
     * 根据HR编号查找聊天室列表
     * @param hrId hr编号
     * @return 聊天室列表
     */
    public List<HrWxHrChatListDO> listHRChatRoom(int hrId) {

        QueryUtil queryUtil = new QueryUtil();
        queryUtil.addEqualFilter("hraccount_id", hrId);

        try {
            return hrDBDao.listChatRooms(queryUtil);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 查找聊天室数量
     * @param hrId HR编号
     * @return 聊天室数量
     */
    public int countHRChatRoom(int hrId) {
        return 0;
    }
}
