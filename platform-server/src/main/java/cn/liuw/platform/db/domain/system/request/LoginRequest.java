package cn.liuw.platform.db.domain.system.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 登录请求
 *
 * @author liuw
 * @date 2020/09/01
 */
@Data
@NoArgsConstructor
public class LoginRequest {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空！")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空！")
    private String password;
}
