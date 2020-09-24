package cn.liuw.platform.db.domain.system.response;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author liuw
 * @date 2020/8/20
 */
@Data
public class SystemUserResponse implements Serializable {

    private static final long serialVersionUID = 3563576242151161046L;
    
    private Long id;

    private String username;

    private String idCard;

    private String sex;

    private String phone;
    
    // private String status;

    
}
