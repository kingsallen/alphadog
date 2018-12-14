package com.moseeker.useraccounts.config;

import com.moseeker.common.util.query.Query;
import com.rabbitmq.client.ConnectionFactory;
import org.neo4j.ogm.session.SessionFactory;
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
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.moseeker.common.constants.Constant.EMPLOYEE_FIRST_REGISTER_EXCHNAGE_ROUTINGKEY;

/**
 * Created by lucky8987 on 17/5/12.
 */
@Configuration
@EnableRabbit
@ComponentScan({"com.moseeker.useraccounts", "com.moseeker.entity", "com.moseeker.common.aop.iface", "com.moseeker.common.aop.notify"})
@PropertySource("classpath:common.properties")
@EnableNeo4jRepositories("com.moseeker.useraccounts.repository")
@Import({com.moseeker.baseorm.config.AppConfig.class})
public class AppConfig {

    Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @Bean
    public Neo4jTransactionManager neo4jTransactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }

    @Bean
    public SessionFactory sessionFactory(){
        return new SessionFactory("com.moseeker.useraccounts.pojo.neo4j");
    }

    /**
     * listener 容器 （consumer 需要手动确认消息）
     * @return
     */
    @Bean("rabbitListenerContainerFactory")
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

        // 设置自动 ACK
        listenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainerFactory;
    }

    @Bean
    public Queue bindAccountQueue() {
        Queue queue = new Queue("chaos.bind.response", true, false, false);
        return queue;
    }

    @Bean
    public TopicExchange bindAccountExchange() {
        TopicExchange topicExchange = new TopicExchange("chaos.bind.response.exchange", true, false);
        return topicExchange;
    }

    @Bean
    public List<Binding> bindingBindAccount() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(bindAccountQueue()).to(bindAccountExchange()).with("chaos.bind.response.#"));
        }};
    }

    @Bean
    public TopicExchange webPresetExchange() {
        TopicExchange topicExchange = new TopicExchange("chaos", true, false);
        return topicExchange;
    }

    @Bean
    public Queue presetQueue() {
        Queue queue = new Queue("chaos.preset.response", true, false, false);
        return queue;
    }

    @Bean
    public List<Binding> webBindingPreset() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(presetQueue()).to(webPresetExchange()).with("preset.response"));
        }};
    }

    @Bean
    public Queue followWechatQueue() {
        Queue queue = new Queue("user_follow_wechat_queue", true, false, false);
        return queue;
    }

    @Bean
    public TopicExchange followWechatExchange() {
        TopicExchange topicExchange = new TopicExchange("user_follow_wechat_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public List<Binding> bingFollowWechat() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(followWechatQueue()).to(followWechatExchange())
                    .with("user_follow_wechat_check_employee_identity"));
        }};
    }

    @Bean
    public Queue unFollowWechatQueue() {
        Queue queue = new Queue("user_unfollow_wechat_queue", true, false, false);
        return queue;
    }

    @Bean
    public TopicExchange unFollowWechatExchange() {
        TopicExchange topicExchange = new TopicExchange("user_unfollow_wechat_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public List<Binding> bingUnFollowWechat() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(unFollowWechatQueue()).to(unFollowWechatExchange())
                    .with("user_unfollow_wechat_check_employee_identity"));
        }};
    }

    @Bean
    public Queue clearUnViewdUpVoteQueue() {
        Queue queue = new Queue("employee_view_leader_board_queue", true, false, false);
        return queue;
    }

    @Bean
    public TopicExchange clearUnViewdUpVoteExchange() {
        TopicExchange topicExchange = new TopicExchange("employee_view_leader_board_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public List<Binding> binglearUnViewdUpVote() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(clearUnViewdUpVoteQueue()).to(clearUnViewdUpVoteExchange())
                    .with("employee_view_leader_board_routing_key"));
        }};
    }

    @Bean
    public Queue addBonusQueue() {
        Queue queue = new Queue("add_bonus_queue", true, false, false);
        return queue;
    }

    @Bean
    public TopicExchange addBonusExchange() {
        TopicExchange topicExchange = new TopicExchange("application_state_change_exchange", true, false);
        return topicExchange;
    }

    @Bean
    public TopicExchange employeeRegisterExchange() {
        return new TopicExchange("employee_first_register_exchange", true, false);
    }

    @Autowired
    public Queue employeeRegisterQueue() {
        return new Queue("add_redpacket_queue", true, false, false);
    }

    @Bean
    public List<Binding> bindBonus() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(addBonusQueue()).to(addBonusExchange())
                    .with("application_state_change_routingkey.change_state"));
            add(BindingBuilder.bind(employeeRegisterQueue()).to(employeeRegisterExchange())
                    .with(EMPLOYEE_FIRST_REGISTER_EXCHNAGE_ROUTINGKEY));
        }};
    }
}
