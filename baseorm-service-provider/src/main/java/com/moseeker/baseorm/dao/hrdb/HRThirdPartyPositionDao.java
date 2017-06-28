package com.moseeker.baseorm.dao.hrdb;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyPositionRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import org.joda.time.DateTime;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public Response upsertThirdPartyPositions(List<HrThirdPartyPositionDO> positions) {
        if (positions == null || positions.size() == 0) return ResponseUtils.success(null);
        logger.info("companyDao upsertThirdPartyPositions" + JSON.toJSONString(positions));
        positions.forEach(position -> {
            upsertThirdPartyPosition(position);
        });
        return ResponseUtils.success(null);

    }

    /**
     * 如果第三方职位数据存在，则修改，否则添加
     *
     * @param position
     * @return
     */
    public int upsertThirdPartyPosition(HrThirdPartyPositionDO position) {
        try {
            Query query = new Query.QueryBuilder()
                    .where("third_party_account_id", position.getThirdPartyAccountId())
                    .and("position_id", position.getPositionId())
                    .buildQuery();
            HrThirdPartyPositionDO thirdPartyPosition = getData(query);
            if (thirdPartyPosition == null) {
                logger.info("添加一个第三方职位:channel:{},positionId:{}", position.getChannel(), position.getPositionId());
                addData(position);
                return 1;
            } else {
                logger.info("更新一个第三方职位:channel:{},positionId:{}", position.getChannel(), position.getPositionId());
                position.setId(thirdPartyPosition.getId());

                int updateResult = updateData(position);

                if (updateResult < 1) {
                    logger.error("更新第三方职位失败:{}", JSON.toJSONString(position));
                }

                return updateResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            logger.error("添加第三方职位同步失败:{}", JSON.toJSONString(position));
            return 0;
        }
    }
}
