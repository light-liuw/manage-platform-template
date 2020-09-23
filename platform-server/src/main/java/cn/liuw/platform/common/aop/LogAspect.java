package cn.liuw.platform.common.aop;

import cn.liuw.platform.common.annotation.Log;
import cn.liuw.platform.common.enums.BusinessStatus;
import cn.liuw.platform.db.domain.system.SystemLog;
import cn.liuw.platform.db.domain.system.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 操作日志记录处理
 *
 * @author userpoints
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    // 配置织入点
    @Pointcut("@annotation(cn.liuw.platform.common.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            // 获取当前的用户 TODO
            SystemUser systemUser = new SystemUser();
            systemUser.setId(1234L);
            systemUser.setUsername("zSan");
            // 记录操作日志
            SystemLog systemLog = new SystemLog();
            systemLog.setUserId(String.valueOf(systemUser.getId()));
            systemLog.setUserName(systemUser.getUsername());
            // 请求的地址
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // IP
            systemLog.setTerminalId(getIpAddress(request));
            // 返回结果
            systemLog.setOperationResult(String.valueOf(BusinessStatus.SUCCESS.ordinal()));
            
            if (e != null) {
                systemLog.setOperationResult(String.valueOf(BusinessStatus.FAIL.ordinal()));
                systemLog.setMessage(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            systemLog.setRemark(className + "." + methodName + "()");
            // 设置请求方式
            // systemLog.setOperationCondition(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, systemLog);
            // 保存数据库
            // AsyncManager.me().execute(AsyncFactory.recordOper(systemLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param systemLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(Log log, SystemLog systemLog) throws Exception {
        // 设置action动作
        systemLog.setOperationResult(String.valueOf(log.businessType().ordinal()));
        // 设置标题
        systemLog.setOperationModule(log.model());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            System.out.println("============保存请求参数和值.");
            // 获取参数的信息，传入到数据库中。
            // setRequestValue(systemLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param systemLog 操作日志
     * @throws Exception 异常
     */
//    private void setRequestValue(SystemLog systemLog) throws Exception {
//        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
//        String params = JSON.marshal(map);
//        systemLog.setOperParam(StringUtils.substring(params, 0, 2000));
//    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
