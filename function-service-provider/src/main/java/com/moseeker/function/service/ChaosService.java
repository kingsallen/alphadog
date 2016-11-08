package com.moseeker.function.service;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ThirdPartyAccountPojo;
import com.moseeker.thrift.gen.common.struct.Response;

/**
 * 
 * 封装数据的chaos系统。
 * chaos系统主要提供将符合第三方渠道的职位数据发布到第三方平台，模拟登录第三方平台，获取发布职位数等功能。
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 7, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public interface ChaosService {

	/**
	 * 绑定第三方帐号
	 * @param account 第三方渠道帐号
	 * @return
	 */
	public Response bind(ThirdPartyAccountPojo account);
	
	/**
	 * 查询最新职位数
	 * @param account 第三方渠道帐号
	 * @return
	 */
	public Response fetchRemainNum(ThirdPartyAccountPojo account);
	
	/**
	 * 同步职位数据
	 * @param account 第三方渠道帐号
	 * @param positionJson 
	 * @return
	 */
	public Response synchronizePosition(ThirdPartyAccountPojo account, String positionJson);
	
	/**
	 * 刷新职位数据
	 * @param account 第三方渠道帐号
	 * @param positionId 职位编号
	 * @param jobNumber 第三方渠道职位编号
	 * @return
	 */
	public Response refreshPosition(ThirdPartyAccountPojo account, int positionId, String jobNumber);
	
	/**
	 * 仟寻职位转第三方职位
	 * @param positionId 职位编号
	 * @param channel 渠道编号
	 * @return
	 */
	public Response distortPosition(int positionId, ChannelType channel);
}
