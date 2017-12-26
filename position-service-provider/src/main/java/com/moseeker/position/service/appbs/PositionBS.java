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
import com.moseeker.position.utils.PositionEmailNotification;
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

import java.util.*;
import java.util.stream.Collectors;

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
    private HRThirdPartyPositionDao thirdPartyPositionDao;
    @Autowired
    private PositionSyncHandler positionSyncHandler;
    @Autowired
    private PositionChangeUtil positionChangeUtil;
    @Autowired
    private TransferCheckUtil transferCheckUtil;
    @Autowired
    private PositionEmailNotification emailNotification;


    /**
     * 单一处理职位同步
     * @param positionForm
     * @return
     * @throws Exception
     */
    @CounterIface
    public List<PositionSyncResultPojo> syncPositionToThirdParty(ThirdPartyPositionForm positionForm) throws Exception {
        return syncPositionToThirdParty(Arrays.asList(positionForm));
    }

    /**
     * 批量处理职位同步
     * @param positionForms
     * @return
     * @throws Exception
     */
    @CounterIface
    public List<PositionSyncResultPojo> syncPositionToThirdParty(List<ThirdPartyPositionForm> positionForms) throws Exception {
        if(StringUtils.isEmptyList(positionForms)){
            return new ArrayList<>();
        }

        //批量获取职位，转换成Map<id,JobPosition>方便查询
        List<Integer> positionIds=positionForms.stream().map(p->p.getPositionId()).collect(Collectors.toList());
        List<JobPositionDO> moseekerJobPositions=positionSyncHandler.getMoSeekerPositions(positionIds);
        Map<Integer,JobPositionDO> moseekerJobPositionMap=moseekerJobPositions.stream().collect(Collectors.toMap(p->p.getId(),p->p));
        if(moseekerJobPositionMap==null || moseekerJobPositionMap.isEmpty()){
            return new ArrayList<>();
        }

        //批量获取第三方账号，转换成Map<hrAccountId,List<HrThirdPartyAccountDO>>方便查询
        List<Integer> publishers=moseekerJobPositions.stream().map(p->p.getPublisher()).collect(Collectors.toList());
        Map<Integer,List<HrThirdPartyAccountDO>> thirdAccountOfHr=positionSyncHandler.getValidThirdPartAccounts(publishers);
        if(thirdAccountOfHr == null || thirdAccountOfHr.isEmpty()){
            return new ArrayList<>();
        }

        List<PositionSyncResultPojo> results=new ArrayList<>();

        for(ThirdPartyPositionForm positionForm:positionForms){
            if(positionForm.getPositionId()==0 || StringUtils.isEmptyList(positionForm.getChannels())){
                continue;
            }

            JobPositionDO moseekerJobPosition=moseekerJobPositionMap.get(positionForm.getPositionId());
            List<HrThirdPartyAccountDO> accounts=thirdAccountOfHr.get(moseekerJobPosition.getPublisher());
            positionSyncHandler.requireAvailablePostiion(moseekerJobPosition);

            try {
                results.addAll(syncPositionToThirdParty(positionForm,moseekerJobPosition,accounts));
            }catch (Exception e){
                logger.error("batch Sync Position error exception:{},positionForm:{}",e,JSON.toJSONString(positionForm));
                emailNotification.sendSyncFailureMail(positionForm, null, e);
                results.add(positionSyncHandler.createFailResult(moseekerJobPosition.getId(),JSON.toJSONString(positionForm),"batch Sync Position error"));
                continue;
            }

        }

        return results;
    }


    /**
     * @param positionForm
     * @param moseekerJobPosition
     * @return
     */
    private List<PositionSyncResultPojo> syncPositionToThirdParty(ThirdPartyPositionForm positionForm,JobPositionDO moseekerJobPosition,List<HrThirdPartyAccountDO> accounts) throws Exception {
        logger.info("syncPositionToThirdParty:" + JSON.toJSONString(positionForm));
        // 职位数据是否存在
        positionSyncHandler.requireAvailablePostiion(moseekerJobPosition);

        // 返回结果
        List<PositionSyncResultPojo> results = new ArrayList<>();

        //第三方职位列表，用来回写写到第三方职位表
        List<TwoParam<HrThirdPartyPositionDO,Object>> writeBackThirdPartyPositionList = new ArrayList<>();

        //已经同步的数据
        List<HrThirdPartyPositionDO> alreadySyncPosition=positionSyncHandler.getAlreadySyncThirdPositions(moseekerJobPosition.getId());

        //用来同步到chaos的职位列表
        List<String>  positionsForSynchronizations=new ArrayList<>();

        //记录已经同步的渠道
        Set<ChannelType> channelTypeSet=new HashSet<>();

        SyncRequestType requestType=SyncRequestType.getInstance(positionForm.getRequestType());

        //这个循环检查需要同步的职位对应渠道下是否有绑定过的账号
        for (String json: positionForm.getChannels()) {
            JSONObject p=JSON.parseObject(json);
            int channel=p.getIntValue("channel");
            //验证渠道是否存在
            ChannelType channelType=ChannelType.instaceFromInteger(channel);
            if(channelType==null){
                results.add(positionSyncHandler.createFailResult(moseekerJobPosition.getId(),json,ResultMessage.CHANNEL_NOT_EXIST.getMessage()));
                continue;
            }
            //验证是否已经有相同渠道职位准备绑定
            if(channelTypeSet.contains(channelType)){
                results.add(positionSyncHandler.createFailResult(moseekerJobPosition.getId(),json,ResultMessage.AREADY_PREPARE_BIND.getMessage()));
                continue;
            }

            //验证并获取对应渠道账号
            if (!positionSyncHandler.containsThirdAccount(accounts,channel)) {
                results.add(positionSyncHandler.createFailResult(moseekerJobPosition.getId(),json,ResultMessage.THIRD_PARTY_ACCOUNT_NOT_EXIST.getMessage()));
                continue;
            }
            HrThirdPartyAccountDO avaliableAccount = positionSyncHandler.getThirdAccount(accounts,channel);
            p.put("thirdPartyAccountId",avaliableAccount.getId());

            //验证是否有正在绑定的第三方职位
            if(positionSyncHandler.containsAlreadySyncThirdPosition(avaliableAccount.getId(),moseekerJobPosition.getId(),alreadySyncPosition)){
                results.add(positionSyncHandler.createFailResult(moseekerJobPosition.getId(),json,ResultMessage.AREADY_BINDING.getMessage()));
                continue;
            }

            //验证同步数据中的参数
            List<String> checkMsg=transferCheckUtil.checkBeforeTransfer(requestType,channelType,p);
            if(!StringUtils.isEmptyList(checkMsg)){
                results.add(positionSyncHandler.createFailResult(moseekerJobPosition.getId(),json,JSON.toJSONString(checkMsg)));
                continue;
            }

            // 转成第三方渠道职位
            AbstractPositionTransfer.TransferResult result= positionChangeUtil.changeToThirdPartyPosition(p, moseekerJobPosition,avaliableAccount);

            positionsForSynchronizations.add(JSON.toJSONString(result.getPositionWithAccount()));
            writeBackThirdPartyPositionList.add(new TwoParam(result.getThirdPartyPositionDO(),result.getExtPosition()));

            results.add(positionSyncHandler.createNormalResult(json));

            //完成转换操作，可以绑定
            channelTypeSet.add(channelType);
        }

        // 提交到chaos处理
        logger.info("chaosService.synchronizePosition:{}", positionsForSynchronizations);
        chaosService.synchronizePosition(positionsForSynchronizations);

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
