package com.moseeker.entity.biz;

import com.moseeker.common.util.StringUtils;
import java.net.URLEncoder;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by moseeker on 2018/4/23.
 */
public class CommonUtils {

    public static String replaceUtil(String context, String companyName, String positionName, String profileName,
            String hrName, String wechatName){

        context = StringEscapeUtils.escapeHtml4(context);
        context = context.replace(" ","&nbsp;");
        context = context.replace("\n","<br>");
        if(StringUtils.isNotNullOrEmpty(companyName)){
            context = context.replace("#公司简称#",companyName);
        }else{
            context = context.replace("#公司简称#","");
        }

        if(StringUtils.isNotNullOrEmpty(positionName)){
            context = context.replace("#职位名称#",positionName);
        }else{
            context = context.replace("#职位名称#","");
        }
        if(StringUtils.isNotNullOrEmpty(hrName)){
            context = context.replace("#HR姓名#",hrName);
        }else{
            context = context.replace("#HR姓名#","");

        }
        if(StringUtils.isNotNullOrEmpty(profileName)){
            context = context.replace("#求职者姓名#",profileName);

        }else{
            context = context.replace("#求职者姓名#","");

        }

        if(StringUtils.isNotNullOrEmpty(wechatName)){
            context = context.replace("#公众号名称#",wechatName);

        }else{
            context = context.replace("#公众号名称#","");
        }
        return  context;
    }

    public static  String appendUrl(String url, String CDN) {

        String logo = "";
        if (StringUtils.isNotNullOrEmpty(url)) {
            if (url.startsWith("http")) {
                logo = url;
            } else {
                logo = CDN + url;
            }
            if (!logo.startsWith("https") && logo.startsWith("http")) {
                logo = logo.replace("http", "https");
            }
        }
        return logo;
    }

    public static String publicKeyString="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOVnJuXqof6f0PYGO2pDvyfEURBgt22ZYeXeDRnYko6DIWP3AYsaGevIfcfoZan8DBisvZih1hLto2ct7ylMh+sgg2cpZrp3tCIlS3uV9WHGsx7uUbw6XEycUlHV4aSzBM4xzQd5wx7OMibkpIgJZ2KzN+r/XeKZ3cr9qifSqcIQIDAQAB";
    public static String privateKeyString="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM5Wcm5eqh/p/Q9gY7akO/J8RREGC3bZlh5d4NGdiSjoMhY/cBixoZ68h9x+hlqfwMGKy9mKHWEu2jZy3vKUyH6yCDZylmune0IiVLe5X1YcazHu5RvDpcTJxSUdXhpLMEzjHNB3nDHs4yJuSkiAlnYrM36v9d4pndyv2qJ9KpwhAgMBAAECgYBrcI4DNF2nYoJ3mDwzpQ7l85lPBPPBPgkx5LYkz7/UwhsahIvM/+llK0wfHu68j4SiArCkpIspyGitg2DdtWUzyz5k3RH5zOa6EYIbhx8vTlDn4pYTOhrrhwNgaCFAypSfwx+hdE+mnUYZrRMBEZHBOcmYNPJ134JY8kBKpLMNhQJBAPl6PJyJ0MZ2pHieOqpvAZnqaLbBSytuhpF/WoA5bzSXlgWXWUiNdmVWOXxnuDNlTJ/wwNsOWCdvIXkWvs9C77MCQQDTu3lGFTIQdpGkq5h35D3QH3iCynoW8XD2c8gzszYRha36QBtW3nV0mG7hmdcAgnf6pEmzAZBNH8qvivs0gzrbAkEAuO96Wvh2wYhVp+xzxMABSd3QvhlP5eRK8TSdHAx3eV8doQtu7i+fosNmXehtTfw77xyDel+JzcG96IuJ6w7NrQJASM+ad8BINCMiGJet2eTSYOTBo3CPpZ4ns4jDWwwQ1tu6pWkwPaJIj+zvjINDzXgQXE/szDMIdY0uPUm08y0BqwJADikGOxIE3LFn50EG2Da4FHBTtSpgRApmq/2H6yFFHpoak2szyaLZw2BxJXTq7fci+8OwYbPq9YwOWfxS3RmrGQ==";

    public static String encryptString(String info) throws Exception {
        //获取公钥
        PublicKey publicKey = getPublicKey(publicKeyString);
        //公钥加密
        byte[] encryptedBytes = encrypt(info.getBytes(), publicKey);
        String s= new String(Base64.getEncoder().encode(encryptedBytes));
        String code = URLEncoder.encode(s);
        return code;
    }

    public static String stringDecrypt(String token) throws Exception {
        //获取私钥
        PrivateKey privateKey = getPrivateKey(privateKeyString);

        //私钥解密
        byte[] decryptedBytes = decrypt(Base64.getDecoder().decode(token), privateKey);
        return new String(decryptedBytes);
    }





    private static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    //将base64编码后的私钥字符串转成PrivateKey实例
    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }


    //公钥加密
    private static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    //私钥解密
    private static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }


}
