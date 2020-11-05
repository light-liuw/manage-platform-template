package cn.liuw.platform.common.util;

import cn.liuw.platform.common.constant.CacheConstant;
import cn.liuw.platform.db.domain.system.response.LoginUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuw
 * @date 2020/9/28
 */
public class UserUtil {

    /**
     * 根据 HttpServletRequest 获取用户信息
     *
     * @param request
     * @return
     */
    public static LoginUser getLoginUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return (LoginUser) request.getAttribute(CacheConstant.USER_ATTRIBUTE + token);
    }

}
