package cn.liuw.platform.db.domain.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * 
 * @author liuw 
 * @date 2020-08-20
 */
@TableName("system_user")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 2596059579817014810L;
    
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    @NotBlank(message = "用户名不能为空！")
    @Length(max = 20, message = "用户名长度不超过20！")
    private String username;

    @NotBlank(message = "密码不能为空！")
    private String password;

    private String idCard;

    private String sex;

    private String status;

    private String orgCode;

    private String phone;

    private String remark;

    private String createName;

    private Date createTime;

    private String updateName;

    private Date updateTime;
}
