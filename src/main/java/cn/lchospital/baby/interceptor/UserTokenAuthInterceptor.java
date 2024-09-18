package cn.lchospital.baby.interceptor;

import cn.lchospital.baby.annotations.ApiLogin;
import cn.lchospital.baby.dto.UserLoginModel;
import cn.lchospital.baby.enumtype.ServerCode;
import cn.lchospital.baby.utils.JsonUtils;
import cn.lchospital.baby.utils.UserInfoThreadLocal;
import cn.lchospital.baby.utils.UserTokenUtil;
import cn.lchospital.baby.vo.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author n3verl4nd
 * @date 2020/3/25
 */
public class UserTokenAuthInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTokenAuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ApiLogin apiLogin = handlerMethod.getMethod().getAnnotation(ApiLogin.class);
            if (apiLogin == null) {
                return true;
            }

            String token = UserTokenUtil.getToken(request, "token");

            if (StringUtils.isBlank(token)) {
                write(response, ServerCode.NO_LOGIN);
                return false;
            }

            // todo 将 token 解析为 UserLoginModel

            UserInfoThreadLocal.set(new UserLoginModel());
            return true;
        } catch (Exception e) {
            LOGGER.error("UserTokenAuthInterceptor preHandle error", e);
            write(response, ServerCode.OPERATION_FAIL);
            return false;
        }
    }

    private void write(HttpServletResponse res, ServerCode serverCode) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        Writer writer = res.getWriter();
        writer.write(JsonUtils.toJson(ApiResult.newResult(serverCode)));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfoThreadLocal.clear();
    }
}