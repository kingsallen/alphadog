package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.alibaba.fastjson.JSON;
import com.moseeker.entity.pojos.Address;
import com.moseeker.entity.pojos.City;
import com.moseeker.entity.pojos.Data;
import com.moseeker.entity.pojos.ThirdPartyAccountExt;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.pojo.BindResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

/**
 * Created by jack on 28/09/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ThirdPartyAccountMessageHandlerTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void bindAccountQueue() throws Exception {

        BindResult result = new BindResult();
        result.setStatus(0);
        result.setMessage("success");

        BindResult.Account account = result.createAccount();
        account.setAccountId(8);

        result.setData(account);
        String str = JSON.toJSONString(result);
        amqpTemplate.send("chaos.bind.response.exchange", "chaos.bind.response", MessageBuilder.withBody(str.getBytes()).build());
    }

    @Test
    public void bindAccountQueue1() throws Exception {

        BindResult result = new BindResult();
        result.setStatus(1);
        result.setMessage("错误测试");

        BindResult.Account account = result.createAccount();
        account.setAccountId(8);

        result.setData(account);
        String str = JSON.toJSONString(result);
        amqpTemplate.send("chaos.bind.response.exchange", "chaos.bind.response", MessageBuilder.withBody(str.getBytes()).build());
    }

    @Test
    public void presetHandler() throws Exception {
        ThirdPartyAccountExt accountExt = new ThirdPartyAccountExt();
        accountExt.setStatus(0);
        accountExt.setMessage("success");

        Data data = new Data();
        data.setAccountId(8);
        data.setOperationType(1);

        Address address = new Address();
        address.setAddress("乐山路33号");
        address.setCity("上海市");
        address.setCode("071100");
        address.setValue(461827);
        data.setAddresses(new ArrayList<Address>(){{add(address);}});

        City city = new City();
        city.setAmount(148);
        city.setArea("全国");
        city.setExpiryDate("2018-09-01");
        city.setJobType("社会职位");
        data.setCities(new ArrayList<City>(){{add(city);}});

        data.setCompanies(new ArrayList<String>(){{add("company1");add("company2");}});

        data.setDepartments(new ArrayList<String>(){{add("department1");add("department2");}});

        accountExt.setData(data);

        String str = JSON.toJSONString(accountExt);
        amqpTemplate.send("chaos.preset.response.exchange", "chaos.preset.response", MessageBuilder.withBody(str.getBytes()).build());
    }

    @Test
    public void testJson() {

        String json = "{\"data\":{\"accountId\":8,\"addresses\":[{\"address\":\"乐山路33号\",\"city\":\"上海市\",\"code\":\"071100\",\"value\":461827}],\"cities\":[{\"amout\":148,\"area\":\"全国\",\"expiryDate\":\"2018-09-01\",\"jobType\":\"社会招聘\"}],\"companies\":[\"company1\",\"company2\"],\"departments\":[\"department1\",\"department2\"],\"operationType\":1},\"message\":\"success\",\"status\":0}";

        try {
            ThirdPartyAccountExt accountExt = JSON.parseObject(json, ThirdPartyAccountExt.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}