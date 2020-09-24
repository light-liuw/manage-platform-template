package cn.liuw.platform.service.system;


import cn.liuw.platform.db.domain.system.response.OrgTreeResponse;

import java.util.List;

/**
 * @author liuw
 * @date 2020-08-13 10:49
 */
public interface SystemOrgService {

    /**
     * 查询组织机构树
     */
    List<OrgTreeResponse> getOrgTree();
}
