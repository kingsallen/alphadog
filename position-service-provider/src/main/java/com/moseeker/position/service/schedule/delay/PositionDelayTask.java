package com.moseeker.position.service.schedule.delay;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author cjm
 * @date 2018-06-28 18:23
 **/
public class PositionDelayTask<T extends Runnable> implements Delayed {
    /**
     * 到期时间
     */
    private final long time;

    /**
     * 延时队列需要处理的对象
     */
    private final T task;

    private static final AtomicLong ATOMIC = new AtomicLong(0);

    private final long n;

    public PositionDelayTask(long timeout, T t) {
        this.time = System.nanoTime() + timeout;
        this.task = t;
        this.n = ATOMIC.getAndIncrement();
    }

    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        return unit.convert(this.time - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(@NotNull Delayed other) {
        // compare zero ONLY if same object
        if (other == this){
            return 0;
        }
        if (other instanceof PositionDelayTask) {
            PositionDelayTask x = (PositionDelayTask) other;
            long diff = time - x.time;
            if (diff < 0){
                return -1;
            } else if (diff > 0){
                return 1;
            } else if (n < x.n){
                return -1;
            } else{
                return 1;
            }
        }
        long d = (getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }

    public T getTask() {
        return this.task;
    }

    @Override
    public int hashCode() {
        return task.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof PositionDelayTask && object.hashCode() == hashCode();
    }

}
