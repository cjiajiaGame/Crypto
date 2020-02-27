/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crypto.Base;
import org.apache.commons.codec.binary.Base32;

/**
 * Base32加密。
 * @author cjiajiaGame
 * @since Crypto-1.0
 */
public class b32 {
    private static String str = null;

    /**
     * 构建一个新的Base32加密对象。
     * @param STR 解密或加密的字符串
     */
    public b32(String STR){
        str = STR;
    }

    /**
     * 加密字符串。
     * @return 返回加密后的数据
     */
    public static String Encrypt(){
        return new Base32().encodeToString(str.getBytes());
    }
    /**
     * 解密字符串。
     * @return 返回解密后的数据
     */
    public static String Decrypt(){
        return new String(new Base32().decode(str));
    }
}
