/**
 * @author 作者: Ben
 * @date 创建日期: 2020年12月8日
 * @description 常用工具集
 */

package com.wuxb.utils;

import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class Tools {

    /**
     * 可信任代理层数
     */
    private static final int PROXY_LEN = 1;

    public static String getRealIp(HttpServletRequest httpServletRequest) {
        String xRealIp = httpServletRequest.getHeader("X-Real-IP");
        // 根据反向代理服务器写入的X-Real-IP头判断（代理机必须配置这个头）
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        // 根据反向代理服务器写入的X-Forwarded-For头判断，PROXY_LEN为0就是不用代理，用直连
        String xForwardedFor = httpServletRequest.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            String[] ips = xForwardedFor.split(",");
            String ip = ips[ips.length - PROXY_LEN].trim();
            return ip;
        }
        // 直接返回直连客户端的ip
        return httpServletRequest.getRemoteAddr();
    }

    public static long ip2long(String ip) {
        String[] temp = ip.split("\\.");
        int a0 = Integer.parseInt(temp[0]);
        int a1 = Integer.parseInt(temp[1]);
        int a2 = Integer.parseInt(temp[2]);
        int a3 = Integer.parseInt(temp[3]);
        long res = a3 + a2 * 256 + a1 * 65536L + a0 * 16777216L;
        return res;
    }

    public static String long2ip(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }

    public static <T> T copyNewBean(Object source, Class<T> clazz) {
        try {
            var instance = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, instance);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> copyNewBean(List<?> sourceList, Class<T> clazz) {
        List<T> instanceList = new ArrayList<T>(sourceList.size());
        sourceList.forEach((source) -> {
            var instance = copyNewBean(source, clazz);
            instanceList.add(instance);
        });
        return instanceList;
    }

    public static String bin2hex(byte[] bytes) {
        var sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Character.forDigit((bytes[i] & 240) >> 4, 16));
            sb.append(Character.forDigit(bytes[i] & 15, 16));
        }
        return sb.toString();
    }

}
