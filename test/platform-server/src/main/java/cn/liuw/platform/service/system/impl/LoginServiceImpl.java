package cn.liuw.platform.service.system.impl;


import cn.liuw.platform.common.base.exception.LoginException;
import cn.liuw.platform.common.constant.CacheConstant;
import cn.liuw.platform.common.util.CodeUtil;
import cn.liuw.platform.common.util.RedisUtil;
import cn.liuw.platform.db.domain.system.SystemUser;
import cn.liuw.platform.db.domain.system.request.LoginRequest;
import cn.liuw.platform.db.domain.system.response.LoginUser;
import cn.liuw.platform.service.system.LoginService;
import cn.liuw.platform.service.system.SystemUserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuw
 * @date 2020/9/1
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String loginIn(LoginRequest loginRequest) {
        // 根据用户名查询用户信息
        SystemUser systemUser = systemUserService.getByUsername(loginRequest.getUsername());
        if (null != systemUser && loginRequest.getPassword().equals(systemUser.getPassword())) {
            // 存用户信息至redis
            LoginUser loginUser = new LoginUser();
            loginUser.setUsername(systemUser.getUsername());
            loginUser.setIdCard(systemUser.getIdCard());
            loginUser.setSex(systemUser.getSex());
            loginUser.setOrgCode(systemUser.getOrgCode());
            loginUser.setPhone(systemUser.getPhone());
            
            String token = CodeUtil.getUserToken(loginUser.getUsername());
            String loginUserKey = CacheConstant.USER_TOKEN + token;
            redisUtil.set(loginUserKey, new Gson().toJson(loginUser), 60*60*24);
            return token;
        }
        throw new LoginException("用户名或密码错误！");
    }

    @Override
    public void loginOut(String token) {
        // 拼接 redis key
        String loginUserKey = CacheConstant.USER_TOKEN + token;
        // 根据用户名查询用户信息
        if(redisUtil.hasKey(loginUserKey)) {
            redisUtil.del(loginUserKey);
            return;
        }
        throw new LoginException("用户已登出或登录信息不存在！");
    }

    @Override
    public String getLoginUserName(String token) {
        String username = "";
        String loginUserKey = CacheConstant.USER_TOKEN + token;
        // 根据用户名查询用户信息
        if(redisUtil.hasKey(loginUserKey)) {
            LoginUser loginUser = new Gson().fromJson((String) redisUtil.get(loginUserKey), LoginUser.class);
            username = loginUser.getUsername();
        }
        return username;
    }


}
