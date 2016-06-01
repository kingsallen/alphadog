package com.moseeker.common.validation;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moseeker.common.exception.ParamNullException;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.sensitivewords.SensitiveWordNode;

/**
 * 
 * 敏感词词库管理类 
 * date Aug 5, 2015
 * company 大岂仟寻
 * author wjf
 * email wjf2255@gmail.com
 */
public class SensitiveWordDB {
	
	Logger logger = LoggerFactory.getLogger(SensitiveWordDB.class);

	private SensitiveWordNode rootNode = new SensitiveWordNode(); //根节点

	private volatile static SensitiveWordDB instance;
	
	//分词标识
	private	char[] punctuations = new char[]{'`','~','!','@','#','$','%','^','&','*','(',')','+','=','|','{','}','\'',':',';','‘',',','\\','[','/',']','.','<','>','/','?','~','！','@','#','￥','%','…','&','*','（','）','-','+','|','{','}','【','】','‘','；','：','”','“','’','。','，','、','？','\t','\n','\r','\b','\f'};

	private SensitiveWordDB() {
	}

	@SuppressWarnings("rawtypes")
	public static SensitiveWordDB getSingleton() {
		if (instance == null) {
			synchronized (SensitiveWordDB.class) {
				if (instance == null) {
					instance = new SensitiveWordDB();
					Properties props = new Properties();
					try {
						//加载配置文件
						ConfigPropertiesUtil configUtil = ConfigPropertiesUtil.getInstance();
						InputStream in = new BufferedInputStream(new FileInputStream(configUtil.get("sensitiveWords", String.class)));
						props.load(in);
						Set keyValue = props.keySet();
						for (Iterator it = keyValue.iterator(); it.hasNext();)
						{
							String sensitiveWord = new String(((String)it.next()).getBytes("ISO-8859-1"),"utf8");
							instance.addSensitiveWords(sensitiveWord);
						}
							
					} catch (Exception e) {
						LoggerFactory.getLogger(SensitiveWordDB.class).error("error", e);
						//send a warning 
					}
				}
			}
		}
		return instance;
	}
	
	@SuppressWarnings("rawtypes")
	public static SensitiveWordDB getSingleton(String sensitiveWordsFilePath) {
		if (instance == null) {
			synchronized (SensitiveWordDB.class) {
				if (instance == null) {
					instance = new SensitiveWordDB();
					Properties props = new Properties();
					try {
						InputStream in = new BufferedInputStream(new FileInputStream(sensitiveWordsFilePath));
						props.load(in);
						Set keyValue = props.keySet();
						for (Iterator it = keyValue.iterator(); it.hasNext();)
						{
							String sensitiveWord = new String(((String)it.next()).getBytes("ISO-8859-1"),"utf8");
							instance.addSensitiveWords(sensitiveWord);
						}
							
					} catch (Exception e) {
						instance.logger.error(e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
		return instance;
	}

	/**
	 * 添加敏感词
	 * 
	 * @param sensitiveWords
	 */
	private void addSensitiveWords(String sensitiveWords) {
		if (FormCheck.specialCharactor(sensitiveWords)) {
			logger.error(sensitiveWords);
			// new SpecialCharException();
		} else {
			rootNode.addDescendants(sensitiveWords);
		}
	}

	/**
	 * 查看敏感词词库中的敏感词
	 * 
	 * @return
	 */
	public List<String> getSensitiveWords() {
		List<String> sensitiveWordsArray = new ArrayList<String>();

		if (rootNode.getChildren() != null && rootNode.getChildren().size() > 0) {
			String sensitiveWords = "";
			traversalSensitiveTree(rootNode, sensitiveWords,
					sensitiveWordsArray);
		}

		return sensitiveWordsArray;
	}
	/**
	 * 检查需要判断的内容中是否存在敏感词
	 * @param essay
	 * @return true:查找到敏感词，false：没有查找到敏感词
	 */
	public boolean sensitiveExamin(String essay) {
		boolean flag = false;
		
		List<String> essays = generatKeywords(essay);
		if(essays != null && essays.size() > 0) {
			for(String ess : essays) {
				try {
					boolean isCharacter = FormCheck.isCharacter(ess);
					if(isCharacter) {
						flag = examinEssay(ess, isCharacter);
					} else {
						for(int i=0; i< ess.length(); i++) {
							if(examinEssay(ess.substring(i, ess.length()), isCharacter)) {	//逐个去匹配敏感词
								flag = true;
								break;
							}
						}
					}
				} catch (ParamNullException e) {
					continue;
				}
				if(flag) {
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 检查句子或者短语或者词组中是否包含敏感词。
	 * @param ess 被检查的文本
	 * @param isCharacter 是否是英文 如果包含英文，无需逐个匹配
	 * @return true:包含敏感词; false:不包含敏感词
	 */
	private boolean examinEssay(String ess, boolean isCharacter) {
		boolean flag = false; //判断是否是敏感词
		
		if(!StringUtils.isNullOrEmpty(ess)) {
			HashSet<SensitiveWordNode> children = rootNode.getChildren();
			
			for(int i=0; i< ess.length(); i++) {
				boolean sameKeyWord = false;  //如果遇到关键词，没有必要继续循环下去
				boolean endSensitiveDB = false; //如果已经到达敏感词库结尾,直接退出
				char essayChar = ess.charAt(i);
				if(children != null && children.size() > 0) {
					for(SensitiveWordNode node : children) {
						if(essayChar == node.getValue()) {
							if(node.isSensitive() && (!isCharacter || i == ess.length()-1)) {
								flag = true;
								break;
							}
							children = node.getChildren();
							if(children == null) {
								endSensitiveDB = true;
								break;
							}
							sameKeyWord = true;
							break;   //如果遇到相同关键词，没有必要继续循环属性结构
						}
					}
					if(flag) {
						break;
					}
				}
				if(endSensitiveDB) {
					break;
				}
				if(!sameKeyWord) {
					break;
				}
			}
		}
		return flag;
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
					if(i - step > 0)  {
						keywords.addAll(fetchCharactor(essay.substring(step, i)));
					}
					step = i+1;
				}
			}
		}
		if(step == 0) {
			keywords.addAll(fetchCharactor(essay));
		} else if(step < essay.length() ) {
			keywords.addAll(fetchCharactor(essay.substring(step, essay.length())));
		}
		return keywords;
	}
	
	private List<String> fetchCharactor(String essay) {
		List<String> essays = new ArrayList<String>();
		List<Integer> splitIndex = new ArrayList<Integer>();
		
		boolean charactorFlag = false;
		for(int j=0; j< essay.length(); j++) {
			try {
				if(FormCheck.isCharacter(String.valueOf(essay.charAt(j)))) {
					if(!charactorFlag) {
						splitIndex.add(j);
					}
					charactorFlag = true;
				} else {
					if(charactorFlag) {
						splitIndex.add(j);
					}
					charactorFlag = false;
				}
			} catch (ParamNullException e) {
				if(charactorFlag) {
					splitIndex.add(j);
				}
				charactorFlag = false;
			}
		}
		
		if(splitIndex.size() > 0) {
			int startIndex = 0;
			for(int i : splitIndex) {
				if(i == 0 || i <= startIndex) {
					continue;
				}
				if(i > startIndex) {
					essays.add(essay.substring(startIndex, i));
					startIndex = i;
				}
			}
			if(startIndex > 0 && startIndex < essay.length()-1) {
				essays.add(essay.substring(startIndex, essay.length()));
			}
		}
		if(essays.size() == 0) {
			essays.add(essay);
		}
		return essays;
	}

	/**
	 * 遍历叶子节点
	 * 
	 * @param node
	 * @param sensitiveWords
	 * @param sensitiveWordsArray
	 */
	private void traversalSensitiveTree(SensitiveWordNode node,
			String sensitiveWords, List<String> sensitiveWordsArray) {
		String stepSensitiveWords = "";
		if (node.getStep() > 0) {
			stepSensitiveWords = sensitiveWords + node.getValue();
			// 如果是敏感词，添加到敏感词列表中
			if (node.isSensitive()) {
				sensitiveWordsArray.add(stepSensitiveWords);
			}
		}
		if (node.getChildren() != null && node.getChildren().size() > 0) {
			for (SensitiveWordNode child : node.getChildren()) {
				traversalSensitiveTree(child, stepSensitiveWords,
						sensitiveWordsArray);
			}
		}
	}
}
