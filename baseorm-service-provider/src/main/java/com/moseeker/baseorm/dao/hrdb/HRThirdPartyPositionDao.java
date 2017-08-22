package com.moseeker.baseorm.dao.hrdb;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyPositionRecord;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * HR帐号数据库持久类
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Nov 9, 2016
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 *
 * @author wjf
 */
@Service
public class HRThirdPartyPositionDao extends JooqCrudImpl<HrThirdPartyPositionDO, HrThirdPartyPositionRecord> {

    public HRThirdPartyPositionDao() {
        super(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION, HrThirdPartyPositionDO.class);
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public HRThirdPartyPositionDao(TableImpl<HrThirdPartyPositionRecord> table, Class<HrThirdPartyPositionDO> hrThirdPartyPositionDOClass) {
        super(table, hrThirdPartyPositionDOClass);
    }

    public HrThirdPartyPositionDO getThirdPositionById(int id) {
        Query query = new Query.QueryBuilder().where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.ID.getName(), id).buildQuery();
        return getData(query);
    }

    /**
     * 获取第三方职位数据
     *
     * @param positionId 职位编号
     *                   渠道号
     * @return
     */
    public HrThirdPartyPositionDO getThirdPartyPosition(int positionId, int accountId) {
        HrThirdPartyPositionDO position = new HrThirdPartyPositionDO();
        if (positionId > 0 && accountId > 0) {
            HrThirdPartyPositionRecord record = create.selectFrom(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION)
                    .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID
                            .eq(Integer.valueOf(positionId)))
                    .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.THIRD_PARTY_ACCOUNT_ID.eq(accountId)).fetchOne();
            if (record != null) {
                record.into(position);
                position.setUpdateTime(sdf.format(record.getUpdateTime()));
                if (record.getSyncTime() != null) {
                    position.setSyncTime(sdf.format(record.getSyncTime()));
                }
                if (record.getRefreshTime() != null) {
                    position.setRefreshTime(sdf.format(record.getRefreshTime()));
                }
            }
        }
        return position;
    }


    /**
     * 批量插入或更新第三方职能
     *
     * @param positions
     * @return
     */
    @Transactional
    public Response upsertThirdPartyPositions(List<HrThirdPartyPositionDO> positions) throws BIZException {
        if (positions == null || positions.size() == 0) return ResponseUtils.success(null);
        logger.info("companyDao upsertThirdPartyPositions" + JSON.toJSONString(positions));
        for (HrThirdPartyPositionDO thirdPartyPositionDO : positions) {
            upsertThirdPartyPosition(thirdPartyPositionDO);
        }
        return ResponseUtils.success(null);

    }

    /**
     * 如果第三方职位数据存在，则修改，否则添加
     *
     * @param thirdPartyPositionDO
     * @return
     */
    public HrThirdPartyPositionDO upsertThirdPartyPosition(HrThirdPartyPositionDO thirdPartyPositionDO) throws BIZException {
        Query query = new Query.QueryBuilder()
                .where("third_party_account_id", thirdPartyPositionDO.getThirdPartyAccountId())
                .and("position_id", thirdPartyPositionDO.getPositionId())
                .buildQuery();
        HrThirdPartyPositionDO thirdPartyPosition = getData(query);
        if (thirdPartyPosition == null) {
            logger.info("添加一个第三方职位:channel:{},positionId:{}", thirdPartyPositionDO.getChannel(), thirdPartyPositionDO.getPositionId());
            return addData(thirdPartyPositionDO);
        } else {
            logger.info("更新一个第三方职位:channel:{},positionId:{}", thirdPartyPositionDO.getChannel(), thirdPartyPositionDO.getPositionId());
            thirdPartyPositionDO.setId(thirdPartyPosition.getId());

            int updateResult = updateData(thirdPartyPositionDO);

            if (updateResult < 1) {
                logger.error("更新第三方职位失败:{}", JSON.toJSONString(thirdPartyPositionDO));
                throw new BIZException(-1, "更新状态时发生了错误，请重试！");
            }
            query = new Query.QueryBuilder().where("id", thirdPartyPosition.getId()).buildQuery();
            thirdPartyPosition = getData(query);
            return thirdPartyPosition;
        }
    }
}
