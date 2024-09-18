package cn.lchospital.baby.utils;

import sun.net.util.IPAddressUtil;

import javax.servlet.http.HttpServletRequest;
 
public class IPUtil {
    public static String getUserIp(HttpServletRequest request) {
        String userIp = request.getHeader("X-Real-IP");
 
        if (isPublicIp(userIp)) {
            return userIp;
        }
 
        userIp = getFirstPublicIp(request.getHeader("X-Forwarded-For"));
        if (userIp != null) {
            return userIp;
        }
 
        return request.getRemoteAddr();
    }
 
    private static String getFirstPublicIp(String proxyIps) {
        String[] proxyIpArray = null;
 
        if (proxyIps != null && !proxyIps.isEmpty()) {
            proxyIpArray = proxyIps.split(", ");
        }
 
        if (proxyIpArray != null) {
            for (String proxyIp : proxyIpArray) {
                if (isPublicIp(proxyIp)) {
                    return proxyIp;
                }
            }
        }
        return null;
    }
 
    private static boolean isPublicIp(String userIp) {
        return userIp != null && !userIp.isEmpty() && !isLocalIp(userIp) && !isInternalIp(userIp);
    }
 
    private static boolean isLocalIp(String userIp) {
        return "127.0.0.1".equals(userIp);
    }
 
 
    private static boolean isInternalIp(String ip) {
        byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);
        return isInternalIp(addr);
    }
 
 
    /**
     * 内网地址范围,addr为null时候，默认为内网ip
     * 10.0.0.0～10.255.255.255
     * 172.16.0.0～172.31.255.255
     * 192.168.0.0～192.168.255.255
     *
     * @param addr
     * @return
     */
    private static boolean isInternalIp(byte[] addr) {
        if (addr == null) {
            return true;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        //10.0.0.0～10.255.255.255
        final byte SECTION_1 = 0x0A;
        //172.16.0.0～172.31.255.255
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        //192.168.0.0～192.168.255.255
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch (b1) {
                    case SECTION_6:
                        return true;
                }
            default:
                return false;
        }
    }
}