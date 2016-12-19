package com.moseeker.position.service.position.pojo;

/**
 * 第三方渠道城市
 * @author wjf
 *
 */
public class ThirdPartyCityPojo {

	private String moseekerCode;
	private String thirdPartyCityCode;
	private String thirdPartyCityName;
	private String thirdPartyParentCode;
	private int channel;
	
	public String getMoseekerCode() {
		return moseekerCode;
	}
	public void setMoseekerCode(String moseekerCode) {
		this.moseekerCode = moseekerCode;
	}
	public String getThirdPartyCityCode() {
		return thirdPartyCityCode;
	}
	public void setThirdPartyCityCode(String thirdPartyCityCode) {
		this.thirdPartyCityCode = thirdPartyCityCode;
	}
	public String getThirdPartyCityName() {
		return thirdPartyCityName;
	}
	public void setThirdPartyCityName(String thirdPartyCityName) {
		this.thirdPartyCityName = thirdPartyCityName;
	}
	public String getThirdPartyParentCode() {
		return thirdPartyParentCode;
	}
	public void setThirdPartyParentCode(String thirdPartyParentCode) {
		this.thirdPartyParentCode = thirdPartyParentCode;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
}
