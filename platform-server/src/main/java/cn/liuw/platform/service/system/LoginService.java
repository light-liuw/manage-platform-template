package cn.liuw.platform.service.system;


import cn.liuw.platform.db.domain.system.request.LoginRequest;

/**
 * @author liuw
 * @date 2020/09/01
 */
public interface LoginService {

    /**
     * 用户登录
     *
     * @param loginRequest
     * @return
     */
    String loginIn(LoginRequest loginRequest);

    /**
     * 用户登出
     * 
     */
    void loginOut(String token);

    /**
     * 根据 token 获取缓存中的用户名
     *
     */
    String getLoginUserName(String token);

}
