package com.moseeker.position.service.position.carnoc.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictCarnocOccupationDao;
import com.moseeker.position.service.position.base.refresh.handler.AbstractOccupationResultHandler;
import com.moseeker.position.service.position.base.refresh.handler.DefaultOccupationResultHandler;
import com.moseeker.position.service.position.jobsdb.refresh.handler.JobsDBOccupationResultHandler;
import com.moseeker.position.utils.PositionParamRefreshUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCarnocOccupationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CarnocOccupationResultHandler extends DefaultOccupationResultHandler<DictCarnocOccupationDO> implements CarnocResultHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(CarnocOccupationResultHandler.class);

    @Override
    protected List<Occupation> toList(JSONObject msg) {
        // 复用jobsdb的处理策略
        JobsdbHandleStrategy jobsdbHandleStrategy = new JobsdbHandleStrategy();
        return jobsdbHandleStrategy.toList(msg);
    }

    @Override
    protected String occupationKey() {
        return "functions";
    }

    /**
     * 复用jobsdb的处理策略，因为民航传过来的职能和jobsdb的职能格式相同
     */
    private static class JobsdbHandleStrategy extends JobsDBOccupationResultHandler{
        /**
         * 放大访问权限，否则不能调用protected
         * @param msg
         * @return
         */
        @Override
        public List<Occupation> toList(JSONObject msg) {
            return super.toList(msg);
        }
    }
}
