package com.moseeker.common.thread;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 为所有的需要使用线程的地方提供统一的线程池，避免开启的线程过多。
 * Created by jack on 13/02/2017.
 */
public enum ThreadPool {

    /**
     * 线程池实例
     */
    Instance;

    /**
     * JDK提供的线程池
     */
    ExecutorService service;

    private ThreadPool() {
        init();
    }

    public <T> Future<T> startTast(Callable<T> task) {
        if(service == null) {
            synchronized (this) {
                init();
            }
        }

        return this.service.submit(task);
    }

    public <T> Future<T> startTastSleep(Callable<T> task) {
        if(service == null) {
            synchronized (this) {
                init();
            }
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.service.submit(task);
    }

    public <T> Future<T> startTast(Runnable task, T t) {
        if(service == null) {
            synchronized (this) {
                init();
            }
        }
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
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-pool").build();
        service = new ThreadPoolExecutor(3,
                1000,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(), threadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

}
