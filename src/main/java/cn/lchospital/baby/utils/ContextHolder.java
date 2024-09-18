package cn.lchospital.baby.utils;

import com.google.common.collect.Maps;

import java.util.Map;
 
public class ContextHolder {
 
    private static final String CLIENT_IP = "CLIENT_IP";
 
 
    private static final ThreadLocal<Map<String, Object>> holder = new ThreadLocal<>();
 
    private static Map<String, Object> getThreadContext() {
        Map<String, Object> context = holder.get();
        if (null == context) {
            context = Maps.newHashMap();
            holder.set(context);
        }
        return context;
    }
 
    public static void clearThreadContext() {
        holder.remove();
    }
 
 
    public static void setClientIp(String ip) {
        getThreadContext().put(CLIENT_IP, ip);
    }
 
    public static String getClientIp() {
        Object ip = getThreadContext().get(CLIENT_IP);
        if (ip == null) {
            return "";
        } else {
            return (String) ip;
        }
    }
}