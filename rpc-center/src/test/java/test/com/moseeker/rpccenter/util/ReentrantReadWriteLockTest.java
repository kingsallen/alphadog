package test.com.moseeker.rpccenter.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {
	
	private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
	
	private List<String> store = new ArrayList<>();

	public static void main(String[] args) {
		try {
			ReentrantReadWriteLockTest test = new ReentrantReadWriteLockTest();
			int i=0;
			while(i<15) {
				i += 1;
				String key = i+"key";
				Thread t = new Thread(() -> {
					test.add(key);
				});
				t.start();
			}
			Thread.sleep(20000);
			test.read();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void read() {
		lock.readLock().lock();
		if(store != null && store.size() > 0) {
			store.forEach(result -> {
				System.out.println("result:"+result);
			});
		}
		lock.readLock().unlock();
	}
	
	public void add(String result) {
		lock.writeLock().lock();
		try {
			add1(result);
			Thread.sleep(500);
			add1(result+"1");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	private void add1(String result) {
		lock.writeLock().lock();
		try {
			store.add(result);
			Thread.sleep(500);
			store.add("test"+result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void remove(String key) {
		store.remove(key);
	}
}
