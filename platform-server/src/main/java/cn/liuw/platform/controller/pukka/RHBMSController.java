package cn.liuw.platform.controller.pukka;

import cn.liuw.platform.common.base.controller.BaseController;
import cn.liuw.platform.common.base.response.ResponseData;
import cn.liuw.platform.service.rhbms.RHBMSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author liuw
 * @date 2020/9/29
 */
@Api(value = "RHBMSController", tags = "RHBMS Utils")
@Slf4j
@RestController
@RequestMapping("/rhbms")
public class RHBMSController extends BaseController {
    
    @Autowired
    private RHBMSService rhbmsService;

    @ApiOperation(value = "用户信息转换成XML")
    @PostMapping("/xml")
    public ResponseData getUserInfoByXml() {
        rhbmsService.findUserInfoByXml();
        return success();
    }
}
