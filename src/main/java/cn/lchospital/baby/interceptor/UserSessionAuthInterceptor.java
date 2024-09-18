package cn.lchospital.baby.interceptor;

import cn.lchospital.baby.constants.LoginConstant;
import cn.lchospital.baby.dto.UserLoginModel;
import cn.lchospital.baby.utils.UserInfoThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author n3verl4nd
 * @date 2020/3/25
 */
public class UserSessionAuthInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionAuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserLoginModel userLoginModel = (UserLoginModel) session.getAttribute(LoginConstant.SESSION_NAME);
        if (userLoginModel != null) {
            UserInfoThreadLocal.set(userLoginModel);
            return true;
        } else {
            response.sendRedirect("/index");
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfoThreadLocal.clear();
    }
}