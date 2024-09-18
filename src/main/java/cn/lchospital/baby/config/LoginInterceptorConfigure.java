package cn.lchospital.baby.config;

import cn.lchospital.baby.interceptor.ClientInfoInterceptor;
import cn.lchospital.baby.interceptor.UserSessionAuthInterceptor;
import cn.lchospital.baby.interceptor.UserTokenAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 用户鉴权 token 拦截器
 *
 * @author N3verL4nd
 * @date 2020/3/25
 */
//@Configuration
public class LoginInterceptorConfigure implements WebMvcConfigurer {

    @Bean
    public UserSessionAuthInterceptor userSessionAuthInterceptor() {
        return new UserSessionAuthInterceptor();
    }

    @Bean
    public UserTokenAuthInterceptor userTokenAuthInterceptor() {
        return new UserTokenAuthInterceptor();
    }

    @Bean
    public ClientInfoInterceptor clientInfoInterceptor() {
        return new ClientInfoInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(userSessionAuthInterceptor()).addPathPatterns("/**").excludePathPatterns("", "/", "/index", "/login","/favicon.ico", "/css/**", "/images/**", "/js/**", "/api/**");
    }
}