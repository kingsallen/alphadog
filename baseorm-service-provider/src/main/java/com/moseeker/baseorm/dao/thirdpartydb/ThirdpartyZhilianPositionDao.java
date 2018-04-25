package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.base.IThirdPartyPositionDao;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyZhilianPositionAddress;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyZhilianPositionAddressDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 这个类是用来在智联职位同步保存职位额外字段的，即智联的多个城市
 * 如需操作thirdparty_zhilian_position_address表请使用{@link ThirdpartyZhilianPositionAddressDao}
 */
@Repository
public class ThirdpartyZhilianPositionDao implements IThirdPartyPositionDao<List<ThirdpartyZhilianPositionAddressDO>> {

    @Autowired
    ThirdpartyZhilianPositionAddressDao zhilianPositionAddressDao;

    @Override
    public TwoParam<HrThirdPartyPositionDO, List<ThirdpartyZhilianPositionAddressDO>> getData(HrThirdPartyPositionDO thirdPartyPositionDO) {
        Query query = new Query.QueryBuilder()
                .where(ThirdpartyZhilianPositionAddress.THIRDPARTY_ZHILIAN_POSITION_ADDRESS.PID.getName(), thirdPartyPositionDO.getId()).buildQuery();
        List<ThirdpartyZhilianPositionAddressDO> address = zhilianPositionAddressDao.getDatas(query);
        TwoParam<HrThirdPartyPositionDO, List<ThirdpartyZhilianPositionAddressDO>> result = new TwoParam<>(thirdPartyPositionDO, address);
        return result;
    }

    @Override
    public List<TwoParam<HrThirdPartyPositionDO, List<ThirdpartyZhilianPositionAddressDO>>> getDatas(List<HrThirdPartyPositionDO> list) {
        List<Integer> ids = list.stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());
        Query query = new Query.QueryBuilder().where(new Condition(ThirdpartyZhilianPositionAddress.THIRDPARTY_ZHILIAN_POSITION_ADDRESS.PID.getName(), ids, ValueOp.IN)).buildQuery();

        List<ThirdpartyZhilianPositionAddressDO> zhilianPositionAddress = zhilianPositionAddressDao.getDatas(query);

        List<TwoParam<HrThirdPartyPositionDO, List<ThirdpartyZhilianPositionAddressDO>>> results = new ArrayList<>();

        for (HrThirdPartyPositionDO thirdPartyPositionDO : list) {
            List<ThirdpartyZhilianPositionAddressDO> address = new ArrayList<>();
            for (ThirdpartyZhilianPositionAddressDO a : zhilianPositionAddress) {
                if (thirdPartyPositionDO.getId() == a.getPid()) {
                    address.add(a);
                }
            }
            results.add(new TwoParam<>(thirdPartyPositionDO, address));
        }

        return results;
    }

    @Override
    public TwoParam<HrThirdPartyPositionDO, List<ThirdpartyZhilianPositionAddressDO>> addData(HrThirdPartyPositionDO thirdPartyPositionDO, List<ThirdpartyZhilianPositionAddressDO> thirdpartyZhilianPositionAddressDOS) {
        if (thirdPartyPositionDO == null || thirdPartyPositionDO.getId() == 0) {
            return null;
        }
        thirdpartyZhilianPositionAddressDOS.forEach(a->a.setPid(thirdPartyPositionDO.getId()));
        List<ThirdpartyZhilianPositionAddressDO> address = zhilianPositionAddressDao.addAllData(thirdpartyZhilianPositionAddressDOS);
        return new TwoParam<>(thirdPartyPositionDO, address);
    }

    @Override
    @Transactional
    public int updateExtPosition(List<ThirdpartyZhilianPositionAddressDO> thirdpartyZhilianPositionAddressDOS) {
        int pid = thirdpartyZhilianPositionAddressDOS.get(0).getPid();

        //全删全插入
        zhilianPositionAddressDao.deleteByPid(pid);

        thirdpartyZhilianPositionAddressDOS.forEach(a->a.setPid(pid));
        List<ThirdpartyZhilianPositionAddressDO> result = zhilianPositionAddressDao.addAllData(thirdpartyZhilianPositionAddressDOS);
        return 1;
    }

    @Override
    public int[] updateExtPositions(List<List<ThirdpartyZhilianPositionAddressDO>> list) {

        // 先删除所有pid对应的职位
        List<Integer> pids = list.stream().map(a->a.get(0).getPid()).collect(Collectors.toList());
        zhilianPositionAddressDao.deleteByPids(pids);


        List<ThirdpartyZhilianPositionAddressDO> address = new ArrayList<>();

        list.forEach(address::addAll);

        zhilianPositionAddressDao.addAllData(address);

        int result[] =new int[list.size()];
        Arrays.fill(result,1);
        return result;
    }

    @Override
    public List<ThirdpartyZhilianPositionAddressDO> setId(HrThirdPartyPositionDO thirdPartyPositionDO, List<ThirdpartyZhilianPositionAddressDO> thirdpartyZhilianPositionAddressDOS) {
        thirdpartyZhilianPositionAddressDOS.forEach(a->a.setPid(thirdPartyPositionDO.getId()));
        return thirdpartyZhilianPositionAddressDOS;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }
}
