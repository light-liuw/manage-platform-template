package cn.liuw.platform.config.interceptor;

import cn.liuw.platform.common.base.response.ResponseData;
import cn.liuw.platform.common.constant.CacheConstants;
import cn.liuw.platform.common.util.RedisUtil;
import cn.liuw.platform.db.domain.system.response.LoginUser;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author liuw
 * @date 2020-08-26
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private RedisUtil redisUtil;
    
    /**
     * 在业务处理器处理请求之前被调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        log.info("RequestURI:{}", request.getRequestURI());

        // 如果是 swagger 请求通过
        if (request.getRequestURI().startsWith("/swagger-resources") ||
                request.getRequestURI().startsWith("/swagger") ||
                request.getRequestURI().startsWith("/webjars") ||
                request.getRequestURI().startsWith("/error")) {
            return true;
        }

        // 如果是登录放过
        if (request.getRequestURI().startsWith("/user/login")) {
            return true;
        }

        // 获取header串号
        String token = request.getHeader("Authorization");
        log.info("获取token值:" + token);
        log.info("获取请求方式:" + request.getMethod());

        // 验证redis串号
        if (token == null || "".equals(token)) {
            ResponseData rd = new ResponseData.Builder().fail("登录失效，请重新登录!").build();
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Content-Type", "application/json;charset=UTF-8");
            response.getWriter().print(new Gson().toJson(rd));
            log.info("------非法登入(token为空)-----");
            return false;
        } else {
            String loginUserKey = CacheConstants.USER_TOKEN + token;
            boolean exists = redisUtil.hasKey(loginUserKey);
            if (exists) {
                LoginUser loginUser = new Gson().fromJson((String) redisUtil.get(loginUserKey), LoginUser.class);
                request.setAttribute(CacheConstants.USER_ATTRIBUTE + token, loginUser);
                return true;
            } else {
                ResponseData rd = new ResponseData.Builder().fail("登录失效，请重新登录!").build();
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Content-Type", "application/json;charset=UTF-8");
                response.getWriter().print(new Gson().toJson(rd));
                log.info("------非法登入(redis验证token不存在)-----");
                return false;
            }
        }
    }

}
