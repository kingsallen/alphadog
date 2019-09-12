package com.moseeker.entity;

import com.moseeker.baseorm.dao.campaigndb.CampaignPersonaRecomDao;
import com.moseeker.baseorm.dao.historydb.HistoryCampaignPersonaRecomDao;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignPersonaRecomRecord;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryCampaignPersonaRecomRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public int handlePersonaRecomData(int userId,String positionIds,int companyId,int type) throws TException {
        int result=this.handlerHistoryData(userId,companyId,type);
        if(result==0){
            throw new TException();
        }
        int result1=upsertPersonRecom(userId,companyId,positionIds,type);
        if(result1==0){
            throw new TException();
        }
        return 1;
    }
    /*
     根据createTime排序，获取固定userid的20条数据
     */
    public  List<CampaignPersonaRecomRecord> getCampaignPersonaRecomByuserId(int userId,int companyId,int page,int pageSize){
        Query query=new Query.QueryBuilder().where("user_id",userId).and("company_id",companyId).orderBy("create_time", Order.DESC).setPageNum(page).setPageSize(pageSize).buildQuery();
        List<CampaignPersonaRecomRecord> list=campaignPersonaRecomDao.getRecords(query);
        return list;
    }
    /*
        更新推荐职位数据是否已经推荐
     */
    public int updateIsSendPersonaRecom(int userId,int companyId,int type,int page,int pageSize){
        Query query=new Query.QueryBuilder().where("user_id",userId).and("company_id",companyId).and("type",(byte)type).orderBy("create_time", Order.DESC).setPageNum(page).setPageSize(pageSize).buildQuery();
        List<CampaignPersonaRecomRecord> list=campaignPersonaRecomDao.getRecords(query);
        if(StringUtils.isEmptyList(list)){
            return 1;
        }
        SimpleDateFormat f=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String date=f.format(new Date());
        for(CampaignPersonaRecomRecord pojo:list){
            pojo.setIsSend((byte)1);
            pojo.setSendTime(new Timestamp(System.currentTimeMillis()));
        }
        campaignPersonaRecomDao.updateRecords(list);
        return 1;

    }
    /*
     更新或者或者
     */
    private int upsertPersonRecom(int userId,int companyId,String positionIds,int type){
        if(StringUtils.isNotNullOrEmpty(positionIds)){
            String[] ids=positionIds.split(",");
            List<CampaignPersonaRecomRecord> list=new ArrayList<>();
            for(String positionId :ids){
                CampaignPersonaRecomRecord campaignPersonaRecomRecord=new CampaignPersonaRecomRecord();
                campaignPersonaRecomRecord.setUserId(userId);
                campaignPersonaRecomRecord.setCompanyId(companyId);
                campaignPersonaRecomRecord.setPositionId(Integer.parseInt(positionId));
                campaignPersonaRecomRecord.setType((byte)type);
                campaignPersonaRecomRecord.setSendTime(Timestamp.valueOf("1970-01-02 00:00:00"));
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
    private int handlerHistoryData(int userId,int companyId,int type){
        List<CampaignPersonaRecomRecord> list=this.getCampaignPersonaRecomRecordByUserIdAndCompanyId(userId,companyId,type);
        List<HistoryCampaignPersonaRecomRecord> hisList=this.convertToHistoryCampaignPersonaRecomPojo(list);
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
    private int deleteRecomPosition(List<CampaignPersonaRecomRecord> list){
        if(StringUtils.isEmptyList(list)){
            return 1;
        }
        int[] ids=campaignPersonaRecomDao.deleteRecords(list);
        if(ids!=null&&ids.length>0){
            return 1;
        }
        return 0;
    }
    /*
     根据user_id获取所有的campaign_persona_recom记录
     */
    private List<CampaignPersonaRecomRecord>  getCampaignPersonaRecomRecordByUserIdAndCompanyId(int userId,int companyId,int type){
        Query query=new Query.QueryBuilder().where("user_id",userId).and("company_id",companyId).and("type",type).buildQuery();
        List<CampaignPersonaRecomRecord> list=campaignPersonaRecomDao.getRecords(query);
        return list;
    }
    /*
     将数据插入到历史记录中
     */
    private int  insertHistoryCampaignPersonaRecomRecord(List<HistoryCampaignPersonaRecomRecord> list){
        if(StringUtils.isEmptyList(list)){
            return 1;
        }
        List<HistoryCampaignPersonaRecomRecord> result=historyCampaignPersonaRecomDao.addAllRecord(list);
        if(!StringUtils.isEmptyList(result)){
            return 1;
        }
        return 0;
    }
    /*
     将CampaignPersonaRecomPojo转化为HistoryCampaignPersonaRecomPojo
     */
    private List<HistoryCampaignPersonaRecomRecord> convertToHistoryCampaignPersonaRecomPojo(List<CampaignPersonaRecomRecord> list){
        if(StringUtils.isEmptyList(list)){

            return null;
        }
        List<HistoryCampaignPersonaRecomRecord> result=new ArrayList<>();
        for(CampaignPersonaRecomRecord pojo:list){
            HistoryCampaignPersonaRecomRecord historyCampaignPersonaRecomPojo=new HistoryCampaignPersonaRecomRecord();
            historyCampaignPersonaRecomPojo.setId(pojo.getId());
            historyCampaignPersonaRecomPojo.setCreateTime(pojo.getCreateTime());
            historyCampaignPersonaRecomPojo.setIsSend(pojo.getIsSend());
            historyCampaignPersonaRecomPojo.setPositionId(pojo.getPositionId());
            historyCampaignPersonaRecomPojo.setSendTime(pojo.getSendTime());
            historyCampaignPersonaRecomPojo.setUpdateTime(pojo.getUpdateTime());
            historyCampaignPersonaRecomPojo.setUserId(pojo.getUserId());
            historyCampaignPersonaRecomPojo.setCompanyId(pojo.getCompanyId());
            historyCampaignPersonaRecomPojo.setType(pojo.getType());
            result.add(historyCampaignPersonaRecomPojo);
        }
        return result;
    }

}
