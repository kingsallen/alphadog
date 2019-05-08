package com.moseeker.position.service.appbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionLiepinMappingDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.*;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.UrlUtil;
import com.moseeker.position.constants.ResultMessage;
import com.moseeker.position.pojo.PositionSyncResultPojo;
import com.moseeker.position.service.position.PositionChangeUtil;
import com.moseeker.position.service.position.base.PositionFactory;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.base.sync.PositionSyncVerifyHandlerUtil;
import com.moseeker.position.service.position.base.sync.verify.MobileVeifyHandler;
import com.moseeker.position.service.position.base.sync.verify.PositionSyncVerifyHandler;
import com.moseeker.position.service.position.base.sync.TransferPreHandleUtil;
import com.moseeker.position.utils.PositionEmailNotification;
import com.moseeker.position.utils.PositionSyncHandler;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.positionbs.struct.ScraperHtmlParam;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import com.moseeker.thrift.gen.position.struct.Position;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 职位业务层
 *
 * @author wjf
 */
@Service
public class PositionBS {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);

    //scrapper获取超时时间
    private static int timeOut = 3*60*1000;

    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;
    @Autowired
    private PositionSyncHandler positionSyncHandler;
    @Autowired
    private PositionChangeUtil positionChangeUtil;
    @Autowired
    private TransferPreHandleUtil transferPreHandleUtil;
    @Autowired
    private PositionEmailNotification emailNotification;
    @Autowired
    private PositionFactory positionFactory;
    @Autowired
    private PositionSyncVerifyHandlerUtil verifyHandlerUtil;
    @Autowired
    private MobileVeifyHandler mobileVeifyHandler;
    @Autowired
    private HRThirdPartyAccountDao thirdPartyAccountDao;
    @Autowired
    private JobPositionLiepinMappingDao mappingDao;

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
        logger.info("PositionBS syncPositionToThirdParty");
        if(StringUtils.isEmptyList(positionForms)){
            return new ArrayList<>();
        }

        //批量获取职位，转换成Map<id,JobPosition>方便查询
        List<Integer> positionIds=positionForms.stream().map(p->p.getPositionId()).collect(Collectors.toList());
        List<JobPositionDO> moseekerJobPositions=positionSyncHandler.getMoSeekerPositions(positionIds);
        Map<Integer,JobPositionDO> moseekerJobPositionMap=moseekerJobPositions.stream().collect(Collectors.toMap(p->p.getId(),p->p));
        if(moseekerJobPositionMap==null || moseekerJobPositionMap.isEmpty()){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.NO_SYNC_QX_POSITION);
        }

        //批量获取第三方账号，转换成Map<hrAccountId,List<HrThirdPartyAccountDO>>方便查询
        List<Integer> publishers=moseekerJobPositions.stream().map(p->p.getPublisher()).collect(Collectors.toList());
        Map<Integer,List<HrThirdPartyAccountDO>> thirdAccountOfHr=positionSyncHandler.getValidThirdPartAccounts(publishers);
        if(thirdAccountOfHr == null || thirdAccountOfHr.isEmpty()){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.NO_SYNC_THIRD_PARTY_ACCOUNT);
        }

        List<PositionSyncResultPojo> results=new ArrayList<>();

        logger.info("PositionBS syncPositionToThirdParty positionForms:{}", JSONObject.toJSONString(positionForms));
        for(ThirdPartyPositionForm positionForm:positionForms){
            if(positionForm.getPositionId()==0 || StringUtils.isEmptyList(positionForm.getChannels())){
                continue;
            }

            JobPositionDO moseekerJobPosition=moseekerJobPositionMap.get(positionForm.getPositionId());
            List<HrThirdPartyAccountDO> accounts=thirdAccountOfHr.get(moseekerJobPosition.getPublisher());
            positionSyncHandler.requireAvailablePostiion(moseekerJobPosition);

            try {
                results.addAll(syncPositionToThirdParty(positionForm,moseekerJobPosition,accounts));
            } catch(BIZException e){
                logger.error("batch Sync Position error bizexception:{},positionForm:{}",e,JSON.toJSONString(positionForm));
                results.add(positionSyncHandler.createFailResult(moseekerJobPosition.getId(),JSON.toJSONString(positionForm),e.getMessage(),-1));
                continue;
            } catch(Exception e){
                logger.error("batch Sync Position error exception:{},positionForm:{}",e,JSON.toJSONString(positionForm));
                emailNotification.sendSyncFailureMail(positionForm, null, e);
                results.add(positionSyncHandler.createFailResult(moseekerJobPosition.getId(),JSON.toJSONString(positionForm),"batch Sync Position error",-1));
                continue;
            }

        }
        logger.info("PositionBS syncPositionToThirdParty results:{}", JSONObject.toJSONString(results));
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

        if(positionSyncHandler.alreadyInRedis(moseekerJobPosition.getId())){
            throw new BIZException(ResultMessage.AREADY_SYNCING_IN_REDIS.getStatus(),ResultMessage.AREADY_SYNCING_IN_REDIS.getMessage());
        }

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
                results.add(positionSyncHandler.createFailResult(moseekerJobPosition.getId(),json,ResultMessage.CHANNEL_NOT_EXIST.getMessage(),channel));
                continue;
            }
            //验证是否已经有相同渠道职位准备绑定
            if(channelTypeSet.contains(channelType)){
                results.add(positionSyncHandler.createFailResult(moseekerJobPosition.getId(),json,ResultMessage.AREADY_PREPARE_BIND.getMessage(),channel));
                continue;
            }

            //验证并获取对应渠道账号
            if (!positionSyncHandler.containsThirdAccount(accounts,channel)) {
                results.add(positionSyncHandler.createFailResult(moseekerJobPosition.getId(),json,ResultMessage.THIRD_PARTY_ACCOUNT_NOT_EXIST.getMessage(),channel));
                continue;
            }
            HrThirdPartyAccountDO avaliableAccount = positionSyncHandler.getThirdAccount(accounts,channel);
            p.put("thirdPartyAccountId",avaliableAccount.getId());

            //验证是否有正在绑定的第三方职位
            if(positionSyncHandler.containsAlreadySyncThirdPosition(avaliableAccount.getId(),moseekerJobPosition.getId(),alreadySyncPosition)){
                results.add(positionSyncHandler.createFailResult(moseekerJobPosition.getId(),json,ResultMessage.AREADY_SYNCING_IN_DATABASE.getMessage(),channel));
                continue;
            }

            //对同步职位参数进行预处理
            transferPreHandleUtil.handleBeforeTransfer(requestType,channelType,p,moseekerJobPosition);

            //验证同步数据中的参数
            List<String> checkMsg= transferPreHandleUtil.checkBeforeTransfer(requestType,channelType,p,moseekerJobPosition);
            if(!StringUtils.isEmptyList(checkMsg)){
                results.add(positionSyncHandler.createFailResult(moseekerJobPosition.getId(),json,JSON.toJSONString(checkMsg),channel));
                continue;
            }

            // 转成第三方渠道职位
            AbstractPositionTransfer.TransferResult result= positionChangeUtil.changeToThirdPartyPosition(p, moseekerJobPosition,avaliableAccount);

            positionsForSynchronizations.addAll(positionChangeUtil.toChaosJson(channel,result.getPositionWithAccount()));

            writeBackThirdPartyPositionList.add(new TwoParam(result.getThirdPartyPositionDO(),result.getExtPosition()));

            results.add(positionSyncHandler.createNormalResult(moseekerJobPosition.getId(),channel,json));

            //完成转换操作，可以绑定
            channelTypeSet.add(channelType);

            positionChangeUtil.sendRequest(channel,result,moseekerJobPosition);
        }

        positionSyncHandler.removeRedis(moseekerJobPosition.getId());
        return results;
    }

    /**
     * 获取缓存验证信息
     * @param param
     * @return
     * @throws BIZException
     * @throws TException
     */
    public Response getVerifyParam(String param) throws BIZException, TException {
        Response response=mobileVeifyHandler.getVerifyParam(param);
        return response;
    }

    /**
     * 发送验证完成信息
     * @param jsonParam
     * @return
     * @throws BIZException
     */
    public Response syncVerifyInfo(String jsonParam) throws BIZException {
        JSONObject jsonObject=JSON.parseObject(jsonParam);
        int channel=jsonObject.getIntValue("channel");

        ChannelType channelType=ChannelType.instaceFromInteger(channel);

        PositionSyncVerifyHandler verifyHandler=positionFactory.getVerifyHandlerInstance(channelType);

        verifyHandler.handler(jsonParam);

        return ResultMessage.SUCCESS.toResponse("");
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

    /**
     * 调用scraper获取html
     * @param param
     * @return
     * @throws BIZException
     * @throws TException
     */
    public String getThirdPartyHtml(ScraperHtmlParam param)  throws BIZException {
      {
        logger.info("get html from scraper param:{}",param);

        int channel = param.getChannel();

        if(!ChannelType.containsChannelType(channel)){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.WRONG_SYNC_CHANNEL);
        }

        int positionId = param.getPositionId();

        JobPositionRecord positionRecord = jobPositionDao.getPositionById(positionId);

        if(positionRecord==null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_DATA_DELETE_FAIL);
        }

        int publisher = positionRecord.getPublisher();


        HrThirdPartyAccountDO thirdPartyAccount = thirdPartyAccountDao.getThirdPartyAccountByUserId(publisher,param.getChannel());

        if(thirdPartyAccount == null){
          throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_NOT_EXIST);
        }

        JSONObject jsonObject=new JSONObject();

        jsonObject.put("detail_url",param.getUrl());
        jsonObject.put("username",thirdPartyAccount.getUsername());
        jsonObject.put("password",thirdPartyAccount.getPassword());

        String scrapperUrl = getUrlByChannel(param.getChannel());

        if(StringUtils.isNullOrEmpty(scrapperUrl)){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.REQUEST_SCRAPER_URL_NOT_EXIST);
        }

        try {
            String html= UrlUtil.sendPost(scrapperUrl,jsonObject.toJSONString(),timeOut,timeOut);

            logger.info("get html from scraper success. html length:{}",html.length());

            JSONObject result=JSON.parseObject(html);

            String status=result.getString("status");
            if("1".equals(status)){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.CRAWLER_USER_NOPERMITION);
            }else if(!"0".equals(status)){
                logger.error("get html from scraper return status error :{}",status);
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.CRAWLER_SERVICE_TIMEOUT);
            }

            return result.getJSONArray("resumes").getString(0);
        } catch (ConnectException e){
            logger.error("get html from scraper connection error:{}",e);
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.CRAWLER_SERVICE_TIMEOUT);
        }

      }
    }

    private String getUrlByChannel(int channel) throws BIZException {
        ChannelType channelType=ChannelType.instaceFromInteger(channel);
        String url="";
        switch (channelType){
            case JOBSDB:
                url = getSettingProperty("CRAWLER_JOBSDB_B");
                break;
            default:
                break;
        }
        return url;
    }

    /**
     *
     * @param key
     * @return
     * @throws BIZException
     */
    private String getSettingProperty(String key) throws BIZException {
        ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
        try {
            propertiesUtils.loadResource("setting.properties");
            return propertiesUtils.get(key, String.class);
        } catch (Exception e) {
            logger.error("get html from scraper load properties error:{}",e);
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.LOAD_CONFIG_ERROR);
        }
    }

    private void writeBackToQX(int positionId) {
        JobPositionDO positionDO = new JobPositionDO();
        positionDO.setId(positionId);
        positionDO.setUpdateTime((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
        jobPositionDao.updateData(positionDO);
    }

    public List<JobPositionLiepinMappingDO> getLiepinPositionIds(int userId) {
        return mappingDao.getMappingDataByUserId(userId);
    }
}
