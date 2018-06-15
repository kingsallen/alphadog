package com.moseeker.useraccounts.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author cjm
 * @date 2018-06-15 9:48
 **/
public class AESUtils {
    public static final String CIPHER_ALGORITHM_AES = "AES";
    public static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
    public static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
    public static final String CIPHER_ALGORITHM_CBC_NoPadding = "AES/CBC/NoPadding";

    private static final String ALGORITHM_MD5 = "md5";
    private static final String CHARSET = "UTF-8";
    private static final String ALGORITHM_AES = "AES";


    public static void main(String[] args) throws Exception {
        byte[] key = getAESKeyBytes("01234^!@#$%56789");
        byte[] source = "2892c63f12e0e8849f2a7dd981375331".getBytes(CHARSET);

        // 解密密文
        byte[] target = encryptOrDecrypt(source, key, CIPHER_ALGORITHM_CBC_NoPadding, Cipher.DECRYPT_MODE);
        System.out.println("加密前【" + new String(source, CHARSET) + "】\n加密后【" + "2892c63f12e0e8849f2a7dd981375331" + "】\n解密后【" + new String(target, CHARSET) + "】");
        //如果原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n，其它情况下加密数据长度等于16*(n+1)
        //在不足16的整数倍的情况下，假如原始数据长度等于16*n+m（m小于16），除了NoPadding填充之外的任何方式，加密数据长度都等于16*(n+1)
        System.out.println("加密前字节数【" + source.length + "】加密后字节数【" + "2892c63f12e0e8849f2a7dd981375331".length() + "】解密后字节数【" + target.length + "】\n");
    }

    /**
     * 加密或解密。加密和解密用的同一个算法和密钥
     * @param source    要加密或解密的数据
     * @param key    密钥
     * @param transformation
     * @param mode   加密或解密模式。值请选择Cipher.DECRYPT_MODE或Cipher.ENCRYPT_MODE
     * @return         加密或解密后的数据
     */
    public static byte[] encryptOrDecrypt(byte[] source, byte[] key, String transformation, int mode) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        //密钥
        Key secretKey = new SecretKeySpec(key, ALGORITHM_AES);
        if (transformation.equals(CIPHER_ALGORITHM_CBC) || transformation.equals(CIPHER_ALGORITHM_CBC_NoPadding)) {
            //指定一个初始化向量 (Initialization vector，IV)， IV 必须是16位
            cipher.init(mode, secretKey, new IvParameterSpec(getIV()));
            return cipher.doFinal(source);
        } else {
            cipher.init(mode, secretKey);
            return cipher.doFinal(source);
        }
    }
    /**
     * 根据字符串生成AES的密钥字节数组<br>
     */
    public static byte[] getAESKeyBytes(String sKey) throws Exception {
        //获得指定摘要算法的 MessageDigest 对象
        MessageDigest md = MessageDigest.getInstance(ALGORITHM_MD5);
        //使用指定的字节更新摘要（继续多部分加密或解密操作，以处理其他数据部分）
        md.update(sKey.getBytes("ASCII"));
        //获得密文。注意：长度为16而不是32。一个字节(byte)占8位(bit)
        return md.digest();
    }

    private static void test2(byte[] source, byte[] key, String mode) throws Exception {
        //生成的密文
        byte[] cryptograph = encryptOrDecrypt(source, key, mode, Cipher.ENCRYPT_MODE);
        //通过Base64编码为ASCII字符后传输
        String cryptographStr = Base64.getEncoder().encodeToString(cryptograph);
        //收到后先用Base64解码
        byte[] targetBase64 = Base64.getDecoder().decode(cryptographStr.getBytes(CHARSET));
        // 解密密文
        byte[] target = encryptOrDecrypt(targetBase64, key, mode, Cipher.DECRYPT_MODE);
        System.out.println("加密前【" + new String(source, CHARSET) + "】\n加密后【" + cryptographStr + "】\n解密后【" + new String(target, CHARSET) + "】");
        //如果原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n，其它情况下加密数据长度等于16*(n+1)
        //在不足16的整数倍的情况下，假如原始数据长度等于16*n+m（m小于16），除了NoPadding填充之外的任何方式，加密数据长度都等于16*(n+1)
        System.out.println("加密前字节数【" + source.length + "】加密后字节数【" + cryptograph.length + "】解密后字节数【" + target.length + "】\n");
    }

    private static final byte[] getIV() throws Exception {
        return "01234^!@#$%56789".getBytes(CHARSET);
    }


}
