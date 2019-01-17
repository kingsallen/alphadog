package com.moseeker.useraccounts.kafka;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cjm
 * @date 2019/01/15
 */
public class KafkaConsumerPlugin {

    /**
     * 即所有副本都同步到数据时send方法才返回, 以此来完全判断数据是否发送成功, 理论上来讲数据不会丢失.
     */
    @Value(value = "${kafka.bootstrap.servers}")
    private String servers;

    /**
     * 如果为true，消费者的偏移量将在后台定期提交
     */
    @Value(value = "${kafka.enable.auto.commit}")
    private Boolean enableAutoCommit;

    /**
     * 自动提交周期
     */
    @Value(value = "${kafka.auto.commit.interval.ms}")
    private Integer automitIntervalMs;

    /**
     * 在使用Kafka的组管理时，用于检测消费者故障的超时
     */
    @Value(value = "${kafka.session.timeout.ms}")
    private Integer sessionTimeoutMs;

    @Value(value = "${kafka.auto.offset.reset}")
    private String autoOffSet;

    private Map<String, Object> props = new HashMap<>();

    public KafkaConsumerPlugin buildProps() {
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, automitIntervalMs);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffSet);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return this;
    }

    /**
     * 消费者群组ID，发布-订阅模式，即如果一个生产者，多个消费者都要消费，那么需要定义自己的群组，同一群组内的消费者只有一个能消费到消息
     */
    public Map<String, Object> buildGroupId(String groupId) {
        if(StringUtils.isEmpty(groupId)){
            return props;
        }
        Map<String, Object> tempMap = new HashMap<>(props);
        tempMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return tempMap;
    }

    public Map<String, Object> getConsumerProps(){
        return props;
    }


}
