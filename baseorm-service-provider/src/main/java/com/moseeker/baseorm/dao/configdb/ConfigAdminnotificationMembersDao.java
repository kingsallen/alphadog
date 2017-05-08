package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigAdminnotificationMembers;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigAdminnotificationMembersRecord;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigAdminnotificationMembersDO;
import com.moseeker.thrift.gen.dao.struct.configdb.Member;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author xxx
* ConfigAdminnotificationMembersDao 实现类 （groovy 生成）
* 2017-05-05
*/
@Repository
public class ConfigAdminnotificationMembersDao extends JooqCrudImpl<ConfigAdminnotificationMembersDO, ConfigAdminnotificationMembersRecord> {

    @Autowired
    private ConfigAdminnotificationGroupmembersDao groupmembersDao;


    public ConfigAdminnotificationMembersDao(TableImpl<ConfigAdminnotificationMembersRecord> table, Class<ConfigAdminnotificationMembersDO> configAdminnotificationMembersDOClass) {
        super(table, configAdminnotificationMembersDOClass);
    }

   public ConfigAdminnotificationMembersDao() {
        super(ConfigAdminnotificationMembers.CONFIG_ADMINNOTIFICATION_MEMBERS, ConfigAdminnotificationMembersDO.class);
   }

    /**
     * 根据groupId获取组员信息
     * @param groupId
     * @return list<{@link Memeber}>
     */
    public List<Member> getMembers(Integer groupId){
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where("groupid", groupId);
        List<Integer> memberIds = groupmembersDao.getRecords(queryBuilder.buildQuery()).stream().map(m -> m.getMemberid()).collect(Collectors.toList());
        Query.QueryBuilder queryBuilder1 = new Query.QueryBuilder();
        queryBuilder1.where(new Condition("id", memberIds, ValueOp.IN));
        queryBuilder1.and("status", 1);
        return getDatas(queryBuilder1.buildQuery(), Member.class);
    }
}
