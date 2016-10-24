package com.moseeker.warn.dto;

/**
 * @author ltf
 * 人员信息
 */
public class Member {
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 手机号
	 */
	private String mobilephone;
	
	/**
	 * 接受通知的微信openid
	 */
	private String wechatopenid;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 状态（1:有效， 0:无效）
	 */
	private int status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getWechatopenid() {
		return wechatopenid;
	}

	public void setWechatopenid(String wechatopenid) {
		this.wechatopenid = wechatopenid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String
				.format("Member [name=%s, mobilephone=%s, wechatopenid=%s, email=%s, status=%s]",
						name, mobilephone, wechatopenid, email, status);
	}
	
}
