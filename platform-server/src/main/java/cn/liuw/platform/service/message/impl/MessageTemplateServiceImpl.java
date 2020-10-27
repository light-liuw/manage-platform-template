package cn.liuw.platform.service.message.impl;

import cn.liuw.platform.common.base.exception.BusinessException;
import cn.liuw.platform.common.util.CodeUtil;
import cn.liuw.platform.common.util.UserUtil;
import cn.liuw.platform.db.domain.message.MessageTemplate;
import cn.liuw.platform.db.domain.system.response.LoginUser;
import cn.liuw.platform.db.mapper.message.MessageTemplateMapper;
import cn.liuw.platform.service.message.MessageTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author liuw
 * @date 2020/9/24
 */
@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {

    @Resource
    private MessageTemplateMapper messageTemplateMapper;
    
    @Override
    public void insert(MessageTemplate messageTemplate) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        LoginUser loginUser = UserUtil.getLoginUser(request);
        messageTemplate.setStatus(1);
        messageTemplate.setCode(CodeUtil.getUUID());
        messageTemplate.setCreateName(loginUser.getUsername());
        messageTemplate.setCreateTime(new Date());
        messageTemplateMapper.insert(messageTemplate);
    }

    /**
     * 发布消息模板，将消息推进队列
     */
    @Override
    @Transactional
    public void release(Long id) {
        // 校验
        MessageTemplate messageTemplate = messageTemplateMapper.selectById(id);
        if(null == messageTemplate) {
            throw new BusinessException("id:" + id + "，对应消息模板不存在" );
        }
        if("2".equals(messageTemplate.getStatus())) {
            throw new BusinessException("消息模板状态不是发布中" );
        }
        // 修改状态发布消息
        messageTemplate.setStatus(2);
        messageTemplateMapper.updateById(messageTemplate);
        // 消息推进队列
        // pushMessageToQueue();
    }

}
