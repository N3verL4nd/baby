package cn.lchospital.baby.annotations;

import java.lang.annotation.*;

/**
 * @author N3verL4nd
 * @date 2020/3/25
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLogin {
}