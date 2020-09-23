package cn.liuw.platform.common.base.controller;


import cn.liuw.platform.common.base.response.ResponseData;

/**
 * @author liuw
 * @date 2020-08-06
 * 封装的基础 controller
 */
public class BaseController {

    /**
     * 自定义状态返回信息
     *
     * @return
     */
    public ResponseData response(String code, String message, Object object) {
        return new ResponseData.Builder().response(code, message, object).build();
    }

    /**
     * 返回成功
     *
     * @return
     */
    public ResponseData success() {
        return new ResponseData.Builder().success().build();
    }

    /**
     * 返回单个对象
     *
     * @return
     */
    public ResponseData success(Object object) {
        return new ResponseData.Builder().success(object).build();
    }

    /**
     * 返回分页查询
     *
     * @return
     */
    public ResponseData success(Object object, Long total) {
        return new ResponseData.Builder().success(object, total).build();
    }

    /**
     * 返回失败
     *
     * @return
     */
    public ResponseData fail() {
        return new ResponseData.Builder().fail().build();
    }

    /**
     * 自定义消息返回失败
     *
     * @return
     */
    public ResponseData fail(String message) {
        return new ResponseData.Builder().fail(message).build();
    }


}
