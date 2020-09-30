package cn.liuw.platform.db.domain.rhbms.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XStreamAlias("IPTVDATA")
public class SxUserXmlResponse {

    /**
     * 用户账号
     */
    @XStreamAlias("SERVICE_ID")
    private String serviceId;

    /**
     * '机顶盒编码'
     */
    @XStreamAlias("BOX_CODE")
    private String boxCode;

    /**
     * '地市信息'
     */
    @XStreamAlias("ACCEPT_CITY")
    private String acceptCity;

    /**
     * '客户姓名'
     */
    @XStreamAlias("F_LINKMAN")
    private String fLinkman;

    /**
     * '联系电话'
     */
    @XStreamAlias("CONTACT_PHONE")
    private String contactPhone;

    /**
     * '地址'
     */
    @XStreamAlias("CONTACT_ADDRESS")
    private String contactAddress;

    /**
     * '操作代码\r\nSelectList{1:开通;2:换机;3:停机;4:销户;5:复机}'
     */
    @XStreamAlias("OPER_CODE")
    private String operCode;

    /**
     * '旧机顶盒编码'
     */
    @XStreamAlias("OLD_BOX_CODE")
    private String oldBosCode;

    /**
     * '产品标识'
     */
    @XStreamAlias("PROD_ID")
    private String prodId;

    /**
     * 保留字段值
     */
    @XStreamAlias("PARA_VALUE")
    private String paraValue;

    /**
     * VIP标识：1-vip,2-非vip
     */
    @XStreamAlias("USER_VIP")
    private String userVip;

}