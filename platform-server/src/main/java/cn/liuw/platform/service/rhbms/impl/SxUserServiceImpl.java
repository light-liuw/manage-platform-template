package cn.liuw.platform.service.rhbms.impl;

import cn.liuw.platform.db.domain.rhbms.response.SxUserXmlResponse;
import cn.liuw.platform.db.mapper.rhbms.SxUserMapper;
import cn.liuw.platform.service.rhbms.SxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuw
 * @date 2020/9/29
 */
@Service
@Slf4j
public class SxUserServiceImpl implements SxUserService {

    @Resource
    private SxUserMapper sxUserMapper;

    @Override
    public List<SxUserXmlResponse> findSxUserXmlList() {
        return sxUserMapper.selectUserXmlList();
    }

}
