package com.moseeker.position.service.position.liepin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionLiepinMappingDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.constants.PositionSyncVerify;
import com.moseeker.position.constants.position.liepin.LiepinPositionOperateConstant;
import com.moseeker.position.pojo.LiePinPositionVO;
import com.moseeker.position.service.schedule.bean.PositionSyncStateRefreshBean;
import com.moseeker.position.service.schedule.delay.PositionTaskQueueDaemonThread;
import com.moseeker.position.utils.HttpClientUtil;
import com.moseeker.position.utils.PositionEmailNotification;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 1、监听das中的职位操作，进行与猎聘相关的职位处理
 * 2、处理ats的批量请求
 * 3、和liepinSocialPositionTransfer公用的一些方法
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
    private JobPositionCityDao jobPositionCityDao;

    @Autowired
    private PositionEmailNotification emailNotification;

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private PositionTaskQueueDaemonThread delayQueueThread;

    private Random random = new Random();

    /**
     * 批量处理编辑职位操作
     * 用于ats批量处理
     *
     * @param list      修改后的jobposition集合
     * @param oldJobMap 修改前的jobposition集合
     * @return 返回批量处理是否调用成功
     * @author cjm
     * @date 2018/6/11
     */
    public boolean batchHandleLiepinEditOperation(List<JobPositionRecord> list, Map<Integer, JobPositionRecord> oldJobMap) {

        List<Integer> remainIds = list.stream().map(JobPositionRecord::getId).collect(Collectors.toList());

        try {
            JSONObject liePinJsonObject = new JSONObject();
            JobPositionDO jobPositionDO;
            JobPositionDO oldJobPositionDO;
            for (JobPositionRecord record : list) {

                if (record.getCandidateSource() == 1) {
                    log.info("=========批量处理，当前职位为校招职位，无需猎聘api同步==========");
                    continue;
                }
                jobPositionDO = BeanUtils.DBToStruct(JobPositionDO.class, record);
                log.info("======================批量处理的DO转换jobPositionDO:{}", jobPositionDO);
                JobPositionRecord oldRecord = oldJobMap.get(jobPositionDO.getId());

                oldJobPositionDO = BeanUtils.DBToStruct(JobPositionDO.class, oldRecord);
                log.info("======================批量处理的DO转换oldJobPositionDO:{}", oldJobPositionDO);

                boolean positionFlag = false;
                if (jobPositionDO.getStatus() == 0 && (oldJobPositionDO.getStatus() == 1 || oldJobPositionDO.getStatus() == 2)) {
                    positionFlag = true;
                }

                liePinJsonObject.put("id", jobPositionDO.getId());
                liePinJsonObject.put("positionFlag", positionFlag);
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
            emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinProdMails, null, e, "调用api批量修改职位信息时发生错误，职位ids:" + remainIds);
            e.printStackTrace();
        }

        return false;
    }


    @RabbitListener(queues = PositionSyncVerify.POSITION_QUEUE_EDIT, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handlerPositionLiepinEditOperation(Message message, Channel channel) {
        String msgBody = "{}";
        LiePinPositionVO liePinPositionVO = null;
        Integer id;
        try {

            msgBody = new String(message.getBody(), "UTF-8");

            log.info("===============msgBody:{}===============", msgBody);

            JSONObject msgObject = JSONObject.parseObject(msgBody);

            id = requireValidEditId(msgObject);

            if (id == null) {
                return;
            }

            int positionChannel = ChannelType.LIEPIN.getValue();

            int positionId = id;

            HrThirdPartyAccountDO hrThirdPartyAccountDO = getLiepinToken(positionId);

            if (hrThirdPartyAccountDO == null) {
                log.info("=============positionId:{}查不到用户猎聘第三方账号信息，本次操作结束===========", positionId);
                return;
            }

            String liePinToken = hrThirdPartyAccountDO.getExt2();

            int hrAccountId = hrThirdPartyAccountDO.getId();

            if (StringUtils.isBlank(liePinToken)) {
                log.info("============hrAccountId:{}, token为空=============", hrAccountId);
                return;
            }

            log.info("==============liePinToken:{}===============", liePinToken);

            // 获取在仟寻填写的猎聘职位信息
            HrThirdPartyPositionDO hrThirdPartyPositionDO = hrThirdPartyPositionDao.getThirdPartyPositionById(positionId, positionChannel, hrAccountId);

            log.info("================hrThirdPartyPositionDO:{}", hrThirdPartyPositionDO);

            if (hrThirdPartyPositionDO == null) {
                log.info("==============第三方职位信息为空，positionId:{}=============", positionId);
                return;
            }

            int thirdPartyPositionId = hrThirdPartyPositionDO.getId();

            // 组装同步时需要的数据，相当于在第三方页面填写的表单数据，将不匹配字段手动映射
            ThirdPartyPosition thirdPartyPosition = mappingThirdPartyPosition(hrThirdPartyPositionDO);

            // 获取das端已修改后的职位数据
            JobPositionDO updateJobPosition = getJobPositionFromMq(msgObject, "params");

            JobPositionDO jobPositionDO = getJobPositionFromMq(msgObject, "oldPosition");

            // true表示从下架状态编辑
            boolean positionFlag = getPositionFlag(msgObject);

            if (updateJobPosition == null || jobPositionDO == null) {
                return;
            }

            if (jobPositionDO.getCandidateSource() == 1) {
                log.info("===========校招职位不用处理===========");
                return;
            }

            // 是否需要编辑，返回true，两个职位相同，不需要edit，需要注意的是比较中包括title，但是city的比较不准确，所以代码下方对city进行了精确的比对
            boolean noNeedEdit = compareJobPosition(jobPositionDO, updateJobPosition);

            // 用于查询所修改的职位之前是否发布过
            List<JobPositionLiepinMappingDO> liepinMappingDOList = liepinMappingDao.getMappingDataByPid(positionId);

            if (null == liepinMappingDOList || liepinMappingDOList.size() < 1) {
                log.info("=================该职位未在猎聘发布过，编辑时无需到猎聘修改===============");
                return;
            }

            // 编辑职位中的城市list
            List<JobPositionCityDO> positionCityList = jobPositionCityDao.getPositionCityBypid(positionId);
            log.info("==============编辑城市positionCityList:{}============", positionCityList);
            // 编辑职位中的城市codelist
            List<String> newCityList = positionCityList.stream().map(positionCityDO -> String.valueOf(positionCityDO.getCode())).collect(Collectors.toList());
            log.info("==============编辑城市newCityList:{}============", newCityList);

            // 数据库中该仟寻职位id对应的城市codes list
            List<String> mappingCityList = liepinMappingDOList.stream().filter(mappingDo -> mappingDo.getState() != 0)
                    .map(mappingDo -> String.valueOf(mappingDo.getCityCode())).distinct().collect(Collectors.toList());
            log.info("===============数据库中该仟寻职位id对应的职位状态为1的城市cityDbList:{}====================", mappingCityList);

            // 数据库中该仟寻职位id对应的titlelist
            List<String> titleDbList = liepinMappingDOList.stream().map(JobPositionLiepinMappingDO::getJobTitle).collect(Collectors.toList());

            // 将titlelist去重复
            titleDbList = removeDuplicateTitle(titleDbList);
            log.info("===============数据库中该仟寻职位id对应的titlelist:{}====================", titleDbList);

            // city改变标志 true是city发生改变，false是未发生改变
            boolean isCityChange = compareCity(newCityList, mappingCityList);

            // true表示标题变化了
            boolean isTitleChange = !jobPositionDO.getTitle().equals(updateJobPosition.getTitle());

            log.info("==========isCityChange:{},isTitleChange:{},positionFlag:{}==================", isCityChange, isTitleChange, positionFlag);
            // das端其实已经做过同步状态的修改，此举是为了兼容ats端
            if (isCityChange) {
                // 如果是city变化，或者是下架状态的修改，将同步状态置为0，未同步
                log.info("================city变化，或者是下架状态的修改==============");
                hrThirdPartyPositionDao.updateBindState(positionId, hrAccountId, ChannelType.LIEPIN.getValue(), 0);

            }

            if (!positionFlag && noNeedEdit && !isCityChange) {
                log.info("=============没有修改猎聘所需字段，无需发布修改============");
                return;
            }

            // 将数据组装为向猎聘请求的格式，此数据也是用户编辑后的数据
            liePinPositionVO = liepinSocialPositionTransfer.changeToThirdPartyPosition(thirdPartyPosition, updateJobPosition, null);
            log.info("================liePinPositionVO:{}=============", liePinPositionVO);

            // 如果title没变
            if (!isTitleChange) {
                // 如果城市没变
                if (!isCityChange) {
                    for (JobPositionLiepinMappingDO mappingDO : liepinMappingDOList) {
                        // 修改
                        if (mappingDO.getState() != 0) {
                            liepinSocialPositionTransfer.editSinglePosition(liePinPositionVO, liePinToken, mappingDO);
                            // 将职位同步状态设置为2，待审核
                            hrThirdPartyPositionDO.setIsSynchronization(PositionSync.binding.getValue());
                            TwoParam<HrThirdPartyPositionDO, HrThirdPartyPositionDO> twoParam = new TwoParam<>(hrThirdPartyPositionDO, null);
                            hrThirdPartyPositionDao.upsertThirdPartyPosition(twoParam);
                            log.info("======================hrThirdPartyPositionDO:{}",hrThirdPartyPositionDO);
                            PositionSyncStateRefreshBean refreshBean = new PositionSyncStateRefreshBean(thirdPartyPositionId, positionChannel);
                            // 过期时间加上一个随机数，减少大量职位在同一时间内操作时的服务器压力
                            delayQueueThread.put(random.nextInt(5 * 1000), refreshBean);
                            log.info("========================refreshBean:{},放入LiepinSyncStateRefresh", refreshBean);
                        }
                    }

                } else {
                    // 数据库中存在，但是本次编辑中没有的城市，执行下架
                    for (JobPositionLiepinMappingDO mappingDO : liepinMappingDOList) {

                        if (!newCityList.contains(String.valueOf(mappingDO.getCityCode())) && mappingDO.getState() == 1) {
                            log.info("============本次下架mappingdo:{}===========", mappingDO);
                            // 如果编辑的城市中存在数据库中的该城市，但是title不相同，并且该城市之前出于上架状态，则将其下架
                            downShelfOldSinglePosition(mappingDO, liePinToken);
                        }
                    }
                }

            } else {
                //  title变化，将之前所有的下架获取所有的jobMappingIds，新的发布
                log.info("============title变化，将之前所有的下架获取所有的jobMappingIds，新的发布===============");
                downShelfOldPositions(liepinMappingDOList, liePinToken);
            }

        } catch (BIZException e) {
            log.warn(e.getMessage(), e);
        } catch (Exception e1) {
            emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinProdMails, liePinPositionVO, e1, "【调用api批量修改职位信息时发生错误】:" + msgBody);
            log.error(e1.getMessage(), e1);
        }
    }

    private ThirdPartyPosition mappingThirdPartyPosition(HrThirdPartyPositionDO hrThirdPartyPositionDO) {
        ThirdPartyPosition thirdPartyPosition = new ThirdPartyPosition();
        org.springframework.beans.BeanUtils.copyProperties(hrThirdPartyPositionDO, thirdPartyPosition);
        thirdPartyPosition.setSalaryDiscuss(hrThirdPartyPositionDO.getSalaryDiscuss() != 0);
        thirdPartyPosition.setSalaryBottom(hrThirdPartyPositionDO.getSalaryBottom() / 1000);
        thirdPartyPosition.setSalaryTop(hrThirdPartyPositionDO.getSalaryTop() / 1000);
        String occupations = hrThirdPartyPositionDO.getOccupation();
        String[] occupationsArr = occupations.split(",");
        thirdPartyPosition.setOccupation(Arrays.asList(occupationsArr));
        return thirdPartyPosition;
    }

    private boolean compareCity(List<String> newCityList, List<String> mappingCityList) {
        if (newCityList == null || mappingCityList == null) {
            return false;
        }
        if (newCityList.size() != mappingCityList.size()) {
            return true;
        }
        for (String mappingCity : mappingCityList) {
            if (!newCityList.contains(mappingCity)) {
                return true;
            }
        }
        return false;
    }

    private boolean getPositionFlag(JSONObject msgObject) {
        try {
            String flag = msgObject.getString("positionFlag");
            if (StringUtils.isBlank(flag)) {
                return false;
            }
            if ("true".equals(flag)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private JobPositionDO getJobPositionFromMq(JSONObject msgObject, String key) throws BIZException {
        String jobPositionJSON = msgObject.getString(key);
        log.info("============jobPositionJSON:{}=============", jobPositionJSON);
        JobPositionDO jobPositionDO = JSON.parseObject(jobPositionJSON, JobPositionDO.class);
        log.info("============jobPositionDO:{}=============", jobPositionDO);
        return jobPositionDO;
    }

    /**
     * 批量处理职位下架
     * ats批量下架
     *
     * @param ids jobPosition职位id集合
     * @return boolean 是否调用成功
     * @author cjm
     * @date 2018/6/11
     */
    public boolean batchHandlerLiepinDownShelfOperation(List<Integer> ids) {
        try {
            log.info("===========ids:{}=========", ids);
            if (ids == null || ids.size() < 1) {
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
            emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinProdMails, null, e, "【调用api猎聘api批量处理职位下架失败】positionids:" + ids);
            e.printStackTrace();
        }
        return false;

    }


    /**
     * 职位上架，目前逻辑改变，上架职位不再同时在猎聘上架
     *
     * @author cjm
     * @date 2018/7/6
     */
    @Deprecated
    @RabbitListener(queues = PositionSyncVerify.POSITION_QUEUE_RESYNC, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handlerPositionLiepinReSyncOperation(Message message, Channel channel) {
        String msgBody = "{}";
        List<Integer> ids;
        try {
            msgBody = new String(message.getBody(), "UTF-8");

            ids = requireValidMessage(msgBody);

            if (ids == null || ids.size() == 0) {
                return;
            }

            for (int positionId : ids) {

                // 获取hr账号在猎聘token
                HrThirdPartyAccountDO hrThirdPartyAccountDO = getLiepinToken(positionId);

                if (hrThirdPartyAccountDO == null) {
                    log.info("=============查不到用户猎聘第三方账号信息，本次操作结束===========");
                    return;
                }

                String liePinToken = hrThirdPartyAccountDO.getExt2();

                int hrAccountId = hrThirdPartyAccountDO.getId();

                List<Integer> idsList = new ArrayList<>();
                try {

                    JobPositionDO jobPositionDO = jobPositionDao.getJobPositionByPid(positionId);

                    if (jobPositionDO == null || jobPositionDO.getCandidateSource() == 1) {
                        continue;
                    }

                    // 通过职位id获取职位所在城市list
                    List<JobPositionCityDO> jobPositionCityList = jobPositionCityDao.getPositionCitysByPid(positionId);

                    // 获取职位所在城市codeList
                    List<Integer> rePublishCityCodes = jobPositionCityList.stream().map(JobPositionCityDO::getCode).collect(Collectors.toList());

                    // 通过职位id和code获取已经下架的JobPositionLiepinMapping表的信息，此表存的是仟寻请求猎聘生成的职位主键id等信息的映射
                    List<JobPositionLiepinMappingDO> liepinMappingDOList = liepinMappingDao.getDownShelfMappingDataByPidAndCode(positionId, rePublishCityCodes);

                    if (liepinMappingDOList == null || liepinMappingDOList.size() < 1) {
                        log.info("===============未在猎聘发布过，不需要重新发布================");
                        continue;
                    }

                    // 过滤重新发布城市中的state已经为1的城市
                    liepinMappingDOList = liepinMappingDOList.stream().filter(liepinMappingDO -> liepinMappingDO.getState() == 0).collect(Collectors.toList());

                    if (liepinMappingDOList.size() > 0) {

                        // 将需要重新发布的城市的主键id取出，用于向猎聘请求
                        idsList = liepinMappingDOList.stream().map(JobPositionLiepinMappingDO::getId).collect(Collectors.toList());

                        List<String> requestIdsStr = liepinMappingDOList.stream().map(liepinMappingDO -> String.valueOf(liepinMappingDO.getId())).collect(Collectors.toList());

                        List<Integer> idsListDb = liepinMappingDOList.stream().map(JobPositionLiepinMappingDO::getId).collect(Collectors.toList());

                        String requestIds = String.join(",", requestIdsStr);

                        if (StringUtils.isNotBlank(requestIds)) {

                            JSONObject liePinJsonObject = new JSONObject();

                            liePinJsonObject.put("ejob_extRefids", requestIds);

                            String httpResultJson = httpClientUtil.sendRequest2LiePin(liePinJsonObject, liePinToken, LiepinPositionOperateConstant.liepinPositionRepub);

                            httpClientUtil.requireValidResult(httpResultJson);

                            liepinMappingDao.updateState(idsListDb, (byte) 1);

                            hrThirdPartyPositionDao.updateBindState(positionId, hrAccountId, 2, 1);
                        }
                    }
                } catch (BIZException e) {
                    liepinMappingDao.updateErrMsgBatch(positionId, e.getMessage());
                } catch (Exception e1) {
                    emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinProdMails, null, e1, "【调用api猎聘api处理职位重新发布失败】msgBody:" + msgBody);
                    liepinMappingDao.updateErrMsgBatch(idsList, e1.getMessage());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinProdMails, null, e, "【调用api猎聘api处理职位重新发布失败】msgBody:" + msgBody);
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

            for (int positionId : ids) {

                try {

                    // 获取hr账号在猎聘token
                    HrThirdPartyAccountDO hrThirdPartyAccountDO = getLiepinToken(positionId);

                    if (hrThirdPartyAccountDO == null) {
                        log.info("=============查不到用户猎聘第三方账号信息，本次操作结束===========");
                        return;
                    }

                    String liePinToken = hrThirdPartyAccountDO.getExt2();

                    JobPositionDO jobPositionDO = jobPositionDao.getJobPositionByPid(positionId);

                    if (jobPositionDO == null || jobPositionDO.getCandidateSource() == 1) {
                        continue;
                    }

                    List<JobPositionLiepinMappingDO> liepinMappingDOS = liepinMappingDao.getValidMappingDataByPid(positionId, (byte) 1);

                    if (liepinMappingDOS == null || liepinMappingDOS.size() < 1) {
                        log.info("==============该职位没有在猎聘发布过，不需要下架==============");
                        continue;
                    }

                    requestIds = liepinMappingDOS.stream().map(JobPositionLiepinMappingDO::getId).collect(Collectors.toList());

                    List<String> requestIdsStr = liepinMappingDOS.stream().map(liepinMappingDO -> String.valueOf(liepinMappingDO.getId())).collect(Collectors.toList());

                    if (requestIds.size() < 1) {
                        log.info("==============没有需要下架的猎聘职位==============");
                        continue;
                    }

                    // 构造请求数据
                    JSONObject liePinJsonObject = new JSONObject();

                    String requestStr = String.join(",", requestIdsStr);

                    liePinJsonObject.put("ejob_extRefids", requestStr);

                    String httpResultJson = httpClientUtil.sendRequest2LiePin(liePinJsonObject, liePinToken, LiepinPositionOperateConstant.liepinPositionEnd);

                    // 猎聘返回code如果不是0，就抛异常
                    httpClientUtil.requireValidResult(httpResultJson);

                    liepinMappingDao.updateState(requestIds, (byte) 0);
                } catch (BIZException e) {
                    log.info("=============下架猎聘职位失败：requestIds:{},失败信息:msg:{}=================", requestIds.toString(), e.getMessage());
                    liepinMappingDao.updateErrMsgBatch(requestIds, e.getMessage());
                } catch (Exception e1) {
                    log.error(e1.getMessage(), e1);
                    liepinMappingDao.updateErrMsgBatch(requestIds, "后台异常");
                    emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinProdMails, null, e1, "【调用api猎聘api处理职位下架失败】mappingId:" + requestIds.toString());
                }

            }

        } catch (Exception e) {
            emailNotification.sendSyncLiepinFailEmail(PositionEmailNotification.liepinProdMails, null, e, "【调用api猎聘api处理职位下架失败】positionid:" + (ids == null ? msgBody : ids.toString()));
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 取出rabbitmq发来的message
     *
     * @param msgObject rabbitmq传来的数据参数
     * @return 返回职位id
     * @author cjm
     * @date 2018/6/13
     */
    private Integer requireValidEditId(JSONObject msgObject) {

        log.info("职位操作的rabitmq的参数是========" + msgObject.toJSONString());

        Integer positionId = msgObject.getInteger("id");

        if (positionId == null) {
            log.info("===============传入id为空=================");
            return null;
        }

        return positionId;
    }

    private List<Integer> requireValidMessage(String msgBody) {

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
     * @param positionId 职位id
     * @return HrThirdPartyAccountDO
     * @author cjm
     * @date 2018/6/13
     */
    private HrThirdPartyAccountDO getLiepinToken(int positionId) {

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

        return thirdPartyAccountDO;
    }

    /**
     * 下架单个职位
     *
     * @param jobPositionMapping job_position_mapping表实体
     * @param liePinToken        hr在猎聘绑定后的token
     * @author cjm
     * @date 2018/6/11
     */
    private void downShelfOldSinglePosition(JobPositionLiepinMappingDO jobPositionMapping, String liePinToken) throws Exception {

        List<JobPositionLiepinMappingDO> list = new ArrayList<>();

        list.add(jobPositionMapping);

        downShelfOldPositions(list, liePinToken);
    }

    /**
     * 下架多个职位
     *
     * @param liePinMappingDOList job_position_mapping表实体list
     * @param liePinToken         hr在猎聘绑定后的token
     * @author cjm
     * @date 2018/6/11
     */
    private void downShelfOldPositions(List<JobPositionLiepinMappingDO> liePinMappingDOList, String liePinToken) throws Exception {

        List<String> downShelfPositonList = new ArrayList<>();

        List<Integer> downShelfPositonListDb = new ArrayList<>();

        for (JobPositionLiepinMappingDO liePinMappingDO : liePinMappingDOList) {

            int state = liePinMappingDO.getState();

            if (state == 1) {

                downShelfPositonList.add(String.valueOf(liePinMappingDO.getId()));

                downShelfPositonListDb.add(liePinMappingDO.getId());
            }
        }
        if (downShelfPositonList.size() > 0) {

            // 将所有状态为1的职位下架
            JSONObject liePinJsonObject = new JSONObject();

            String ids = String.join(",", downShelfPositonList);

            liePinJsonObject.put("ejob_extRefids", ids);

            try {
                // 下架
                String httpResultJson = httpClientUtil.sendRequest2LiePin(liePinJsonObject, liePinToken, LiepinPositionOperateConstant.liepinPositionEnd);

                httpClientUtil.requireValidResult(httpResultJson);

                liepinMappingDao.updateState(downShelfPositonListDb, (byte) 0);

            } catch (BIZException e) {
                log.warn(e.getMessage(), e);
                liepinMappingDao.updateErrMsgBatch(downShelfPositonListDb, e.getMessage());
                throw e;
            } catch (Exception e1) {
                liepinMappingDao.updateErrMsgBatch(downShelfPositonListDb, "后台异常");
                log.error("===============向猎聘请求下架失败，mapping表主键ids:{}===============", ids);
                throw e1;
            }
        }
    }

    /**
     * 数据库中可能存在重复的title，将重复的title去掉
     *
     * @param titleDbList 数据库中的titlelist
     * @return 去重复后的list
     * @author cjm
     * @date 2018/7/2
     */
    private List<String> removeDuplicateTitle(List<String> titleDbList) {
        LinkedHashSet<String> set = new LinkedHashSet<>(titleDbList.size());
        set.addAll(titleDbList);
        titleDbList.clear();
        titleDbList.addAll(set);
        return titleDbList;
    }

    /**
     * 比较编辑前后的职位数据是否发生变化
     *
     * @param jobPositionDO     编辑前的jobPosition实体
     * @param updateJobPosition 编辑后的jobPosition实体
     * @return 返回是否发生变化，true表示变化，false表示没有变化
     * @author cjm
     * @date 2018/7/2
     */
    private boolean compareJobPosition(JobPositionDO jobPositionDO, JobPositionDO updateJobPosition) {
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

    private boolean strEquals(String s1, String s2) {
        if (StringUtils.isBlank(s1) && StringUtils.isBlank(s2)) {
            return true;
        }
        if (StringUtils.isNotBlank(s1) && StringUtils.isNotBlank(s2)) {
            return s1.equals(s2);
        }
        return false;
    }

    private boolean byteEquals(Byte s1, Byte s2) {
        if (null == s1 && null == s2) {
            return true;
        }
        if (null != s1 && null != s2) {
            return s1.equals(s2);
        }
        return false;
    }

    private boolean doubleEquals(Double s1, Double s2) {
        return null == s1 && null == s2 || null != s1 && null != s2 && s1.equals(s2);
    }

    /**
     * 为减少接口访问次数，防止增减个空格就要向猎聘发请求，将部分字段里的空格过滤掉，如果过滤后的字段相同，认为职位信息没有改变
     *
     * @param jobPositionDO jobPosition实体
     * @return jobPositionDO
     * @author cjm
     * @date 2018/7/2
     */
    private JobPositionDO filterBlank(JobPositionDO jobPositionDO) {
        if (StringUtils.isNotBlank(jobPositionDO.getDepartment())) {
            jobPositionDO.setDepartment(jobPositionDO.getDepartment().replaceAll("\\s", ""));
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

    /**
     * 由于这个事务和sendSyncRequest()的事务在一个被事务aop代理的对象里，无法做到当同步失败时回滚数据库的目的，因此暂时将该方法放入rabbitmq的监听类里
     * 优化时考虑将猎聘的四个职位操作分别封装
     *
     * @param syncCityList     需要同步的cityCode集合
     * @param liePinPositionVO 需要同步的猎聘职位实体
     * @param liePinUserId     hr在猎聘的id
     * @param liePinToken      hr在猎聘绑定后返回的token
     * @return 返回成功同步职位的个数
     * @author cjm
     * @date 2018/7/4
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int syncNewPosition(List<String> syncCityList, LiePinPositionVO liePinPositionVO, int liePinUserId, String liePinToken) throws Exception {
        int index = 0;
        // 逐个城市发布职位
        for (String cityCode : syncCityList) {

            String liepinCityCode = liepinSocialPositionTransfer.getValidLiepinDictCode(cityCode);

            // 生成职位发布到猎聘时需要的id,插入一条mapping数据
            JobPositionLiepinMappingDO jobPositionLiepinMappingDO = liepinSocialPositionTransfer.getNewJobPositionLiepinMapping(liePinPositionVO, cityCode, liePinUserId);
            liePinPositionVO.setEjob_extRefid(jobPositionLiepinMappingDO.getId() + "");
            liePinPositionVO.setEjob_dq(liepinCityCode);
            // 截取title
            liepinSocialPositionTransfer.requireValidTitle(liePinPositionVO);
            JSONObject liepinObject = JSONObject.parseObject(JSON.toJSONString(liePinPositionVO));
            String httpResultJson = httpClientUtil.sendRequest2LiePin(liepinObject, liePinToken, LiepinPositionOperateConstant.liepinPositionSync);

            // 验证业务是否成功，不成功就会抛出bizException
            JSONObject syncResult = httpClientUtil.requireValidResult(httpResultJson);

            String data = syncResult.getString("data");
            // 猎聘返回的职位在猎聘的id标识
            String thirdPositionId = data.substring(1, data.length() - 1);

            // 成功发布职位的个数
            index++;

            int id = jobPositionLiepinMappingDO.getId();
            // 默认改为待审核
            liepinMappingDao.updateJobInfoById(id, Integer.parseInt(thirdPositionId), (byte) 2);
        }
        return index;
    }
}
