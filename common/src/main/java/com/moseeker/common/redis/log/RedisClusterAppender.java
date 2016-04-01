package com.moseeker.common.redis.log;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.util.SafeEncoder;

import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;

/**
 * 
 * 用于让log4j支持rediscluster方式存储日志 
 * <p>Company: MoSeeker</P>  
 * <p>date: Mar 31, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version Beta
 */
public class RedisClusterAppender extends AppenderSkeleton {
	
	private int msetmax = 100;

	// Redis connection and messages buffer
	private JedisCluster redisCluster;
	private Map<String, String> messages;

	public void activateOptions() {
		super.activateOptions();

		ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil
				.getInstance();
		if (redisCluster == null) {
			Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
			// Jedis Cluster will attempt to discover cluster nodes
			String host = propertiesUtils.get("log_redis_host", String.class);
			String port = propertiesUtils.get("log_redis_port", String.class);
			if (!StringUtils.isNullOrEmpty(host)
					&& !StringUtils.isNullOrEmpty(port)) {
				String[] hostArray = host.split(",");
				String[] portArray = port.split(",");
				if (hostArray.length == portArray.length) {
					for (int i = 0; i < hostArray.length; i++) {
						jedisClusterNodes.add(new HostAndPort(hostArray[i],
								Integer.parseInt(portArray[i])));
					}
				}
			}
			redisCluster = new JedisCluster(jedisClusterNodes);
		}
		messages = new ConcurrentHashMap<String, String>();

		new Timer().schedule(new TimerTask() {
			public void run() {
				// long begin = System.nanoTime();

				Entry<String, String> message;

				int currentMessagesCount = messages.size();
				int bucketSize = currentMessagesCount < msetmax ? currentMessagesCount
						: msetmax;
				byte[][] bucket = new byte[bucketSize * 2][];

				int messageIndex = 0;

				for (Iterator<Entry<String, String>> it = messages.entrySet()
						.iterator(); it.hasNext();) {
					message = it.next();
					it.remove();

					// [k1, v1, k2, v2, ..., kN, vN]
					bucket[messageIndex] = SafeEncoder.encode(message.getKey());
					bucket[messageIndex + 1] = SafeEncoder.encode(message
							.getValue());
					messageIndex += 2;

					if (messageIndex == bucketSize * 2) {
						redisCluster.mset(bucket);

						currentMessagesCount -= bucketSize;

						if (currentMessagesCount == 0) {
							// get out the loop and wait 1/2 second
							break;
						} else {
							bucketSize = currentMessagesCount < msetmax ? currentMessagesCount
									: msetmax;
							bucket = new byte[bucketSize * 2][];

							messageIndex = 0;
						}
					}
				}

				// long expendHere = System.nanoTime() - begin;
				// System.out.println("Expend here: " + expendHere + " ns");
			}
		}, 500, 500);
	}

	protected void append(LoggingEvent event) {
		try {
			messages.put(this.layout.format(event), event.getRenderedMessage());
		} catch (Exception e) {
			// what to do? ignore? send back error - from log???
		}
	}

	public void close() {
	}

	public void setMsetmax(int msetmax) {
		this.msetmax = msetmax;
	}

	public boolean requiresLayout() {
		return true;
	}
}
