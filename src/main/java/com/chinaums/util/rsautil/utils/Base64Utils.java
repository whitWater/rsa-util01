package com.chinaums.util.rsautil.utils;

import org.apache.commons.codec.binary.Base64;


/** */

/**
 * <p>
 * BASE64编码解码工具包
 * </p>
 * <p>
 * 依赖javabase64-1.3.1.jar
 * </p>
 * 
 * @author IceWee
 * @date 2012-5-19
 * @version 1.0
 */
public class Base64Utils {

    /** *//**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;
    
    /** *//**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     * 
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) {
        return Base64.decodeBase64(base64.getBytes());
    }
    
    public static byte[] decode(byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }
    
    /** *//**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     * 
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes){
        return new String(Base64.encodeBase64(bytes));
    }
    
}
