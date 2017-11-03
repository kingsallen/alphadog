package com.moseeker.entity;

import com.moseeker.baseorm.dao.campaigndb.CampaignPersonaRecomDao;
import com.moseeker.baseorm.dao.historydb.HistoryCampaignPersonaRecomDao;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignPersonaRecomRecord;
import com.moseeker.baseorm.pojo.CampaignPersonaRecomPojo;
import com.moseeker.baseorm.pojo.HistoryCampaignPersonaRecomPojo;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.sun.xml.internal.txw2.TxwException;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zztaiwll on 17/11/3.
 */
@Service
public class PersonaRecomEntity {
    @Autowired
    private CampaignPersonaRecomDao campaignPersonaRecomDao;
    @Autowired
    private HistoryCampaignPersonaRecomDao historyCampaignPersonaRecomDao;
    //将推荐职位数据插入数据库
    @CounterIface
    @Transactional
    public int handlePersonaRecomData(int userId,String positionIds) throws TException {
        int result=this.handlerHistoryData(userId);
        if(result==0){
            throw new TException();
        }
        int result1=upsertPersonRecom(userId,positionIds);
        if(result1==0){
            throw new TException();
        }
        return 1;
    }

    /*
     更新或者或者
     */
    private int upsertPersonRecom(int userId,String positionIds){
        if(StringUtils.isNotNullOrEmpty(positionIds)){
            String[] ids=positionIds.split(",");
            List<CampaignPersonaRecomRecord> list=new ArrayList<>();
            for(String positionId :ids){
                CampaignPersonaRecomRecord campaignPersonaRecomRecord=new CampaignPersonaRecomRecord();
                campaignPersonaRecomRecord.setUserId(userId);
                campaignPersonaRecomRecord.setPositionId(Integer.parseInt(positionId));
                list.add(campaignPersonaRecomRecord);
            }
            if(!StringUtils.isEmptyList(list)){
                List<CampaignPersonaRecomRecord> result=campaignPersonaRecomDao.addAllRecord(list);
                if(!StringUtils.isEmptyList(result)){
                    return 1;
                }
            }
        }
        return 0;
    }
    /*
     处理历史数据
     */
    private int handlerHistoryData(int userId){
        List<CampaignPersonaRecomPojo> list=this.getCampaignPersonaRecomRecordByUserId(userId);
        List<HistoryCampaignPersonaRecomPojo> hisList=this.convertToHistoryCampaignPersonaRecomPojo(list);
        if(StringUtils.isEmptyList(hisList)){
            return 1;
        }
        int result1=insertHistoryCampaignPersonaRecomRecord(hisList);
        if(result1>0){
            int result2=this.deleteRecomPosition(list);
            return result2;
        }
        return 0;
    }
    /*
     删除原有的user_id的数据
     */
    private int deleteRecomPosition(List<CampaignPersonaRecomPojo> list){
        if(StringUtils.isEmptyList(list)){
            return 1;
        }
        int[] ids=campaignPersonaRecomDao.deleteDatas(list);
        if(ids!=null&&ids.length>0){
            return 1;
        }
        return 0;
    }
    /*
     根据user_id获取所有的campaign_persona_recom记录
     */
    private List<CampaignPersonaRecomPojo>  getCampaignPersonaRecomRecordByUserId(int userId){
        Query query=new Query.QueryBuilder().where("user_id",userId).buildQuery();
        List<CampaignPersonaRecomPojo> list=campaignPersonaRecomDao.getDatas(query);
        return list;
    }
    /*
     将数据插入到历史记录中
     */
    private int  insertHistoryCampaignPersonaRecomRecord(List<HistoryCampaignPersonaRecomPojo> list){
        if(StringUtils.isEmptyList(list)){
            return 1;
        }
        List<HistoryCampaignPersonaRecomPojo> result=historyCampaignPersonaRecomDao.addAllData(list);
        if(!StringUtils.isEmptyList(result)){
            return 1;
        }
        return 0;
    }
    /*
     将CampaignPersonaRecomPojo转化为HistoryCampaignPersonaRecomPojo
     */
    private List<HistoryCampaignPersonaRecomPojo> convertToHistoryCampaignPersonaRecomPojo(List<CampaignPersonaRecomPojo> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        List<HistoryCampaignPersonaRecomPojo> result=new ArrayList<>();
        for(CampaignPersonaRecomPojo pojo:list){
            HistoryCampaignPersonaRecomPojo historyCampaignPersonaRecomPojo=new HistoryCampaignPersonaRecomPojo();
            historyCampaignPersonaRecomPojo.setId(pojo.getId());
            historyCampaignPersonaRecomPojo.setCreateTime(pojo.getCreateTime());
            historyCampaignPersonaRecomPojo.setIsSend(pojo.getIsSend());
            historyCampaignPersonaRecomPojo.setPositionId(pojo.getPositionId());
            historyCampaignPersonaRecomPojo.setSendTime(pojo.getSendTime());
            historyCampaignPersonaRecomPojo.setUpdateTime(pojo.getUpdateTime());
            historyCampaignPersonaRecomPojo.setUserId(pojo.getUserId());
            result.add(historyCampaignPersonaRecomPojo);
        }
        return result;
    }

}
