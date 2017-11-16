package com.moseeker.useraccounts;

import com.alibaba.fastjson.JSON;
import com.moseeker.entity.pojos.ThirdPartyAccountExt;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.service.thirdpartyaccount.ThirdPartyAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ThirdPartyAccountMessageHandlerTest {

    @Autowired
    ThirdPartyAccountService thirdPartyAccountService;

    @Test
    @Commit
    public void test() throws UnsupportedEncodingException {
        String msgBody=
                "{data:{\"accountId\":230,\"departments\":[\"Admin\",\"BIE\",\"CHC\",\"CNS\",\"Commercial\",\"Commercial &Emerging business\",\"Commercial Excellence\",\"Communication\",\"CV\",\"财务\",\"DBU\",\"法务部\",\"Genzyme\",\"Gx & Outsource\",\"核心产品事业部\",\"IA\",\"IS\",\"基层医疗事业部\",\"KA\",\"Maketing\",\"ONCO\",\"PAMA\",\"Pasteur 巴斯德\",\"PC\",\"Pharma\",\"Procurement 采购\",\"R&D\",\"人力资源\",\"Sales\",\"Specialty Care\",\"Strategic Marketing Department\",\"Support Functions\",\"赛诺菲美华美\",\"TBA\",\"特药事业部\",\"销售部\",\"行政\",\"医学部\",\"招投标\",\"质量\",\"注册\"],\"operationType\":2}}";

        ThirdPartyAccountExt accountExt = JSON.parseObject(msgBody, ThirdPartyAccountExt.class);
        thirdPartyAccountService.thirdPartyAccountExtHandler(accountExt);
    }
}
