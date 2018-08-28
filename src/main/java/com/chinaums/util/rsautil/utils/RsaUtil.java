package com.chinaums.util.rsautil.utils;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

/** */

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 *
 * @author IceWee
 * @date 2012-4-26
 * @version 1.0
 */
public class RsaUtil {

    /** *//**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** *//**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /** *//**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** *//**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    /**
     *私钥的模
     */
    public static final String PRIVATE_KEY_MODULE = "privateKeyModulus";
    /**
     *私钥的指数
     */
    public static final String PRIVATE_KEY_EXPONENT = "privateKeyExponent";
    /**
     *公钥的模
     */
    public static final String PUBLIC_KEY_MODULE = "publicKeyModulus";
    /**
     *公钥的指数
     */
    public static final String PUBLIC_KEY_EXPONENT = "publicKeyExponent";

    /** *//**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** *//**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;



    /** *//**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>

     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair(int keySize) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(keySize);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        System.out.println("privateKey模："+ privateKey.getModulus());
        System.out.println("privateKey指数:" + privateKey.getPrivateExponent());
        System.out.println("prviateKeyAl:" + privateKey.getAlgorithm());
        System.out.println("privateKey-format : " + privateKey.getFormat());
        BigInteger publicModulus = privateKey.getModulus();

        System.out.println("publicKey模：" + publicKey.getModulus());
        System.out.println("publicKey指数：" + publicKey.getPublicExponent());
        System.out.println("publicKeyAl :" + publicKey.getAlgorithm());
        System.out.println("privateKey-format : " + publicKey.getFormat());
        System.out.println("publicKey指数base64格式：" + Base64Utils.encode(publicKey.getPublicExponent().toByteArray()));

        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        keyMap.put(PRIVATE_KEY_MODULE, privateKey.getModulus().toString());
        keyMap.put(PRIVATE_KEY_EXPONENT, privateKey.getPrivateExponent().toString());
        keyMap.put(PUBLIC_KEY_MODULE, publicKey.getModulus().toString());
        keyMap.put(PUBLIC_KEY_EXPONENT, publicKey.getPublicExponent().toString());
        getPublicKeyFromPublicModules(publicModulus);
        return keyMap;
    }

    public static void main(String args[]) throws Exception{
        Map<String, Object> map = genKeyPair(1024);
        String privateKey = getPrivateKey(map);
        String publicKey = getPublicKey(map);
        System.out.println("privateKey = "+privateKey);
        System.out.println("publicKey = "+publicKey);
    }

    public static PrivateKey getPrivateKey(String privateKey){
        try {
            byte[] keyBytes = Base64Utils.decode(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            return privateK;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static PublicKey getPublicKeyFromPublicModules(BigInteger publicModels){
        // 加密
        try{
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(
                    publicModels,
                    new BigInteger("65537"));
            final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            System.out.println(Base64Utils.encode(publicKey.getEncoded()));
            return publicKey;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /** *//**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /** *//**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

}
