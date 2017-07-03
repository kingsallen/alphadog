package com.moseeker.baseorm.redis.log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.SafeEncoder;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class RedisAppender extends AppenderSkeleton implements Runnable {

    // configs
    private String host = "127.0.0.1";
    private int port = 6379;
    private String password;
    private int queueSize = 5000;
    private int batchSize = 100;
    private long flushInterval = 500;
    private boolean purgeOnFailure = true;
    private long waitTerminate = 1000;
    private String key = "alphadog_log";

    // runtime stuff
    private Queue<LoggingEvent> events;
    private String[] batch;
    private Jedis jedis;
    private ScheduledExecutorService executor;
    private ScheduledFuture<?> task;


    @Override
    public void activateOptions() {
        try {
            super.activateOptions();
            LogLog.setInternalDebugging(true);
            LogLog.setQuietMode(false);
            if (key == null) throw new IllegalStateException("Must set 'key'");
            if (!(flushInterval > 0))
                throw new IllegalStateException("FlushInterval (ex. Period) must be > 0. Configured value: " + flushInterval);
            if (!(queueSize > 0))
                throw new IllegalStateException("QueueSize must be > 0. Configured value: " + queueSize);
            if (!(batchSize > 0))
                throw new IllegalStateException("BatchSize must be > 0. Configured value: " + batchSize);

            if (executor == null)
                executor = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory(this.getClass().getSimpleName(), true));

            if (task != null && !task.isDone()) task.cancel(true);

            events = new ArrayBlockingQueue<LoggingEvent>(queueSize);
            batch = new String[batchSize];
            createJedis();

            task = executor.scheduleWithFixedDelay(this, flushInterval, flushInterval, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            LogLog.error("Error during activateOptions", e);
        }
    }

    protected void createJedis() {
        if (jedis != null && jedis.isConnected()) {
            jedis.disconnect();
        }
        jedis = new Jedis(host, port);
    }

    @Override
    protected void append(LoggingEvent event) {
        try {
            int size = events.size();
            if (size < queueSize) {
                populateEvent(event);
                try {
                    events.add(event);
                } catch (IllegalStateException e) {
                    // safeguard in case multiple threads raced on almost full queue
                    e.printStackTrace();
                }
            } else {
                LogLog.warn("drop event:" + layout.format(event));
            }

        } catch (Exception e) {
            LogLog.error("Error populating event and adding to queue", e);
        }
    }

    protected void populateEvent(LoggingEvent event) {
        event.getThreadName();
        event.getRenderedMessage();
        event.getNDC();
        event.getMDCCopy();
        event.getThrowableStrRep();
        event.getLocationInformation();
    }

    @Override
    public void close() {
        try {
            task.cancel(false);
            executor.shutdown();

            boolean finished = executor.awaitTermination(waitTerminate, TimeUnit.MILLISECONDS);
            if (finished) {
                // We finished successfully, process any events in the queue if any still remain.
                if (!events.isEmpty())
                    run();
                // Flush any remainder regardless of the alwaysBatch flag.
                if (batch.length > 0)
                    push();
            } else {
                LogLog.warn("Executor did not complete in " + waitTerminate + " milliseconds. Log entries may be lost.");
            }

            safeDisconnect();
        } catch (Exception e) {
            LogLog.error(e.getMessage(), e);
        }
    }

    /**
     * Pre: jedis not null
     */
    protected void safeDisconnect() {
        try {
            jedis.disconnect();
        } catch (Exception e) {
            LogLog.warn("Disconnect failed to Redis at " + getRedisAddress());
        }
    }

    protected boolean connect() {
        try {
            if (!jedis.isConnected()) {
                LogLog.debug("Connecting to Redis at " + getRedisAddress());
                jedis.connect();

                if (password != null) {
                    String result = jedis.auth(password);
                    if (!"OK".equals(result)) {
                        LogLog.error("Error authenticating with Redis at " + getRedisAddress());
                    }
                }

                // make sure we got a live connection
                jedis.ping();
            }
            return true;
        } catch (Exception e) {
            LogLog.error("Error connecting to Redis at " + getRedisAddress() + ": " + e.getMessage());
            return false;
        }
    }

    public void run() {

        if (!connect()) {
            return;
        }

        try {
            if (events.size() < batchSize) {
                batch = new String[events.size()];
            } else {
                batch = new String[batchSize];
            }

            LoggingEvent event;
            int index = 0;
            while (index < batch.length && (event = events.poll()) != null) {
                batch[index++] = layout.format(event);
            }

            if (index > 0) {
                push();
            }

        } catch (JedisException je) {

            LogLog.debug("Can't push " + batch.length + " events to Redis. Reconnecting for retry.", je);
            safeDisconnect();

        } catch (Exception e) {
            LogLog.error("Can't push events to Redis", e);
        }
    }

    long total;

    protected boolean push() {
        LogLog.debug("Sending " + batch.length + " log messages to Redis at " + getRedisAddress());
        try {
            jedis.rpush(key, batch);
            total += batch.length;
            batch = new String[0];
            return true;

        } catch (JedisDataException jde) {
            // Handling stuff like OOM's on Redis' side
            if (purgeOnFailure) {
                LogLog.error("Can't push events to Redis at " + getRedisAddress() + ": " + jde.getMessage());
            }
            return false;
        }
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRedisAddress() {
        return host + ":" + port;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFlushInterval(long millis) {
        this.flushInterval = millis;
    }

    // deprecated, use flushInterval instead
    public void setPeriod(long millis) {
        setFlushInterval(millis);
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public void setPurgeOnFailure(boolean purgeOnFailure) {
        this.purgeOnFailure = purgeOnFailure;
    }

    public void setWaitTerminate(long waitTerminate) {
        this.waitTerminate = waitTerminate;
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    public void setKey(String key) {
        this.key = key;
    }

    class NamedThreadFactory implements ThreadFactory {

        private final String prefix;
        private final ThreadFactory threadFactory;
        private final boolean daemonThread;
        private final AtomicInteger counter = new AtomicInteger();

        public NamedThreadFactory(final String prefix, final boolean daemonThread) {
            this(prefix, daemonThread, Executors.defaultThreadFactory());
        }

        public NamedThreadFactory(final String prefix, final boolean daemonThread, final ThreadFactory threadFactory) {
            this.prefix = prefix;
            this.threadFactory = threadFactory;
            this.daemonThread = daemonThread;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = this.threadFactory.newThread(r);
            t.setDaemon(daemonThread);
            t.setName(this.prefix + "-Thread-" + this.counter.incrementAndGet());
            return t;
        }

    }
}