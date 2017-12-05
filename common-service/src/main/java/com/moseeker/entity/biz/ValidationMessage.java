package com.moseeker.entity.biz;

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

	private volatile boolean result = true;			//是否通过
	private Map<String, List<String>> message = new HashMap<>();   //错误信息
	private List<T> failedArray;

	ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

	/**
	 * 添加错误信息
	 * @param elementName 错误项
	 * @param reason	错误原因
	 */
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

	/**
	 * 校验结果
	 * @return true 通过；false 未通过
	 */
	public boolean isPass() {
		return result;
	}

	/**
	 * 错误详情
	 * @return 错误详情
	 */
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
						reasons.forEach(reason -> elementReason.append(reason));
					}
					sb.append(elementReason);
				});
				if (sb.length() > 0) {
					sb.deleteCharAt(0);
				}
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
