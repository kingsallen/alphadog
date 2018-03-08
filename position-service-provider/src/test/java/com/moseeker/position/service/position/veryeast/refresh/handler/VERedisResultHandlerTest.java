package com.moseeker.position.service.position.veryeast.refresh.handler;

import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.position.base.refresh.handler.ResultHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class VERedisResultHandlerTest {

    @Resource(type = VERedisResultHandler.class)
    ResultHandler veRedisResultHandler;

    @Test
    public void test(){
        String redis="{\"data\":{\n" +
                "  \"languageType\": [\n" +
                "    {\n" +
                "      \"code\": \"11\",\n" +
                "      \"text\": \"中国普通话\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"12\",\n" +
                "      \"text\": \"粤语\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"13\",\n" +
                "      \"text\": \"上海话\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"14\",\n" +
                "      \"text\": \"闽南话\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"16\",\n" +
                "      \"text\": \"北方方言\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"17\",\n" +
                "      \"text\": \"吴方言\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"18\",\n" +
                "      \"text\": \"湘方言\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"19\",\n" +
                "      \"text\": \"赣方言\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"20\",\n" +
                "      \"text\": \"客家方言\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1\",\n" +
                "      \"text\": \"英语\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2\",\n" +
                "      \"text\": \"日语\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3\",\n" +
                "      \"text\": \"法语\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4\",\n" +
                "      \"text\": \"德语\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5\",\n" +
                "      \"text\": \"俄语\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6\",\n" +
                "      \"text\": \"西班牙语\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"7\",\n" +
                "      \"text\": \"韩语\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"8\",\n" +
                "      \"text\": \"阿拉伯语\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"9\",\n" +
                "      \"text\": \"葡萄牙语\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"10\",\n" +
                "      \"text\": \"意大利语\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"15\",\n" +
                "      \"text\": \"其它\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"workMode\": [\n" +
                "    {\n" +
                "      \"code\": \"1\",\n" +
                "      \"text\": \"全职\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2\",\n" +
                "      \"text\": \"兼职\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3\",\n" +
                "      \"text\": \"实习\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4\",\n" +
                "      \"text\": \"临时\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"computerLevel\": [\n" +
                "    {\n" +
                "      \"code\": \"0\",\n" +
                "      \"text\": \"不限\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1\",\n" +
                "      \"text\": \"较差\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2\",\n" +
                "      \"text\": \"一般\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3\",\n" +
                "      \"text\": \"良好\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4\",\n" +
                "      \"text\": \"熟练\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5\",\n" +
                "      \"text\": \"精通\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"languageLevel\": [\n" +
                "    {\n" +
                "      \"code\": \"1\",\n" +
                "      \"text\": \"较差\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2\",\n" +
                "      \"text\": \"一般\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3\",\n" +
                "      \"text\": \"良好\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4\",\n" +
                "      \"text\": \"流利\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5\",\n" +
                "      \"text\": \"精通\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"accommodation\": [\n" +
                "    {\n" +
                "      \"code\": \"1\",\n" +
                "      \"text\": \"提供\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2\",\n" +
                "      \"text\": \"不提供\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3\",\n" +
                "      \"text\": \"可提供吃\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4\",\n" +
                "      \"text\": \"可提供住\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5\",\n" +
                "      \"text\": \"面议\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"degree\": [\n" +
                "    {\n" +
                "      \"code\": \"0\",\n" +
                "      \"text\": \"不限\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1\",\n" +
                "      \"text\": \"初中\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2\",\n" +
                "      \"text\": \"高中 \"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"3\",\n" +
                "      \"text\": \"中技 \"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"4\",\n" +
                "      \"text\": \"中专 \"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"5\",\n" +
                "      \"text\": \"大专\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"6\",\n" +
                "      \"text\": \"本科\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"7\",\n" +
                "      \"text\": \"硕士\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"8\",\n" +
                "      \"text\": \"博士\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"indate\": [\n" +
                "    {\n" +
                "      \"code\": \"7\",\n" +
                "      \"text\": \"7天\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"15\",\n" +
                "      \"text\": \"15天\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"30\",\n" +
                "      \"text\": \"30天\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"60\",\n" +
                "      \"text\": \"60天\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"salary\": [\n" +
                "    {\n" +
                "      \"code\": \"0\",\n" +
                "      \"text\": \"面议\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"1\",\n" +
                "      \"text\": \"1000以下\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"code\": \"2\",\n" +
                "      \"text\": \"1001~2000\"\n" +
                "    }\n" +
                "  ]\n" +
                "}}";
        veRedisResultHandler.handle(redis);
    }
}