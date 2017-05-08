package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
public class HRThirdPartyAccountDao extends JooqCrudImpl<HrThirdPartyAccountDO, HrThirdPartyAccountRecord> {

	private static final String UPSERT_SQL = "insert into hrdb.hr_third_party_account(channel, username, password, membername, binding, company_id, remain_num, sync_time) select ?, ?, ?, ?, ?, ?, ?, ? from DUAL where not exists(select id from hrdb.hr_third_party_account where channel = ? and company_id = ?)";

	public HRThirdPartyAccountDao(TableImpl<HrThirdPartyAccountRecord> table, Class<HrThirdPartyAccountDO> hrThirdPartyAccountDOClass) {
		super(table, hrThirdPartyAccountDOClass);
	}

	public int upsertResource(HrThirdPartyAccountRecord record) {
		logger.info("HRThirdPartyAccountDao upsertResource");
		logger.info("HRThirdPartyAccountDao upsertResource channel:{}, company_id:{}",record.getChannel(), record.getCompanyId());
		logger.info("HRThirdPartyAccountDao upsertResource record:{}",record);
		int count = 0;
<<<<<<< HEAD
		count = create.execute(UPSERT_SQL, record.getChannel(), record.getUsername(), record.getPassword(),
				record.getMembername(), record.getBinding(), record.getCompanyId().intValue(),
				record.getRemainNum().intValue(), record.getSyncTime(), record.getChannel(),
				record.getCompanyId().intValue());
=======
		try (Connection conn = DBConnHelper.DBConn.getConn();) {

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(UPSERT_SQL);
			pstmt.setShort(1, record.getChannel());
			pstmt.setString(2, record.getUsername());
			pstmt.setString(3, record.getPassword());
			pstmt.setString(4, record.getMembername());
			pstmt.setShort(5, record.getBinding());
			pstmt.setInt(6, record.getCompanyId().intValue());
			pstmt.setInt(7, record.getRemainNum().intValue());
			pstmt.setTimestamp(8, record.getSyncTime());
			pstmt.setShort(9, record.getChannel());
			pstmt.setInt(10, record.getCompanyId().intValue());
			count = pstmt.executeUpdate();
			logger.info("HRThirdPartyAccountDao count:{}",count);
			if (count == 0) {
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				HrThirdPartyAccountRecord dbrecord = create.selectFrom(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
						.where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.COMPANY_ID.equal(record.getCompanyId())
								.and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.equal(record.getChannel())))
						.fetchOne();
				logger.info("HRThirdPartyAccountDao dbrecord:{}",dbrecord);
				dbrecord.setUsername(record.getUsername());
				dbrecord.setPassword(record.getPassword());
				dbrecord.setMembername(record.getMembername());
				dbrecord.setRemainNum(record.getRemainNum());
				dbrecord.setSyncTime(record.getSyncTime());
				dbrecord.setBinding(record.getBinding());
				count = dbrecord.update();
				conn.commit();
				conn.setAutoCommit(true);
				logger.info("HRThirdPartyAccountDao dbrecord:{}",dbrecord);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
>>>>>>> master

		if (count == 0) {
			HrThirdPartyAccountRecord dbrecord = create.selectFrom(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
					.where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.COMPANY_ID.equal(record.getCompanyId())
							.and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.equal(record.getChannel())))
					.fetchOne();
			dbrecord.setUsername(record.getUsername());
			dbrecord.setPassword(record.getPassword());
			dbrecord.setMembername(record.getMembername());
			dbrecord.setBinding(record.getBinding());
			dbrecord.setRemainNum(record.getRemainNum());
			dbrecord.setSyncTime(record.getSyncTime());
			count = dbrecord.update();
		}
		return count;
	}

	public List<HrThirdPartyAccountRecord> getThirdPartyBindingAccounts(Query query) {
		return getRecords(query);
	}

	/**
	 * 修改第三方帐号信息
	 * @param account
	 * @return
	 */
	public int updatePartyAccountByCompanyIdChannel(ThirdPartAccountData account) {
		logger.info("updatePartyAccountByCompanyIdChannel");
		int count = 0;
		try {
			Date date = sdf.parse(account.getSync_time());
			HrThirdPartyAccountRecord record = create.selectFrom(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
                    .where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.COMPANY_ID
                            .equal((int)(account.getCompany_id())))
                    .and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.equal((short) account.getChannel()))
                    .fetchOne();
			if(record != null) {
                logger.info("HrThirdPartyAccount.id:{}", record.getId().intValue());
                logger.info("remainume:{}",account.getRemain_num());
                record.setSyncTime(new Timestamp(date.getTime()));
                record.setRemainNum((int)(account.getRemain_num()));
                count = record.update();
            }
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return count;
	}
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
