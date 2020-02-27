/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crypto.RSA;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.*;
import java.security.spec.*;
import java.util.HashMap;
import java.util.Map;
import crypto.Exception.*;

/**
 * RSA加密。
 * @author cjiajiaGame
 * @since Crypto-1.1
 */
public class RSA {
    private static final String CHARSET = "utf-8";
    private static final String ALGORITHM = "RSA";
    private static String str = null;
    private static String PublicKey = null, PrivateKey = null;

    /**
     * 构建一个临时的RSA对象。此对象不能用来加密或解密。
     */
    public RSA(){}
    /**
     * 构建一个新的RSA对象。参数可以按照情况填写NULL。
     * @param STR 要解密的值
     * @param publicKey 公钥（解密用）
     * @param privateKey 私钥 （加密用）
     */
    public RSA(String STR, String publicKey, String privateKey){
        str = STR;
        PublicKey = publicKey;
        PrivateKey = privateKey;
    }

    /**
     * 创建一个带公钥和私钥的Map->HashMap。
     * Map里，publicKey为公钥，privateKey为私钥。
     * @param keySize 秘钥的长度。应是64的倍数。
     * @return 带公钥和私钥Map->HashMap。
     * @throws NoSuchAlgorithmException
     * @throws KeyIvNotLengthyException
     */
    public final Map<String, String> createKeys(int keySize) throws NoSuchAlgorithmException,
            KeyIvNotLengthyException{
        if(keySize%64 != 0) throw new KeyIvNotLengthyException("秘钥长度应是64的倍数！");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        kpg.initialize(keySize);
        KeyPair keypair = kpg.generateKeyPair();
        Key publicKey = keypair.getPublic();
        String publicKeySTR = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        Key privateKey = keypair.getPrivate();
        String privateKeySTR = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> map = new HashMap<>();
        map.put("publicKey", publicKeySTR);
        map.put("privateKey", privateKeySTR);
        return map;
    }
    private RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, 
            InvalidKeySpecException{
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }
    private RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, 
            InvalidKeySpecException{
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 使用私钥加密字符串。
     * @return 正常则返回加密后的数据，否则抛出异常。构造时不声明私钥也会抛出异常。
     * @throws NullKeyException
     * @throws KeyIvNotLengthyException
     * @throws Exception
     */
    public String Encrypt() throws NullKeyException,
            Exception{
        if(PrivateKey.length() == 0 || PrivateKey == null) throw new NullKeyException("私钥为空");
        RSAPrivateKey privateKey = getPrivateKey(PrivateKey);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.encodeBase64String(cipher.doFinal(str.getBytes()));
    }

    /**
     * 使用公钥解密字符串。
     * @return 正常则返回解密后的数据，否则抛出异常。构造时不声明公钥也会抛出异常。
     * @throws NullKeyException
     * @throws crypto.Exception.KeyIvNotLengthyException
     * @throws Exception
     */
    public String Decrypt() throws NullKeyException,
            Exception{
        if(PublicKey.length() == 0 || PublicKey == null) throw new NullKeyException("公钥为空");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] encryptding = new Base64().decode(str.getBytes());
        RSAPublicKey publickey = getPublicKey(PublicKey);
        cipher.init(Cipher.DECRYPT_MODE, publickey);
        return new String(cipher.doFinal(encryptding));
    }
}
