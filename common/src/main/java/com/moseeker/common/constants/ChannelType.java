package com.moseeker.common.constants;

import com.moseeker.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 渠道 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 7, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public enum ChannelType {

	JOB51(1, "51job","51job") {
		@Override
		public String getOrigin(String origin) {
			String result;
			if(StringUtils.isNullOrEmpty(origin)) {
				result = String.valueOf(1000000000000000l);
			} else {
				if(origin.length() >= 16) {
					if(origin.charAt(origin.length()-16) == '0') {
						result = String.valueOf(Long.valueOf(origin)+1000000000000000l);
					} else {
						result = origin;
					}
				} else {
					result = String.valueOf(Long.valueOf(origin)+1000000000000000l);
				}
			}
			return result;
		}
	}, LIEPIN(2, "liepin","猎聘") {
		@Override
		public String getOrigin(String origin) {
			String result;
			if(StringUtils.isNullOrEmpty(origin)) {
				result = String.valueOf(100000000000000000l);
			} else {
				if(origin.length() >= 18) {
					if(origin.charAt(origin.length()-18) == '0') {
						result = String.valueOf(Long.valueOf(origin)+100000000000000000l);
					} else {
						result = origin;
					}
				} else {
					result = String.valueOf(Long.valueOf(origin)+100000000000000000l);
				}
			}
			return result;
		}
	}, ZHILIAN(3, "zhaopin","智联") {
		@Override
		public String getOrigin(String origin) {
			String result;
			if(StringUtils.isNullOrEmpty(origin)) {
				result = String.valueOf(10000000000000000l);
			} else {
				if(origin.length() >= 17) {
					if(origin.charAt(origin.length()-17) == '0') {
						result = String.valueOf(Long.valueOf(origin)+10000000000000000l);
					} else {
						result = origin;
					}
				} else {
					result = String.valueOf(Long.valueOf(origin)+10000000000000000l);
				}
			}
			return result;
		}
	}, LINKEDIN(4, "linkedin","领英") {
		@Override
		public String getOrigin(String origin) {

			return null;
		}
	}, ALIPAY(5, "alipay","支付宝") {
		@Override
		public String getOrigin(String origin) {
			String result;
			if(StringUtils.isNullOrEmpty(origin)) {
				result = String.valueOf(1000000000000000000l);
			} else {
				if(origin.length() >= 19) {
					if(origin.charAt(origin.length()-19) == '0') {
						result = String.valueOf(Long.valueOf(origin)+1000000000000000000l);
					} else {
						result = origin;
					}
				} else {
					result = String.valueOf(Long.valueOf(origin)+1000000000000000000l);
				}
			}
			return result;
		}
	};

	private ChannelType(int value, String name,String alias) {
		this.value = value;
		this.name = name;
		this.alias = alias;
	}

	private int value = 0;				//渠道值
	private String name = null;			//渠道名称
	private String alias = null;        //渠道别名

	public abstract String getOrigin(String origin);

	private static final String BINDING = "position/binding"; //帐号绑定请求名称
	private static final String REMAIN_NUM = "position/remain"; //帐号绑定请求名称

	private static final Map<Integer, ChannelType> intToEnum
			= new HashMap<Integer, ChannelType>();

	static { // Initialize map from constant name to enum constant
		for (ChannelType op : values())
			intToEnum.put(op.getValue(), op);
	}

	public static ChannelType instaceFromInteger(int value) {
		return intToEnum.get(value);
	}

	public int getValue() {
		return this.value;
	}

	/**
	 * 返回该渠道的绑定请求地址
	 * @param domain
	 * @param domain chaos域名
	 * @return
	 */
	public String getBindURI(String domain) {
		return domain+"/"+name+"/"+BINDING;
	}

	/**
	 * 返回该渠道的绑定请求地址
	 * @param domain chaos域名
	 * @return
	 */
	public String getRemain(String domain) {
		return domain+"/"+name+"/"+REMAIN_NUM;
	}

	/**
	 * 返回该渠道的可发布职位数的请求地址
	 * @param domain chaos域名
	 * @return
	 */
	public String getRemainURI(String domain) {
		return domain+"/"+name+"/"+REMAIN_NUM;
	}

	public String getAlias() {
		return alias;
	}

	/**
	 * 简历回收 中  profile_profile.source（简历来源）的值
	 * @return
	 */
	public int getRetrievalSource() {
		return 210+value;
	}
}
