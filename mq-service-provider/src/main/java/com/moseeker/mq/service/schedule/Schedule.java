package com.moseeker.mq.service.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 定时任务，用于定时获取消息模版延迟队列的任务，
 * 并将符合条件的任务移到消息模版的执行队列中 
 * <p>Company: MoSeeker</P>  
 * <p>date: Oct 27, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class Schedule {
	
	private long initialDelay;
	private long period;
	private TimeUnit unit;
	
	/**
	 * 
	 * @param initialDelay
	 * @param period
	 * @param unit
	 */
	public Schedule(long initialDelay, long period, TimeUnit unit) {
		this.initialDelay = initialDelay;
		this.period = period;
		this.unit = unit;
	}

	public void startListeningMessageDelayQueue() {
		ScheduledExecutorService service = Executors  
                .newSingleThreadScheduledExecutor();  
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
		PortConsumer porter = new PortConsumer();
        service.scheduleAtFixedRate(porter, initialDelay, period, unit);  
	}
}
