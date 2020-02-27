/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crypto.AES;

/**
 *
 * @author Administrator
 */
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import crypto.Exception.*;

/**
 * AES加密ECB模式。
 * @author cjiajiaGame
 * @since Crypto-1.0
 */
public class ECB {
    static String str = null;
    static String key = null;

    /**
     * 构建一个新的AES-ECB加密对象。
     * @param STR 解密或加密的字符串
     * @param KEY 秘钥
     */
    public ECB(String STR, String KEY){
        str = STR;
        key = KEY;
    }

    /**
     * 随机获取一个秘钥。
     * @return 秘钥
     */
    public static final String GetKey(){
        String chars = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
        StringBuffer value = new StringBuffer();
        for(int i = 0; i < 16; i++){
            value.append(chars.charAt((int) (Math.random()* 62)));
        }
        return value.toString();
    }

    /**
     * 加密字符串。
     * @return 正常则返回加密后的数据，秘钥错误或其他错误均会被抛出。
     * @throws Exception 其他错误
     * @throws NullKeyException 秘钥为空
     * @throws KeyIvNotLengthyException 秘钥的长度不够16位
     */
    public static String Encrypt() throws Exception, 
            NullKeyException, 
            KeyIvNotLengthyException{
        if(key == null) throw new NullKeyException();
        if(key.length() != 16) throw new KeyIvNotLengthyException("秘钥的长度不够16位");
        byte[] raw = key.getBytes("utf-8");
        SecretKeySpec Spec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, Spec);
        byte[] encrypted = cipher.doFinal(str.getBytes("utf-8"));
        return new Base64().encodeToString(encrypted);
    }
    
    /**
     * 解密字符串。
     * @return 正常则返回解密后的数据，秘钥或其他错误均会被抛出。
     * @throws Exception 其他错误
     * @throws NullKeyException 秘钥为空
     * @throws KeyIvNotLengthyException 秘钥长度不够16位
     */
    public static String Decrypt() throws Exception, 
            NullKeyException,
            KeyIvNotLengthyException{
        if (key == null) throw new NullKeyException();
        if (key.length() != 16) throw new KeyIvNotLengthyException("秘钥的长度不够16位");
        byte[] raw = key.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] encrypted1 = new Base64().decode(str);//先用base64解密
        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original,"utf-8");
        return originalString;
    }
}
