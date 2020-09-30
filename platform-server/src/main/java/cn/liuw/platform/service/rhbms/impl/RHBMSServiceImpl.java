package cn.liuw.platform.service.rhbms.impl;

import cn.liuw.platform.common.util.XmlUtil;
import cn.liuw.platform.db.domain.rhbms.response.SxUserXmlResponse;
import cn.liuw.platform.service.rhbms.RHBMSService;
import cn.liuw.platform.service.rhbms.SxUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuw
 * @date 2020/9/29
 */
@Service
@Slf4j
public class RHBMSServiceImpl implements RHBMSService {
    
    @Autowired
    private SxUserService sxUserService;
    
    @Override
    public List<String> findUserInfoByXml() {
        // 1. 查询全部用户列表
        List<SxUserXmlResponse> sxUserList = sxUserService.findSxUserXmlList();
        
        // 2. 封装XML
        sxUserList.stream().forEach(e -> {
            formatUserToXml(e);
        });
        return null;
    }

    /**
     * 将用户信息转成xml
     */
    private void formatUserToXml(SxUserXmlResponse sxUserXmlResponse) {
        log.info("开始xml转换:【serviceId={}】", sxUserXmlResponse.getServiceId());
        
        StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append(XmlUtil.toXml(sxUserXmlResponse));
        String resultXml = xml.toString();
        resultXml = resultXml.replace("__", "_");
        
        log.info("result xml:【{}】", resultXml);
    }
}
