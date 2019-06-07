package com.moseeker.common.thread;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 为所有的需要使用线程的地方提供统一的线程池，避免开启的线程过多。
 * Created by jack on 13/02/2017.
 * @author jack
 */
public enum Neo4jThreadPool {

    /**
     * 单例的实例变量
     */
    Instance;

    /**
     * 线程池
     */
    private ExecutorService service;

    /**
     * Neo4j调用专用线程池
     */
    Neo4jThreadPool() {
        init();
    }

    /**
     * 利用线程池执行任务
     * @param task 任务
     * @param <T> 返回类型
     * @return future
     */
    public <T> Future<T> startTast(Callable<T> task) {
        return this.service.submit(task);
    }

    /**
     * 关闭线程池
     */
    public void close() {
        synchronized (service) {
            if(service != null) {
                this.service.shutdown();
            }
        }
    }

    /**
     * 初始化线程池
     */
    private void init() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("neo4j-thread-pool").build();
        service = new ThreadPoolExecutor(1,
                33,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(20000), threadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }
}
