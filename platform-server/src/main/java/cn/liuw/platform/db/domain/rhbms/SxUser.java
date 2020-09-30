package cn.liuw.platform.db.domain.rhbms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("t_sx_user")
@Data
@NoArgsConstructor
public class SxUser implements Serializable {

    private static final long serialVersionUID = 6480444207175845778L;

    /**
     * ID
     */
    @TableField("ID")
    private Long id;

    /**
     * 用户账号
     */
    private String serviceId;

    /**
     * '机顶盒编码'
     */
    private String boxCode;

    /**
     * '地市信息'
     */
    private String acceptCity;

    /**
     * '客户姓名'
     */
    private String fLinkman;

    /**
     * '联系电话'
     */
    private String contactPhone;

    /**
     * '地址'
     */
    private String contactAddress;

    /**
     * '操作代码\r\nSelectList{1:开通;2:换机;3:停机;4:销户;5:复机}'
     */
    private String operCode;

    /**
     * '旧机顶盒编码'
     */
    private String oldBosCode;

    /**
     * '产品标识'
     */
    private String prodId;

    /**
     * '保留字段值'
     */
    private String paraValue;

    /**
     * '创建时间'
     */
    private String createtime;

    /**
     * '更新时间'
     */
    private String updatetime;

    /**
     * '用户有效期'
     */
    private String userValidity;

    /**
     * '用户ip'
     */
    private String ip;

    /**
     * '0:否;1:是'
     */
    private String isBlacklist;

    /**
     * vip标识
     */
    private String userVip;

}