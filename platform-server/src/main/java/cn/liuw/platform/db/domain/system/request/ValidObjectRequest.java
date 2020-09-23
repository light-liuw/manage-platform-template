package cn.liuw.platform.db.domain.system.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 登录请求
 *
 * @author liuw
 * @date 2020/09/01
 */
@Data
@NoArgsConstructor
public class ValidObjectRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 类别
     */
    private String type;

    /**
     * 值1
     */
    private String value1;

    /**
     * 值2
     */
    private String value2;

    /**
     * 值3
     */
    private String value3;

    /**
     * 值33
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date value33;
}
