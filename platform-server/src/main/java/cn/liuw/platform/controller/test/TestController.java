package cn.liuw.platform.controller.test;


import cn.liuw.platform.common.base.controller.BaseController;
import cn.liuw.platform.common.base.response.ResponseData;
import cn.liuw.platform.db.domain.system.request.ValidObjectRequest;
import cn.liuw.platform.db.domain.system.response.LoginUser;
import cn.liuw.platform.service.test.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private TestService testService;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @ApiOperation(value = "排序")
    @GetMapping("/sort")
    public ResponseData sort() {
        testService.testSort();
        return success();
    }

    @ApiOperation(value = "字段校验")
    @PostMapping("/valid")
    public ResponseData validObject(@RequestBody ValidObjectRequest validObjectRequest) {
        String result = testService.validObject(validObjectRequest);
        return success(result);
    }

    @ApiOperation(value = "post-测试")
    @PostMapping("/post")
    public ResponseData post() {
        return success();
    }

    @ApiOperation(value = "mongo-测试")
    @PostMapping("/mongo")
    public ResponseData mongo() {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("root");
        
        mongoTemplate.insert(loginUser, "user");
        
        return success();
    }

    @ApiOperation(value = "mongo-测试2")
    @PostMapping("/mongo2")
    public ResponseData mongo2() {
        Query query = new Query(Criteria.where("username").is("ee"));
        List<LoginUser> ans = mongoTemplate.find(query, LoginUser.class ,"user");
        for (LoginUser an : ans) {
            System.out.println(an.toString());
        }
        return success();
    }
    
}
