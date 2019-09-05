package com.moseeker.common.weixin;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 二维码类型，分为场景二维码和永久二维码 
 * <p>Company: MoSeeker</P>  
 * <p>date: Oct 9, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public enum QrcodeType {

	QR_SCENE,QR_LIMIT_SCENE,QR_STR_SCENE,QR_LIMIT_STR_SCENE,;
	
	private static final List<QrcodeType> intToQrcodeType = new ArrayList<>();
	
	static {
		for (QrcodeType op : values())
			intToQrcodeType.add(op);
	}
	
	public static QrcodeType fromInt(int qrcodeType) {
		if(qrcodeType < 0 || qrcodeType >= intToQrcodeType.size()) {
			return null;
		}
	    return intToQrcodeType.get(qrcodeType);
	}
}
