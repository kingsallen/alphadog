package com.moseeker.position.config;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.PositionSyncVerify;
import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.entity.pojo.mq.kafka.KafkaConsumerPlugin;
import com.moseeker.entity.pojo.mq.kafka.KafkaProducerPlugin;
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
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.List;

import static com.moseeker.position.constants.position.PositionAsyncConstant.*;

/**
 * Created by lucky8987 on 17/5/11.
 */
@Configuration
@EnableRabbit
@EnableScheduling
@EnableKafka
@ComponentScan({"com.moseeker.position", "com.moseeker.common.aop.iface", "com.moseeker.entity"})
@Import({com.moseeker.baseorm.config.AppConfig.class})
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
    /*
       监听器
        */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(kafkaConsumerPlugin().buildProps().buildGroupId(Constant.KAFKA_GROUP_RADAR_TOPN));
    }

    @Bean
    public KafkaProducerPlugin kafkaProducerPlugin(){
        return new KafkaProducerPlugin();
    }

    @Bean
    public KafkaConsumerPlugin kafkaConsumerPlugin(){
        return new KafkaConsumerPlugin();
    }


    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerPlugin().buildProps().getProducerProps());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    //设置rabbitMQ的Exchange
    @Bean
    public TopicExchange exchange() {
        TopicExchange exchange = new TopicExchange(RefreshConstant.EXCHANGE, true, false);
        return exchange;
    }


    //绑定第一次推送第三方账号队列(即推送需要绑定的账号和密码)
    @Bean
    public Queue paramQueue() {
        Queue queue = new Queue(RefreshConstant.PARAM_GET_QUEUE, true, false, false);
        return queue;
    }
    @Bean
    public List<Binding> bindingParamQueue() {
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(paramQueue()).to(exchange()).with(RefreshConstant.PARAM_GET_ROUTING_KEY));
        }};
    }


    //手机验证码Exchange
    @Bean
    public DirectExchange positionSyncMobileExchange(){
        DirectExchange exchange=new DirectExchange(PositionSyncVerify.MOBILE_VERIFY_EXCHANGE,true,false);

        return exchange;
    }

    @Bean
    public Queue positionSyncMobileQueue(){
        Queue queue=new Queue(PositionSyncVerify.MOBILE_VERIFY_QUEUE,true,false,false);
        return queue;
    }

    @Bean
    public List<Binding> bindPositionSyncMobileQueue(){
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(positionSyncMobileQueue()).to(positionSyncMobileExchange()).with(PositionSyncVerify.MOBILE_VERIFY_ROUTING_KEY));
        }};
    }

    // 猎聘api对接的交换机
    @Bean
    public DirectExchange positionSyncLiePinExchange(){
        return new DirectExchange(PositionSyncVerify.POSITION_OPERATE_LIEPIN_EXCHANGE,true,false);
    }

    @Bean
    public Queue positionDelLiePinQueue(){
        return new Queue(PositionSyncVerify.POSITION_QUEUE_DELETE,true,false,false);
    }

    @Bean
    public Queue positionRevertLiePinQueue(){
        return new Queue(PositionSyncVerify.POSITION_QUEUE_RESYNC,true,false,false);
    }

    @Bean
    public Queue positionDownShelfLiePinQueue(){
        return new Queue(PositionSyncVerify.POSITION_QUEUE_DOWNSHELF,true,false,false);
    }

    @Bean
    public Queue positionEditLiePinQueue(){
        return new Queue(PositionSyncVerify.POSITION_QUEUE_EDIT,true,false,false);

    }

    @Bean
    public List<Binding> bindPositionLiePinQueue(){
        return new ArrayList<Binding>(){{
            add(BindingBuilder.bind(positionDelLiePinQueue()).to(positionSyncLiePinExchange()).with(PositionSyncVerify.POSITION_OPERATE_ROUTEKEY_DEL));
            add(BindingBuilder.bind(positionRevertLiePinQueue()).to(positionSyncLiePinExchange()).with(PositionSyncVerify.POSITION_OPERATE_ROUTEKEY_RESYNC));
            add(BindingBuilder.bind(positionDownShelfLiePinQueue()).to(positionSyncLiePinExchange()).with(PositionSyncVerify.POSITION_OPERATE_ROUTEKEY_DOWNSHELF));
            add(BindingBuilder.bind(positionEditLiePinQueue()).to(positionSyncLiePinExchange()).with(PositionSyncVerify.POSITION_OPERATE_ROUTEKEY_EDIT));
        }};
    }

    @Bean
    public DirectExchange positionBatchHandleExchange() {
        DirectExchange exchange = new DirectExchange(POSITION_BATCH_HANDLE_EXCHANGE, true, false);
        return exchange;
    }

    @Bean
    public Queue positionBatchHandleRequestQueue() {
        return new Queue(POSITION_BATCH_HANDLE_REQUEST_QUEUE, true, false, false);
    }

    @Bean
    public Queue positionBatchHandleResponseQueue() {
        return new Queue(POSITION_BATCH_HANDLE_RESPONSE_QUEUE, true, false, false);
    }

    @Bean
    public List<Binding> bindPositionBatchHandle() {
        return new ArrayList<Binding>() {{
            add(BindingBuilder.bind(positionBatchHandleRequestQueue()).to(positionBatchHandleExchange()).with(POSITION_BATCH_HANDLE_REQUEST_ROUTING_KEY));
            add(BindingBuilder.bind(positionBatchHandleResponseQueue()).to(positionBatchHandleExchange()).with(POSITION_BATCH_HANDLE_RESPONSE_ROUTING_KEY));
        }};
    }

    @Bean
    public DirectExchange positionProcessExchange() {
        return new DirectExchange(POSITION_PROCESS_EXCHANGE, true, false);
    }

    @Bean
    public Queue positionProcessQueue() {
        return new Queue(POSITION_PROCESS_QUEUE, true, false, false);
    }

    @Bean
    public List<Binding> bindPositionProcess() {
        return new ArrayList<Binding>() {{
            add(BindingBuilder.bind(positionProcessQueue()).to(positionProcessExchange()).with(POSITION_PROCESS_ROUTEKEY));
        }};
    }
}
