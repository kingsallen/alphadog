package com.moseeker.baseorm.dao.hrdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jooq.DSLContext;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
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
public class HRThirdPartyAccountDao extends BaseDaoImpl<HrThirdPartyAccountRecord, HrThirdPartyAccount> {

	private static final String UPSERT_SQL = "insert into hrdb.hr_third_party_account(channel, username, password, membername, binding, company_id, remain_num, sync_time) select ?, ?, ?, ?, ?, ?, ?, ? from DUAL where not exists(select id from hrdb.hr_third_party_account where channel = ? and company_id = ?)";

	@Override
	protected void initJOOQEntity() {
		this.tableLike = HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT;
	}

	public int upsertResource(HrThirdPartyAccountRecord record) {
		int count = 0;
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
			if (count == 0) {
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
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
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);

		} finally {
			// do nothing
		}
		return count;
	}

	public List<HrThirdPartyAccountRecord> getThirdPartyBindingAccounts(CommonQuery query) {
		try {
			return this.getResources(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return null;
	}

	public Response saveThirdPartyPosition(ThirdPartyPositionData position) {
		return null;
	}

	/**
	 * 修改第三方帐号信息
	 * @param account
	 * @return
	 */
	public int updatePartyAccountByCompanyIdChannel(ThirdPartAccountData account) {
		logger.info("updatePartyAccountByCompanyIdChannel");
		int count = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);) {
			HrThirdPartyAccountRecord record = create.selectFrom(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
					.where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.COMPANY_ID
							.equal((int)(account.getCompany_id())))
					.and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.equal((short) account.getChannel()))
					.fetchOne();
			if(record != null) {
				logger.info("HrThirdPartyAccount.id:{}", record.getId().intValue());
				logger.info("remainume:{}",account.getRemain_num());
				Date date = sdf.parse(account.getSync_time());
				record.setSyncTime(new Timestamp(date.getTime()));
				record.setRemainNum((int)(account.getRemain_num()));
				count = record.update();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return count;
	}
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
