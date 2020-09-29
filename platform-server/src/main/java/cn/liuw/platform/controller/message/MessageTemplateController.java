package cn.liuw.platform.controller.message;

import cn.liuw.platform.common.base.controller.BaseController;
import cn.liuw.platform.common.base.response.ResponseData;
import cn.liuw.platform.db.domain.message.MessageTemplate;
import cn.liuw.platform.service.message.MessageTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author liuw
 * @date 2020/09/01
 */
@Api(value = "MessageTemplateController", tags = "消息模板")
@Slf4j
@RestController
@RequestMapping("/message")
public class MessageTemplateController extends BaseController {

    @Autowired
    private MessageTemplateService messageTemplateService;

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public ResponseData add(@RequestBody @Valid MessageTemplate messageTemplate, HttpServletRequest request) {
        messageTemplateService.insert(messageTemplate);
        return success();
    }

    @ApiOperation(value = "发布")
    @GetMapping("/release/{id}")
    public ResponseData release(@PathVariable("id") @NotNull Long id) {
        messageTemplateService.release(id);
        return success();
    }
}
