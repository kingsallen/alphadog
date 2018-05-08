package com.moseeker.position.service.position.jobsdb.refresh.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.dao.dictdb.DictJobsDBOccupationDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.base.refresh.handler.AbstractOccupationResultHandler;
import com.moseeker.position.service.position.base.refresh.handler.DefaultOccupationResultHandler;
import com.moseeker.position.utils.PositionParamRefreshUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictJobsDBOccupationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class JobsDBOccupationResultHandler extends DefaultOccupationResultHandler<DictJobsDBOccupationDO> implements JobsDBResultHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(JobsDBOccupationResultHandler.class);

    @Override
    protected List<Occupation> toList(JSONObject msg) {
        TypeReference<List<JobFunction>> typeRef
                = new TypeReference<List<JobFunction>>() {
        };

        List<JobFunction> occupations = JSON.parseObject(msg.getString(occupationKey()), typeRef);

        List<Occupation> result = new ArrayList<>();

        for (JobFunction jobFunction : occupations) {
            recursiveOccupation(jobFunction, result, null);
        }

        return result;
    }

    /**
     * 递归生成occupation
     *
     * @param jobFunction
     * @param result
     */
    private void recursiveOccupation(JobFunction jobFunction, List<Occupation> result, Occupation parent) {
        Occupation thisOccupation = new Occupation();
        thisOccupation.setCode(new ArrayList<>());
        thisOccupation.setText(new ArrayList<>());
        if (parent != null) {
            thisOccupation.getCode().addAll(parent.getCode());
            thisOccupation.getText().addAll(parent.getText());
        }

        thisOccupation.getCode().add(String.valueOf(jobFunction.getId()));
        thisOccupation.getText().add(jobFunction.getName());

        result.add(thisOccupation);

        if (!StringUtils.isEmptyList(jobFunction.getChildren())) {
            for (JobFunction childJobFunction : jobFunction.getChildren()) {
                recursiveOccupation(childJobFunction, result, thisOccupation);
            }
        }
    }

    @Override
    protected String occupationKey() {
        return "job_functions";
    }

    @Override
    protected Class<DictJobsDBOccupationDO> getOccupationClass() {
        return DictJobsDBOccupationDO.class;
    }

    private static class JobFunction {
        private int id;
        private String name;
        private List<JobFunction> Children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<JobFunction> getChildren() {
            return Children;
        }

        public void setChildren(List<JobFunction> children) {
            Children = children;
        }
    }
}
