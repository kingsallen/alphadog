package com.moseeker.position.service.position.liepin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionLiepinMappingDao;
import com.moseeker.baseorm.dao.logdb.LogDeadLetterDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSyncVerify;
import com.moseeker.common.email.Email;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.position.constants.position.LiepinPositionOperateUrl;
import com.moseeker.position.pojo.LiePinPositionVO;
import com.moseeker.position.service.appbs.PositionBS;
import com.moseeker.position.utils.EmailSendUtil;
import com.moseeker.position.utils.HttpClientUtil;
import com.moseeker.position.utils.Md5Utils;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityLiePinDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    private HRThirdPartyAccountHrDao hrThirdPartyDao;

    @Autowired
    private LiepinSocialPositionTransfer liepinSocialPositionTransfer;

    @Autowired
    private JobPositionLiepinMappingDao liepinMappingDao;

    @Autowired
    private PositionBS positionBS;

    @Autowired
    private JobPositionCityDao jobPositionCityDao;

    private String emailSubject = "猎聘api请求操作失败";

    /**
     * 批量处理编辑职位操作
     * 用于ats批量处理
     * todo
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/6/11
     */
    public boolean batchHandleLiepinEditOperation(List<JobPositionRecord> list, Map<Integer, JobPositionRecord> oldJobMap) throws UnsupportedEncodingException {

        List<Integer> remainIds = list.stream().map(jobPositionRecord -> jobPositionRecord.getId()).collect(Collectors.toList());

        try {
            JSONObject liePinJsonObject = new JSONObject();
            JobPositionDO jobPositionDO;
            JobPositionDO oldJobPositionDO;
            for (JobPositionRecord record : list) {

                if(record.getCandidateSource() == 1){
                    log.info("=========批量处理，当前职位为校招职位，无需猎聘api同步==========");
                    continue;
                }
                jobPositionDO = BeanUtils.DBToStruct(JobPositionDO.class, record);

                JobPositionRecord oldRecord = oldJobMap.get(jobPositionDO.getId());

                oldJobPositionDO = BeanUtils.DBToStruct(JobPositionDO.class, oldRecord);

                liePinJsonObject.put("id", jobPositionDO.getId());
                liePinJsonObject.put("params", JSONObject.toJSONString(jobPositionDO));
                liePinJsonObject.put("oldPosition", JSONObject.toJSONString(oldJobPositionDO));

                String requestStr = JSONObject.toJSONString(liePinJsonObject);

                Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);

                handlerPositionLiepinEditOperation(requestMsg, null);

                remainIds.remove(record.getId());
            }

            return true;
        } catch (Exception e) {
            log.error("调用api批量修改职位信息时发生错误，职位ids:{}", remainIds);
            EmailSendUtil.sendWarnEmail("调用api批量修改职位信息时发生错误，剩余职位ids" + remainIds.toString(),
                    emailSubject);
            e.printStackTrace();
        }

        return false;
    }


    @RabbitListener(queues = PositionSyncVerify.POSITION_QUEUE_EDIT, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handlerPositionLiepinEditOperation(Message message, Channel channel) {
        String msgBody = "{}";
        Integer id = null;
        try {

            msgBody = new String(message.getBody(), "UTF-8");

            log.info("===============msgBody:{}===============", msgBody);

            id = requireValidEditId(msgBody);

            if (id == null) {
                return;
            }

            JobPositionDO updateJobPosition = getUpdateJobPositionFromMq(msgBody);

            // 获取das端已修改后的职位数据
            JobPositionDO jobPositionDO = getOldJobPositionFromMq(msgBody);

            boolean positionFlag = getPositionFlag(msgBody);

            if (updateJobPosition == null || jobPositionDO == null) {
                return;
            }

            if(jobPositionDO.getCandidateSource() == 1){
                log.info("===========校招职位不用处理===========");
                return;
            }

            int positionChannel = 2;

            int positionId = id;

            String liePinToken = getLiepinToken(positionId);

            if (StringUtils.isBlank(liePinToken)) {
                log.info("============token为空=============");
                return;
            }

            log.info("==============liePinToken:{}===============", liePinToken);

            // 是否需要编辑，返回true，两个职位相同，不需要edit
            boolean noNeedEdit = compareJobPosition(jobPositionDO, updateJobPosition);

            // city改变标志 true是city发生改变，false是未发生改变
            boolean cityChangeFlag = !jobPositionDO.getCity().equals(updateJobPosition.getCity());

            // 如果positionFlag是true，说明das操作修改时是下架状态，所以将数据库同步状态设置为1
            if(!cityChangeFlag && positionFlag){
                hrThirdPartyPositionDao.updateBindState(positionId, 2, 1);
            }

//            // 如果修改了city，将同步状态修改为未同步
//            if(cityChangeFlag){
//                hrThirdPartyPositionDao.updateBindState(positionId, 2, 0);
//            }

            if (!positionFlag && noNeedEdit) {
                log.info("=============没有修改猎聘所需字段，无需发布修改============");
                return;
            }

            // 获取在仟寻填写的猎聘职位信息
            HrThirdPartyPositionDO hrThirdPartyPositionDO = hrThirdPartyPositionDao.getThirdPartyPositionById(positionId, positionChannel);

            if (hrThirdPartyPositionDO == null) {
                log.info("==============第三方职位信息为空，未填写，positionId:{}=============", positionId);
                return;
            }

            ThirdPartyPosition thirdPartyPosition = new ThirdPartyPosition();

            // 组装同步时需要的数据，相当于在第三方页面填写的表单数据，将不匹配字段手动映射
            org.springframework.beans.BeanUtils.copyProperties(hrThirdPartyPositionDO, thirdPartyPosition);
            thirdPartyPosition.setSalaryDiscuss(hrThirdPartyPositionDO.getSalaryDiscuss() != 0);
            thirdPartyPosition.setSalaryBottom(thirdPartyPosition.getSalaryBottom() / 1000);
            thirdPartyPosition.setSalaryTop(thirdPartyPosition.getSalaryTop() / 1000);
            String occupations = hrThirdPartyPositionDO.getOccupation();
            String[] occupationsArr = occupations.split(",");
            thirdPartyPosition.setOccupation(Arrays.asList(occupationsArr));

            // 将数据组装为向猎聘请求的格式，此数据也是用户编辑后的数据
            LiePinPositionVO liePinPositionVO = liepinSocialPositionTransfer.changeToThirdPartyPosition(thirdPartyPosition, updateJobPosition, null);
            log.info("================liePinPositionVO:{}=============", liePinPositionVO);
            // 用于查询所修改的职位之前是否发布过
            List<JobPositionLiepinMappingDO> liepinMappingDOList = liepinMappingDao.getMappingDataByPid(positionId);

            if (null == liepinMappingDOList || liepinMappingDOList.size() < 1) {
                log.info("=================该职位未在猎聘发布过，编辑时无需到猎聘修改===============");
                return;
            }

            // 判断编辑的城市中是否有未发布过的城市，如果有，就走一遍发布流程，职位发布中会判断如果是发布过的职位，不会重新发布
//            boolean flag = true;

            // 编辑职位中的城市list
            List<JobPositionCityDO> positionCityList = jobPositionCityDao.getPositionCityBypid(positionId);
            log.info("==============编辑城市positionCityList:{}============", positionCityList);
            // 编辑职位中的城市codelist
            List<String> cityCodesList = positionCityList.stream().map(positionCityDO -> String.valueOf(positionCityDO.getCode())).collect(Collectors.toList());
            log.info("==============编辑城市cityCodesList:{}============", cityCodesList);
            // 如果数据库不存在编辑的职位，则发布新职位

            // 数据库中该仟寻职位id对应的城市codes list
            List<String> cityDbList = liepinMappingDOList.stream().map(mappingDo -> String.valueOf(mappingDo.getCityCode())).collect(Collectors.toList());
            log.info("===============数据库中该仟寻职位id对应的城市cityDbList:{}====================", cityDbList);

            // 数据库中该仟寻职位id对应的titlelist
            List<String> titleDbList = liepinMappingDOList.stream().map(mappingDo -> mappingDo.getJobTitle()).collect(Collectors.toList());

            // 将titlelist去重复
            titleDbList = removeDuplicateTitle(titleDbList);
            log.info("===============数据库中该仟寻职位id对应的titlelist:{}====================", titleDbList);

            String title = updateJobPosition.getTitle();

            // 先判断title是否存在，不存在的话发布新职位
            if (titleDbList.contains(title)) {
                log.info("=================title存在=====================");
                for (String cityCode : cityCodesList) {

                    for (JobPositionLiepinMappingDO mappingDO : liepinMappingDOList) {
                        log.info("==============当前citycode:{},当前数据库mapping citycode:{}=================", cityCode, mappingDO.getCityCode());
                        // 存在城市，并且状态正常
                        if (cityCode.equals(String.valueOf(mappingDO.getCityCode())) && mappingDO.getState() == 1 && title.equals(mappingDO.getJobTitle())) {

                            if(!cityChangeFlag){
                                log.info("===============存在城市，并且状态正常，修改================");
                                // 修改
                                editSinglePosition(liePinPositionVO, liePinToken, mappingDO);
                            }
                            break;

                        } else if (cityCode.equals(String.valueOf(mappingDO.getCityCode())) && mappingDO.getState() == 0 && title.equals(mappingDO.getJobTitle())) {

                            // 存在城市，但是状态为下架，先上架，后修改
                            if(!cityChangeFlag){
                                log.info("============存在城市，但是状态为下架，先上架，后修改============");
                                upShelfOldSinglePosition(mappingDO, liePinToken);

                                // 修改
                                editSinglePosition(liePinPositionVO, liePinToken, mappingDO);
                            }

                            break;

                        } else if (cityCodesList.contains(String.valueOf(mappingDO.getCityCode())) && mappingDO.getState() == 1 && !title.equals(mappingDO.getJobTitle())) {
                            log.info("============如果编辑的城市中存在数据库中的该城市，但是title不相同，并且该城市之前出于上架状态，则将其下架============");
                            // 如果编辑的城市中存在数据库中的该城市，但是title不相同，并且该城市之前出于上架状态，则将其下架
                            downShelfOldSinglePosition(mappingDO, liePinToken);

                        } else if (!cityCodesList.contains(String.valueOf(mappingDO.getCityCode())) && mappingDO.getState() == 1) {
                            log.info("============如果编辑的城市中没有数据库中的该城市，并且该城市之前出于上架状态，则将其下架============");
                            // 如果编辑的城市中没有数据库中的该城市，并且该城市之前出于上架状态，则将其下架
                            downShelfOldSinglePosition(mappingDO, liePinToken);

                        }

//                        if (!cityDbList.isEmpty() && !cityDbList.contains(cityCode) && title.equals(mappingDO.getJobTitle())) {
//                            // 如果该职位数据库的发布城市中没有编辑职位中的第i个城市，判定为新城市，需要发布
//                            log.info("================如果该职位数据库的发布城市中没有编辑职位中的当前城市，判定为新城市，需要发布================");
//                            flag = false;
//                        }
                    }

                }
            } else {
                //  title变化，将之前所有的下架获取所有的jobMappingIds，新的发布
                log.info("============title变化，将之前所有的下架获取所有的jobMappingIds，新的发布===============");
                downShelfOldPositions(liepinMappingDOList, liePinToken);

                // 发布新title对应的职位 todo 不发送同步请求
//                log.info("===========发布新title对应的职位===========");
//                sendSyncPosition(positionId, thirdPartyPosition);
            }


//            else {
//                // 不存在映射记录，发布职位
//                log.info("==========不存在映射记录，发布职位==========");
//                sendSyncPosition(positionId, thirdPartyPosition);
//            }

//            if (!flag) {
//                // 说明编辑职位中存在没有发布的城市，需要新发布职位
//                log.info("==============说明编辑职位中存在没有发布的城市，需要新发布职位============");
//                sendSyncPosition(positionId, thirdPartyPosition);
//            }


        } catch (Exception e) {
            EmailSendUtil.sendWarnEmail("调用api批量修改职位信息时发生错误，职位信息如下:</br>" + msgBody,
                    emailSubject);
            log.error(e.getMessage(), e);
        }
    }

    private boolean getPositionFlag(String msgBody) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            return jsonObject.getBoolean("positionFlag");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private JobPositionDO getOldJobPositionFromMq(String msgBody) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            JSONObject jobPositionJSON = JSONObject.parseObject(jsonObject.getString("oldPosition"));
            JobPositionDO jobPositionDO = convertJSON2DO(jobPositionJSON);
            log.info("============jobPositionDO:{}=============", jobPositionDO);
            return jobPositionDO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private JobPositionDO getUpdateJobPositionFromMq(String msgBody) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            JSONObject jobPositionJSON = JSONObject.parseObject(jsonObject.getString("params"));
            JobPositionDO jobPositionDO = convertJSON2DO(jobPositionJSON);
            log.info("============jobPositionDO:{}=============", jobPositionDO);
            return jobPositionDO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 批量处理职位下架
     * ats批量下架
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/6/11
     */
    public boolean batchHandlerLiepinDownShelfOperation(List<Integer> ids) throws UnsupportedEncodingException {
        try {
            if(ids == null || ids.size() < 1){
                log.info("==============没有需要处理的猎聘api下架职位==============");
                return false;
            }
            JSONObject liePinJsonObject = new JSONObject();

            liePinJsonObject.put("id", ids);

            String requestStr = JSONObject.toJSONString(liePinJsonObject);

            Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);

            handlerPositionLiepinDownShelfOperation(requestMsg, null);
            return true;
        } catch (Exception e) {
            log.error("==============猎聘api批量处理职位下架失败, ids:{}==============", ids);
            EmailSendUtil.sendWarnEmail("猎聘api批量处理职位下架失败" + ids.toString(), emailSubject);
            e.printStackTrace();
        }
        return false;

    }

    @RabbitListener(queues = PositionSyncVerify.POSITION_QUEUE_RESYNC, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handlerPositionLiepinReSyncOperation(Message message, Channel channel) {
        String msgBody = "{}";
        List<Integer> ids = null;
        try {
            msgBody = new String(message.getBody(), "UTF-8");

            ids = requireValidMessage(msgBody);

            if (ids == null || ids.size() == 0) {
                return;
            }



            for (int id : ids) {

                int positionId = id;

                // 获取hr账号在猎聘token
                String liepinToken = getLiepinToken(positionId);

                List<Integer> idsList = new ArrayList<>();
                try {

                    JobPositionDO jobPositionDO = jobPositionDao.getJobPositionByPid(id);

                    if(jobPositionDO == null || jobPositionDO.getCandidateSource() == 1){
                        continue;
                    }

                    // 通过职位id获取职位所在城市list
                    List<JobPositionCityDO> jobPositionCityList = jobPositionCityDao.getPositionCitysByPid(id);

                    // 获取职位所在城市codeList
                    List<Integer> rePublishCityCodes = jobPositionCityList.stream().map(jobPositionCityDO -> jobPositionCityDO.getCode()).collect(Collectors.toList());

                    // 通过职位id和code获取JobPositionLiepinMapping表的信息，此表存的是仟寻请求猎聘生成的职位主键id等信息的映射
                    List<JobPositionLiepinMappingDO> liepinMappingDOList = liepinMappingDao.getMappingDataByPidAndCode(id, rePublishCityCodes);

                    if (liepinMappingDOList == null || liepinMappingDOList.size() < 1) {
                        log.info("===============未在猎聘发布过，不需要重新发布================");
                        continue;
                    }

                    // 过滤重新发布城市中的state已经为1的城市
                    liepinMappingDOList = filterUnneedRepublishCitys(liepinMappingDOList);

                    if (liepinMappingDOList.size() > 0) {

                        // 将需要重新发布的城市的主键id取出，用于向猎聘请求
                        idsList = liepinMappingDOList.stream().map(liepinMappingDO -> liepinMappingDO.getId()).collect(Collectors.toList());

                        Set<Integer> pidSet = liepinMappingDOList.stream().map(liepinMappingDO -> liepinMappingDO.getJobId()).collect(Collectors.toSet());

                        List<Integer> pids = new ArrayList<>(pidSet);

                        List<String> requestIdsStr = liepinMappingDOList.stream().map(liepinMappingDO -> String.valueOf(liepinMappingDO.getId())).collect(Collectors.toList());

                        List<Integer> idsListDb = liepinMappingDOList.stream().map(liepinMappingDO -> liepinMappingDO.getId()).collect(Collectors.toList());

                        String requestIds = String.join(",", requestIdsStr);

                        if (StringUtils.isNotBlank(requestIds)) {

                            JSONObject liePinJsonObject = new JSONObject();

                            liePinJsonObject.put("ejob_extRefids", requestIds);

                            String httpResultJson = sendRequest2LiePin(liePinJsonObject, liepinToken, LiepinPositionOperateUrl.liepinPositionRepub);

                            log.info("===================httpResultJson:{}=====================", httpResultJson);

                            requireValidResult(httpResultJson);

                            liepinMappingDao.updateState(idsListDb, (byte) 1);

                            hrThirdPartyPositionDao.updateBindState(pids, 2, 1);
                        }
                    }
                } catch (BIZException e) {
                    liepinMappingDao.updateErrMsgBatch(positionId, e.getMessage());
                } catch (Exception e1) {
                    EmailSendUtil.sendWarnEmail("下架猎聘职位失败：" + idsList.toString(), emailSubject);
                    liepinMappingDao.updateErrMsgBatch(idsList, e1.getMessage());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            EmailSendUtil.sendWarnEmail("调用api批量修改职位信息时发生错误，职位ids" + (ids == null ? msgBody : ids.toString()),
                    emailSubject);
        }

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
        List<Integer> ids = null;
        try {
            msgBody = new String(message.getBody(), "UTF-8");

            ids = requireValidMessage(msgBody);

            if (ids == null || ids.size() == 0) {
                return;
            }

            List<Integer> requestIds = new ArrayList<>();

            for (int id : ids) {

                try {

                    int positionId = id;

                    // 获取hr账号在猎聘token
                    String liepinToken = getLiepinToken(positionId);

                    JobPositionDO jobPositionDO = jobPositionDao.getJobPositionByPid(id);

                    if(jobPositionDO == null || jobPositionDO.getCandidateSource() == 1){
                        continue;
                    }

                    List<JobPositionLiepinMappingDO> liepinMappingDOS = liepinMappingDao.getValidMappingDataByPid(id, (byte) 1);

                    if (liepinMappingDOS == null || liepinMappingDOS.size() < 1) {
                        log.info("==============该职位没有在猎聘发布过，不需要下架==============");
                        continue;
                    }

                    requestIds = liepinMappingDOS.stream().map(liepinMappingDO -> liepinMappingDO.getId()).collect(Collectors.toList());

                    List<String> requestIdsStr = liepinMappingDOS.stream().map(liepinMappingDO -> String.valueOf(liepinMappingDO.getId())).collect(Collectors.toList());

                    if (requestIds.size() < 1) {
                        log.info("==============没有需要下架的猎聘职位==============");
                        continue;
                    }

                    // 构造请求数据
                    JSONObject liePinJsonObject = new JSONObject();

                    String requestStr = String.join(",", requestIdsStr);

                    liePinJsonObject.put("ejob_extRefids", requestStr);

                    String httpResultJson = sendRequest2LiePin(liePinJsonObject, liepinToken, LiepinPositionOperateUrl.liepinPositionEnd);

                    log.info("===================httpResultJson:{}=====================", httpResultJson);


                    // 猎聘返回code如果不是0，就抛异常
                    requireValidResult(httpResultJson);

                    liepinMappingDao.updateState(requestIds, (byte) 0);
                } catch (BIZException e) {
                    log.info("=============下架猎聘职位失败：requestIds:{},失败信息:msg:{}=================", requestIds.toString(), e.getMessage());
                    EmailSendUtil.sendWarnEmail("下架猎聘职位失败：" + requestIds.toString(), emailSubject);
                } catch (Exception e1) {
                    EmailSendUtil.sendWarnEmail("下架猎聘职位失败,职位id：" + ids, emailSubject);
                    liepinMappingDao.updateErrMsgBatch(requestIds, e1.getMessage());
                }

            }

        } catch (Exception e) {
            EmailSendUtil.sendWarnEmail("调用api批量修改职位信息时发生错误，职位ids" + (ids == null ? msgBody : ids.toString()),
                    emailSubject);
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 取出rabbitmq发来的message
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/6/13
     */
    private Integer requireValidEditId(String msgBody) throws UnsupportedEncodingException {

        JSONObject jsonObject = JSONObject.parseObject(msgBody);

        log.info("职位操作的rabitmq的参数是========" + jsonObject.toJSONString());

        Integer positionId = jsonObject.getInteger("id");

        if (positionId == null) {
            log.info("===============传入id为空=================");
            return null;
        }

        return positionId;
    }

    private List<Integer> requireValidMessage(String msgBody) throws UnsupportedEncodingException {

        JSONObject jsonObject = JSONObject.parseObject(msgBody);

        log.info("职位操作的rabitmq的参数是========" + jsonObject.toJSONString());

        JSONArray positionIds = jsonObject.getJSONArray("id");

        if (positionIds == null || positionIds.isEmpty()) {
            log.info("===============传入id为空=================");
            return new ArrayList<>();
        }

        List<String> idStrs = positionIds.toJavaList(String.class);

        List<Integer> ids = new ArrayList<>();

        for (String id : idStrs) {
            if (StringUtils.isBlank(id)) {
                continue;
            }
            ids.add(Integer.parseInt(id));
        }

        if (ids.size() < 1) {
            log.info("===============传入id为空=================");
            return new ArrayList<>();
        }
        return ids;
    }

    /**
     * 通过职位id获取token
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/6/13
     */
    private String getLiepinToken(int positionId) throws Exception {

        int channel = 2;

        // 获取发布人hrid
        JobPositionDO jobPositionDO = jobPositionDao.getJobPositionByPid(positionId);

        if (jobPositionDO == null) {
            return null;
        }

        int publisher = jobPositionDO.getPublisher();

        // 获取第三方账号和hr账号关联表数据
        HrThirdPartyAccountHrDO hrThirdDO = hrThirdPartyDao.getHrAccountInfo(publisher, channel);

        if (hrThirdDO == null) {
            return null;
        }

        int thirdAccountId = hrThirdDO.getThirdPartyAccountId();

        // 获取第三方账号token
        HrThirdPartyAccountDO thirdPartyAccountDO = thirdPartyAccountDao.getAccountById(thirdAccountId);

        if (thirdPartyAccountDO == null) {
            return null;
        }

        return thirdPartyAccountDO.getExt2();
    }

    /**
     * 获取猎聘职位信息      todo 不确定是否用得到
     *
     * @param positionId 职位id
     * @param id         liepinmapping中的主键id
     * @return
     * @author cjm
     * @date 2018/6/10
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
            return sendRequest2LiePin(liePinJsonObject, liePinToken, LiepinPositionOperateUrl.liepinPositionGet);
        }
        return null;
    }

    /**
     * 过滤不需要重新发布的职位，通过发布状态是否为1来确定
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/6/19
     */
    private List<JobPositionLiepinMappingDO> filterUnneedRepublishCitys(List<JobPositionLiepinMappingDO> liepinMappingDOList) {
        List<JobPositionLiepinMappingDO> list = new ArrayList<>();
        for (JobPositionLiepinMappingDO mappingDO : liepinMappingDOList) {
            if (mappingDO.getState() == 1) {
                continue;
            }
            list.add(mappingDO);
        }
        return list;
    }

    /**
     * 编辑单个职位信息
     *
     * @param liePinToken 猎聘生成的hr账号token
     * @return
     * @author cjm
     * @date 2018/6/10
     */
    private void editSinglePosition(LiePinPositionVO liePinPositionVO, String liePinToken, JobPositionLiepinMappingDO mappingDO) throws BIZException {
        // 猎聘修改职位api必填字段
//        liePinPositionVO.setDetail_duty(liePinPositionVO.getDetail_duty());
//
//        liePinPositionVO.setDetail_require(liePinPositionVO.getDetail_require());

        liePinPositionVO.setEjob_extRefid(String.valueOf(mappingDO.getId()));

        liePinPositionVO.setEjob_id(mappingDO.getLiepinJobId());

        String liepinCityCode = liepinSocialPositionTransfer.getValidLiepinDictCode(mappingDO.getCityCode() + "");

        liePinPositionVO.setEjob_dq(liepinCityCode);

        JSONObject liePinObject = (JSONObject) JSONObject.toJSON(liePinPositionVO);
        try {

            String httpResultJson = sendRequest2LiePin(liePinObject, liePinToken, LiepinPositionOperateUrl.liepinPositionEdit);

            log.info("============httpResultJson:{}===========", httpResultJson);

            requireValidResult(httpResultJson);

        } catch (BIZException e) {
            liepinMappingDao.updateErrMsg(mappingDO.getId(), e.getMessage());
            EmailSendUtil.sendWarnEmail("修改猎聘职位失败：mappingId为" + mappingDO.getId(), emailSubject);
        } catch (Exception e1) {
            EmailSendUtil.sendWarnEmail("修改猎聘职位失败：mappingId为" + mappingDO.getId(), emailSubject);
            liepinMappingDao.updateErrMsg(mappingDO.getId(), e1.getMessage());
        }
    }

    /**
     * 上架单个职位，并没有上架多个职位的接口
     *
     * @param mappingDO 数据库liepingmapping表中针对当前pid的单个职位
     * @return
     * @author cjm
     * @date 2018/6/10
     */
    private void upShelfOldSinglePosition(JobPositionLiepinMappingDO mappingDO, String liepinToken) {

        JSONObject liePinJsonObject = new JSONObject();

        liePinJsonObject.put("ejob_extRefids", mappingDO.getId());

        try {

            String httpResultJson = sendRequest2LiePin(liePinJsonObject, liepinToken, LiepinPositionOperateUrl.liepinPositionRepub);

            requireValidResult(httpResultJson);

            List<Integer> ids = new ArrayList<>();

            ids.add(mappingDO.getId());

            liepinMappingDao.updateState(ids, (byte) 1);

        } catch (BIZException e) {
            liepinMappingDao.updateErrMsg(mappingDO.getId(), e.getMessage());
            EmailSendUtil.sendWarnEmail("上架猎聘职位失败：mappingId为" + mappingDO.getId(), emailSubject);
        } catch (Exception e1) {
            EmailSendUtil.sendWarnEmail("上架猎聘职位失败：mappingId为" + mappingDO.getId(), emailSubject);
            liepinMappingDao.updateErrMsg(mappingDO.getId(), e1.getMessage());
        }
    }

    /**
     * 下架单个职位
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/6/11
     */
    private void downShelfOldSinglePosition(JobPositionLiepinMappingDO jobPositionMapping, String liepinToken) {

        List<JobPositionLiepinMappingDO> list = new ArrayList<>();

        list.add(jobPositionMapping);

        downShelfOldPositions(list, liepinToken);
    }

    /**
     * 下架多个职位
     *
     * @param
     * @return
     * @author cjm
     * @date 2018/6/11
     */
    public void downShelfOldPositions(List<JobPositionLiepinMappingDO> liepinMappingDOList, String liepinToken) {

        List<String> downShelfPositonList = new ArrayList<>();

        List<Integer> downShelfPositonListDb = new ArrayList<>();

        for (JobPositionLiepinMappingDO liepinMappingDO : liepinMappingDOList) {

            int state = liepinMappingDO.getState();

            if (state == 1) {

                downShelfPositonList.add(String.valueOf(liepinMappingDO.getId()));

                downShelfPositonListDb.add(liepinMappingDO.getId());
            }
        }
        if (downShelfPositonList.size() > 0) {

            // 将所有状态为1的职位下架
            JSONObject liePinJsonObject = new JSONObject();

            String ids = String.join(",", downShelfPositonList);

            liePinJsonObject.put("ejob_extRefids", ids);

            try {
                log.info("==================title变化，将之前所有的下架获取所有的hashid，新的发布=====================");

                // 下架
                String httpResultJson = sendRequest2LiePin(liePinJsonObject, liepinToken, LiepinPositionOperateUrl.liepinPositionEnd);

                log.info("================httpResultJson:{}===============", httpResultJson);

                requireValidResult(httpResultJson);

                liepinMappingDao.updateState(downShelfPositonListDb, (byte) 0);

            } catch (BIZException e) {
                liepinMappingDao.updateErrMsgBatch(downShelfPositonListDb, e.getMessage());
                EmailSendUtil.sendWarnEmail("downShelfOldPositions批量下架猎聘职位失败：mappingIds为" + ids, emailSubject);
            } catch (Exception e1) {
                EmailSendUtil.sendWarnEmail("下架猎聘职位失败：mappingIds为" + ids, emailSubject);
                liepinMappingDao.updateErrMsgBatch(downShelfPositonListDb, "后台异常");
                log.error("===============向猎聘请求下架失败，mapping表主键ids:{}===============", ids);
            }
        }
    }

    public JSONObject requireValidResult(String httpResultJson) throws BIZException {

        if (StringUtils.isBlank(httpResultJson)) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.LIEPIN_REQUEST_RESPONSE_NULL);
        }

        JSONObject httpResult = JSONObject.parseObject(httpResultJson);

        if (null == httpResult) {

            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.LIEPIN_REQUEST_RESPONSE_NULL);

        }else if (httpResult.getIntValue("code") == 1002) {

            log.info("==============httpResult:{}================", httpResult);
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.LIEPIN_REQUEST_LIMIT);
        }else if (httpResult.getIntValue("code") != 0) {

            log.info("==============httpResult:{}================", httpResult);
            throw ExceptionUtils.getBizException("{'status':-1,'message':'猎聘职位操作请求失败！" + httpResult.getString("message") + "'}");
        }
        return httpResult;
    }

    public String sendRequest2LiePin(JSONObject liePinJsonObject, String liePinToken, String url) throws Exception {

        String t = DateUtils.dateToPattern(new Date(), "yyyyMMdd");

        liePinJsonObject.put("t", t);

        String sign = Md5Utils.getMD5SortKey(Md5Utils.mapToList(liePinJsonObject), liePinJsonObject);

        liePinJsonObject.put("sign", sign);

        //设置请求头
        Map<String, String> headers = new HashMap<>();

        headers.put("channel", "qianxun");

        headers.put("token", liePinToken);

        return HttpClientUtil.sentHttpPostRequest(url, headers, liePinJsonObject);
    }

    private List<String> removeDuplicateTitle(List<String> titleDbList) {
        LinkedHashSet<String> set = new LinkedHashSet<>(titleDbList.size());
        set.addAll(titleDbList);
        titleDbList.clear();
        titleDbList.addAll(set);
        return titleDbList;
    }


    private JobPositionDO convertJSON2DO(JSONObject jobPositionJSON) {
        JobPositionDO jobPositionDO = new JobPositionDO();
        jobPositionDO.setId(jobPositionJSON.getIntValue("id"));
        jobPositionDO.setJobnumber(jobPositionJSON.getString("jobnumber"));
        jobPositionDO.setCompanyId(jobPositionJSON.getIntValue("company_id"));
        jobPositionDO.setTitle(jobPositionJSON.getString("title"));
        jobPositionDO.setProvince(jobPositionJSON.getString("province"));
        jobPositionDO.setCity(jobPositionJSON.getString("city"));
        jobPositionDO.setDepartment(jobPositionJSON.getString("department"));
        jobPositionDO.setLJobid(jobPositionJSON.getIntValue("l_jobid"));
        jobPositionDO.setPublishDate(jobPositionJSON.getString("publish_date"));
        jobPositionDO.setStopDate(jobPositionJSON.getString("stop_date"));
        jobPositionDO.setAccountabilities(jobPositionJSON.getString("accountabilities"));
        jobPositionDO.setExperience(jobPositionJSON.getString("experience"));
        jobPositionDO.setRequirement(jobPositionJSON.getString("requirement"));
        jobPositionDO.setSalary(jobPositionJSON.getString("salary"));
        jobPositionDO.setLanguage(jobPositionJSON.getString("language"));
        jobPositionDO.setJobGrade(jobPositionJSON.getIntValue("job_grade"));
        jobPositionDO.setStatus(jobPositionJSON.getDouble("status") == null ? 0 : jobPositionJSON.getDouble("status"));
        jobPositionDO.setVisitnum(jobPositionJSON.getIntValue("visitnum"));
        jobPositionDO.setLastvisit(jobPositionJSON.getString("lastvisit"));
        jobPositionDO.setSourceId(jobPositionJSON.getIntValue("source_id"));
        jobPositionDO.setUpdateTime(jobPositionJSON.getString("update_time"));
        jobPositionDO.setBusinessGroup(jobPositionJSON.getString("business_group"));
        jobPositionDO.setEmploymentType(jobPositionJSON.getDouble("employment_type") == null ? 0 : jobPositionJSON.getDouble("employment_type"));
        jobPositionDO.setHrEmail(jobPositionJSON.getString("hr_email"));
        jobPositionDO.setBenefits(jobPositionJSON.getString("benefits"));
        jobPositionDO.setDegree(jobPositionJSON.getDouble("degree") == null ? 0 : jobPositionJSON.getDouble("degree"));
        jobPositionDO.setFeature(jobPositionJSON.getString("feature"));
        jobPositionDO.setEmailNotice("true".equals(jobPositionJSON.getString("email_notice")) ? (byte) 1 : 0);
        jobPositionDO.setCandidateSource(jobPositionJSON.getDouble("candidate_source") == null ? 0 : jobPositionJSON.getDouble("candidate_source"));
        jobPositionDO.setOccupation(jobPositionJSON.getString("occupation"));
        jobPositionDO.setIsRecom(jobPositionJSON.getIntValue("is_recom"));
        jobPositionDO.setIndustry(jobPositionJSON.getString("industry"));
        jobPositionDO.setHongbaoConfigAppId(jobPositionJSON.getIntValue("hongbao_config_app_id"));
        jobPositionDO.setHongbaoConfigId(jobPositionJSON.getIntValue("hongbao_config_id"));
        jobPositionDO.setHongbaoConfigRecomId(jobPositionJSON.getIntValue("hongbao_config_recom_id"));
        jobPositionDO.setEmailResumeConf(jobPositionJSON.getDouble("email_resume_conf") == null ? 0 : jobPositionJSON.getDouble("email_resume_conf"));
        jobPositionDO.setLPostingtargetid(jobPositionJSON.getDouble("l_PostingTargetId") == null ? 0 : jobPositionJSON.getDouble("l_PostingTargetId"));
        jobPositionDO.setPriority(jobPositionJSON.getDouble("priority") == null ? 10 : jobPositionJSON.getDouble("priority"));
        jobPositionDO.setShareTplId(jobPositionJSON.getDouble("share_tpl_id") == null ? 0 : jobPositionJSON.getDouble("share_tpl_id"));
        jobPositionDO.setDistrict(jobPositionJSON.getString("district"));
        jobPositionDO.setCount(jobPositionJSON.getDouble("count") == null ? 0 : jobPositionJSON.getDouble("count"));
        jobPositionDO.setSalaryTop(jobPositionJSON.getDouble("salary_top"));
        jobPositionDO.setSalaryBottom(jobPositionJSON.getDouble("salary_bottom"));
        jobPositionDO.setExperienceAbove("true".equals(jobPositionJSON.getString("experience_above")) ? (byte) 1 : 0);
        jobPositionDO.setDegreeAbove("true".equals(jobPositionJSON.getString("degree_above")) ? (byte) 1 : 0);
        jobPositionDO.setManagementExperience(jobPositionJSON.getDouble("management_experience") == null ? 1 : 0);
        jobPositionDO.setGender(jobPositionJSON.getDouble("gender") == null ? 2 : jobPositionJSON.getDouble("gender"));
        jobPositionDO.setPublisher(jobPositionJSON.getIntValue("publisher"));
        jobPositionDO.setAppCvConfigId(jobPositionJSON.getIntValue("app_cv_config_id"));
        jobPositionDO.setSource(jobPositionJSON.getDouble("source") == null ? 0 : jobPositionJSON.getDouble("source"));
        jobPositionDO.setHbStatus(jobPositionJSON.getByte("hb_status") == null ? 0 : jobPositionJSON.getByte("hb_status"));
        jobPositionDO.setChildCompanyId(jobPositionJSON.getIntValue("child_company_id"));
        jobPositionDO.setAge(jobPositionJSON.getByte("age") == null ? 0 : jobPositionJSON.getByte("age"));
        jobPositionDO.setMajorRequired(jobPositionJSON.getString("major_required"));
        jobPositionDO.setWorkAddress(jobPositionJSON.getString("work_address"));
        jobPositionDO.setKeyword(jobPositionJSON.getString("keyword"));
        jobPositionDO.setReportingTo(jobPositionJSON.getString("reporting_to"));
        jobPositionDO.setIsHiring("true".equals(jobPositionJSON.getString("is_hiring")) ? (byte) 1 : 0);
        jobPositionDO.setUnderlings(jobPositionJSON.getByte("underlings") == null ? 0 : jobPositionJSON.getByte("underlings"));
        jobPositionDO.setLanguageRequired("true".equals(jobPositionJSON.getString("language_required")) ? (byte) 1 : 0);
        jobPositionDO.setTargetIndustry(jobPositionJSON.getByte("target_industry") == null ? 0 : jobPositionJSON.getByte("target_industry"));
        jobPositionDO.setCurrentStatus(jobPositionJSON.getByte("current_status") == null ? 0 : jobPositionJSON.getByte("current_status"));
        jobPositionDO.setPositionCode(jobPositionJSON.getIntValue("position_code"));
        jobPositionDO.setTeamId(jobPositionJSON.getIntValue("team_id"));
        jobPositionDO.setProfile_cc_mail_enabled(jobPositionJSON.getByte("profile_cc_mail_enabled") == null ? 0 : jobPositionJSON.getByte("profile_cc_mail_enabled"));
        return jobPositionDO;
    }

    private boolean compareJobPosition(JobPositionDO jobPositionDO, JobPositionDO updateJobPosition) {
        jobPositionDO = filterBlank(jobPositionDO);
        updateJobPosition = filterBlank(updateJobPosition);
        return strEquals(jobPositionDO.getTitle(), updateJobPosition.getTitle())
                && strEquals(jobPositionDO.getCity(), updateJobPosition.getCity())
                && strEquals(jobPositionDO.getAccountabilities(), updateJobPosition.getAccountabilities())
                && strEquals(jobPositionDO.getRequirement(), updateJobPosition.getRequirement())
                && strEquals(jobPositionDO.getExperience(), updateJobPosition.getExperience())
                && strEquals(jobPositionDO.getSalary(), updateJobPosition.getSalary())
                && strEquals(jobPositionDO.getLanguage(), updateJobPosition.getLanguage())
                && doubleEquals(jobPositionDO.getDegree(), updateJobPosition.getDegree())
                && strEquals(jobPositionDO.getFeature(), updateJobPosition.getFeature())
                && doubleEquals(jobPositionDO.getCount(), updateJobPosition.getCount())
                && doubleEquals(jobPositionDO.getSalaryBottom(), updateJobPosition.getSalaryBottom())
                && doubleEquals(jobPositionDO.getSalaryTop(), updateJobPosition.getSalaryTop())
                && doubleEquals(jobPositionDO.getGender(), updateJobPosition.getGender())
                && byteEquals(jobPositionDO.getAge(), updateJobPosition.getAge())
                && strEquals(jobPositionDO.getDepartment(), updateJobPosition.getDepartment());
    }

    private boolean strEquals(String s1, String s2){
        if(StringUtils.isBlank(s1) && StringUtils.isBlank(s2)){
            return true;
        }
        if(StringUtils.isNotBlank(s1) && StringUtils.isNotBlank(s2)){
            return s1.equals(s2);
        }
        return false;
    }

    private boolean byteEquals(Byte s1, Byte s2){
        if(null == s1 && null == s2){
            return true;
        }
        if(null != s1 && null != s2){
            return s1.equals(s2);
        }
        return false;
    }
    private boolean doubleEquals(Double s1, Double s2){
        if(null == s1 && null == s2){
            return true;
        }
        if(null != s1 && null != s2){
            return s1.equals(s2);
        }
        return false;
    }
    private boolean intEquals(Integer s1, Integer s2){
        if(null == s1 && null == s2){
            return true;
        }
        if(null != s1 && null != s2){
            return s1.equals(s2);
        }
        return false;
    }

    private JobPositionDO filterBlank(JobPositionDO jobPositionDO) {
        if (StringUtils.isNotBlank(jobPositionDO.getTitle())) {
            jobPositionDO.setTitle(jobPositionDO.getTitle().replaceAll("\\s", ""));
        }
        if (StringUtils.isNotBlank(jobPositionDO.getDepartment())) {
            jobPositionDO.setDepartment(jobPositionDO.getDepartment().replaceAll("\\s", ""));
        }
        if (StringUtils.isNotBlank(jobPositionDO.getAccountabilities())) {
            jobPositionDO.setAccountabilities(jobPositionDO.getAccountabilities().replaceAll("\\s", ""));
        }
        if (StringUtils.isNotBlank(jobPositionDO.getRequirement())) {
            jobPositionDO.setRequirement(jobPositionDO.getRequirement().replaceAll("\\s", ""));
        }
        if (StringUtils.isNotBlank(jobPositionDO.getLanguage())) {
            jobPositionDO.setLanguage(jobPositionDO.getLanguage().replaceAll("\\s", ""));
        }
        if (StringUtils.isNotBlank(jobPositionDO.getHrEmail())) {
            jobPositionDO.setHrEmail(jobPositionDO.getHrEmail().replaceAll("\\s", ""));
        }
        if (StringUtils.isNotBlank(jobPositionDO.getBenefits())) {
            jobPositionDO.setBenefits(jobPositionDO.getBenefits().replaceAll("\\s", ""));
        }
        if (StringUtils.isNotBlank(jobPositionDO.getFeature())) {
            jobPositionDO.setFeature(jobPositionDO.getFeature().replaceAll("\\s", ""));
        }
        if (StringUtils.isNotBlank(jobPositionDO.getOccupation())) {
            jobPositionDO.setOccupation(jobPositionDO.getOccupation().replaceAll("\\s", ""));
        }
        if (StringUtils.isNotBlank(jobPositionDO.getMajorRequired())) {
            jobPositionDO.setMajorRequired(jobPositionDO.getMajorRequired().replaceAll("\\s", ""));
        }
        if (StringUtils.isNotBlank(jobPositionDO.getWorkAddress())) {
            jobPositionDO.setWorkAddress(jobPositionDO.getWorkAddress().replaceAll("\\s", ""));
        }
        if (StringUtils.isNotBlank(jobPositionDO.getKeyword())) {
            jobPositionDO.setKeyword(jobPositionDO.getKeyword().replaceAll("\\s", ""));
        }
        return jobPositionDO;
    }

    private void sendSyncPosition(int positionId, ThirdPartyPosition thirdPartyPosition) throws Exception {

        ThirdPartyPositionForm positionForm = new ThirdPartyPositionForm();

        positionForm.setAppid(0);
        positionForm.setPositionId(positionId);
        positionForm.setRequestType(1);
        List<String> list = new ArrayList<>();
        list.add(JSONObject.toJSONString(thirdPartyPosition));
        positionForm.setChannels(list);

        hrThirdPartyPositionDao.updateBindState(positionId, 2, 0);

        positionBS.syncPositionToThirdParty(positionForm);
    }

}
