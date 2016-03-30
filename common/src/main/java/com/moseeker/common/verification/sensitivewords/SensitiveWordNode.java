package com.moseeker.common.verification.sensitivewords;

import java.util.HashSet;

/**
 * 
 * @description 敏感词节点
 * @author wjf
 * @date Jun 24, 2015
 * @company 大岂千寻
 * @email wjf2255@gmail.com
 */
public class SensitiveWordNode {

	private char value;				//敏感字
	private int step = 0;			//敏感词层级
	private boolean sensitive;		//是否是敏感词 用于在“毛泽”是敏感词，“毛泽东”也是敏感词时，能够区分出“毛泽”是敏感词来
	private SensitiveWordNode fatherNode;			//父节点
	private HashSet<SensitiveWordNode> children = null;			//叶子节点
	
	/**
	 * 添加敏感词
	 * @param words
	 */
	public void addDescendants(String words) {
		if(words != null && words.length() > 0) {
			SensitiveWordNode currentNode = this;
			for(int i=0; i<words.length();i++) {
				boolean flag = false; 
				if(i == words.length() -1) {
					flag = true;
				}
				char sensitiveWord = words.charAt(i);
				SensitiveWordNode child = currentNode.addChildAndReturn(sensitiveWord, flag);
				currentNode = child;
			}
		}
	}
	
	/*private void addChild(char sensitiveWord) {
		if(this.children == null || this.children.size() == 0) {
			this.children = new HashSet<SensitiveWordNode>();
			SensitiveWordNode swf = new SensitiveWordNode();
			swf.setStep(this.step + 1);
			swf.setValue(sensitiveWord);
			swf.setFatherNode(this);
			this.children.add(swf);
		} else {
			boolean flag = false;
			for(SensitiveWordNode swf : this.getChildren()) {
				if(swf.getValue() == sensitiveWord) {
					flag = true;
					break;
				}
			}
			if(!flag) {
				SensitiveWordNode swf = new SensitiveWordNode();
				swf.setStep(this.step + 1);
				swf.setValue(sensitiveWord);
				swf.setFatherNode(this);
				this.children.add(swf);
			}
		}
	}*/
	
	/**
	 * 添加敏感词节点。flag2用于处理敏感词重叠的问题，比如“毛泽”是敏感词，“毛泽东”也是敏感词。
	 * @param sensitiveWord 敏感词中的字符
	 * @param flag2 敏感词最后字符
	 * @return
	 */
	private SensitiveWordNode addChildAndReturn(char sensitiveWord, boolean flag2) {
		if(this.children == null || this.children.size() == 0) {
			this.children = new HashSet<SensitiveWordNode>();
			SensitiveWordNode swf = new SensitiveWordNode();
			swf.setStep(this.step + 1);
			swf.setValue(sensitiveWord);
			swf.setFatherNode(this);
			swf.setSensitive(flag2);
			this.children.add(swf);
			return swf;
		} else {
			SensitiveWordNode swf1 = null;
			boolean flag = false;
			for(SensitiveWordNode swf : this.getChildren()) {
				if(swf.getValue() == sensitiveWord) {
					flag = true;
					swf1 = swf;
					if(flag2) {
						swf.setSensitive(flag2);
					}
					break;
				}
			}
			if(!flag) {
				swf1 = new SensitiveWordNode();
				swf1.setStep(this.step + 1);
				swf1.setValue(sensitiveWord);
				swf1.setFatherNode(this);
				swf1.setSensitive(flag2);
				this.children.add(swf1);
				return swf1;
			}
			return swf1;
		}
	}
	
	public char getValue() {
		return value;
	}
	public void setValue(char value) {
		this.value = value;
	}
	public SensitiveWordNode getFatherNode() {
		return fatherNode;
	}
	public void setFatherNode(SensitiveWordNode fatherNode) {
		this.fatherNode = fatherNode;
	}
	public HashSet<SensitiveWordNode> getChildren() {
		return children;
	}
	public void setChildren(HashSet<SensitiveWordNode> children) {
		this.children = children;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	
	public boolean isSensitive() {
		return sensitive;
	}

	public void setSensitive(boolean sensitive) {
		this.sensitive = sensitive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + step;
		result = prime * result + value;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SensitiveWordNode other = (SensitiveWordNode) obj;
		if (step != other.step)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
}
