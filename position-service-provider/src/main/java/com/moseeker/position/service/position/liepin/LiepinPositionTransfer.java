package com.moseeker.position.service.position.liepin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.fundationbs.PositionQxService;
import com.moseeker.position.service.position.DegreeChangeUtil;
import com.moseeker.position.service.position.WorkTypeChangeUtil;
import com.moseeker.position.service.position.base.sync.AbstractPositionTransfer;
import com.moseeker.position.service.position.liepin.pojo.PositionLiepin;
import com.moseeker.position.service.position.liepin.pojo.PositionLiepinWithAccount;
import com.moseeker.position.service.position.qianxun.Degree;
import com.moseeker.common.constants.WorkType;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public abstract class LiepinPositionTransfer<R,INFO> extends AbstractPositionTransfer<ThirdPartyPosition,R,INFO,EmptyExtThirdPartyPosition> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected R createAndInitAccountInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB, HrThirdPartyAccountDO account) {
        return null;
    }

    @Override
    protected INFO createAndInitPositionInfo(ThirdPartyPosition positionForm, JobPositionDO positionDB) throws Exception {
        return null;
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.LIEPIN;
    }

    @Override
    public Class<ThirdPartyPosition> getFormClass() {
        return ThirdPartyPosition.class;
    }

    @Override
    public EmptyExtThirdPartyPosition toExtThirdPartyPosition(ThirdPartyPosition position, R r) {
        return EmptyExtThirdPartyPosition.EMPTY;
    }

    @Override
    public EmptyExtThirdPartyPosition toExtThirdPartyPosition(Map<String, String> data) {
        return EmptyExtThirdPartyPosition.EMPTY;
    }

    @Override
    public JSONObject toThirdPartyPositionForm(HrThirdPartyPositionDO thirdPartyPosition, EmptyExtThirdPartyPosition extPosition) {
        return JSONObject.parseObject(JSON.toJSONString(thirdPartyPosition));
    }
}
