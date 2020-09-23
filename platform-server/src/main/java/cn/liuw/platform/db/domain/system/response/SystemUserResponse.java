package cn.liuw.platform.db.domain.system.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuw
 * @date 2020/8/20
 */
@Data
public class SystemUserResponse implements Serializable {

    private Long id;

    private String username;

    private String idCard;

    private String sex;

    private String phone;
}
