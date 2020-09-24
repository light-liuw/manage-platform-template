package cn.liuw.platform.service.system.impl;

import cn.liuw.platform.common.util.PojoConvertUtil;
import cn.liuw.platform.db.domain.system.SystemUser;
import cn.liuw.platform.db.domain.system.request.SystemUserRequest;
import cn.liuw.platform.db.domain.system.response.SystemUserResponse;
import cn.liuw.platform.db.mapper.system.SystemUserMapper;
import cn.liuw.platform.service.system.SystemUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuw
 * @date 2020/8/20
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "USER")
public class SystemUserServiceImpl implements SystemUserService {

    @Resource
    private SystemUserMapper systemUserMapper;

    @Override
    @Cacheable(key = "#id")
    public SystemUser getById(Long id) {
        return systemUserMapper.selectById(id);
    }

    @Override
    public int insert(SystemUser systemUser) {
        return systemUserMapper.insert(systemUser);
    }

    @Override
    public int update(SystemUser systemUser) {
        return systemUserMapper.updateById(systemUser);
    }

    @Override
    public int delete(Long id) {
        return systemUserMapper.deleteById(id);
    }
    
    @Override
    public List<SystemUserResponse> getEffectiveUserList() {
        // 设置用户启用状态
        String status = "1";
        return systemUserMapper.selectByStatus(status);
    }

    @Override
    public IPage<SystemUserResponse> findByPage(SystemUserRequest systemUserRequest) {
        IPage<SystemUserResponse> response = new Page<>();
        
        // 设置分页
        Page<SystemUser> page = new Page<>(systemUserRequest.getPage(), systemUserRequest.getPageSize());
        
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<SystemUser>();
        queryWrapper.eq("status","1");
        String orgCode = systemUserRequest.getOrgCode();
        if(StringUtils.isNotBlank(orgCode)) {
            queryWrapper.eq("org_code",orgCode);
        }
        queryWrapper.orderByDesc("id");
        
        IPage<SystemUser> systemUserIPage = systemUserMapper.selectPage(page, queryWrapper);

        List<SystemUser> systemUsers = systemUserIPage.getRecords();
        List<SystemUserResponse> systemUserResponses = PojoConvertUtil.convertPojos(systemUsers, SystemUserResponse.class);
        response.setRecords(systemUserResponses);
        response.setTotal(systemUserIPage.getTotal());
        
        return response;
    }

//    @Override
//    public IPage<SystemUserResponse> getByPage(SystemUserRequest systemUserRequest) {
//        // 设置分页
//        Page<SystemUserResponse> page = new Page<>(systemUserRequest.getPage(), systemUserRequest.getPageSize());
//        return systemUserMapper.selectByStatus(page, systemUserRequest.getStatus());
//    }

    @Override
    public SystemUser getByUsername(String username) {
        return systemUserMapper.selectByUsername(username);
    }
    
}
