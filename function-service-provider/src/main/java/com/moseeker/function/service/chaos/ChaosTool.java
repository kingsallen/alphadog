package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;

import java.util.HashMap;
import java.util.Map;

import static com.moseeker.common.constants.ChannelType.JOB51;

/**
 * chaos帮助类
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Nov 8, 2016
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 *
 * @author wjf
 */
public class ChaosTool {

    /**
     * 封装同步帐号和刷新帐号的JSON
     *
     * @param accountDO
     * @param extras
     * @return
     */
    public static String getParams(HrThirdPartyAccountDO accountDO, Map<String, ? extends Object> extras) {
        Map<String, Object> pramas = new HashMap<>();
        pramas.put("account_id",accountDO.getId());
        pramas.put("id",accountDO.getId());
        pramas.put("user_name", accountDO.getUsername());
        pramas.put("password", accountDO.getPassword());
        pramas.put("channel", accountDO.getChannel());

        if (accountDO.getChannel() == ChannelType.JOB51.getValue()) {
            pramas.put("member_name", accountDO.getMembername());
        }

        if (extras != null) {
            pramas.putAll(extras);
        }
        return JSON.toJSONString(pramas);
    }
}
