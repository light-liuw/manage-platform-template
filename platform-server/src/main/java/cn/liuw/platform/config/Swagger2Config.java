package cn.liuw.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuw
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createTestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("测试")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.liuw.platform.controller.test"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getParameterList());
    }

    @Bean
    public Docket createSystemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("系统管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.liuw.platform.controller.system"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getParameterList());
    }

    @Bean
    public Docket createMessageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("消息管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.liuw.platform.controller.message"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getParameterList());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger2构建RESTful APIs")
                .description("请持续关注我们")
                .termsOfServiceUrl("http://www.baidu.com/")
                .version("1.0")
                .build();
    }

    /**
     * 返回构建的swagger参数
     *
     * @return
     */
    private List<Parameter> getParameterList() {
        ParameterBuilder parameter = new ParameterBuilder();
        List<Parameter> parameterList = new ArrayList<Parameter>();
        parameter.name("Authorization")
                .description("登录校验")    //name表示名称，description表示描述
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .defaultValue("63A9F0EA7BB98050796B649E85481845") // 用户{username: root，password: 11}的token
                .build();    //required表示是否必填，defaultvalue表示默认值
        parameterList.add(parameter.build());    //添加完此处一定要把下边的带***的也加上否则不生效
        return parameterList;
    }
}

