package com.chinaums.util.rsautil.controller;

import com.chinaums.util.rsautil.utils.Base64Utils;
import com.chinaums.util.rsautil.utils.RsaUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:郝山山
 * @Description:<>
 * @Date: 2018/8/27
 * @Copyright:银联商务有限公司
 */
@Controller
public class IndexController {

    @GetMapping("/rsa-generator")
    @ResponseBody
    public Map<String,String> rsaGenerator(String keySize) throws Exception {
        JSONObject json = new JSONObject();
        System.out.println("keySize = " + keySize);
        Map<String, Object> map = RsaUtil.genKeyPair(Integer.parseInt(keySize));
        json.put("privateKey", RsaUtil.getPrivateKey(map));
        json.put("publicKey", RsaUtil.getPublicKey(map));
        Map<String,String> result = new HashMap<>();
        result.putAll(json);
        return result;
    }

    @GetMapping("/rsa-priKey")
    @ResponseBody
    public Map<String,String> rsaPriKey(String priKey) throws Exception {
        Map<String,String> result = new HashMap<>();
        System.out.println("priKey = " + priKey);
        priKey = priKey.replaceAll(" ","+");
        System.out.println("priKey = " + priKey);
        RSAPrivateKey privateKey = (RSAPrivateKey)RsaUtil.getPrivateKey(priKey);
        BigInteger priModulus = privateKey.getModulus();
        RSAPublicKey publicKey = (RSAPublicKey) RsaUtil.getPublicKeyFromPublicModules(priModulus);
        result.put("privateKey", priKey);
        result.put("publicKey", Base64Utils.encode(publicKey.getEncoded()));
        return result;
    }

}

