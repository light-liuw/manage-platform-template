package cn.liuw.platform.db.domain.system.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liuw
 * @date 2020/8/20
 */
@Data
@NoArgsConstructor
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 8663737308075230115L;

    private String username;
    
    private String idCard;
    
    private String sex;
    
    private String orgCode;
    
    private String phone;
    
}
