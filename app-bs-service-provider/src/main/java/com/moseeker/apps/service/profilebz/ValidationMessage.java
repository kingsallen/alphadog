package com.moseeker.apps.service.profilebz;

import com.moseeker.common.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 校验结果
 * @author jack
 *
 */
public class ValidationMessage<T> {

	private volatile boolean result = true;
	private Map<String, List<String>> message = new HashMap<>();
	private List<T> failedArray;
	
	ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
	
	public void addFailedElement(String elementName, String reason) {
		lock.writeLock().lock();
		try {
			if(StringUtils.isNotNullOrEmpty(elementName)) {
				if(message.get(elementName) != null) {
					result = false;
					message.get(elementName).add(reason);
				} else {
					message.put(elementName, new ArrayList<String>(){
						private static final long serialVersionUID = -8320131279823564200L;

					{
						result = false;
						add(reason);
					}});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	public boolean isPass() {
		return result;
	}
	
	public String getResult() {
		lock.readLock().lock();
		try {
			if(!result) {
				StringBuffer sb = new StringBuffer();
				message.forEach((elementName, reasons) -> {
					StringBuffer elementReason = new StringBuffer();
					elementReason.append("|");
					elementReason.append(elementName);
					elementReason.append(":");
					if(reasons != null && reasons.size() > 0) {
						reasons.forEach(reason -> {
							elementReason.append(reason);
						});
					}
					sb.append(elementReason);
				});
				return sb.toString();
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public void addFailed(T t) {
		failedArray.add(t);
	}
}
