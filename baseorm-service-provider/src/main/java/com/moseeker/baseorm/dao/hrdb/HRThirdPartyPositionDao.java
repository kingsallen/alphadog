package com.moseeker.baseorm.dao.hrdb;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyPositionRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import org.joda.time.DateTime;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * HR帐号数据库持久类
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Nov 9, 2016
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 *
 * @author wjf
 */
@Service
public class HRThirdPartyPositionDao extends JooqCrudImpl<HrThirdPartyPositionDO, HrThirdPartyPositionRecord> {

    public HRThirdPartyPositionDao() {
        super(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION, HrThirdPartyPositionDO.class);
    }

    private static final String UPSERT_SQL = "insert into hrdb.hrThirdPartyPosition(positionId, thirdPartPositionId, isSynchronization, isRefresh, syncTime, refreshTime, updateTime, occupation, address, channel, thirdParty_accountId) select ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? from DUAL where not exists(select id from hrdb.hrThirdPartyPosition where thirdParty_accountId = ? and positionId = ?)";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public HRThirdPartyPositionDao(TableImpl<HrThirdPartyPositionRecord> table, Class<HrThirdPartyPositionDO> hrThirdPartyPositionDOClass) {
        super(table, hrThirdPartyPositionDOClass);
    }

    public List<HrThirdPartyPositionDO> getThirdPartyPositions(Query query) {
        List<HrThirdPartyPositionDO> datas = new ArrayList<>();
        try {
            List<HrThirdPartyPositionRecord> records = this.getRecords(query);
            if (records != null && records.size() > 0) {
                records.forEach(record -> {
                    HrThirdPartyPositionDO position = new HrThirdPartyPositionDO();
                    copy(position, record);
                    datas.add(position);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            // do nothing
        }
        return datas;
    }

    private void copy(HrThirdPartyPositionDO position, HrThirdPartyPositionRecord record) {
        position.setAddress(record.getAddress());
        position.setId(record.getId());
        position.setChannel(record.getChannel().byteValue());
        position.setOccupation(record.getOccupation());
        position.setPositionId(record.getPositionId().intValue());
        if (record.getSyncTime() != null) {
            position.setSyncTime((new DateTime(record.getSyncTime().getTime())).toString("yyyy-MM-dd"));
        }
        if (record.getRefreshTime() != null) {
            position.setRefreshTime((new DateTime(record.getRefreshTime().getTime())).toString("yyyy-MM-dd"));
        }
        if (record.getUpdateTime() != null) {
            position.setUpdateTime((new DateTime(record.getUpdateTime().getTime())).toString("yyyy-MM-dd"));
        }
        position.setThirdPartPositionId(record.getThirdPartPositionId());
        position.setIsRefresh(record.getIsRefresh().byteValue());
        position.setIsSynchronization(record.getIsSynchronization().byteValue());
    }

    public Response upsertThirdPartyPositions(List<HrThirdPartyPositionDO> positions) {
        if (positions != null && positions.size() > 0) {
            logger.info("companyDao upsertThirdPartyPositions" + JSON.toJSONString(positions));
            positions.forEach(position -> {
                try {
                    Date syncTime = StringUtils.isNotNullOrEmpty(position.getSyncTime()) ? sdf.parse(position.getSyncTime()) : null;
                    Date refreshTime = StringUtils.isNotNullOrEmpty(position.getRefreshTime()) ? sdf.parse(position.getRefreshTime()) : null;
                    Date updateTime = StringUtils.isNotNullOrEmpty(position.getUpdateTime()) ? sdf.parse(position.getUpdateTime()) : null;

                    int count = create.execute(UPSERT_SQL, position.getPositionId(), position.getThirdPartPositionId(),
                            position.getIsSynchronization(), position.getIsRefresh(), syncTime, refreshTime,
                            updateTime, position.getOccupation(), position.getAddress(), position.getChannel(),
                            position.getChannel(), position.getPositionId());

                    if (count == 0) {
                        logger.info("companyDao upsertThirdPartyPositions exist");
                        HrThirdPartyPositionRecord dbrecord = create
                                .selectFrom(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION)
                                .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID
                                        .equal(Integer.valueOf(position.getPositionId())))
                                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.THIRD_PARTY_ACCOUNT_ID
                                        .equal(Integer.valueOf(position.getThirdPartyAccountId())))
                                .fetchOne();
                        if (dbrecord != null) {
                            if (StringUtils.isNotNullOrEmpty(position.getAddress())) {
                                dbrecord.setAddress(position.getAddress());
                            }

                            if (StringUtils.isNotNullOrEmpty(position.getThirdPartPositionId())) {
                                dbrecord.setThirdPartPositionId(position.getThirdPartPositionId());
                            }
                            dbrecord.setPositionId(Integer.valueOf(position.getPositionId()));
                            if (position.getThirdPartPositionId() != null) {
                                dbrecord.setThirdPartPositionId(position.getThirdPartPositionId());
                            }
                            dbrecord.setChannel(Integer.valueOf(position.getChannel()).shortValue());
                            if (position.isSetIsRefresh()) {
                                dbrecord.setIsRefresh(Integer.valueOf(position.getIsRefresh()).shortValue());
                            }
                            if (position.isSetIsSynchronization()) {
                                dbrecord.setIsSynchronization(Integer.valueOf(position.getIsSynchronization()).shortValue());
                            }
                            if (position.getOccupation() != null) {
                                dbrecord.setOccupation(position.getOccupation());
                            }
                            if (position.getSyncTime() != null) {
                                dbrecord.setSyncTime(new Timestamp(sdf.parse(position.getSyncTime()).getTime()));
                            }
                            if (position.getRefreshTime() != null) {
                                dbrecord.setRefreshTime(
                                        new Timestamp(sdf.parse(position.getRefreshTime()).getTime()));
                            }
                            if (position.getUpdateTime() != null) {
                                dbrecord.setUpdateTime(
                                        new Timestamp(sdf.parse(position.getUpdateTime()).getTime()));
                            }
                            if (position.isSetSyncFailReason()) {
                                dbrecord.setSyncFailReason(position.getSyncFailReason());
                            }
                            count = dbrecord.update();
                            if (count > 0) {
                                logger.info("companyDao upsertThirdPartyPositions update success");
                            } else {
                                logger.info("companyDao upsertThirdPartyPositions update failed");
                            }
                        }
                    } else {
                        logger.info("companyDao upsertThirdPartyPositions add success");
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                }
            });
            return ResponseUtils.success(null);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
    }

    /**
     * 获取第三方职位数据
     *
     * @param positionId 职位编号
     *                   渠道号
     * @return
     */
    public HrThirdPartyPositionDO getThirdPartyPosition(int positionId, int accountId) {
        HrThirdPartyPositionDO position = new HrThirdPartyPositionDO();
        if (positionId > 0 && accountId > 0) {
            HrThirdPartyPositionRecord record = create.selectFrom(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION)
                    .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID
                            .eq(Integer.valueOf(positionId)))
                    .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.THIRD_PARTY_ACCOUNT_ID.eq(accountId)).fetchOne();
            if (record != null) {
                record.into(position);
                position.setUpdateTime(sdf.format(record.getUpdateTime()));
                if (record.getSyncTime() != null) {
                    position.setSyncTime(sdf.format(record.getSyncTime()));
                }
                if (record.getRefreshTime() != null) {
                    position.setRefreshTime(sdf.format(record.getRefreshTime()));
                }
            }
        }
        return position;
    }

    /**
     * 如果第三方职位数据存在，则修改，否则添加
     *
     * @param position
     * @return
     */
    public int upsertThirdPartyPosition(HrThirdPartyPositionDO position) {
        int count = 0;

        logger.info("isrefresh:" + position.getIsRefresh());
        logger.info("refreshTime:" + position.getRefreshTime());
        HrThirdPartyPositionRecord record = create.selectFrom(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION)
                .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID
                        .eq(Integer.valueOf(position.getPositionId())))
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.CHANNEL.eq((short) position.getChannel()))
                .fetchOne();
        if (record != null) {
            //BeanUtils.structToDB(position, record, null);
            logger.info("record before isRefresh:" + record.getIsRefresh());
            try {
                if (StringUtils.isNotNullOrEmpty(position.getAddress())) {
                    record.setAddress(position.getAddress());
                }
                if (StringUtils.isNotNullOrEmpty(position.getThirdPartPositionId())) {
                    record.setThirdPartPositionId(position.getThirdPartPositionId());
                }
                record.setPositionId(position.getPositionId());
                if (position.getThirdPartPositionId() != null) {
                    record.setThirdPartPositionId(position.getThirdPartPositionId());
                }
                record.setChannel(Integer.valueOf(position.getChannel()).shortValue());
                if (position.isSetIsRefresh()) {
                    record.setIsRefresh(Integer.valueOf(position.getIsRefresh()).shortValue());
                }
                if (position.getIsSynchronization() != 0) {
                    record.setIsSynchronization(Integer.valueOf(position.getIsSynchronization()).shortValue());
                }
                if (position.getOccupation() != null) {
                    record.setOccupation(position.getOccupation());
                }
                if (position.getSyncTime() != null) {
                    record.setSyncTime(new Timestamp(sdf.parse(position.getSyncTime()).getTime()));
                }
                if (position.getRefreshTime() != null) {
                    record.setRefreshTime(
                            new Timestamp(sdf.parse(position.getRefreshTime()).getTime()));
                }
                if (position.getUpdateTime() != null) {
                    record.setUpdateTime(
                            new Timestamp(sdf.parse(position.getUpdateTime()).getTime()));
                }
                if (position.isSetSyncFailReason()) {
                    record.setSyncFailReason(position.getSyncFailReason());
                }
                logger.info("record isRefresh:" + record.getIsRefresh());
                logger.info("record RefreshTime:" + record.getRefreshTime());
                create.attach(record);
                count = record.update();
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            HrThirdPartyPositionRecord record1 = BeanUtils.structToDB(position, HrThirdPartyPositionRecord.class);
            create.attach(record1);
            count = record1.insert();
        }
        logger.info("upsertThirdPartyPosition count:" + count);
        return count;
    }
}
