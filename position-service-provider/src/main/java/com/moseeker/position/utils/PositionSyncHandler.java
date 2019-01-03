package com.moseeker.position.utils;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.BindThirdPart;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.position.constants.ResultMessage;
import com.moseeker.position.pojo.PositionSyncResultPojo;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PositionSyncHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;
    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;
    @Autowired
    private HRThirdPartyAccountHrDao hrThirdPartyAccountHrDao;
    @Resource(name = "cacheClient")
    protected RedisClient redisClient;



    //创建失败结果
    public PositionSyncResultPojo createFailResult(int positionId,String data,String reason,int channel){
        PositionSyncResultPojo result=new PositionSyncResultPojo();
        result.setPosition_id(positionId);
        result.setData(data);
        result.setSync_fail_reason(reason);
        result.setSync_status(PositionSyncResultPojo.FAIL);
        result.setChannel(channel);
        result.setSync_time(DateUtils.dateToShortTime(new Date()));
        return result;
    }
    //创建普通结果
    public PositionSyncResultPojo createNormalResult(int positionId,int channel,String data){
        PositionSyncResultPojo result = new PositionSyncResultPojo();
        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");

        result.setPosition_id(positionId);
        result.setData(data);
        result.setSync_status(PositionSyncResultPojo.SUCCESS);
        result.setSync_time(syncTime);
        result.setChannel(channel);

        return result;
    }


    public List<HrThirdPartyPositionDO> getAlreadySyncThirdPositions(int positionId){
        Query query=new Query.QueryBuilder()
                .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(),positionId)
                .and(new Condition(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(), Arrays.asList(PositionSync.bound.getValue(),PositionSync.binding.getValue(),PositionSync.bindingError.getValue()), ValueOp.IN)).buildQuery();

        return thirdPartyPositionDao.getSimpleDatas(query);
    }

    public boolean containsAlreadySyncThirdPosition(int thirdPartyAccountId, int positionId, List<HrThirdPartyPositionDO> list){
        for(HrThirdPartyPositionDO position:list){
            if(position.getThirdPartyAccountId()==thirdPartyAccountId && position.getPositionId()==positionId){
                return true;
            }
        }
        return false;
    }

    //获取发布者在对应渠道下的第三方账号
    public List<HrThirdPartyAccountDO> getThirdPartAccount(int publisher){
        List<HrThirdPartyAccountDO> acounts=getValidThirdPartAccounts(Arrays.asList(publisher)).get(publisher);
        if(acounts==null){
            return new ArrayList<>();
        }
        return acounts;
    }
    //获取可用并且remainNum>0的第三方账号
    public boolean containsThirdAccount(List<HrThirdPartyAccountDO> accounts,int channel){
        return getThirdAccount(accounts,channel) != null;
    }
    //获取可用并且remainNum>0的第三方账号
    public HrThirdPartyAccountDO getThirdAccount(List<HrThirdPartyAccountDO> accounts,int channel){
        if(!StringUtils.isEmptyList(accounts)){
            for(HrThirdPartyAccountDO account:accounts){
                if(account.channel==channel){
                    return account;
                }
            }
        }
        return null;
    }

    public Map<Integer,List<HrThirdPartyAccountDO>> getValidThirdPartAccounts(List<Integer> publisher){
        List<HrThirdPartyAccountHrDO> relations=hrThirdPartyAccountHrDao.getHrAccounts(publisher);

        List<Integer> accountIds=relations.stream().map(r->r.getThirdPartyAccountId()).collect(Collectors.toList());

        List<HrThirdPartyAccountDO> accounts=hrThirdPartyAccountDao.getAccountsById(accountIds);

        Map<Integer,List<HrThirdPartyAccountDO>> hrToThirdAccountMap=new HashMap<>();

        for(HrThirdPartyAccountHrDO relation:relations){
            if(!hrToThirdAccountMap.containsKey(relation.getHrAccountId())){
                hrToThirdAccountMap.put(relation.getHrAccountId(),new ArrayList<>());
            }
            for(HrThirdPartyAccountDO account:accounts) {
                if(account.getId()==relation.getThirdPartyAccountId()) {
                    hrToThirdAccountMap.get(relation.getHrAccountId()).add(account);
                }
            }
        }

        return hrToThirdAccountMap;
    }


    //根据职位id获取MoSeeker的职位
    public JobPositionDO getMoSeekerPosition(int positionId){
        Query qu = new Query.QueryBuilder()
                .where(JobPosition.JOB_POSITION.ID.getName(), positionId)
                .buildQuery();
        JobPositionDO moseekerPosition = jobPositionDao.getData(qu);
        logger.info("position:" + JSON.toJSONString(moseekerPosition));
        return moseekerPosition;
    }
    //检查ID对应的职位是否存在或可用
    public JobPositionDO getAvailableMoSeekerPosition(int positionId) throws BIZException {
        JobPositionDO moseekerPosition=getMoSeekerPosition(positionId);
        requireAvailablePostiion(moseekerPosition);
        return moseekerPosition;
    }


    public List<JobPositionDO> getMoSeekerPositions(List<Integer> positionIds){
        Query qu = new Query.QueryBuilder()
                .where(new Condition(JobPosition.JOB_POSITION.ID.getName(), positionIds,ValueOp.IN))
                .buildQuery();
        List<JobPositionDO> moseekerPosition = jobPositionDao.getDatas(qu);
        logger.info("positions:" + JSON.toJSONString(moseekerPosition));
        if(moseekerPosition==null){
            return new ArrayList<>();
        }
        return moseekerPosition;
    }

    public boolean requireAvailablePostiion(JobPositionDO moseekerPosition) throws BIZException {
        if (moseekerPosition == null || moseekerPosition.getId() == 0 || moseekerPosition.getStatus() != 0) {
            throw new BIZException(ResultMessage.POSITION_NOT_EXIST.getStatus(),ResultMessage.POSITION_NOT_EXIST.getMessage());
        }
        return true;
    }

    public boolean alreadyInRedis(int positionId) throws BIZException {
        long check= redisClient.incrIfNotExist(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.SYNC_THIRD_PARTY_POSITION.toString(), positionId+"");
        if (check>1) {
            //绑定中
            return true;
        }
        redisClient.expire(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.SYNC_THIRD_PARTY_POSITION.toString(), positionId+"" , 300);
        return false;
    }

    public void removeRedis(int positionId) throws BIZException {
        String cache = redisClient.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.SYNC_THIRD_PARTY_POSITION.toString(), positionId+"");
        if (cache != null) {
            redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.SYNC_THIRD_PARTY_POSITION.toString(), positionId+"");
        }
    }
}
