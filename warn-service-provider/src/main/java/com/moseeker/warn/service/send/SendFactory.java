package com.moseeker.warn.service.send;

import java.util.Map;

import com.moseeker.warn.dto.Event;
/**
 * 发送消息的工厂类
 * @author zztaiwll
 *
 */
public class SendFactory{
	private Map<String,Object> map;
	public SendFactory(Map<String,Object> map){
		this.map=map;
	}
	public  void createSend(Class<? extends SendParent> c){
		SendParent send=null;
		try{
			//send=(SendParent) Class.forName(c.getName()).newInstance();
			send= c.newInstance();
			send.send(map);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
