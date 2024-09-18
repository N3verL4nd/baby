package cn.lchospital.baby.aop;

import cn.lchospital.baby.annotations.Log;
import cn.lchospital.baby.utils.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 打印参数和返回值的AOP, 拦截 Log 注解
 * 一般会在Facade上, service的处理的内容比较多,并且不会循环调用的方法上打出运行时间
 *
 * @author N3verL4nd
 * @date 2021/6/3
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 拦截方法上的注解
     */
    @Around(value = "@annotation(log)")
    public Object aroundMethod(ProceedingJoinPoint pjp, Log log) throws Throwable {
        return logHandler(pjp, log);
    }

    /**
     * 拦截类上的注解
     */
    @Around(value = "@within(log)")
    public Object aroundClass(ProceedingJoinPoint pjp, Log log) throws Throwable {
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        // 获取方法上的注解
        Log methodLog = AnnotationUtils.findAnnotation(method, Log.class);
        // 若方法上也有注解, 不再处理, 因为拦截方法的时候已经处理过了
        if (methodLog != null) {
            return pjp.proceed();
        }
        return logHandler(pjp, log);
    }

    private Object logHandler(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        // 切点处的方法签名, 有参数形如 Hello.hello(..); 无参数形如: Hello.hello()
        String signature = joinPoint.getSignature().toShortString();
        // 请求参数
        String request = "";
        if (Boolean.TRUE.equals(log.jsonLog())) {
            request = JsonUtils.toJson(Arrays.stream(joinPoint.getArgs()).filter(o -> !(o instanceof HttpServletRequest)).collect(Collectors.toList()));
        } else {
            request = Arrays.toString(joinPoint.getArgs());
        }
        // 打印请求参数
        if (Boolean.TRUE.equals(log.request()) && !Boolean.TRUE.equals(log.exceptionOnly())) {
            logger.info("{}的请求参数为:{}", signature, request);
        }
        long startTime = System.currentTimeMillis();
        Object returnObject = null;
        try {
            returnObject = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable throwable) {
            if (Boolean.TRUE.equals(log.request()) && Boolean.TRUE.equals(log.exceptionOnly())) {
                logger.info("{}的请求参数为:{}", signature, request);
            }
            throw throwable;
        }
        // 打印响应结果
        if (Boolean.TRUE.equals(log.response()) && !Boolean.TRUE.equals(log.exceptionOnly())) {
            if (Boolean.TRUE.equals(log.jsonLog())) {
                logger.info("{}返回结果为:{}", signature, JsonUtils.toJson(returnObject));
            } else {
                logger.info("{}返回结果为:{}", signature, returnObject);
            }
        }
        // 打印运行时间
        if (Boolean.TRUE.equals(log.runTime()) && !Boolean.TRUE.equals(log.exceptionOnly())) {
            logger.info("{}运行时间为:{}ms", signature, System.currentTimeMillis() - startTime);
        }
        return returnObject;
    }
}