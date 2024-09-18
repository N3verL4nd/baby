package cn.lchospital.baby.interceptor;

import cn.lchospital.baby.utils.ContextHolder;
import cn.lchospital.baby.utils.IPUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * @author liguanghui02
 * @date 2020/12/11
 */
public class ClientInfoInterceptor extends HandlerInterceptorAdapter {
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ContextHolder.setClientIp(IPUtil.getUserIp(request));
        return true;
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ContextHolder.clearThreadContext();
    }
}