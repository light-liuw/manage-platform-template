package cn.liuw.platform.db.domain.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息表
 *
 * @author liuw
 * @date 2020-09-24
 */
@TableName("message_template")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MessageTemplate implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String code;
    
    private String content;
    
    private String href;
    
    private String sendCycle;
    
    private String channelType;
    
    private Long crowdId;
    
    private String sendAccount;
    
    private String isDistinct;
    
    private String isShield;
    
    private String createName;
    
    private Date createTime;


}
