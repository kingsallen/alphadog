package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrTeam;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamRecord;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.SelectOp;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * @author xxx HrTeamDao 实现类 （groovy 生成） 2017-03-21
 */
@Repository
public class HrTeamDao extends JooqCrudImpl<HrTeamDO, HrTeamRecord> {

    public HrTeamDao() {
        super(HrTeam.HR_TEAM, HrTeamDO.class);
    }

    public HrTeamDao(TableImpl<HrTeamRecord> table, Class<HrTeamDO> hrTeamDOClass) {
        super(table, hrTeamDOClass);
    }

    public List<HrTeamDO> getHrTeams(Map<String, String> query) throws Exception {
        QueryUtil queryUtil = new QueryUtil();
        if (query == null) {
            query = new HashMap<>();
        }
        queryUtil.setEqualFilter(query);

        if (query.containsKey("appid")) {
            query.remove("appid");
        }

        if (query.containsKey("page")) {
            String page = query.remove("page");
            if (StringUtils.isNumeric(page)) {
                queryUtil.setPageNo(Integer.valueOf(page));
            }
        }

        if (query.containsKey("per_page")) {
            String perPage = query.remove("per_page");
            if (StringUtils.isNumeric(perPage)) {
                queryUtil.setPer_page(Integer.valueOf(perPage));
            }
        } else if (query.containsKey("company_id")) {
            queryUtil.setPer_page(Integer.MAX_VALUE);
        }
        List<HrTeamDO> hrTeamDOList = new ArrayList<>();
        List<HrTeamRecord> records = getRecords(queryUtil);
        for (HrTeamRecord record : records) {
            hrTeamDOList.add(record.into(HrTeamDO.class));
        }
        return hrTeamDOList;

    }
    

	
}
