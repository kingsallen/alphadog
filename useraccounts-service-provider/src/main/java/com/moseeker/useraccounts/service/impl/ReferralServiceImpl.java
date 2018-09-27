package com.moseeker.useraccounts.service.impl;


import com.moseeker.baseorm.dao.hrdb.HrHbItemsDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.ReferralEntity;
import com.moseeker.entity.pojos.HBBonusData;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.ReferralService;
import com.moseeker.useraccounts.service.impl.biztools.HBBizTool;
import com.moseeker.useraccounts.service.impl.vo.RedPacket;
import com.moseeker.useraccounts.service.impl.vo.RedPackets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jack
 * @Date: 2018/9/26
 */
@Service
@CounterIface
public class ReferralServiceImpl implements ReferralService {

    @Autowired
    private HrHbItemsDao itemsDao;

    @Autowired
    private UserWxUserDao wxUserDao;

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    ReferralEntity referralEntity;

    @Override
    public RedPackets getRedPackets(int id, int pageNum, int pageSize) throws UserAccountException {
        if (pageSize > Constant.DATABASE_PAGE_SIZE) {
            throw UserAccountException.PROGRAM_FETCH_TOO_MUCH;
        }
        UserEmployeeDO userEmployeeDO = employeeEntity.getActiveEmployeeDOByUserId(id);
        if (userEmployeeDO == null) {
            throw UserAccountException.USEREMPLOYEES_EMPTY;
        }
        RedPackets redPackets = new RedPackets();
        redPackets.setTotalBonus(userEmployeeDO.getBonus());
        BigDecimal bonus = new BigDecimal(userEmployeeDO.getBonus());
        redPackets.setTotalBonus(bonus.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        List<UserWxUserRecord> wxUserRecords = wxUserDao.getWXUsersByUserId(id);
        if (wxUserRecords != null && wxUserRecords.size() > 0) {

            List<Integer> wxUserIdList = wxUserRecords.stream().map(UserWxUserRecord::getSysuserId).collect(Collectors.toList());

            //计算红包总额
            redPackets.setTotalRedpackets(itemsDao.sumRedPacketsByWxUserIdList(wxUserIdList));

            int index = (pageNum - 1) * pageSize;
            if (index < 0) {
                index = 0;
            }
            if (pageSize <= 0) {
                pageSize = 10;
            }
            int count = itemsDao.countByWxUserIdList(wxUserIdList);
            redPackets.setTotalRedpackets(count);
            List<HrHbItemsRecord> itemsRecords = itemsDao.fetchItemsByWxUserIdList(wxUserIdList, index, pageSize);
            if (itemsRecords != null && itemsRecords.size() > 0) {

                HBBonusData data = referralEntity.fetchHBBonusData(itemsRecords);

                List<RedPacket> list = new ArrayList<>();
                for (HrHbItemsRecord hrHbItemsRecord : itemsRecords) {
                    list.add(HBBizTool.packageRedPacket(hrHbItemsRecord, data.getCandidateNameMap(),
                            data.getConfigMap(), data.getTitleMap(), data.getCardNoMap()));
                }
                redPackets.setRedpackets(list);
            }
        }
        return redPackets;
    }
}
