package cn.liuw.platform.service.message.impl;

import cn.liuw.platform.common.util.CodeUtil;
import cn.liuw.platform.db.domain.message.MessageTemplate;
import cn.liuw.platform.db.mapper.message.MessageTemplateMapper;
import cn.liuw.platform.service.message.MessageTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liuw
 * @date 2020/9/24
 */
@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {
    
    @Resource
    private MessageTemplateMapper messageTemplateMapper;

    /**
     * 1. 新增模板信息
     * 2. 根据模板信息组装数据将数据丢入队列
     * 
     * @param messageTemplate
     */
    @Override
    public void insert(MessageTemplate messageTemplate) {
        // 新增模板
        messageTemplate.setCode(CodeUtil.getUUID());
        messageTemplateMapper.insert(messageTemplate);
        // TODO 根据通道类型将数据发至队列
    }
    
}
