package cn.liuw.platform.db.domain.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class SystemOrg implements Serializable {

    private static final long serialVersionUID = 9153789135189035052L;
    
    private Integer id;

    private String name;

    private String code;

    private Integer parentId;

    private String parentName;

    private String parentCode;

    private String status;

    private Integer sort;

    private String remark;

    private String createName;

    private Date createTime;

    private String updateName;

    private Date updateTime;
    
}