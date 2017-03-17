package com.moseeker.common.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.xml.bind.DatatypeConverterInterface;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ltf
 *
 * 2017年3月9日
 */
public class DESCoder {
	
	public static final Logger log = LoggerFactory.getLogger(DESCoder.class);
	
	/**
	 * 加密方式
	 */
	public static final String ALGORITHM = "DES";
	
	/**
	 * 秘钥
	 */
	public static final String SECRETKEY = "alphadog";
	
	/**
	 * 秘钥转换
	 * @param key
	 * @return 
	 * @throws Exception
	 */
	public static Key toKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}
	
	/**
	 * 加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static String encrypt(String data) {
		try {
			Key k = toKey(SECRETKEY.getBytes());
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(cipher.ENCRYPT_MODE, k);
			return new String(Hex.encodeHex(cipher.doFinal(data.getBytes())));
		} catch (Exception e) {
			log.error("DES encrypt fail", e);
		}
		return "";
	}
	
	/**
	 * 解密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static String decrypt(String data) {
		try {
			Key k = toKey(SECRETKEY.getBytes());
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(cipher.DECRYPT_MODE, k);
			
			return new String(cipher.doFinal(Hex.decodeHex(data.toCharArray())));
		} catch (Exception e) {
			log.error("DES decrypt fail", e);
		}
		return "";
	}
	
	public static void main(String[] args) {
		String str = "今晚打老虎";
		try {
			String encrypt = encrypt(str);
			System.out.println("密文：" + encrypt);
			System.out.println("明文: " + decrypt(encrypt));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
