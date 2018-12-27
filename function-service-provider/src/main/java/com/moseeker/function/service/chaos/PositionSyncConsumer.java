package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountHrRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.annotation.iface.CounterIface;
import static com.moseeker.common.constants.Constant.POSITION_SYNC_FAIL_ROUTINGKEY;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 监听同步完成队列
 *
 * @author wjf
 */
@Component
public class PositionSyncConsumer  {

    private static Logger logger = LoggerFactory.getLogger(PositionSyncConsumer.class);

    @Autowired
    JobPositionDao positionDao;

    @Autowired
    HRThirdPartyPositionDao thirdpartyPositionDao;

    @Autowired
    HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    PositionSyncFailedNotification syncFailedNotification;

    private static final String MESSAGE_TEMPLATE_EXCHANGE = "message_template_exchange";




    /**
     * 同步信息回写到数据库
     *
     * @param pojo
     */
    @CounterIface
    public void positionSyncComplete(PositionForSyncResultPojo pojo) throws BIZException {

        if (pojo == null) return;

        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();
        data.setChannel(Byte.valueOf(pojo.getData().getChannel()));
        data.setPositionId(Integer.valueOf(pojo.getData().getPositionId()));
        data.setThirdPartPositionId(pojo.getData().getJobId());
        data.setThirdPartyAccountId(pojo.getData().getAccountId());
        if (pojo.getStatus() == 0) {
            data.setIsSynchronization((byte) PositionSync.bound.getValue());
        } else if (pojo.getStatus() == 2) {
            //只会出现在猎聘的情况，这种情况下面会发送邮件
            data.setIsSynchronization((byte) PositionSync.bindingError.getValue());
        } else {
            data.setIsSynchronization((byte) PositionSync.failed.getValue());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("positionId", Integer.valueOf(pojo.getData().getPositionId()));
            if(!StringUtils.isEmptyList(pojo.getMessage())) {
                String str = StringEscapeUtils.unescapeJava(pojo.getMessage().get(0));
                JSONArray array = JSONArray.parseArray(str);
                if (array!= null && array.size()>0) {
                    Object[] strs = array.stream().map(m -> m.toString()).toArray();
                    jsonObject.put("message", strs[0]);
                }
            }
            jsonObject.put("channal", pojo.getData().getChannel());
            amqpTemplate.sendAndReceive(MESSAGE_TEMPLATE_EXCHANGE,
                    POSITION_SYNC_FAIL_ROUTINGKEY, MessageBuilder.withBody(jsonObject.toJSONString().getBytes())
                            .build());
        }

        data.setSyncFailReason(JSON.toJSONString(pojo.getMessage()));

        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("id", pojo.getData().getPositionId());
        JobPositionDO positionDO = positionDao.getData(qu.buildQuery());
        if (positionDO == null || positionDO.getId() < 1) {
            logger.warn("同步完成队列中包含不存在的职位:{}", pojo.getData().getPositionId());
            return;
        }

        HrThirdPartyPositionDO dbData=thirdpartyPositionDao.getBindingData(data.getPositionId(),data.getThirdPartyAccountId());
        if(dbData==null) {
            logger.info(ConstantErrorCodeMessage.POSITION_SYNC_NOT_FIND_THIRD_PARTY_POSITION+":{}",JSON.toJSONString(pojo));
            syncFailedNotification.sendHandlerFailureMail(JSON.toJSONString(pojo),ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_SYNC_NOT_FIND_THIRD_PARTY_POSITION));
        }else{  //只有正在绑定的数据才更新同步状态
            data.setId(dbData.getId());

            TwoParam<HrThirdPartyPositionDO,Object> thirdPartyPositionDO = new TwoParam<>(data, EmptyExtThirdPartyPosition.EMPTY);
            try {
                thirdPartyPositionDO = thirdpartyPositionDao.upsertThirdPartyPosition(thirdPartyPositionDO);
            } catch (BIZException e) {
                e.printStackTrace();
                logger.error("读取职位同步队列后无法更新到数据库:{}", JSON.toJSONString(data));
                throw e;
            }

            if (pojo.getStatus() == 2) {
                logger.info("发送同步失败的邮件:{}", pojo.getStatus());
                try {
                    //发送邮件，表示这个职位无法判断是否成功同步到对应的平台，需要确认一下。
                    syncFailedNotification.sendUnKnowResultMail(positionDO, thirdPartyPositionDO.getR1(),thirdPartyPositionDO.getR2(), pojo);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                    throw e;
                }
            }

            if (pojo.getStatus() == 0) {
                HrThirdPartyAccountDO thirdPartyAccount = new HrThirdPartyAccountDO();
                thirdPartyAccount.setChannel(Short.valueOf(pojo.getData().getChannel().trim()));
                thirdPartyAccount.setId(pojo.getData().getAccountId());
                logger.info("同步完成队列中更新第三方帐号信息:{}", JSON.toJSONString(thirdPartyAccount));
                thirdPartyAccountDao.updateData(thirdPartyAccount);
            }
        }


    }

}
