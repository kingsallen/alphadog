package com.moseeker.baseorm.dao.hrdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountHrRecord;
import org.jooq.DSLContext;
import org.jooq.types.UInteger;
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
public class HRThirdPartyAccountDao extends BaseDaoImpl<HrThirdPartyAccountRecord, HrThirdPartyAccount> {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT;
    }

    public int addThirdPartyAccount(int userId, HrThirdPartyAccountRecord record) {
        int count = 0;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            conn.setAutoCommit(false);
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            create.attach(record);
            //添加第三方账号
            record.insert();
            //HR关联到第三方账号
            if (userId > 0) {
                HrThirdPartyAccountHrRecord hrThirdPartyAccountHrRecord = new HrThirdPartyAccountHrRecord();
                hrThirdPartyAccountHrRecord.setChannel(record.getChannel());
                hrThirdPartyAccountHrRecord.setHrAccountId(userId);
                hrThirdPartyAccountHrRecord.setThirdPartyAccountId(record.getId());
                create.attach(hrThirdPartyAccountHrRecord);
                return hrThirdPartyAccountHrRecord.insert();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    public int updateThirdPartyAccount(HrThirdPartyAccountRecord record) {
        try {
            return putResource(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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

    /**
     * 修改第三方帐号信息
     *
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
                            .equal(UInteger.valueOf(account.getCompany_id())))
                    .and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.equal((short) account.getChannel()))
                    .fetchOne();
            if (record != null) {
                logger.info("HrThirdPartyAccount.id:{}", record.getId().intValue());
                logger.info("remainume:{}", account.getRemain_num());
                Date date = sdf.parse(account.getSync_time());
                record.setSyncTime(new Timestamp(date.getTime()));
                record.setRemainNum(UInteger.valueOf(account.getRemain_num()));
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
