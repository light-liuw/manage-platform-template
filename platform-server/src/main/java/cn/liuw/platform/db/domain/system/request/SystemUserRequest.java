package cn.liuw.platform.db.domain.system.request;

import cn.liuw.platform.common.base.request.BaseRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuw
 * @date 2020/8/20
 */
@Data
@NoArgsConstructor
public class SystemUserRequest extends BaseRequest {
    
    private String username;
    
    private String status;
    
    private String orgCode;
}
