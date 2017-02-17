package com.moseeker.common.thread;


import java.util.concurrent.*;

/**
 * 为所有的需要使用线程的地方提供统一的线程池，避免开启的线程过多。
 * Created by jack on 13/02/2017.
 */
public enum ThreadPool {

    Instance;

    ExecutorService service = new ThreadPoolExecutor(0, 1000,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    public Future<?> startTast(Runnable task) {
        return this.service.submit(task);
    }

    public <T> Future<T> startTast(Runnable task, T t) {
        return this.service.submit(task, t);
    }
}
