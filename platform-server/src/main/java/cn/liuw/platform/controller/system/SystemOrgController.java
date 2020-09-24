package cn.liuw.platform.controller.system;


import cn.liuw.platform.common.base.controller.BaseController;
import cn.liuw.platform.common.base.response.ResponseData;
import cn.liuw.platform.service.system.SystemOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuw
 * @date 2020-08-13 10:48
 */

@Api(value = "OrgController", tags = "组织机构")
@Slf4j
@RestController
@RequestMapping("/org")
public class SystemOrgController extends BaseController {
    
    @Autowired
    private SystemOrgService systemOrgService;

    @ApiOperation(value = "查询组织机构树")
    @GetMapping("/tree")
    public ResponseData getOrgTree() {
        return success(systemOrgService.getOrgTree());
    }
    
}
