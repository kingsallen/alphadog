package com.moseeker.position.service.appbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionLiepinMappingDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionRefreshType;
import com.moseeker.common.constants.SyncRequestType;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.UrlUtil;
import com.moseeker.position.constants.ResultMessage;
import com.moseeker.position.pojo.PositionSyncResultPojo;
import com.moseeker.position.service.position.PositionChangeUtil;
import com.moseeker.position.service.position.base.PositionFactory;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.base.sync.PositionSyncVerifyHandlerUtil;
import com.moseeker.position.service.position.base.sync.TransferPreHandleUtil;
import com.moseeker.position.service.position.base.sync.verify.MobileVeifyHandler;
import com.moseeker.position.service.position.base.sync.verify.PositionSyncVerifyHandler;
import com.moseeker.position.utils.HttpClientUtil;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
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


    private static String jobboardUrl;

    private ThreadPool threadPool = ThreadPool.Instance;

    static {
        ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
        try {
            configUtils.loadResource("setting.properties");
            jobboardUrl = configUtils.get("alphacloud.jobboard.postition.sync.url", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        if (StringUtils.isEmptyList(positionForms)) {
            return new ArrayList<>();
        }

        List<PositionSyncResultPojo> results = Collections.synchronizedList(new ArrayList<>());
        try{

            JSONArray array = new JSONArray();
            positionForms.forEach(positionForm->{
                JSONObject param = JSON.parseObject(JSON.toJSONString(positionForm));
                JSONArray jsonChannels = new JSONArray();
                positionForm.getChannels().forEach(channel -> {
                    jsonChannels.add(JSON.parseObject(channel));
                });
                param.put("channels", jsonChannels);
                array.add(param);
            });

            String result = HttpClientUtil.sentHttpPostRequest(jobboardUrl + "?appid=A11017&interfaceid=A11017001", null, array.toJSONString());

            if (StringUtils.isNotNullOrEmpty(result)) {
                if (!result.startsWith("{")) {
                    throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "unknow sync error:" + result);
                } else {
                    JSONObject resultObject = JSON.parseObject(result);
                    if (resultObject.getIntValue("code") != 0) {
                        throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "sync error:" + resultObject.getString("message"));
                    } else {
                        results.addAll(JSON.parseArray(resultObject.getString("data"), PositionSyncResultPojo.class));
                    }
                }
            } else {
                throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "unknow sync error");
            }

        } catch(BIZException e){
            results.add(positionSyncHandler.createFailResult(-1, JSON.toJSONString(positionForms), e.getMessage(), -1));
        } catch(Exception e){
            emailNotification.sendSyncFailureMail(positionForms, null, e);
            results.add(positionSyncHandler.createFailResult(-1, JSON.toJSONString(positionForms), "batch Sync Position error", -1));
        }

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
