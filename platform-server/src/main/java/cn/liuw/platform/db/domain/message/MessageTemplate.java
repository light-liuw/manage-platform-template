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
 * 消息模板
 *
 * @author liuw
 * @date 2020-09-24
 */
@TableName("message_template")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MessageTemplate implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 编号
     */
    private String code;

    /**
     * 内容
     */
    private String content;

    /**
     * 链接
     */
    private String href;

    /**
     * 状态：0-删除，1-待发布，2-发布中，3-已发布
     */
    private Integer status;

    /**
     * 发送周期：1-立即发送，2-定时发送，3-周期发送
     */
    private Integer sendCycle;

    /**
     * 通道类型：1-短信，2-邮件，3-通知消息
     */
    private Integer channelType;

    /**
     * 人群id
     */
    private Long crowdId;

    /**
     * 发送账号
     */
    private String sendAccount;

    /**
     * 是否去重：0-否，1-一小时去重
     */
    private Integer isDistinct;

    /**
     * 夜间屏蔽：0-不屏蔽，1-次日展示，2-次日不展示
     */
    private Integer isShield;

    /**
     * 创建人
     */
    private String createName;

    /**
     * 创建时间
     */
    private Date createTime;


}
