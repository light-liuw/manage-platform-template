package cn.liuw.platform.common.base.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuw
 * @date 2020-08-20
 */

@Data
public class HttpResponseData implements Serializable {

    private static final long serialVersionUID = -498749404030235432L;

    /**
     * 响应代码
     */
    private Integer code = 0;

    /**
     * 结果说明
     */
    private String message = "成功";

    public HttpResponseData() {
    }

    public HttpResponseData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setValues(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

