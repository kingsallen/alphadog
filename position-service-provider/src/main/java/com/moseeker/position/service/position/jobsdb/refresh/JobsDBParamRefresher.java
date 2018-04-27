package com.moseeker.position.service.position.jobsdb.refresh;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import com.moseeker.position.service.position.jobsdb.refresh.handler.JobsDBResultHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobsDBParamRefresher extends AbstractRabbitMQParamRefresher {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private List<JobsDBResultHandlerAdapter> refreshList;

    //因为JobsDB environ的参数在发布职位第二页，所以需要给他传一部分值，让他填写完第一页，再跳转到第二页
    //实话，我不知道为什么这一步也要基础服务来做，本来这个environ对接传账号密码，感觉就已经不合理了
    private static final String POSITION_INFO =
            "{" +
            "  'job_title': 'kantoku'," +
            "  'job_details': 'kantoku'," +
            "  'email': 'daqi@moseeker.com'," +
            "  'summary_points': [" +
            "    'first'," +
            "    'second'," +
            "    'third'" +
            "  ]" +
            "}";

    @Override
    public void addSendParam(JSONObject jsonSend) {
        JSONObject positionInfo= JSON.parseObject(POSITION_INFO);

        jsonSend.put("position_info",positionInfo);
    }

    @Override
    public void receiveAndHandle(String json) {
        //调用所有处理策略
        refreshList.forEach(r->r.handle(json));
    }

    @Override
    public void addUserParam(JSONObject jsonSend) {
        jsonSend.put("user_name",getConfig("jobsdb.username"));
        jsonSend.put("password",getConfig("jobsdb.password"));


    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOBSDB;
    }

}
