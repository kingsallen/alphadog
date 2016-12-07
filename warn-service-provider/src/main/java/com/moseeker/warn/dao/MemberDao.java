package com.moseeker.warn.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SelectWhereStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.configdb.tables.ConfigAdminnotificationGroupmembers;
import com.moseeker.db.configdb.tables.ConfigAdminnotificationMembers;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationGroupmembersRecord;
import com.moseeker.db.configdb.tables.records.ConfigAdminnotificationMembersRecord;
import com.moseeker.warn.dto.Member;

@Repository
public class MemberDao extends BaseDaoImpl<ConfigAdminnotificationMembersRecord, ConfigAdminnotificationMembers> {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private GroupMemberDao groupMemberDao;
	
	@Override
	protected void initJOOQEntity() {
		this.tableLike = ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS;
	}
	
	/**
	 * 获取组员信息
	 * @param groupId
	 * @return list<{@link Memeber}>
	 */
	public List<Member> getMembers(Integer groupId){
		List<Member> list = new ArrayList<Member>();
		byte status = 1;
		try (Connection conn = DBConnHelper.DBConn.getConn()) {
			DSLContext jooqDSL = DBConnHelper.DBConn.getJooqDSL(conn);
			SelectWhereStep<ConfigAdminnotificationGroupmembersRecord> selectFrom = jooqDSL.selectFrom(ConfigAdminnotificationGroupmembers.CONFIG_ADMINNOTIFICATION_GROUPMEMBERS);
			List<Integer> memberIds = selectFrom.where(ConfigAdminnotificationGroupmembers.CONFIG_ADMINNOTIFICATION_GROUPMEMBERS.GROUPID.eq(groupId)).fetch(ConfigAdminnotificationGroupmembers.CONFIG_ADMINNOTIFICATION_GROUPMEMBERS.MEMBERID);
			list = jooqDSL.selectFrom(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS).where(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS.ID.in(memberIds)).and(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS.STATUS.eq(status)).fetch().into(Member.class);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

}
