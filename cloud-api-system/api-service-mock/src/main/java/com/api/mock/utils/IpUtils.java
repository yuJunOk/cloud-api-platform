package com.api.mock.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.List;

public class IpUtils {

    // 常见的代理服务器传递IP的Header
    private static final List<String> IP_HEADERS = Arrays.asList(
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_CLIENT_IP",
        "HTTP_X_FORWARDED_FOR",
        "X-Real-IP"
    );

    /**
     * 获取客户端公网IP（自动识别代理）
     */
    public static String getClientPublicIp(HttpServletRequest request) {
        String ip = null;
        
        // 1. 优先从代理Header中查找
        for (String header : IP_HEADERS) {
            ip = request.getHeader(header);
            if (isValidIp(ip)) {
                break;
            }
        }

        // 2. 如果Header中未找到，使用默认的RemoteAddr
        if (!isValidIp(ip)) {
            ip = request.getRemoteAddr();
        }

        // 3. 处理多级代理（取第一个有效IP）
        if (ip != null && ip.contains(",")) {
            ip = Arrays.stream(ip.split(","))
                    .map(String::trim)
                    .filter(IpUtils::isValidIp)
                    .findFirst()
                    .orElse("未知IP");
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 验证IP是否有效
     */
    private static boolean isValidIp(String ip) {
        return StringUtils.isNotEmpty(ip) 
                && !"unknown".equalsIgnoreCase(ip)
                && !ip.startsWith("127.")
                && !ip.startsWith("192.168.")
                && !ip.startsWith("10.")
                && !ip.startsWith("172.");
    }
}