package cn.liuw.platform.db.mapper.system;


import cn.liuw.platform.db.domain.system.SystemUser;
import cn.liuw.platform.db.domain.system.response.SystemUserResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuw
 * @date 2020/8/20
 */
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 根据状态查询
     *
     * @param status
     * @return
     */
    List<SystemUserResponse> selectByStatus(@Param("status") String status);

    /**
     * 根据状态分页查询
     *
     * @param page
     * @param status
     * @return
     */
    IPage<SystemUserResponse> selectByStatus(Page<SystemUserResponse> page, @Param("status") String status);

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return
     */
    SystemUser selectByUsername(String username);
}
