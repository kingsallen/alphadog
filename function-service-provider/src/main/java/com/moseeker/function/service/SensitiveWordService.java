package com.moseeker.function.service;

import java.util.List;

/**
 * 
 * 敏感词处理服务
 * <p>Company: MoSeeker</P>  
 * <p>date: Jul 29, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public interface SensitiveWordService {

	/**
	 * 批量检查contents是否包含敏感词
	 * @param contents 被校验文本的数组
	 * @return 检查结果
	 */
	public List<Boolean> verifySensitiveWords(List<String> contents);
}
