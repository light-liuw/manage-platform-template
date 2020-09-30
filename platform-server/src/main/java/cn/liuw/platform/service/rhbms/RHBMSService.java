package cn.liuw.platform.service.rhbms;


import java.util.List;

/**
 * @author liuw
 * @date 2020/09/29
 */
public interface RHBMSService {

    /**
     * 根据用户信息生成xml
     */
    List<String> findUserInfoByXml();
}
