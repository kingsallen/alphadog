package com.moseeker.mq.config;

import com.rabbitmq.client.ConnectionFactory;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * Created by lucky8987 on 17/5/11.
 */
@Configuration
@EnableRabbit
@ComponentScan({"com.moseeker.mq", "com.moseeker.common.aop.iface", "com.moseeker.entity"})
@PropertySource("classpath:common.properties")
@Import(com.moseeker.baseorm.config.AppConfig.class)
public class AppConfig {

    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Autowired
    private Environment env;

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost(env.getProperty("rabbitmq.host").trim());
        cf.setPort(Integer.valueOf(env.getProperty("rabbitmq.port").trim()));
        cf.setUsername(env.getProperty("rabbitmq.username").trim());
        cf.setPassword(env.getProperty("rabbitmq.password").trim());
        return cf;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory());
        RetryTemplate retryTemplate = new RetryTemplate();
        // 重试机制
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        rabbitTemplate.setRetryTemplate(retryTemplate);
        return rabbitTemplate;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(connectionFactory());
    }


    @Bean
    public AmqpAdmin amqpAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(cachingConnectionFactory());
        rabbitAdmin.setIgnoreDeclarationExceptions(true);
        return rabbitAdmin;
    }

    /**
     * listener 容器 （consumer 需要手动确认消息）
     * @return
     */
    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory listenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(cachingConnectionFactory());
        // 设置手动 ACK
        listenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return listenerContainerFactory;
    }

    /**
     * listener 容器 （AcknowledgeMode：auto）
     * @return
     */
    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactoryAutoAck() {
        SimpleRabbitListenerContainerFactory listenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(cachingConnectionFactory());
        return listenerContainerFactory;
    }


    @Bean
    public Queue addAwardQue() {
        Queue queue = new Queue("add_award_que", true, false, false);
        return queue;
    }

    @Bean
    public Queue sendTemplateQue() {
        Queue queue = new Queue("send_template_que", true, false, false);
        return queue;
    }
    //数据组推送职位队列
    @Bean
    public Queue personaRecomQue() {
        Queue queue = new Queue("persona_recom_que", true, false, false);
        return queue;
    }

    @Bean
    public Queue sendSeekReferralTemplateQueue() {
        Queue queue = new Queue("seek_referral_template_queue", true, false, false);
        return queue;
    }


    @Bean
    public Queue employeeFirstRegisterQueue() {
        Queue queue = new Queue("employee_first_register_exchange", true, false, false);
        return queue;
    }

    @Bean
    public TopicExchange employeeRegisterExchange() {
        TopicExchange topicExchange = new TopicExchange("employee_register_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public TopicExchange seekReferralTemplateExchange() {
        TopicExchange topicExchange = new TopicExchange("referral_seek_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public TopicExchange redpacketTemplateExchange() {
        TopicExchange topicExchange = new TopicExchange("redpacket_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public TopicExchange templateExchange() {
        TopicExchange topicExchange = new TopicExchange("message_template_exchange", true, false);
        return topicExchange;
    }
    @Bean
    public TopicExchange personaRecomExchange() {
        TopicExchange topicExchange = new TopicExchange("person_recom_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public TopicExchange topicExchange() {
        TopicExchange topicExchange = new TopicExchange("user_action_topic_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public Queue profileCompanyTagQue() {
        Queue queue = new Queue("profile_company_tag_recom_que", true, false, false);
        return queue;
    }

    @Bean
    public TopicExchange profileCompanyTagRecomExchange() {
        TopicExchange topicExchange = new TopicExchange("profile_company_tag_recom_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public TopicExchange referralRadarExchange() {
        TopicExchange topicExchange = new TopicExchange("referral_radar_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public Queue bonusNoticeQueue() {
        Queue queue = new Queue("bonus_notice_queue", true, false, false);
        return queue;
    }

    @Bean
    public Queue referralRadarTenMinuteQueue() {
        Queue queue = new Queue("referral_radar_tenminute_queue", true, false, false);
        return queue;
    }

    @Bean
    public Queue redpacketTemplateQueue() {
        Queue queue = new Queue("redpacket_template_queue", true, false, false);
        return queue;
    }


    @Bean
    public TopicExchange applicationStateChangeExchange() {
        TopicExchange topicExchange = new TopicExchange("add_bonus_change_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public Queue employeeRegister() {
        Queue queue = new Queue("redpacket_template_queue", true, false, false);
        return queue;
    }

    @Bean
    public TopicExchange employeeVerificationExchange() {
        TopicExchange topicExchange = new TopicExchange("employee_verification_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public Queue demonstrationEmployeeRegisterQueue() {
        Queue queue = new Queue("demonstration_employee_register_queue", true, false, false);
        return queue;
    }

    @Bean
    public Queue demonstrationFollowWechatQueue() {
        Queue queue = new Queue("demonstration_follow_wechat_queue", true, false, false);
        return queue;
    }

    @Bean
    public TopicExchange followWechatExchange() {
        TopicExchange topicExchange = new TopicExchange("user_follow_wechat_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public List<Binding> binding() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(addAwardQue()).to(topicExchange()).with("sharejd.#"));
            add(BindingBuilder.bind(sendTemplateQue()).to(templateExchange()).with("messagetemplate.#"));
            add(BindingBuilder.bind(personaRecomQue()).to(personaRecomExchange()).with("personarecom.#"));
            add(BindingBuilder.bind(profileCompanyTagQue()).to(profileCompanyTagRecomExchange()).with("profilecompanytagrecom.#"));
            add(BindingBuilder.bind(bonusNoticeQueue()).to(applicationStateChangeExchange()).with("add_bonus_change_routingkey.add_bonus"));
            add(BindingBuilder.bind(employeeFirstRegisterQueue()).to(employeeRegisterExchange()).with("employee_register_routingkey.first_register"));
            add(BindingBuilder.bind(sendSeekReferralTemplateQueue()).to(seekReferralTemplateExchange()).with("*.referral_template"));
            add(BindingBuilder.bind(referralRadarTenMinuteQueue()).to(referralRadarExchange()).with("referral_radar.referral_radar_template"));
            add(BindingBuilder.bind(redpacketTemplateQueue()).to(redpacketTemplateExchange()).with("*.redpacket_template"));
            add(BindingBuilder.bind(demonstrationEmployeeRegisterQueue()).to(employeeVerificationExchange()).with("employee_verification_exchange.demonstration"));
            add(BindingBuilder.bind(demonstrationFollowWechatQueue()).to(followWechatExchange()).with("user_follow_wechat.demonstration"));
        }};
    }
}
