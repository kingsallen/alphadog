package com.moseeker.entity;

import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyEmailInfoDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolEmailDao;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateMessageLibrary;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyEmailInfo;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolEmailRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysTemplateMessageLibraryDO;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentPoolEmailEntity {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HrCompanyEmailInfoDao hrCompanyEmailInfoDao;
    @Autowired
    private ConfigSysTemplateMessageLibraryDao configSysTemplateMessageLibraryDao;
    @Autowired
    private TalentpoolEmailDao talentpoolEmailDao;

    public HrCompanyEmailInfo getHrCompanyEmailInfoByCompanyId(int company_id){
        List<HrCompanyEmailInfo> infoList = hrCompanyEmailInfoDao.getHrCompanyEmailInfoListByCompanyId(company_id);
        if(infoList != null && infoList.size()>0){
            return infoList.get(0);
        }
        return  null;
    }

    public List<ConfigSysTemplateMessageLibraryDO> getConfigSysTemplateMessageLibraryDOByIdList(List<Integer> idList){
        return  configSysTemplateMessageLibraryDao.getConfigSysTemplateMessageLibraryDOByidListAndDisable(idList, 0);
    }

    public ConfigSysTemplateMessageLibraryDO getConfigSysTemplateMessageLibraryDOById(Integer id){
        Query query = new Query.QueryBuilder().where(ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.ID.getName(), id)
                .buildQuery();
        return  configSysTemplateMessageLibraryDao.getData(query);
    }

    public List<TalentpoolEmail> getTalentpoolEmailByCompanyId(Integer company_id){
        return talentpoolEmailDao.getTalentpoolEmailByCompanyId(company_id);
    }

    public List<TalentpoolEmail> getTalentpoolEmailByCompanyIdAndConfigId(Integer company_id, int config_id){
        return talentpoolEmailDao.getTalentpoolEmailByCompanyIdAndConfigId(company_id, config_id);
    }

    public int updateEmailInfo( int company_id, int type, int disable, String context, String inscribe){
        Query query = new Query.QueryBuilder().where("company_id", company_id)
                .and("config_id", type).buildQuery();
        TalentpoolEmailRecord emailRecord = talentpoolEmailDao.getRecord(query);
        if(disable>=0){
            emailRecord.setDisable(disable);
        }
        if(StringUtils.isNotNullOrEmpty(context)){
            emailRecord.setContext(context);
        }
        if(StringUtils.isNotNullOrEmpty(inscribe)){
            emailRecord.setInscribe(inscribe);
        }
        int result = talentpoolEmailDao.updateRecord(emailRecord);
        return result;
    }


}
