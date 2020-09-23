package cn.liuw.platform.common.base.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * @author liuw
 * @date 2020-08-20
 */
@Data
@NoArgsConstructor
public class ResponseData implements Serializable {

    public static final long serialVersionUID = 1L;

    public static final String SUCCESS_MESSAGE = "操作成功";
    public static final String ERROR_MESSAGE = "服务器异常";
    public static final String SUCCESS_CODE = "200";
    public static final String ERROR_CODE = "400";

    private Result result;

    ResponseData(ResponseData responseData) {
        this.result = responseData.result;
    }

    /**
     * 利用构造者模式
     */
    public static class Builder {

        private ResponseData target;

        public Builder() {
            target = new ResponseData();
        }

        /**
         * 返回数据
         *
         * @return
         */
        public Builder response(String code, String message, Object object) {
            Result result = new Result(code, message, object);
            target.result = result;
            return this;
        }

        /**
         * 返回成功
         *
         * @return
         */
        public Builder success() {
            target.result = new Result(SUCCESS_CODE, SUCCESS_MESSAGE);
            return this;
        }

        /**
         * 返回单个对象
         *
         * @return
         */
        public Builder success(Object object) {
            target.result = new Result(SUCCESS_CODE, SUCCESS_MESSAGE, object);
            return this;
        }

        /**
         * 返回分页查询
         *
         * @return
         */
        public Builder success(Object object, Long total) {
            // 封装分页
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(2);
            map.put("total", total);
            map.put("list", object);

            target.result = new Result(SUCCESS_CODE, SUCCESS_MESSAGE, map);
            return this;
        }

        /**
         * 返回失败
         *
         * @return
         */
        public Builder fail() {
            target.result = new Result(ERROR_CODE, ERROR_MESSAGE);
            return this;
        }

        /**
         * 自定义消息返回失败
         *
         * @return
         */
        public Builder fail(String message) {
            target.result = new Result(ERROR_CODE, message);
            return this;
        }

        public ResponseData build() {
            return new ResponseData(target);
        }

    }
}

