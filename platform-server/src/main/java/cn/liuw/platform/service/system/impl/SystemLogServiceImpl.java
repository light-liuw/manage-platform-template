package cn.liuw.platform.service.system.impl;

import cn.liuw.platform.db.domain.system.SystemLog;
import cn.liuw.platform.db.domain.system.request.SystemLogRequest;
import cn.liuw.platform.db.mapper.system.SystemLogMapper;
import cn.liuw.platform.service.system.SystemLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liuw
 * @date 2020/8/26
 */
@Slf4j
@Service
public class SystemLogServiceImpl implements SystemLogService {

    @Resource
    private SystemLogMapper systemLogMapper;
    
    @Override
    public SystemLog getById(Long id) {
        return systemLogMapper.selectById(id);
    }

    @Override
    public int insert(SystemLog systemLog) {
        return systemLogMapper.insert(systemLog);
    }

    @Override
    public IPage<SystemLog> getByPage(SystemLogRequest systemLogRequest) {
        // 设置分页
        Page<SystemLog> page = new Page<>(systemLogRequest.getPage(), systemLogRequest.getPageSize());
        // 设置排序
        QueryWrapper<SystemLog> queryWrapper = new QueryWrapper<SystemLog>();
        queryWrapper.orderByDesc("id");
        return systemLogMapper.selectPage(page, queryWrapper);
    }
}
