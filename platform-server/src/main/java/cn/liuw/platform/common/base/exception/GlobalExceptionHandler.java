package cn.liuw.platform.common.base.exception;

import cn.liuw.platform.common.base.response.ResponseData;
import cn.liuw.platform.common.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liuw
 * 全局的Exception 处理器
 * ExceptionHandler ：指定抛出的Exception的类型，进入不同的处理逻辑
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 抛出异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseData handleException(Exception e) {
        log.error(ExceptionUtil.getMessage(e));
        String message = null;
        if (e instanceof BaseException) {
            message = e.getMessage();
        } else {
            message = "服务器异常";
        }
        return new ResponseData.Builder().fail(message).build();
    }

    /**
     * 参数校验异常处理器
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseData handleMessageException(Exception e) {
        log.error(ExceptionUtil.getMessage(e));
        MethodArgumentNotValidException c = (MethodArgumentNotValidException) e;
        List<ObjectError> errors = c.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        for (ObjectError error : errors) {
            errorMsg.append(error.getDefaultMessage()).append(";");
        }

        return new ResponseData.Builder().
                fail(errorMsg.substring(0, errorMsg.lastIndexOf(";"))).build();
    }


    /**
     * 404 - Not Found
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseData handleHttpNotFoundException(NoHandlerFoundException e) {
        log.error("网页不存在");
        return new ResponseData.Builder().fail("网页不存在").build();
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseData handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数解析失败");
        log.error(e.getMessage());
        return new ResponseData.Builder().fail("参数解析失败").build();
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseData handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法");
        return new ResponseData.Builder().fail("不支持当前请求方法").build();
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseData handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("不支持当前媒体类型");
        return new ResponseData.Builder().fail("不支持当前媒体类型").build();
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Object businessException(HttpServletRequest request, BusinessException e) {
        log.error(e.getMessage(), e);
        return new ResponseData.Builder().fail(e.getMessage()).build();
    }

    /**
     * 登录异常
     */
    @ExceptionHandler(LoginException.class)
    public Object loginException(HttpServletRequest request, LoginException e) {
        log.error(e.getMessage(), e);
        return new ResponseData.Builder().fail(e.getMessage()).build();
    }

}
