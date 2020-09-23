package cn.liuw.platform.controller.system;

import cn.liuw.platform.common.base.controller.BaseController;
import cn.liuw.platform.common.base.response.ResponseData;
import cn.liuw.platform.db.domain.system.SystemLog;
import cn.liuw.platform.db.domain.system.request.SystemLogRequest;
import cn.liuw.platform.service.system.SystemLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuw
 * @date 2020/8/26
 */
@Api(value = "SystemLogController", tags = "日志管理")
@Slf4j
@RestController
@RequestMapping("/log")
public class SystemLogController extends BaseController {

    @Autowired
    private SystemLogService systemLogService;

    @ApiOperation(value = "根据id查询日志信息")
    @GetMapping("/id/{id}")
    public ResponseData getById(@PathVariable("id") Long id) {
        return success(systemLogService.getById(id));
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/page")
    public ResponseData getLogByPage(@RequestBody SystemLogRequest systemLogRequest) {
        IPage<SystemLog> page = systemLogService.getByPage(systemLogRequest);
        return success(page.getRecords(), page.getTotal());
    }
}
