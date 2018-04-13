package com.moseeker.application.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionProfileFilterDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolExecuteDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolProfileFilterDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolProfileFilterExcuteDao;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionProfileFilter;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogTalentpoolProfileFilterLogRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolExecute;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilterExecute;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.biz.CompanyFilterTagValidation;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.mq.struct.MessageEmailStruct;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by moseeker on 2018/4/13.
 */
@Service
public class JobApplicationFilterService {

    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private JobApplicationDao jobApplicationDao;
    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;
    @Autowired
    private JobPositionProfileFilterDao jobPositionProfileFilterDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private TalentpoolProfileFilterDao talentpoolProfileFilterDao;

    @Autowired
    private CompanyFilterTagValidation tagValidation;

    @Autowired
    private TalentpoolProfileFilterExcuteDao talentpoolProfileFilterExcuteDao;

    @Autowired
    private TalentpoolExecuteDao talentpoolExecuteDao;


    WholeProfileServices.Iface profileService = ServiceManager.SERVICEMANAGER.getService(WholeProfileServices.Iface.class);

    public void handerApplicationFilter(MessageEmailStruct filterInfoStruct) throws TException {
        JobPositionRecord positionDO = jobPositionDao.getPositionById(filterInfoStruct.getPosition_id());
        HrCompanyDO companyDO = hrCompanyDao.getCompanyById(positionDO.getCompanyId());
        if(companyDO != null && companyDO.getType() == 0){
            List<Integer> companyIds = new ArrayList<>();
            companyIds.add(companyDO.getId());
            List<HrCompanyConfDO> companyConfDOList = hrCompanyConfDao.getHrCompanyConfByCompanyIds(companyIds);
            if(companyConfDOList != null && companyConfDOList.size()>0){
                if(companyConfDOList.get(0).getTalentpoolStatus() == 2){
                    List<JobPositionProfileFilter> positionProfileFilterList = jobPositionProfileFilterDao.getFilterPositionRecordByPositionId(positionDO.getCompanyId());
                    if(positionProfileFilterList!= null && positionProfileFilterList.size()>0) {
                        List<Integer> filterIdList = positionProfileFilterList.stream().map(m -> m.getPfid()).collect(Collectors.toList());
                        List<Integer> filterExecute1 = talentpoolProfileFilterExcuteDao.getFilterExcuteByFilterIdListAndExecuterId(filterIdList,1);
                        List<Integer> filterExecute2 = talentpoolProfileFilterExcuteDao.getFilterExcuteByFilterIdListAndExecuterId(filterIdList,2);
                        List<Integer> filterExecute3 = talentpoolProfileFilterExcuteDao.getFilterExcuteByFilterIdListAndExecuterId(filterIdList,3);
                        List<Integer> filterExecute4 = talentpoolProfileFilterExcuteDao.getFilterExcuteByFilterIdListAndExecuterId(filterIdList,4);
                        Map<Integer,TalentpoolProfileFilter>  talentpoolProfileFilterMap = talentpoolProfileFilterDao.getTalentpoolProfileFilterMapByIdListAndCompanyId(companyDO.getId(), 1, filterIdList);
                        Response res=profileService.getResource(filterInfoStruct.getApplier_id(),0,null);
                        Map<Integer, Boolean> filterPassMap = new HashMap<>();
                        boolean passTalentpoolExecute = false;
                        boolean passApplicationExecute = false;
                        Map<String, Object> profiles = JSON.parseObject(res.getData());
                        if(talentpoolProfileFilterMap != null && talentpoolProfileFilterMap.size()>0){
                            passTalentpoolExecute = forTalentpoolProfileFilter(filterExecute2, talentpoolProfileFilterMap, profiles, filterInfoStruct, filterPassMap, companyDO.getId(), passTalentpoolExecute, 2);
                            passTalentpoolExecute = forTalentpoolProfileFilter(filterExecute1, talentpoolProfileFilterMap, profiles, filterInfoStruct, filterPassMap, companyDO.getId(), passTalentpoolExecute, 1);
                            passApplicationExecute = forTalentpoolProfileFilter(filterExecute4, talentpoolProfileFilterMap, profiles, filterInfoStruct, filterPassMap, companyDO.getId(), passApplicationExecute, 4);
                            passApplicationExecute = forTalentpoolProfileFilter(filterExecute3, talentpoolProfileFilterMap, profiles, filterInfoStruct, filterPassMap, companyDO.getId(), passApplicationExecute, 3);
                        }
                    }
                }
            }
        }
    }

    //循环验证各个筛选项
    private boolean forTalentpoolProfileFilter(List<Integer> filterIdList, Map<Integer,TalentpoolProfileFilter>  talentpoolProfileFilterMap, Map<String, Object> profiles,
        MessageEmailStruct filterInfoStruct,  Map<Integer, Boolean> filterPassMap, int company_id, boolean bool, int type) throws TException {
        if(filterIdList!=null && filterIdList.size()>0 && !bool) {
            for (Integer filter_id : filterIdList) {
                if (talentpoolProfileFilterMap.get(filter_id) != null) {
                    boolean isflag = false;
                    if(filterPassMap.get(filter_id)== null) {
                        isflag = validateFile(talentpoolProfileFilterMap.get(filter_id), filterInfoStruct, profiles, company_id);
                    }else{
                        isflag = filterPassMap.get(filter_id);
                    }
                    filterPassMap.put(filter_id, isflag);
                    bool = isflag;
                    if (isflag){
                        break;
                    }
                }
            }
        }
        return bool;
    }

    //验证筛选项
    private boolean validateFile(TalentpoolProfileFilter filter, MessageEmailStruct filterInfoStruct, Map<String, Object>profiles, int company_id) throws TException {
        String tagStr = JSON.toJSONString(filter);
        Map<String, Object> filterMap = JSON.parseObject(tagStr);
        filterMap.remove("origins");
        filterMap.remove("submitTime");
        filterMap.remove("isRecommend");
        boolean isflag=tagValidation.validateProfileAndComapnyTag(profiles,filterInfoStruct.getApplier_id(), company_id,filterMap);
        if(isflag){
            isflag = validateProfileAndFilterOther(filter, filterInfoStruct);
        }
        LogTalentpoolProfileFilterLogRecord logRecord = new LogTalentpoolProfileFilterLogRecord();
        return isflag;
    }

    private  boolean validateProfileAndFilterOther(TalentpoolProfileFilter filter, MessageEmailStruct filterInfoStruct){
        if(StringUtils.isNotNullOrEmpty(filter.getOrigins())){
            boolean flag = validateProfileAndFilterOrigins(filter.getOrigins(), filterInfoStruct.getOrigin());
            if(!flag){
                return  flag;
            }
        }
        if(filter.getIsRecommend()>0 && filterInfoStruct.getRecommender_user_id()<1){
            return false;
        }
        return true;
    }

    private boolean validateProfileAndFilterOrigins(String origins, int origin){
        String[] originArray = origins.split(",");
        for(String item:originArray){
            if(StringUtils.isNotNullOrEmpty(item)){
                if(Integer.parseInt(item)==origin){
                    return true;
                }
            }
        }
        return  false;
    }
}
