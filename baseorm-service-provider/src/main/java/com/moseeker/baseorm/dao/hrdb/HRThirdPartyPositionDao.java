package com.moseeker.baseorm.dao.hrdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import org.joda.time.DateTime;
import org.jooq.DSLContext;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyPositionRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;

/**
 * 
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
 * @version
 */
@Service
public class HRThirdPartyPositionDao extends JooqCrudImpl<HrThirdPartyPositionDO, HrThirdPartyPositionRecord> {

    public HRThirdPartyPositionDao() {
        super(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION, HrThirdPartyPositionDO.class);
    }

	private static final String UPSERT_SQL = "insert into hrdb.hr_third_party_position(position_id, third_part_position_id, is_synchronization, is_refresh, sync_time, refresh_time, update_time, occupation, address, channel) select ?, ?, ?, ?, ?, ?, ?, ?, ?, ? from DUAL where not exists(select id from hrdb.hr_third_party_position where channel = ? and position_id = ?)";

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public HRThirdPartyPositionDao(TableImpl<HrThirdPartyPositionRecord> table, Class<HrThirdPartyPositionDO> hrThirdPartyPositionDOClass) {
		super(table, hrThirdPartyPositionDOClass);
	}

	public List<ThirdPartyPositionData> getThirdPartyPositions(Query query) {
		List<ThirdPartyPositionData> datas = new ArrayList<>();
		try {
			List<HrThirdPartyPositionRecord> records = this.getRecords(query);
			if (records != null && records.size() > 0) {
				records.forEach(record -> {
					ThirdPartyPositionData position = new ThirdPartyPositionData();
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

	private void copy(ThirdPartyPositionData position, HrThirdPartyPositionRecord record) {
		position.setAddress(record.getAddress());
		position.setId(record.getId());
		position.setChannel(record.getChannel().byteValue());
		position.setOccupation(record.getOccupation());
		position.setPosition_id(record.getPositionId().intValue());
		if (record.getSyncTime() != null) {
			position.setSync_time((new DateTime(record.getSyncTime().getTime())).toString("yyyy-MM-dd"));
		}
		if (record.getRefreshTime() != null) {
			position.setRefresh_time((new DateTime(record.getRefreshTime().getTime())).toString("yyyy-MM-dd"));
		}
		if (record.getUpdateTime() != null) {
			position.setUpdate_time((new DateTime(record.getUpdateTime().getTime())).toString("yyyy-MM-dd"));
		}
		position.setThird_part_position_id(record.getThirdPartPositionId());
		position.setIs_refresh(record.getIsRefresh().byteValue());
		position.setIs_synchronization(record.getIsSynchronization().byteValue());
	}

	public Response upsertThirdPartyPositions(List<ThirdPartyPositionData> positions) {
		if (positions != null && positions.size() > 0) {
			logger.info("companyDao upsertThirdPartyPositions" + JSON.toJSONString(positions));
				positions.forEach(position -> {
					try {
						int count = 0;
						Date syncTime = StringUtils.isNotNullOrEmpty(position.getSync_time())? sdf.parse(position.getSync_time()):null;
						Date refreshTime = StringUtils.isNotNullOrEmpty(position.getRefresh_time())?sdf.parse(position.getRefresh_time()):null;
						Date updateTime = StringUtils.isNotNullOrEmpty(position.getUpdate_time())?sdf.parse(position.getUpdate_time()):null;

						create.execute(UPSERT_SQL, position.getPosition_id(), position.getThird_part_position_id(),
								position.getIs_synchronization(), position.getIs_refresh(), syncTime, refreshTime,
								updateTime, position.getOccupation(), position.getAddress(), position.getChannel(),
								position.getChannel(), position.getPosition_id());

						if (count == 0) {
							logger.info("companyDao upsertThirdPartyPositions exist");
							HrThirdPartyPositionRecord dbrecord = create
									.selectFrom(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION)
									.where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID
											.equal((int)(position.getPosition_id())))
									.and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.CHANNEL
											.equal(Short.valueOf(position.getChannel())))
									.fetchOne();
							if (dbrecord != null) {
								if(StringUtils.isNotNullOrEmpty(position.getAddress())) {
									dbrecord.setAddress(position.getAddress());
								}
								
								if(StringUtils.isNotNullOrEmpty(position.getThird_part_position_id())) {
									dbrecord.setThirdPartPositionId(position.getThird_part_position_id());
								}
								dbrecord.setPositionId((int)(position.getPosition_id()));
								if(position.getThird_part_position_id() != null) {
									dbrecord.setThirdPartPositionId(position.getThird_part_position_id());
								}
								dbrecord.setChannel(Short.valueOf(position.getChannel()));
								if(position.isSetIs_refresh()) {
									dbrecord.setIsRefresh(Short.valueOf(position.getIs_refresh()));
								}
								if(position.isSetIs_synchronization()) {
									dbrecord.setIsSynchronization(Short.valueOf(position.getIs_synchronization()));
								}
								if(position.getOccupation() != null) {
									dbrecord.setOccupation(position.getOccupation());
								}
								if (position.getSync_time() != null) {
									dbrecord.setSyncTime(new Timestamp(sdf.parse(position.getSync_time()).getTime()));
								}
								if (position.getRefresh_time() != null) {
									dbrecord.setRefreshTime(
											new Timestamp(sdf.parse(position.getRefresh_time()).getTime()));
								}
								if (position.getUpdate_time() != null) {
									dbrecord.setUpdateTime(
											new Timestamp(sdf.parse(position.getUpdate_time()).getTime()));
								}
								if(position.isSetSync_fail_reason()) {
									dbrecord.setSyncFailReason(position.getSync_fail_reason());
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
	 * @param positionId
	 *            职位编号
	 * @param channel
	 *            渠道号
	 * @return
	 */
	public ThirdPartyPositionData getThirdPartyPosition(int positionId, int channel) {
		ThirdPartyPositionData position = new ThirdPartyPositionData();
		if (positionId > 0 && channel > 0) {
			HrThirdPartyPositionRecord record = create.selectFrom(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION)
					.where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID
							.eq((int)(positionId)))
					.and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.CHANNEL.eq((short) channel)).fetchOne();
			if (record != null) {
				record.into(position);
				position.setUpdate_time(sdf.format(record.getUpdateTime()));
				if(record.getSyncTime() != null) {
					position.setSync_time(sdf.format(record.getSyncTime()));
				}
				if(record.getRefreshTime() != null) {
					position.setRefresh_time(sdf.format(record.getRefreshTime()));
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
	public int upsertThirdPartyPosition(ThirdPartyPositionData position) {
		int count = 0;
		
		logger.info("isrefresh:"+position.getIs_refresh());
		logger.info("refresh_time:"+position.getRefresh_time());
		HrThirdPartyPositionRecord record = create.selectFrom(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION)
				.where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID
						.eq((int)(position.getPosition_id())))
				.and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.CHANNEL.eq((short) position.getChannel()))
				.fetchOne();
		if (record != null) {
			//BeanUtils.structToDB(position, record, null);
			logger.info("record before is_refresh:"+record.getIsRefresh());
			try {
				if(StringUtils.isNotNullOrEmpty(position.getAddress())) {
                    record.setAddress(position.getAddress());
                }
				if(StringUtils.isNotNullOrEmpty(position.getThird_part_position_id())) {
                    record.setThirdPartPositionId(position.getThird_part_position_id());
                }
				record.setPositionId((int)(position.getPosition_id()));
				if(position.getThird_part_position_id() != null) {
                    record.setThirdPartPositionId(position.getThird_part_position_id());
                }
				record.setChannel(Short.valueOf(position.getChannel()));
				if(position.isSetIs_refresh()) {
                    record.setIsRefresh(Short.valueOf(position.getIs_refresh()));
                }
				if(position.getIs_synchronization() != 0) {
                    record.setIsSynchronization(Short.valueOf(position.getIs_synchronization()));
                }
				if(position.getOccupation() != null) {
                    record.setOccupation(position.getOccupation());
                }
				if (position.getSync_time() != null) {
                    record.setSyncTime(new Timestamp(sdf.parse(position.getSync_time()).getTime()));
                }
				if (position.getRefresh_time() != null) {
                    record.setRefreshTime(
                            new Timestamp(sdf.parse(position.getRefresh_time()).getTime()));
                }
				if (position.getUpdate_time() != null) {
                    record.setUpdateTime(
                            new Timestamp(sdf.parse(position.getUpdate_time()).getTime()));
                }
				if(position.isSetSync_fail_reason()) {
                    record.setSyncFailReason(position.getSync_fail_reason());
                }
				logger.info("record is_refresh:"+record.getIsRefresh());
				logger.info("record Refresh_time:"+record.getRefreshTime());
				create.attach(record);
				count = record.update();
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			HrThirdPartyPositionRecord record1 = BeanUtils.structToDB(position,
					HrThirdPartyPositionRecord.class, null);
			create.attach(record1);
			count = record1.insert();
		}
		logger.info("upsertThirdPartyPosition count:"+count);
		return count;
	}
}
