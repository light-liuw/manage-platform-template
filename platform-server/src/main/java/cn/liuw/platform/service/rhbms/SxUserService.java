package cn.liuw.platform.service.rhbms;


import cn.liuw.platform.db.domain.rhbms.response.SxUserXmlResponse;

import java.util.List;

/**
 * @author liuw
 * @date 2020/09/29
 */
public interface SxUserService {

    /**
     * 查询用户列表
     */
    List<SxUserXmlResponse> findSxUserXmlList();
}
