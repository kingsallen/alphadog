package com.moseeker.common.thread;


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

    Neo4jThreadPool() {
        init();
    }

    public <T> Future<T> startTast(Callable<T> task) {
        return this.service.submit(task);
    }

    public <T> Future<T> startTastSleep(Callable<T> task) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.service.submit(task);
    }

    public <T> Future<T> startTast(Runnable task, T t) {
        return this.service.submit(task, t);
    }

    public void close() {
        synchronized (service) {
            if(service != null) {
                this.service.shutdown();
            }
        }

    }

    private void init() {
        service = new ThreadPoolExecutor(1, 200,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(20000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }

}
