/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crypto.Exception;

/**
 * 当偏移量为空时，抛出此异常。
 * @author Administrator
 */
public class NullIvException extends Exception {

    /**
     * 构建一个不带信息的异常。
     */
    public NullIvException() {
    }

    /**
     * 构建一个带信息的异常。
     * @param msg 消息
     */
    public NullIvException(String msg) {
        super(msg);
    }
}
