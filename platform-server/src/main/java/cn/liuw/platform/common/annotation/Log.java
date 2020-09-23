package cn.liuw.platform.common.annotation;


import cn.liuw.platform.common.enums.BusinessType;

import java.lang.annotation.*;

/**
 * 自定义操作日志注解
 *
 * @author liuw
 * @date 2020/8/25
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块(需要表示多级模块使用 - 分隔)
     */
    public String model() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 说明
     */
    public String comment() default "";

}
