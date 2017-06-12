package com.moseeker.common.validation.sensitivewords;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.moseeker.common.exception.ParamNullException;
import com.moseeker.common.util.StringUtils;
/**
 * 
 * @description 简单的敏感词特殊字符判断
 * @author wjf
 * @date Jun 19, 2015
 * @company 大岂千寻
 * @email wjf2255@gmail.com
 */
public class NormalSpecialCharCheck implements SpecialCharCheck {
	
	//分词标识
	char[] punctuations = new char[]{'`','~','!','@','#','$','%','^','&','*','(',')','+','=','|','{','}','\'',':',';','‘',',','\\','[','/',']','.','<','>','/','?','~','！','@','#','￥','%','…','&','*','（','）','-','+','|','{','}','【','】','‘','；','：','”','“','’','。','，','、','？','\t','\n','\r','\b','\f'};

	@Override
	public boolean checkSensitiveWords(String essay) {
		//List<String> keywords = generatKeywords(essay);
		
		return StringFilter(essay);
	}

	/**
	 * 暂时不支持
	 */
	@Override
	@Deprecated
	public boolean checkSensitiveWord(char word) {
		return false;
	}
	
	/**
	 * 断句
	 * @param essay
	 * @return
	 */
	public List<String> generatKeywords(String essay) {
		List<String> keywords = new ArrayList<String>();
		
		if(StringUtils.isNullOrEmpty(essay)) {
			throw new ParamNullException();
		}
		int step = 0;
		for(int i=0;i<essay.length(); i++) {
			char keyword = essay.charAt(i);
			for(char punctuation : punctuations) {
				if(keyword == punctuation) {
					if(i - step > 1) 
						keywords.add(essay.substring(step+1, i));
					step = i;
				}
			}
		}
		
		return keywords;
	}

	/**
	 * 判断是否包含特殊字符
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	private boolean StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}
}
