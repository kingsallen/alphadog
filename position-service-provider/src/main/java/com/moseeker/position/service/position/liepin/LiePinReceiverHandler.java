package com.moseeker.position.service.position.liepin;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionLiepinMappingDao;
import com.moseeker.baseorm.dao.logdb.LogDeadLetterDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.common.constants.PositionSyncVerify;
import com.moseeker.common.util.DateUtils;
import com.moseeker.position.pojo.LiePinPositionVO;
import com.moseeker.position.service.appbs.PositionBS;
import com.moseeker.position.utils.HttpClientUtil;
import com.moseeker.position.utils.Md5Utils;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
import com.moseeker.thrift.gen.dao.struct.logdb.LogDeadLetterDO;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 监听das中的职位操作，进行与猎聘相关的职位处理
 *
 * @author cjm
 * @date 2018-06-04 19:03
 **/
@Component
@PropertySource("classpath:common.properties")
public class LiePinReceiverHandler {

    private static Logger log = LoggerFactory.getLogger(LiePinReceiverHandler.class);

    @Autowired
    private HRThirdPartyPositionDao hrThirdPartyPositionDao;

    @Autowired
    private JobPositionDao jobPositionDao;

    @Autowired
    private LogDeadLetterDao logDeadLetterDao;

    @Autowired
    private HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    private LiepinSocialPositionTransfer liepinSocialPositionTransfer;

    @Autowired
    private JobPositionLiepinMappingDao liepinMappingDao;

    @Autowired
    private PositionBS positionBS;

    @Autowired
    private JobPositionCityDao jobPositionCityDao;

    private static final String LP_POSITION_EDIT = "https://apidev1.liepin.com/e/job/updateEJob.json";
    private static final String LP_USER_STOP_JOB = "https://apidev1.liepin.com/e/job/endEJob.json";
    private static final String LP_USER_REPUB_JOB = "https://apidev1.liepin.com/e/job/rePublishEjob.json";
    private static final String LP_USER_GETJOB = "https://apidev1.liepin.com/e/job/getEJobByUser.json";


    /**
     * 批量处理编辑职位操作
     * @param
     * @author  cjm
     * @date  2018/6/11
     * @return
     */
    public boolean batchHandleLiepinEditOperation(List<Integer> ids) throws UnsupportedEncodingException {
        int index = 0;
        JSONObject liePinJsonObject = null;
        for(Integer positionId : ids){
            liePinJsonObject = new JSONObject();
            liePinJsonObject.put("id", positionId);
            String requestStr = JSONObject.toJSONString(liePinJsonObject);
            Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
            handlerPositionLiepinEditOperation(requestMsg, null);
        }
        return index == ids.size();
    }



    @RabbitListener(queues = PositionSyncVerify.POSITION_QUEUE_EDIT, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handlerPositionLiepinEditOperation(Message message, Channel channel) {
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");

            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            log.info("职位操作的rabitmq的参数是========" + jsonObject);

            int positionChannel = 2;

            int positionId = jsonObject.getInteger("id");

            // 获取das端已修改后的职位数据
            JobPositionDO jobPositionDO = jobPositionDao.getJobPositionById(positionId);

            // 获取在仟寻填写的猎聘职位信息
            HrThirdPartyPositionDO hrThirdPartyPositionDO = hrThirdPartyPositionDao.getThirdPartyPositionById(positionId, positionChannel);

            // 获取token
            int publisher = jobPositionDO.getPublisher();
            log.info("===========publisher:{}===========", publisher);
            Result result = thirdPartyAccountDao.getThirdPartyAccountTokenByHrId(publisher, positionChannel);
            if (result == null || result.size() < 1) {
                return;
            }
            String liePinToken = String.valueOf(result.getValue(0, HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.EXT2));

            ThirdPartyPosition thirdPartyPosition = new ThirdPartyPosition();

            // 组装同步时需要的数据，相当于在第三方页面填写的表单数据，将不匹配字段手动映射
            BeanUtils.copyProperties(hrThirdPartyPositionDO, thirdPartyPosition);
            thirdPartyPosition.setSalaryDiscuss(hrThirdPartyPositionDO.getSalaryDiscuss() != 0);
            String occupations = hrThirdPartyPositionDO.getOccupation();
            String[] occupationsArr = occupations.split(",");
            thirdPartyPosition.setOccupation(Arrays.asList(occupationsArr));

            // 将数据组装为向猎聘请求的格式，此数据也是用户编辑后的数据
            LiePinPositionVO liePinPositionVO = liepinSocialPositionTransfer.changeToThirdPartyPosition(thirdPartyPosition, jobPositionDO, null);

            // 用于查询所修改的职位之前是否发布过
            List<JobPositionLiepinMappingDO> liepinMappingDOList = liepinMappingDao.getMappingDataByPid(positionId);

            // 判断编辑的城市中是否有未发布过的城市，如果有，就走一遍发布流程，职位发布中会判断如果是发布过的职位，不会重新发布
            boolean flag = true;

            // 编辑职位中的城市list
            List<JobPositionCityDO> positionCityList = jobPositionCityDao.getPositionCitysByPid(positionId);

            // 编辑职位中的城市codelist
            List<String> cityCodesList = positionCityList.stream().map(positionCityDO -> String.valueOf(positionCityDO.getCode())).collect(Collectors.toList());

            // 如果数据库不存在编辑的职位，则发布新职位
            if (null != liepinMappingDOList && liepinMappingDOList.size() > 0) {

                // 数据库中该仟寻职位id对应的城市codeslist
                List<String> cityDbList = liepinMappingDOList.stream().map(mappingDo -> String.valueOf(mappingDo.getCityCode())).collect(Collectors.toList());
                // 数据库中该仟寻职位id对应的titlelist
                List<String> titleDbList = liepinMappingDOList.stream().map(mappingDo -> mappingDo.getJobTitle()).collect(Collectors.toList());

                // 先判断title是否存在，不存在的话发布新职位
                if (titleDbList.contains(jobPositionDO.getTitle())) {

                    for (String cityCode : cityCodesList) {

                        for (JobPositionLiepinMappingDO mappingDO : liepinMappingDOList) {

                            // 猎聘修改职位api必填字段
                            liePinPositionVO.setEjob_extRefid(String.valueOf(mappingDO.getId()));
                            liePinPositionVO.setEjob_id(mappingDO.getLiepinJobId());
                            liePinPositionVO.setEjob_dq(mappingDO.getCityCode() + "");

                            // 存在城市，并且状态正常
                            if (cityCode.equals(String.valueOf(mappingDO.getCityCode())) && mappingDO.getState() == 1) {

                                // 修改
                                editSinglePosition(liePinPositionVO, liePinToken, mappingDO);
                                break;

                            } else if (cityCode.equals(String.valueOf(mappingDO.getCityCode())) && mappingDO.getState() == 0) {

                                // 存在城市，但是状态为下架，先上架，后修改
                                upShelfOldSinglePosition(mappingDO, liePinToken);

                                // 修改
                                editSinglePosition(liePinPositionVO, liePinToken, mappingDO);
                                break;

                            } else if (!cityCodesList.contains(String.valueOf(mappingDO.getCityCode())) && mappingDO.getState() == 1) {

                                // 如果编辑的城市中没有数据库中的该城市，并且该城市之前出于上架状态，则将其下架
                                downShelfOldSinglePosition(mappingDO, liePinToken);

                            } else if (!cityDbList.isEmpty() && !cityDbList.contains(cityCode)) {
                                // 如果该职位数据库的发布城市中没有编辑职位中的第i个城市，判定为新城市，需要发布
                                flag = false;
                                break;
                            }
                        }

                    }
                } else {
                    //  title变化，将之前所有的下架获取所有的jobMappingIds，新的发布
                    downShelfOldPositions(liepinMappingDOList, liePinToken);

                    // 发布新title对应的职位
                    sendSyncPosition(positionId, thirdPartyPosition);
                }

            } else {
                // 不存在映射记录，发布职位
                sendSyncPosition(positionId, thirdPartyPosition);
            }

            if (!flag) {
                // 说明编辑职位中存在没有发布的城市，需要新发布职位
                sendSyncPosition(positionId, thirdPartyPosition);
            }

        } catch (Exception e) {
            this.handleTemplateLogDeadLetter(message, msgBody, e.getMessage());
            log.error(e.getMessage(), e);
        }
    }
    /**
     * 批量处理职位下架
     * @param
     * @author  cjm
     * @date  2018/6/11
     * @return
     */
    public boolean batchHandlerLiepinDownShelfOperation(List<Integer> ids) throws UnsupportedEncodingException {
        int index = 0;
        JSONObject liePinJsonObject = null;
        for(Integer id : ids){
            liePinJsonObject = new JSONObject();
            liePinJsonObject.put("id", id);
            String requestStr = JSONObject.toJSONString(liePinJsonObject);
            Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
            handlerPositionLiepinDownShelfOperation(requestMsg, null);
            index ++;
        }
        return index == ids.size();
    }

    @RabbitListener(queues = PositionSyncVerify.POSITION_QUEUE_DELETE, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handlerPositionLiepinDeleteOperation(Message message, Channel channel) {
        handlerPositionLiepinDownShelfOperation(message, channel);
    }

    @RabbitListener(queues = PositionSyncVerify.POSITION_QUEUE_DOWNSHELF, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handlerPositionLiepinDownShelfOperation(Message message, Channel channel) {

        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            log.info("职位操作的rabitmq的参数是========" + jsonObject.toJSONString());

            Integer positionId = jsonObject.getInteger("id");

            int positionChannel = 2;
            JobPositionDO jobPositionDO = jobPositionDao.getJobPositionById(positionId);
            if (jobPositionDO == null) {
                throw new Exception("职位不存在");
            }
            int publisher = jobPositionDO.getPublisher();
            log.info("===========publisher:{}===========", publisher);
            Result result = thirdPartyAccountDao.getThirdPartyAccountTokenByHrId(publisher, positionChannel);

            if (result != null && result.size() > 0) {
                String liePinToken = String.valueOf(result.getValue(0, HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.EXT2));
                // 构造请求数据
                byte state = 1;
                List<JobPositionLiepinMappingDO> liepinMappingDOList = liepinMappingDao.getMappingDataByPidAndState(positionId, state);
                if (null != liepinMappingDOList && liepinMappingDOList.size() > 0) {
                    List<Integer> idsList = liepinMappingDOList.stream().map(liepinMappingDO -> liepinMappingDO.getId()).collect(Collectors.toList());
                    String ids = idsList.toString();
                    if (StringUtils.isNotBlank(ids)) {
                        JSONObject liePinJsonObject = new JSONObject();
                        liePinJsonObject.put("ejob_extRefids", ids.substring(1, ids.length() - 1));
                        String httpResultJson = sendRequest2LiePin(liePinJsonObject, liePinToken, LP_USER_STOP_JOB);
                        log.info("===================httpResultJson:{}=====================", httpResultJson);
                        try {
                            requireValidResult(httpResultJson);
                            state = 0;
                            liepinMappingDao.updateState(idsList, state);
                            // 修改状态
                        } catch (Exception e) {
                            liepinMappingDao.updateErrMsgBatch(positionId, e.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            this.handleTemplateLogDeadLetter(message, msgBody, e.getMessage());
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取猎聘职位信息      todo 不确定是否用得到
     * @param   positionId 职位id
     * @param   id liepinmapping中的主键id
     * @author  cjm
     * @date  2018/6/10
     * @return
     */
    public String getLpPositionInfo(Integer positionId, Integer id) throws Exception {
        int positionChannel = 2;
        JobPositionDO jobPositionDO = jobPositionDao.getJobPositionById(positionId);
        if (jobPositionDO == null) {
            throw new Exception("职位不存在");
        }
        int publisher = jobPositionDO.getPublisher();
        log.info("===========publisher:{}===========", publisher);
        Result result = thirdPartyAccountDao.getThirdPartyAccountTokenByHrId(publisher, positionChannel);
        if (result != null && result.size() > 0) {
            String liePinToken = String.valueOf(result.getValue(0, HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.EXT2));
            String liePinUserId = String.valueOf(result.getValue(0, HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.EXT));
            JSONObject liePinJsonObject = new JSONObject();
            liePinJsonObject.put("ejob_extRefids", id);
            liePinJsonObject.put("usere_id", liePinUserId);
            return sendRequest2LiePin(liePinJsonObject, liePinToken, LP_USER_GETJOB);
        }
        return null;
    }

    @RabbitListener(queues = PositionSyncVerify.POSITION_QUEUE_RESYNC, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handlerPositionLiepinReSyncOperation(Message message, Channel channel) {
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            log.info("handlerPositionLiepinReSyncOperation的rabitmq的参数是========" + jsonObject.toJSONString());

            int positionId = jsonObject.getInteger("id");
            int positionChannel = 2;
            JobPositionDO jobPositionDO = jobPositionDao.getJobPositionById(positionId);
            if (jobPositionDO == null) {
                throw new Exception("职位不存在");
            }
            int publisher = jobPositionDO.getPublisher();
            log.info("===========publisher:{}===========", publisher);
            Result result = thirdPartyAccountDao.getThirdPartyAccountTokenByHrId(publisher, positionChannel);
            if (result != null && result.size() > 0) {
                String liePinToken = String.valueOf(result.getValue(0, HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.EXT2));

                // 通过职位id获取职位所在城市list
                List<JobPositionCityDO> jobPositionCityList = jobPositionCityDao.getPositionCitysByPid(positionId);

                // 获取职位所在城市codeList
                List<Integer> rePublishCityCodes = jobPositionCityList.stream().map(jobPositionCityDO -> jobPositionCityDO.getCode()).collect(Collectors.toList());

                // 通过职位id和code获取JobPositionLiepinMapping表的信息，此表存的是仟寻请求猎聘生成的职位主键id等信息的映射
                List<JobPositionLiepinMappingDO> liepinMappingDOList = liepinMappingDao.getMappingDataByPidAndCode(positionId, rePublishCityCodes);

                // 过滤重新发布城市中的state已经为1的城市
                liepinMappingDOList = filterUnneedRepublishCitys(liepinMappingDOList);

                // 将需要重新发布的城市的主键id取出，用于向猎聘请求
                List<Integer> idsList = liepinMappingDOList.stream().map(liepinMappingDO -> liepinMappingDO.getId()).collect(Collectors.toList());

                String ids = idsList.toString();
                if (liepinMappingDOList.size() > 0) {
                    if (StringUtils.isNotBlank(ids)) {
                        JSONObject liePinJsonObject = new JSONObject();
                        liePinJsonObject.put("ejob_extRefids", ids.substring(1, ids.length() - 1));
                        String httpResultJson = sendRequest2LiePin(liePinJsonObject, liePinToken, LP_USER_REPUB_JOB);
                        log.info("===================httpResultJson:{}=====================", httpResultJson);
                        try {
                            requireValidResult(httpResultJson);
                            liepinMappingDao.updateState(idsList, (byte) 1);
                        } catch (Exception e) {
                            liepinMappingDao.updateErrMsgBatch(positionId, e.getMessage());
                        }
                    }//
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            this.handleTemplateLogDeadLetter(message, msgBody, "职位操作通过api同步到猎聘失败");
        }

    }

    private List<JobPositionLiepinMappingDO> filterUnneedRepublishCitys(List<JobPositionLiepinMappingDO> liepinMappingDOList) {
        List<JobPositionLiepinMappingDO> list = new ArrayList<>();
        for(JobPositionLiepinMappingDO mappingDO : liepinMappingDOList){
            if(mappingDO.getState() == 1){
                continue;
            }
            list.add(mappingDO);
        }
        return list;
    }

    /**
     * 编辑单个职位信息，并没有编辑多个职位的接口
     * @param  liePinToken 猎聘生成的hr账号token
     * @author  cjm
     * @date  2018/6/10
     * @return
     */
    private void editSinglePosition(LiePinPositionVO liePinPositionVO, String liePinToken, JobPositionLiepinMappingDO mappingDO) {
        JSONObject liePinObject = (JSONObject) JSONObject.toJSON(liePinPositionVO);
        String httpResultJson = sendRequest2LiePin(liePinObject, liePinToken, LP_POSITION_EDIT);
        try {
            requireValidResult(httpResultJson);
        } catch (Exception e) {
            liepinMappingDao.updateErrMsg(mappingDO.getId(), e.getMessage());
        }
    }

    /**
     * 上架单个职位，并没有上架多个职位的接口
     * @param  mappingDO 数据库liepingmapping表中针对当前pid的单个职位
     * @author  cjm
     * @date  2018/6/10
     * @return
     */
    private void upShelfOldSinglePosition(JobPositionLiepinMappingDO mappingDO, String liepinToken) {
        JSONObject liePinJsonObject = new JSONObject();
        liePinJsonObject.put("ejob_extRefids", mappingDO.getId());
        try {
            String httpResultJson = sendRequest2LiePin(liePinJsonObject, liepinToken, LP_USER_REPUB_JOB);
            requireValidResult(httpResultJson);
            List<Integer> ids = new ArrayList<>();
            ids.add(mappingDO.getId());
            liepinMappingDao.updateState(ids, (byte)1);
        } catch (Exception e) {
            liepinMappingDao.updateErrMsg(mappingDO.getId(), e.getMessage());
        }
    }

    /**
     * 下架单个职位
     * @param
     * @author  cjm
     * @date  2018/6/11
     * @return
     */
    private void downShelfOldSinglePosition(JobPositionLiepinMappingDO jobPositionMapping, String liepinToken) {
        List<JobPositionLiepinMappingDO> list = new ArrayList<>();
        list.add(jobPositionMapping);
        downShelfOldPositions(list, liepinToken);
    }

    /**
     * 下架多个职位
     * @param
     * @author  cjm
     * @date  2018/6/11
     * @return
     */
    public void downShelfOldPositions(List<JobPositionLiepinMappingDO> liepinMappingDOList, String liepinToken) {
        List<Integer> downShelfPositonList = new ArrayList<>();
        for (JobPositionLiepinMappingDO liepinMappingDO : liepinMappingDOList) {
            int state = liepinMappingDO.getState();
            if (state == 1) {
                downShelfPositonList.add(liepinMappingDO.getId());
            }
        }
        if (downShelfPositonList.size() > 0) {
            // 将所有状态为1的职位下架
            JSONObject liePinJsonObject = new JSONObject();
            String ids = downShelfPositonList.toString();
            liePinJsonObject.put("ejob_extRefids", ids.substring(1, ids.length() - 1));

            try {
                log.info("==================title变化，将之前所有的下架获取所有的hashid，新的发布=====================");
                // 下架
                String httpResultJson = sendRequest2LiePin(liePinJsonObject, liepinToken, LP_USER_STOP_JOB);
                requireValidResult(httpResultJson);
                liepinMappingDao.updateState(downShelfPositonList, (byte)0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject requireValidResult(String httpResultJson) {
        if (StringUtils.isBlank(httpResultJson)) {
            throw new RuntimeException("猎聘请求结果为空");
        }
        JSONObject httpResult = JSONObject.parseObject(httpResultJson);
        if (null == httpResult) {
            throw new RuntimeException("向猎聘发送请求失败");
        } else if (httpResult.getIntValue("code") != 0) {
            log.info("==============httpResult:{}================", httpResult);
            throw new RuntimeException("猎聘职位请求失败," + httpResult.getString("message"));
        }
        return httpResult;
    }

    public String sendRequest2LiePin(JSONObject liePinJsonObject, String liePinToken, String url) {
        String t = DateUtils.dateToPattern(new Date(), "yyyyMMdd");
        liePinJsonObject.put("t", t);
        String sign = Md5Utils.getMD5SortKey(Md5Utils.mapToList(liePinJsonObject), liePinJsonObject);
        liePinJsonObject.put("sign", sign);

        //设置请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("channel", "qianxun");
        headers.put("token", liePinToken);
        String httpResultJson = null;
        try {
            httpResultJson = HttpClientUtil.sentHttpPostRequest(url, headers, liePinJsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("============职位同步时http请求异常============");
        }
        return httpResultJson;
    }

    private void sendSyncPosition(int positionId, ThirdPartyPosition thirdPartyPosition) throws Exception {
        ThirdPartyPositionForm positionForm = new ThirdPartyPositionForm();
        positionForm.setAppid(0);
        positionForm.setPositionId(positionId);
        positionForm.setRequestType(1);
        List<String> list = new ArrayList<>();
        list.add(JSONObject.toJSONString(thirdPartyPosition));
        positionForm.setChannels(list);
        hrThirdPartyPositionDao.updateBindState(positionId, 2);
        positionBS.syncPositionToThirdParty(positionForm);
    }

    private void handleTemplateLogDeadLetter(Message message, String msgBody, String errorMessage) {
        LogDeadLetterDO logDeadLetterDO = new LogDeadLetterDO();
        logDeadLetterDO.setAppid(NumberUtils.toInt(message.getMessageProperties().getAppId(), 0));
        logDeadLetterDO.setErrorLog(errorMessage);
        logDeadLetterDO.setMsg(msgBody);
        logDeadLetterDO.setExchangeName(StringUtils.defaultIfBlank(message.getMessageProperties().getReceivedExchange(), ""));
        logDeadLetterDO.setRoutingKey(StringUtils.defaultIfBlank(message.getMessageProperties().getReceivedRoutingKey(), ""));
        logDeadLetterDO.setQueueName(StringUtils.defaultIfBlank(message.getMessageProperties().getConsumerQueue(), ""));
        logDeadLetterDao.addData(logDeadLetterDO);
    }


}
