package com.moseeker.position.utils;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.position.constants.ResultMessage;
import com.moseeker.position.pojo.PositionSyncResultPojo;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PositionSyncHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;
    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;



    //创建失败结果
    public PositionSyncResultPojo createFailResult(int channel,int thirdPartyAccountId,String reason){
        PositionSyncResultPojo result=new PositionSyncResultPojo();
        result.setChannel(channel);
        result.setAccount_id(thirdPartyAccountId);
        result.setSync_fail_reason(reason);
        result.setSync_status(PositionSyncResultPojo.FAIL);
        return result;
    }
    //创建普通结果
    public PositionSyncResultPojo createNormalResult(int channel, int thirdPartyAccountId){
        PositionSyncResultPojo result = new PositionSyncResultPojo();
        String syncTime = (new DateTime()).toString("yyyy-MM-dd HH:mm:ss");

        result.setChannel(channel);
        result.setSync_status(PositionSyncResultPojo.SUCCESS);
        result.setSync_time(syncTime);
        result.setAccount_id(thirdPartyAccountId);

        return result;
    }


    public List<HrThirdPartyPositionDO> getAlreadySyncThirdPositions(int positionId){
        Query query=new Query.QueryBuilder()
                .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(),positionId)
                .and(new Condition(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(), Arrays.asList(PositionSync.binding.getValue(),PositionSync.bindingError.getValue()), ValueOp.IN)).buildQuery();

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
    public HrThirdPartyAccountDO getThirdPartAccount(int publisher,int channel){
        return hrThirdPartyAccountDao.getThirdPartyAccountByUserId(publisher,channel);
    }
    //获取可用并且remainNum>0的第三方账号
    public HrThirdPartyAccountDO getAvailableThirdAccount(int publisher,int channel){
        HrThirdPartyAccountDO account=getThirdPartAccount(publisher,channel);
        if(account!=null && account.getId()>0){
            logger.info("发布者：{}获取到渠道：{}第三方账号",publisher,channel,account);
            return account;
        }
        return null;
    }

    //根据职位id获取MoSeeker的职位
    public JobPositionDO getMoSeekerPosition(int positionId){
        Query qu = new Query.QueryBuilder().where("id", positionId).buildQuery();
        JobPositionDO moseekerPosition = jobPositionDao.getData(qu);
        logger.info("position:" + JSON.toJSONString(moseekerPosition));
        return moseekerPosition;
    }
    //检查ID对应的职位是否存在或可用
    public JobPositionDO getAvailableMoSeekerPosition(int positionId) throws BIZException {
        JobPositionDO moseekerPosition=getMoSeekerPosition(positionId);
        if (moseekerPosition == null || moseekerPosition.getId() == 0 || moseekerPosition.getStatus() != 0) {
            throw new BIZException(ResultMessage.POSITION_NOT_EXIST.getStatus(),ResultMessage.POSITION_NOT_EXIST.getMessage());
        }
        return moseekerPosition;
    }
}
