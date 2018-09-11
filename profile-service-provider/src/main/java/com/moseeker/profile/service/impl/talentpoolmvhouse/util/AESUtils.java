package com.moseeker.profile.service.impl.talentpoolmvhouse.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author cjm
 * @date 2018-06-15 9:48
 **/
public class AESUtils {

    private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
    private static final String CIPHER_ALGORITHM_CBC_NOPADDING = "AES/CBC/NoPadding";
    private static final String CHARSET = "UTF-8";
    private static final String ALGORITHM_AES = "AES";
    private static final String PRIVATE_KEY = "01234^!@#$%56789";

    /**
     * aes解密
     * @param
     * @author  cjm
     * @date  2018/6/15
     * @return
     */
    public static byte[] decrypt(String source) throws Exception {
        return encryptOrDecrypt(parseHexStr2Byte(source), PRIVATE_KEY.getBytes(), CIPHER_ALGORITHM_CBC_NOPADDING, Cipher.DECRYPT_MODE);
    }

    /**
     * 加密或解密。加密和解密用的同一个算法和密钥
     *
     * @param source         要加密或解密的数据
     * @param key            密钥
     * @param transformation
     * @param mode           加密或解密模式。值请选择Cipher.DECRYPT_MODE或Cipher.ENCRYPT_MODE
     * @return 加密或解密后的数据
     */
    public static byte[] encryptOrDecrypt(byte[] source, byte[] key, String transformation, int mode) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        //密钥
        Key secretKey = new SecretKeySpec(key, ALGORITHM_AES);
        if (transformation.equals(CIPHER_ALGORITHM_CBC) || transformation.equals(CIPHER_ALGORITHM_CBC_NOPADDING)) {
            //指定一个初始化向量 (Initialization vector，IV)， IV 必须是16位
            cipher.init(mode, secretKey, new IvParameterSpec(getIV()));
            return cipher.doFinal(source);
        } else {
            cipher.init(mode, secretKey);
            return cipher.doFinal(source);
        }
    }

    private static byte[] getIV() throws Exception {
        return PRIVATE_KEY.getBytes(CHARSET);
    }


    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
