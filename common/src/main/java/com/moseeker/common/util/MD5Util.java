package com.moseeker.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * 
 * @description 摘要生成帮助类
 * @author wjf
 * @date May 31, 2015
 * @company 大岂千寻
 * @email wjf2255@gmail.com
 */
public class MD5Util {

	String salt = "dqprism";

	/**
	 * 利用JAVA 自带的消息摘要算法生成消息摘要 如果salt 值发生变更，将会导致变更之前的消息摘要验证失败。
	 * 
	 * @param plainText
	 * @return
	 */
	public static String md5(String plainText) {

		if (plainText == null) {
			return null;
		}

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
			// return buf.toString().substring(8, 24);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public String encodePassword(String msg) {
		MessageDigest md;
		StringBuilder password = new StringBuilder();
		try {
			md = MessageDigest.getInstance("MD5");
			if (salt != null && !salt.trim().equals("")) {
				md.update((msg + salt).getBytes());
			} else {
				md.update(msg.getBytes());
			}
			byte[] bytes = md.digest();
			for (int i = 0; i < bytes.length; i++) {
				String param = Integer.toString((bytes[i] & 0xff) + 0x100, 16);
				password.append(param.substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return password.toString();
	}

	/**
	 * 检验消息摘要 只能校验encodePassword() 。写此方法用于方便测试，请慎用！
	 * 
	 * @param password
	 * @param md5
	 * @return
	 */
	@Deprecated
	public boolean verify(String password, String md5) {
		if (password == null || md5 == null || password.trim().equals("") || md5.trim().equals("")) {
			return false;
		}
		if (encodePassword(password).equals(md5)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * SHA（Secure Hash Algorithm，安全散列算法）是消息摘要算法的一种，被广泛认可的MD5算法的继任者。
	 * SHA算法家族目前共有SHA-0、SHA-1、SHA-224、SHA-256、SHA-384和SHA-512五种算法，
	 * 通常将后四种算法并称为SHA-2算法
	 * 
	 * @param msg
	 * @return
	 */
	public static String encryptSHA(String msg) {
		return encryptSHA(msg, null);
	}

	/**
	 * SHA（Secure Hash Algorithm，安全散列算法）是消息摘要算法的一种，被广泛认可的MD5算法的继任者。
	 * SHA算法家族目前共有SHA-0、SHA-1、SHA-224、SHA-256、SHA-384和SHA-512五种算法，
	 * 通常将后四种算法并称为SHA-2算法
	 * 
	 * @param msg
	 * @return
	 */
	public static String encryptSHA(String msg, String salt) {
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			if(!StringUtils.isNullOrEmpty(salt)) {
				md.update(salt.getBytes());
			}
			byte[] bytes = md.digest(msg.getBytes());
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
		} catch (Exception e) {

		}

		return sb.toString();
	}

	/**
	 * [list] [*]SHA-1 (Simplest one – 160 bits Hash)
	 * 
	 * [*]SHA-256 (Stronger than SHA-1 – 256 bits Hash)
	 * 
	 * [*]HA-384 (Stronger than SHA-256 – 384 bits Hash)
	 * 
	 * [*]SHA-512 (Stronger than SHA-384 – 512 bits Hash)
	 * 
	 * [/list]
	 * 
	 * @return
	 */
	/*private static String getSaltSHA1() {
		SecureRandom sr;
		byte[] salt = new byte[16];
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
			sr.nextBytes(salt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return salt.toString();
	}*/

	/**
	 * PBKDF2加密
	 * 
	 * @param msg
	 * @return
	 */
	public String encryptPBKDF2(String msg) {
		try {
			int iterations = 1000;
			char[] chars = msg.toCharArray();
			byte[] salt = getSalt1().getBytes();

			PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = skf.generateSecret(spec).getEncoded();

			return iterations + toHex(salt) + toHex(hash);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 转化十六进制
	 * 
	 * @param array
	 * @return
	 */
	private String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	/**
	 * 盐值的原理非常简单，就是先把密码和盐值指定的内容合并在一起，再使用md5对合并后的内容进行演算，
	 * 这样一来，就算密码是一个很常见的字符串，再加上用户名，最后算出来的md5值就没那么容易猜出来了。 因为攻击者不知道盐值的值，也很难反算出密码原文。
	 * 
	 * @return
	 */
	private String getSalt1() {
		SecureRandom sr;
		byte[] salt = new byte[16];
		try {
			sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			sr.nextBytes(salt);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return salt.toString();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
