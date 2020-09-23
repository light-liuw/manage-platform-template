package cn.liuw.platform.db.domain.system;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("system_log")
@Data
@NoArgsConstructor
public class SystemLog implements Serializable {

    private static final long serialVersionUID = 3751077286046656434L;
    
    private Long id;

    private String userId;

    private String userName;

    private String organization;

    private String organizationId;

    private String terminalId;

    private String operationType;

    private String operationCondition;

    private String operationResult;

    private String operationTime;

    private String operationModule;

    private String errorCode;

    private String message;

    private String remark;

}