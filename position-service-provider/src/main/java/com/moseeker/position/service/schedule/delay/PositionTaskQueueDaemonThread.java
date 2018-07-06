package com.moseeker.position.service.schedule.delay;

import com.moseeker.position.service.schedule.bean.PositionSyncStateRefreshBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author cjm
 * @date 2018-06-29 10:20
 **/
@Component
public class PositionTaskQueueDaemonThread {

    private Logger logger = LoggerFactory.getLogger(PositionTaskQueueDaemonThread.class);

    private PositionTaskQueueDaemonThread() {
        init();
    }

    private static class LazyHolder{
        private static PositionTaskQueueDaemonThread taskQueueDaemonThread = new PositionTaskQueueDaemonThread();
    }

    public static PositionTaskQueueDaemonThread getInstance() {
        return LazyHolder.taskQueueDaemonThread;
    }

    @Autowired
    SyncStateRefreshFactory refreshFactory;
    /**
     * 守护线程
     */
    private Thread daemonThread;

    /**
     * 初始化守护线程
     */
    public void init() {
        daemonThread = new Thread(() -> execute());
        daemonThread.setDaemon(true);
        daemonThread.setName("Task Queue Daemon Thread");
        daemonThread.start();
    }

    private void execute() {
        while (true) {
            try {
                //从延迟队列中取值,如果没有对象过期则队列一直等待，
                PositionDelayTask t1 = delayQueue.take();
                if (t1 != null) {
                    //修改问题的状态
                    PositionSyncStateRefreshBean task = t1.getTask();
                    if (task == null) {
                        continue;
                    }
                    refreshFactory.refresh(task);
                    logger.info("[at task:" + task + "]   [Time:" + System.currentTimeMillis() + "]");
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                break;
            }
        }
    }

    /**
     * 创建一个最初为空的新 DelayQueue
     */
    private DelayQueue<PositionDelayTask> delayQueue = new DelayQueue<>();

    /**
     * 添加任务，
     * time 延迟时间
     * task 任务
     * 用户为问题设置延迟时间
     */
    @SuppressWarnings("unchecked")
    public void put(long time, PositionSyncStateRefreshBean task) {
        //转换成ns
        long nanoTime = TimeUnit.NANOSECONDS.convert(time, TimeUnit.MILLISECONDS);
        //创建一个任务
        PositionDelayTask k = new PositionDelayTask(nanoTime, task);
        //将任务放在延迟的队列中
        delayQueue.put(k);
    }

    /**
     * 结束订单
     * @param task
     */
    public boolean endTask(PositionDelayTask task){
        return delayQueue.remove(task);
    }
}
