package com.moseeker.useraccounts.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjm
 * @date 2019-01-14
 */
public class KafkaProducerPlugin {

    /**
     * 即所有副本都同步到数据时send方法才返回, 以此来完全判断数据是否发送成功, 理论上来讲数据不会丢失.
     */
    @Value(value = "${bootstrap.servers}")
    private List<String> servers;

    /**
     * 即所有副本都同步到数据时send方法才返回, 以此来完全判断数据是否发送成功, 理论上来讲数据不会丢失
     */
    @Value(value = "${acks}")
    private String acks;

    /**
     * 发送失败重试次数
     */
    @Value(value = "${retries}")
    private Integer retries;

    /**
     * 批处理条数：当多个记录被发送到同一个分区时，生产者会尝试将记录合并到更少的请求中。这有助于客户端和服务器的性能.
     */
    @Value(value = "${batch.size}")
    private Integer batchSize;

    /**
     * 批处理延迟时间上限：即1ms过后，不管是否达到批处理数，都直接发送一次请求
     */
    @Value(value = "${linger.ms}")
    private Integer lingerMs;

    /**
     * 批处理缓冲区
     */
    @Value(value = "${buffer.memory}")
    private Long bufferMemory;

    private Map<String, Object> props = new HashMap<>();

    public KafkaProducerPlugin() {
    }

    public KafkaProducerPlugin buildProps() {
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        return this;
    }

    public Map<String, Object> getProducerProps(){
        return props;
    }
}
