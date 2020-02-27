/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crypto.AES;
import crypto.Exception.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import org.apache.commons.codec.binary.Base64;

/**
 * AES加密CBC模式。
 * @author cjiajiaGame
 * @since Crypto-1.0
 */
public class CBC {
    private static String str = null, key = null, iv = null;

    /**
     * 构建一个新的AES-CBC加密对象。
     * @param STR 解密或加密的字符串
     * @param KEY 秘钥
     * @param IV 偏移量
     */
    public CBC(String STR, String KEY, String IV){
        str = STR;
        key = KEY;
        iv = IV;
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
     * 随机获取一个偏移量。
     * @return 秘钥
     */
    public static final String GetIV(){
        String chars = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
        StringBuffer value = new StringBuffer();
        for(int i = 0; i < 16; i++){
            value.append(chars.charAt((int) (Math.random()* 62)));
        }
        return value.toString();
    }

    /**
     * 加密字符串。
     * @return 正常则返回加密后的数据，秘钥、偏移量错误或其他错误均会被抛出。
     * @throws Exception 其他错误
     * @throws NullKeyException 秘钥为空
     * @throws NullIvException 偏移量为空
     * @throws KeyIvNotLengthyException 秘钥或偏移量的长度不够16位
     */
    public static String Encrypt() throws Exception,
            NullKeyException,
            NullIvException,
            KeyIvNotLengthyException{
        if(key == null) throw new NullKeyException();
        if(key.length() != 16) throw new KeyIvNotLengthyException("秘钥的长度不够16位");
        if(iv == null) throw new NullIvException();
        if(iv.length() != 16) throw new KeyIvNotLengthyException("偏移量(IV)的长度不够16位");
        byte[] raw = key.getBytes("utf-8");
        SecretKeySpec key_spec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec IV = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, key_spec, IV);
        byte[] encrypted = cipher.doFinal(str.getBytes());
        return new Base64().encodeToString(encrypted);
    }
    /**
     * 解密字符串。
     * @return 正常则返回解密后的数据，秘钥、偏移量错误或其他错误均会被抛出。
     * @throws Exception 其他错误
     * @throws NullKeyException 秘钥为空
     * @throws NullIvException 偏移量为空
     * @throws KeyIvNotLengthyException 秘钥或偏移量的长度不够16位
     */
    public static String Decrypt() throws Exception,
            NullKeyException,
            NullIvException,
            KeyIvNotLengthyException{
        if(key == null) throw new NullKeyException();
        if(key.length() != 16) throw new KeyIvNotLengthyException("秘钥的长度不够16位");
        if(iv == null) throw new NullIvException();
        if(iv.length() != 16) throw new KeyIvNotLengthyException("偏移量(IV)的长度不够16位");
        SecretKeySpec key_spec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec IV = new IvParameterSpec(iv.getBytes("utf-8"));
        cipher.init(Cipher.DECRYPT_MODE, key_spec, IV);
        byte[] encryptding = new Base64().decode(str);
        return new String(cipher.doFinal(encryptding));
    }
}