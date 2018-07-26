package com.moseeker.position.service.schedule.delay;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.schedule.bean.PositionSyncStateRefreshBean;
import com.moseeker.position.service.schedule.delay.refresh.DefaultSyncStateRefresh;
import com.moseeker.position.service.schedule.delay.refresh.LiepinSyncStateRefresh;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author cjm
 * @date 2018-06-29 10:20
 **/
@Component
@DependsOn({"HRThirdPartyPositionDao","syncStateRefreshFactory"})
public class PositionTaskQueueDaemonThread {

    @Autowired
    private HRThirdPartyPositionDao hrThirdPartyPositionDao;

    private Logger logger = LoggerFactory.getLogger(PositionTaskQueueDaemonThread.class);

    @Autowired
    SyncStateRefreshFactory refreshFactory;
    /**
     * 守护线程
     */
    private Thread daemonThread;

    private Random random = new Random();

    /**
     * 初始化守护线程
     */
    @PostConstruct
    public void init() {
        daemonThread = new Thread(() -> execute());
        daemonThread.setDaemon(true);
        daemonThread.setName("Task Queue Daemon Thread");
        daemonThread.start();
    }


    private void execute() {
        // 由于服务器重启时会将内存中延迟队列的数据删除，所以服务器启动时将数据库查一遍，将历史数据再次放入队列中
        getHistoryData();
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
            }
        }
    }

    /**
     * 由于服务器重启时会将内存中延迟队列的数据删除，所以服务器启动时将数据库查一遍，将历史数据再次放入队列中
     * @author  cjm
     * @date  2018/7/9
     */
    private void getHistoryData() {
        logger.info("=========================将历史数据再次放入队列中");
        List<HrThirdPartyPositionDO> hrThirdPartyPositionDOS =  hrThirdPartyPositionDao.getAuditPositionData();
        if(hrThirdPartyPositionDOS == null){
            return;
        }
        PositionSyncStateRefreshBean refreshBean;
        for(HrThirdPartyPositionDO hrThirdPartyPositionDO : hrThirdPartyPositionDOS){
            if(hrThirdPartyPositionDO.getChannel() == ChannelType.LIEPIN.getValue()) {
                refreshBean = new PositionSyncStateRefreshBean(hrThirdPartyPositionDO.getId(), hrThirdPartyPositionDO.getChannel());
                put(LiepinSyncStateRefresh.TIMEOUT + random.nextInt(60 * 1000), refreshBean);
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
