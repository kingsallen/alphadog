package com.moseeker.position.service.appbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionRefreshType;
import com.moseeker.common.constants.SyncRequestType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.constants.ResultMessage;
import com.moseeker.position.pojo.PositionSyncResultPojo;
import com.moseeker.position.service.position.PositionChangeUtil;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.base.sync.TransferCheckUtil;
import com.moseeker.position.utils.PositionSyncHandler;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import com.moseeker.thrift.gen.position.struct.Position;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 职位业务层
 *
 * @author wjf
 */
@Service
@Transactional
public class PositionBS {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);

    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;
    @Autowired
    private PositionSyncHandler positionSyncHandler;
    @Autowired
    private PositionChangeUtil positionChangeUtil;
    @Autowired
    private TransferCheckUtil transferCheckUtil;

    /**
     * @param position
     * @return
     */
    @CounterIface
    public List<PositionSyncResultPojo> synchronizePositionToThirdPartyPlatform(ThirdPartyPositionForm position) throws Exception {
        logger.info("synchronizePositionToThirdPartyPlatform:" + JSON.toJSONString(position));
        // 职位数据是否存在
        JobPositionDO moseekerJobPosition = positionSyncHandler.getAvailableMoSeekerPosition(position.getPositionId());

        // 返回结果
        List<PositionSyncResultPojo> results = new ArrayList<>();

        //第三方职位列表，用来回写写到第三方职位表
        List<TwoParam<HrThirdPartyPositionDO,Object>> writeBackThirdPartyPositionList = new ArrayList<>();

        List<HrThirdPartyPositionDO> alreadySyncPosition=positionSyncHandler.getAlreadySyncThirdPositions(moseekerJobPosition.getId());

        //用来同步到chaos的职位列表
        List<String>  positionsForSynchronizations=new ArrayList<>();

        SyncRequestType requestType=SyncRequestType.getInstance(position.getRequestType());

        //这个循环检查需要同步的职位对应渠道下是否有绑定过的账号
        for (String json: position.getChannels()) {
            JSONObject p=JSON.parseObject(json);
            int channel=p.getIntValue("channel");
            int thirdPartyAccountId=p.getIntValue("thirdPartyAccountId");

            HrThirdPartyAccountDO avaliableAccount = positionSyncHandler.getAvailableThirdAccount(moseekerJobPosition.getPublisher(),p.getIntValue("channel"));
            if (avaliableAccount == null) {
                results.add(positionSyncHandler.createFailResult(channel,thirdPartyAccountId,ResultMessage.THIRD_PARTY_ACCOUNT_NOT_EXIST.getMessage()));
                continue;
            } else {
                p.put("thirdPartyAccountId",avaliableAccount.getId());
            }

            if(positionSyncHandler.containsAlreadySyncThirdPosition(avaliableAccount.getId(),moseekerJobPosition.getId(),alreadySyncPosition)){
                results.add(positionSyncHandler.createFailResult(channel,thirdPartyAccountId,ResultMessage.AREADY_BINDING.getMessage()));
                continue;
            }

            ChannelType channelType=ChannelType.instaceFromInteger(channel);
            if(channelType==null){
                results.add(positionSyncHandler.createFailResult(channel,thirdPartyAccountId,ResultMessage.CHANNEL_NOT_EXIST.getMessage()));
                continue;
            }

            List<String> checkMsg=transferCheckUtil.checkBeforeTransfer(requestType,channelType,p);
            if(!StringUtils.isEmptyList(checkMsg)){
                results.add(positionSyncHandler.createFailResult(channel,thirdPartyAccountId,JSON.toJSONString(checkMsg)));
                continue;
            }

            // 转成第三方渠道职位
            AbstractPositionTransfer.TransferResult result= positionChangeUtil.changeToThirdPartyPosition(p, moseekerJobPosition,avaliableAccount);

            positionsForSynchronizations.add(JSON.toJSONString(result.getPositionWithAccount()));
            writeBackThirdPartyPositionList.add(new TwoParam(result.getThirdPartyPositionDO(),result.getExtPosition()));

            results.add(positionSyncHandler.createNormalResult(channel,avaliableAccount.getId()));
        }

        // 提交到chaos处理
        logger.info("chaosService.synchronizePosition:{}", positionsForSynchronizations);
//        chaosService.synchronizePosition(positionsForSynchronizations);

        // 回写数据到第三方职位表表
        logger.info("write back to thirdpartyposition:{}",writeBackThirdPartyPositionList);
        thirdPartyPositionDao.upsertThirdPartyPositions(writeBackThirdPartyPositionList);

        return results;
    }


    /**
     * 刷新职位
     *
     * @param positionId 职位编号
     * @param channel    渠道编号
     * @return
     * @throws TException
     */
    @CounterIface
    public Response refreshPosition(int positionId, int channel) throws TException {
        logger.info("refreshPosition start");
        HashMap<String, Object> result = new HashMap<>();
        result.put("position_id", positionId);
        result.put("channel", channel);
        result.put("is_refresh", PositionRefreshType.notRefresh.getValue());
        //更新仟寻职位的修改时间
        writeBackToQX(positionId);

        result.put("is_refresh", PositionRefreshType.refreshing.getValue());
        return ResultMessage.SUCCESS.toResponse(result);
    }

    @CounterIface
    public Response refreshPositionQX(List<Integer> list) throws TException {
        List<Position> positionList = new ArrayList<Position>();
        for (int i = 0; i < list.size(); i++) {
            Position position = new Position();
            position.setId(list.get(i));
            position.setUpdate_time((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
            positionList.add(position);
        }
        jobPositionDao.updatePositionList(positionList);
        return ResultMessage.SUCCESS.toResponse(null);
    }

    private void writeBackToQX(int positionId) {
        JobPositionDO positionDO = new JobPositionDO();
        positionDO.setId(positionId);
        positionDO.setUpdateTime((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
        jobPositionDao.updateData(positionDO);
    }

}
