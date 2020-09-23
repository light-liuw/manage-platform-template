package cn.liuw.platform;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author liuw
 * @date 2020-08-20
 */
@Slf4j
@EnableCaching // 开启缓存
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan("cn.liuw.platform.db.mapper.*")
public class PlatformServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformServerApplication.class, args);
    }

}
