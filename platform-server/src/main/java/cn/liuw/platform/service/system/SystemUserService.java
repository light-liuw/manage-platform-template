package cn.liuw.platform.service.system;


import cn.liuw.platform.db.domain.system.SystemUser;
import cn.liuw.platform.db.domain.system.request.SystemUserRequest;
import cn.liuw.platform.db.domain.system.response.SystemUserResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author liuw
 * @date 2020/8/20
 */
public interface SystemUserService {

    /**
     * 根据 id 查询
     *
     * @param id
     * @return
     */
    SystemUser getById(Long id);

    /**
     * 新增
     */
    int insert(SystemUser systemUser);

    /**
     * 根据 id 修改
     */
    int update(SystemUser systemUser);

    /**
     * 根据 id 删除
     */
    int delete(Long id);

    /**
     * 获取全量用户信息列表
     * 
     * @return
     */
    List<SystemUserResponse> getEffectiveUserList();

    /**
     * 分页查询
     * 
     * @return
     */
    IPage<SystemUserResponse> getByPage(SystemUserRequest systemUserRequest);

    /**
     * 根据用户名查询用户信息
     */
    SystemUser getByUsername(String username);
}

