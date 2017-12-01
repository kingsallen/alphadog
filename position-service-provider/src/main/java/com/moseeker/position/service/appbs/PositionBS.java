package com.moseeker.position.service.appbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.PositionRefreshType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Query.QueryBuilder;
import com.moseeker.position.constants.ResultMessage;
import com.moseeker.position.pojo.PositionSyncResultPojo;
import com.moseeker.position.service.position.PositionChangeUtil;
import com.moseeker.position.thrift.PositionServicesImpl;
import com.moseeker.position.utils.PositionSyncHandler;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
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
    private PositionServicesImpl positionServices;
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private HRThirdPartyAccountDao hRThirdPartyAccountDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;
    @Autowired
    private PositionSyncHandler positionSyncHandler;
    @Autowired
    private PositionChangeUtil positionChangeUtil;

    /**
     * @param position
     * @return
     */
    @CounterIface
    public Response synchronizePositionToThirdPartyPlatform(ThirdPartyPositionForm position) throws Exception {
        logger.info("synchronizePositionToThirdPartyPlatform:" + JSON.toJSONString(position));
        // 职位数据是否存在
        JobPositionDO moseekerJobPosition = positionSyncHandler.getAvailableMoSeekerPosition(position.getPositionId());

        // 返回结果
        List<PositionSyncResultPojo> results = new ArrayList<>();

        //第三方职位列表，用来回写写到第三方职位表
        List<HrThirdPartyPositionDO> writeBackThirdPartyPositionList = new ArrayList<>();

        //用来同步到chaos的职位列表
        List<String>  positionsForSynchronizations=new ArrayList<>();

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
            // 转成第三方渠道职位
            JSONObject pos = positionChangeUtil.changeToThirdPartyPosition(p, moseekerJobPosition,avaliableAccount);

            positionsForSynchronizations.add(pos.toJSONString());

            writeBackThirdPartyPositionList.add(positionSyncHandler.createHrThirdPartyPositionDO(pos,p));

            results.add(positionSyncHandler.createNormalResult(channel,avaliableAccount.getId()));
        }

        // 提交到chaos处理
        logger.info("chaosService.synchronizePosition:{}", positionsForSynchronizations);
        chaosService.synchronizePosition(positionsForSynchronizations);

        // 回写数据到第三方职位表表
        logger.info("write back to thirdpartyposition:{}",writeBackThirdPartyPositionList);
        thirdPartyPositionDao.upsertThirdPartyPositions(writeBackThirdPartyPositionList);

        //回写薪资到MoSeeker职位表
        positionSyncHandler.writeBackJobPositionField(moseekerJobPosition,positionsForSynchronizations);

        return ResultMessage.SUCCESS.toResponse(results);
    }

    /**
     * 对使用公司地址的职位设置公司地址
     *
     * @param channels  渠道职位
     * @param companyId 公司编号
     */

    private void setAddressByCompanyAddress(List<ThirdPartyPosition> channels, int companyId) {
        boolean useCompanyAddress = false;
        for (ThirdPartyPosition channel : channels) {
            if (channel.isUseCompanyAddress()) {
                useCompanyAddress = true;
                break;
            }
        }
        if (useCompanyAddress) {
            Query query = new QueryBuilder().where("id", companyId).buildQuery();
            HrCompanyDO company = hrCompanyDao.getData(query, HrCompanyDO.class);
            for (ThirdPartyPosition channel : channels) {
                if (channel.isUseCompanyAddress()) {
                    channel.setAddressId(company.getId());
                    channel.setAddressName(company.getAddress());
                }
            }
        }
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

        HrThirdPartyPositionDO p = new HrThirdPartyPositionDO();
        p.setChannel(channel);
        p.setPositionId(positionId);
        p.setIsRefresh((byte) PositionRefreshType.refreshing.getValue());
        p.setRefreshTime((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));

        thirdPartyPositionDao.upsertThirdPartyPosition(p);
        result.put("is_refresh", PositionRefreshType.refreshing.getValue());
        result.put("sync_time", p.getSyncTime());
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
