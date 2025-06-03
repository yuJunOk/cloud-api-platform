package com.api.clientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签名工具
 * @author pengYuJun
 */
public class SignUtils {
    /**
     * 生成签名
     * @param body
     * @param secretKey
     * @return
     */
    public static String genSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey;
        return md5.digestHex(content);
    }

    /**
     * 根据时间生成四位随机数
     * @return
     */
    public static int genNonceByTimeStamp(long timestamp) {
        // 如 sin(1717400000) ≈ 0.024
        double sinValue = Math.sin(timestamp);
        // 取小数部分→0240
        return (int)(Math.abs(sinValue) * 10000) % 10000;
    }
}