package com.moseeker.common.thread;


import java.util.concurrent.*;

/**
 * 提供新的线程池用于尝试处理简历服务异常问题
 */
public enum ProfileThreadPool {

    /**
     * 线程池实例
     */
    Instance;

    /**
     * JDK提供的线程池
     */
    ExecutorService service;

    private ProfileThreadPool() {
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
        service = new ThreadPoolExecutor(0, 1000,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }

}
