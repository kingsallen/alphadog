package com.moseeker.baseorm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;

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

	@Override
	protected void initJOOQEntity() {
		this.tableLike = HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT;
	}

	public int upsertResource(HrThirdPartyAccountRecord record) {
		int count = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				) {
			String sql = "insert into hrdb.hr_third_party_account(channel, username, password, membername, binding, company_id, remain_num, sync_time) select ?, ?, ?, ?, ?, ?, ?, ? from DUAL where not exists(select id from hrdb.hr_third_party_account where channel = ? and company_id = ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
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
			if(count == 0) {
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				HrThirdPartyAccountRecord dbrecord = create.selectFrom(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT).where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.COMPANY_ID.equal(record.getCompanyId()).and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.equal(record.getChannel()))).fetchOne();
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
			
		}
		return count;
	}

}
