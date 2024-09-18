package cn.lchospital.baby.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 获取 token 工具类
 * 查找顺序：header cookie parameter
 *
 * @author n3verl4nd
 * @date 2020/7/9
 */
public class UserTokenUtil {

    public static String getToken(HttpServletRequest request, String tokenName) {
        String token = null;

        // 1. header
        token = request.getHeader(tokenName);
        if (StringUtils.isNotBlank(token)) {
            return token;
        }
        // 2. cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie != null && tokenName.equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (StringUtils.isNotBlank(token)) {
            return token;
        }
        // 3. parameter
        token = request.getParameter(tokenName);
        return token;
    }

}