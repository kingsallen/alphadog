package com.moseeker.common.email.config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 邮件发送线程池配置 
 * <p>Company: MoSeeker</P>  
 * <p>date: Sep 21, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class EmailPoolConfig {

	private int corePoolSize;					//线程池大小
	private int maximumPoolSize;				//线程池最大线程数
	private long keepAliveTime;					//无意义线程的活跃时间
	private TimeUnit unit;						//时间单位
	private BlockingQueue<Runnable> workQueue;	//任务等待队列
	
	public int getCorePoolSize() {
		return corePoolSize;
	}
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}
	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}
	public long getKeepAliveTime() {
		return keepAliveTime;
	}
	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}
	public TimeUnit getUnit() {
		return unit;
	}
	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}
	public BlockingQueue<Runnable> getWorkQueue() {
		return workQueue;
	}
	public void setWorkQueue(BlockingQueue<Runnable> workQueue) {
		this.workQueue = workQueue;
	}
}
