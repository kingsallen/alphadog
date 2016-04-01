package com.moseeker.common.validation.sensitivewords;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.junit.Test;

import com.moseeker.common.exception.ParamNullException;
import com.mysql.jdbc.StringUtils;

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
	
	

	@Test
	public void testStringFilter() throws PatternSyntaxException {
		String str = "*adCVs*34_a _09_b5*[/435^*&城池()^$$&*).{}+.|.)%%*(*.中国}34{45[]12.fd'*&999下面是中文的字符￥……{}【】。，；’“‘”？";
		System.out.println(str);
		System.out.println(StringFilter(str));
	}
	
	@Test
	public void testStringChar() {
		char a = 'a';
		String aa = "a";
		System.out.println(aa.equals(a));
	}

	@Test
	public void subStringTest() {
		String str = "abcdefghijklmnopqrstuvwxyz";
		System.out.println(str.substring(0, 3));
		System.out.println(str.substring(1, 1));
		System.out.println(str.substring(1, 1));
		System.out.println(str.substring(1, 2));
		System.out.println(str.substring(1, 3));
		System.out.println(str.substring(1, 4));
		System.out.println(str.substring(4, 5));
	}
}
