package cn.lchospital.baby.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 事件总线监听器
 *
 * @author N3verL4nd
 * @date 2021/12/6 17:12
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBusListener {
}