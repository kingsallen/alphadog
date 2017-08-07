package com.moseeker.entity;

import com.moseeker.baseorm.dao.configdb.ConfigSysPointsConfTplDao;
import com.moseeker.baseorm.dao.hrdb.HrPointsConfDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysPointsConfTplDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrPointsConfDO;
import com.moseeker.thrift.gen.employee.struct.RewardConfig;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lucky8987 on 17/7/5.
 */
@Service
public class CompanyConfigEntity {

    @Autowired
    private ConfigSysPointsConfTplDao configSysPointsConfTplDao;

    @Autowired
    private HrPointsConfDao hrPointsConfDao;

    public List<RewardConfig> getRerawConfig(int companyId, boolean showInWx) {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("company_id", String.valueOf(companyId));
        List<RewardConfig> pcfList = new ArrayList<>();
        /*
         * 开始查询积分规则：
         */
        List<HrPointsConfDO> pointsConfs = hrPointsConfDao.getDatas(query.buildQuery());
        if (!StringUtils.isEmptyList(pointsConfs)) {
            List<Integer> tpIds = pointsConfs.stream().map(m -> m.getTemplateId()).collect(Collectors.toList());
            query.clear();
            query.where(new Condition("id", tpIds, ValueOp.IN));
            List<ConfigSysPointsConfTplDO> configTpls = configSysPointsConfTplDao.getDatas(query.buildQuery());
            if (!StringUtils.isEmptyList(configTpls)) {
                List<Integer> ctpIds = configTpls.stream().filter(m -> m.isInitAward == 0)
                        .sorted(Comparator.comparingInt(m -> m.getPriority()))
                        .map(m -> m.getId()).collect(Collectors.toList());
                if (ctpIds != null) {
                    for (int tempId : ctpIds) {
                        for (HrPointsConfDO pcf : pointsConfs) {
                            if ((showInWx ? pcf.getReward() > 0 : pcf.getReward() >= 0) && tempId == pcf.getTemplateId()) {
                                RewardConfig rewardConfig = new RewardConfig();
                                rewardConfig.setId(pcf.getId());
                                rewardConfig.setPoints((int) pcf.getReward());
                                rewardConfig.setStatusName(pcf.getStatusName());
                                pcfList.add(rewardConfig);
                            }
                        }
                    }
                }
            }
        }
        return pcfList;
    }
}
