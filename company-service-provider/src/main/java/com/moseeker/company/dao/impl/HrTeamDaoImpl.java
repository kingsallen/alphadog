package com.moseeker.company.dao.impl;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.hrdb.tables.HrTeam;
import com.moseeker.db.hrdb.tables.records.HrTeamRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangdi on 2017/5/15.
 */
@Service
public class HrTeamDaoImpl extends BaseDaoImpl<HrTeamRecord, HrTeam> {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrTeam.HR_TEAM;
    }

    public List<HrTeamDO> getHrTeams(Map<String, String> query) throws Exception {
        QueryUtil queryUtil = new QueryUtil();
        if (query == null) {
            query = new HashMap<>();
        }
        query.remove("appid");
        queryUtil.setEqualFilter(query);
        if (queryUtil.getEqualFilter().containsKey("company_id")) {
            queryUtil.setPer_page(Integer.MAX_VALUE);
        }
        List<HrTeamDO> hrTeamDOList = new ArrayList<>();
        List<HrTeamRecord> records = getResources(queryUtil);
        for (HrTeamRecord record : records) {
            hrTeamDOList.add(record.into(HrTeamDO.class));
        }
        return hrTeamDOList;

    }


}
