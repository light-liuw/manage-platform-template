package cn.liuw.platform.controller.system;


import cn.liuw.platform.common.base.controller.BaseController;
import cn.liuw.platform.common.base.response.ResponseData;
import cn.liuw.platform.db.domain.system.SystemUser;
import cn.liuw.platform.db.domain.system.request.SystemUserRequest;
import cn.liuw.platform.db.domain.system.response.SystemUserResponse;
import cn.liuw.platform.service.system.SystemUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author liuw
 * @date 2020/8/20
 */
@Api(value = "SystemUserController", tags = "用户管理")
@Slf4j
@RestController
@RequestMapping("/user")
public class SystemUserController extends BaseController {

    @Autowired
    private SystemUserService systemUserService;

    @ApiOperation(value = "根据id查询用户信息")
    @GetMapping("/id/{id}")
    public ResponseData getById(@PathVariable("id") Long id) {
        return success(systemUserService.getById(id));
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/add")
    public ResponseData add(@RequestBody @Valid SystemUser systemUser) {
        systemUserService.insert(systemUser);
        return success();
    }

    @ApiOperation(value = "获取全量用户信息列表")
    @GetMapping("/effective")
    public ResponseData getEffectiveUserList() {
        return success(systemUserService.getEffectiveUserList());
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/page")
    public ResponseData findByPage(@RequestBody SystemUserRequest systemUserRequest) {
        IPage<SystemUserResponse> page = systemUserService.findByPage(systemUserRequest);
        return success(page.getRecords(), page.getTotal());
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/update")
    public ResponseData update(@RequestBody SystemUser systemUser) {
        systemUserService.update(systemUser);
        return success();
    }
}
