package com.moseeker.baseorm.base;

import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;

import java.util.List;

public interface IThirdPartyPositionDao<P> extends IChannelType {

    /**
     * 根据传入的第三方职位主表数据，只查附表，传入的主表已经是查询出来了
     * 查询出不同渠道的对应的职位附表数据
     * 比如最加东方是hr_third_party_position + thirdparty_veryeast_position,即{@link HrThirdPartyPositionDO} + {@link com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyVeryEastPositionDO}
     * 拼接成完整的职位数据返回
     *
     * 不要问我为啥这么设计，当然也可以考虑拆表~~~
     *
     * @param thirdPartyPositionDO 第三方职位主表数据
     * @return 完整的职位数据
     */
    TwoParam<HrThirdPartyPositionDO,P> getData(HrThirdPartyPositionDO thirdPartyPositionDO);

    /**
     * 批量获取完整的职位数据
     * @param list 第三方职位主表数据
     * @return 完整的职位数据
     */
    List<TwoParam<HrThirdPartyPositionDO,P>> getDatas(List<HrThirdPartyPositionDO> list);

    /**
     * 添加第三方职位-附表数据
     * @param thirdPartyPositionDO 第三方职位-主表数据
     * @param p 第三方职位-附表数据
     * @return 添加成功以后，完整的职位数据
     */
    TwoParam<HrThirdPartyPositionDO,P> addData(HrThirdPartyPositionDO thirdPartyPositionDO,P p);

    /**
     * 更新第三方职位-附表数据
     * @param p 第三方职位-附表数据
     * @return 更新条数
     */
    int updateExtPosition(P p);

    /**
     * 批量更新第三方职位-附表数据
     * @param list 第三方职位-附表数据
     * @return 更新结果
     */
    int[] updateExtPositions(List<P> list);

    /**
     * 设置第三方职位-附表数据更新时候依赖的数据库键，一般是ID，
     * 但是智联是PID，因为智联有多条附表数据thirdparty_zhilian_position_address
     * @param thirdPartyPositionDO
     * @param p
     * @return
     */
    P setId(HrThirdPartyPositionDO thirdPartyPositionDO,P p);

    /**
     * 判断是否为空职位数据，只是一种抽象，以防将来对这个职位是否为空的判断变化了
     * @param thirdPartyPositionDO
     * @return
     */
    default boolean isEmptyPosition(HrThirdPartyPositionDO thirdPartyPositionDO){
        return thirdPartyPositionDO==null || thirdPartyPositionDO.getId()==0;
    }

    /**
     * 判断是否为空第三方职位-附表数据，只是一种抽象，以防将来对这个判断有变化
     * @param list
     * @param <E>
     * @return
     */
    default <E> boolean isEmptyList(List<E> list){
        return list==null || list.isEmpty();
    }
}
