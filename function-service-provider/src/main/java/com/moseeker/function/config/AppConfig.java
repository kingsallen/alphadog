package com.moseeker.function.config;

import com.moseeker.function.constants.BindThirdPart;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucky8987 on 17/5/11.
 */
@Configuration
@EnableRabbit
@ComponentScan({"com.moseeker.function", "com.moseeker.common.aop.iface", "com.moseeker.entity"})
@Import(com.moseeker.baseorm.config.AppConfig.class)
@PropertySource("classpath:common.properties")
public class AppConfig {


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

    //设置rabbitMQ的Exchange
    @Bean
    public TopicExchange bindAccountExchange() {
        TopicExchange exchange = new TopicExchange(BindThirdPart.BIND_EXCHANGE_NAME, true, false);
        return exchange;
    }


    //绑定第一次推送第三方账号队列(即推送需要绑定的账号和密码)
    @Bean
    public Queue bindAccountQueue() {
        Queue queue = new Queue(BindThirdPart.BIND_GET_QUEUE_NAME, true, false, false);
        return queue;
    }
    @Bean
    public List<Binding> bindingBindQueue() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(bindAccountQueue()).to(bindAccountExchange()).with(BindThirdPart.BIND_GET_ROUTING_KEY));
        }};
    }

    //绑定第二次推送第三方账号队列（即确认是否需要发送手机验证码）
    @Bean
    public Queue confirmQueue() {
        Queue queue = new Queue(BindThirdPart.BIND_CONFIRM_GET_QUEUE_NAME, true, false, false);
        return queue;
    }
    @Bean
    public List<Binding> confirmBindQueue() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(confirmQueue()).to(bindAccountExchange()).with(BindThirdPart.BIND_CONFIRM_GET_ROUTING_KEY));
        }};
    }

    //绑定第三次推送第三方账号队列（即推送手机验证码）
    @Bean
    public Queue codeQueue() {
        Queue queue = new Queue(BindThirdPart.BIND_CODE_GET_QUEUE_NAME, true, false, false);
        return queue;
    }
    @Bean
    public List<Binding> codeBindQueue() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(confirmQueue()).to(bindAccountExchange()).with(BindThirdPart.BIND_CODE_GET_ROUTING_KEY));
        }};
    }

    //同步职位队列
    @Bean
    public Queue positionQueue() {
        Queue queue = new Queue(BindThirdPart.SYNC_POSITION_GET_QUEUE_NAME, true, false, false);
        return queue;
    }
    @Bean
    public List<Binding> positionBindQueue() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(confirmQueue()).to(bindAccountExchange()).with(BindThirdPart.SYNC_POSITION_GET_ROUTING_KEY));
        }};
    }
}
