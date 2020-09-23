package cn.liuw.platform.service.test;

import cn.liuw.platform.db.domain.system.request.ValidObjectRequest;

/**
 * @author liuw
 * @date 2020/09/01
 */
public interface TestService {

    /**
     * 排序字段
     *
     * @return
     */
    public String testSort();

    /**
     * 字段校验
     */
    public String validObject(ValidObjectRequest validObjectRequest);
}
