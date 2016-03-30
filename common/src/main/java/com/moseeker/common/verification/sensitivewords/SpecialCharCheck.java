package com.moseeker.common.verification.sensitivewords;

/**
 * 
 * @description 检查机制 用于检查是否敏感词中包含特殊字符
 * @author wjf
 * @date Jun 19, 2015
 * @company 大岂千寻
 * @email wjf2255@gmail.com
 */
public interface SpecialCharCheck {

	/**
	 * 检查敏感词中是否包含特殊字符
	 * @param words 敏感词
	 * @return true包含敏感词;false不包含敏感词
	 */
	public boolean checkSensitiveWords(String words);
	
	/**
	 * 检查敏感字符是否包含特殊字符
	 * @param word 敏感字符
	 * @return true包含敏感词;false不包含敏感词
	 */
	public boolean checkSensitiveWord(char word);
}
