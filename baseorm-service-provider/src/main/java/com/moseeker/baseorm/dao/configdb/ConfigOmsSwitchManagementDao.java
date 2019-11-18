package com.moseeker.baseorm.dao.configdb;

import com.moseeker.baseorm.constant.ValidGeneralType;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.pojos.ConfigOmsSwitchManagement;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigOmsSwitchManagementRecord;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigOmsSwitchManagementDO;
import org.jooq.Condition;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.moseeker.baseorm.db.configdb.tables.ConfigOmsSwitchManagement.CONFIG_OMS_SWITCH_MANAGEMENT;
@Repository
public class ConfigOmsSwitchManagementDao extends JooqCrudImpl<ConfigOmsSwitchManagementDO, ConfigOmsSwitchManagementRecord> {

    public ConfigOmsSwitchManagementDao() {
        super(CONFIG_OMS_SWITCH_MANAGEMENT, ConfigOmsSwitchManagementDO.class);
    }

    public ConfigOmsSwitchManagementDao(TableImpl<ConfigOmsSwitchManagementRecord> table, Class<ConfigOmsSwitchManagementDO> configCronjobsDOClass) {
        super(table, configCronjobsDOClass);
    }

    public List<ConfigOmsSwitchManagement> getValidOmsSwitchListByParams(int companyId, List<Integer> moduleList){
        Condition condition =  CONFIG_OMS_SWITCH_MANAGEMENT.IS_VALID.eq(ValidGeneralType.valid.getValue());
        if(companyId != 0){
            condition = condition.and(CONFIG_OMS_SWITCH_MANAGEMENT.COMPANY_ID.eq(companyId));
        }
        if(moduleList.size()>0){
            condition = condition.and(CONFIG_OMS_SWITCH_MANAGEMENT.MODULE_NAME.in(moduleList));
        }
        return create.selectFrom(CONFIG_OMS_SWITCH_MANAGEMENT).where(condition).fetchInto(ConfigOmsSwitchManagement.class);
    }

    public ConfigOmsSwitchManagement getOmsSwitchByParams(int companyId, Integer moduleId){
        return create.selectFrom(CONFIG_OMS_SWITCH_MANAGEMENT)
                .where(CONFIG_OMS_SWITCH_MANAGEMENT.COMPANY_ID.eq(companyId)
                        .and(CONFIG_OMS_SWITCH_MANAGEMENT.MODULE_NAME.eq(moduleId)))
                .fetchOneInto(ConfigOmsSwitchManagement.class);
    }


    public ConfigOmsSwitchManagement getValidOmsSwitchByParams(int id,int companyId, Integer moduleId){
        return create.selectFrom(CONFIG_OMS_SWITCH_MANAGEMENT)
                .where(CONFIG_OMS_SWITCH_MANAGEMENT.ID.eq(id)
                        .and(CONFIG_OMS_SWITCH_MANAGEMENT.COMPANY_ID.eq(companyId))
                        .and(CONFIG_OMS_SWITCH_MANAGEMENT.MODULE_NAME.eq(moduleId)))
                .fetchOneInto(ConfigOmsSwitchManagement.class);
    }


    public Integer add(ConfigOmsSwitchManagement configOmsSwitchManagement) {
        ConfigOmsSwitchManagementRecord newRecord = create.newRecord(CONFIG_OMS_SWITCH_MANAGEMENT,configOmsSwitchManagement);
        create.attach(newRecord);
        newRecord.insert();
        return newRecord.getId();
    }

    public Integer update(ConfigOmsSwitchManagement configOmsSwitchManagement) {
        return create.update(CONFIG_OMS_SWITCH_MANAGEMENT)
                .set(CONFIG_OMS_SWITCH_MANAGEMENT.MODULE_PARAM,configOmsSwitchManagement.getModuleParam())
                .set(CONFIG_OMS_SWITCH_MANAGEMENT.IS_VALID,configOmsSwitchManagement.getIsValid())
                .set(CONFIG_OMS_SWITCH_MANAGEMENT.VERSION,configOmsSwitchManagement.getVersion()+1)
                .where(CONFIG_OMS_SWITCH_MANAGEMENT.ID.eq(configOmsSwitchManagement.getId())
                        .and(CONFIG_OMS_SWITCH_MANAGEMENT.COMPANY_ID.eq(configOmsSwitchManagement.getCompanyId()))
                        .and(CONFIG_OMS_SWITCH_MANAGEMENT.MODULE_NAME.eq(configOmsSwitchManagement.getModuleName()))
                        .and(CONFIG_OMS_SWITCH_MANAGEMENT.VERSION.eq(configOmsSwitchManagement.getVersion())))
                .execute();
    }

    public List<ConfigOmsSwitchManagementDO> fetchRadarStatus(int moduleId, int isValid) {
        return create.selectFrom(CONFIG_OMS_SWITCH_MANAGEMENT)
                .where(CONFIG_OMS_SWITCH_MANAGEMENT.IS_VALID.eq((byte)isValid)
                        .and(CONFIG_OMS_SWITCH_MANAGEMENT.MODULE_NAME.eq(moduleId)))
                .fetchInto(ConfigOmsSwitchManagementDO.class);
    }

    public void batchInsertIfNotExists(List<ConfigOmsSwitchManagement> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        List<ConfigOmsSwitchManagementRecord> records = list
                .stream()
                .map(configOmsSwitchManagement
                        -> create.newRecord(CONFIG_OMS_SWITCH_MANAGEMENT,configOmsSwitchManagement))
                .collect(Collectors.toList());
        create.batchInsert(records);
    }
}
