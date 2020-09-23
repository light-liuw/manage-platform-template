package cn.liuw.platform.controller.test;


import cn.liuw.platform.common.base.controller.BaseController;
import cn.liuw.platform.common.base.response.ResponseData;
import cn.liuw.platform.service.test.TestSortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuw
 * @date 2020/8/20
 */
@Api(value = "TestController", tags = "测试")
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    private TestSortService testSortService;

    @ApiOperation(value = "排序")
    @GetMapping("/sort}")
    public ResponseData sort() {
        testSortService.testSort();
        return success();
    }
    
}
