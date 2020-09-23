package cn.liuw.platform.common.base.exception;

/**
 * 业务异常
 *
 * @author liuw
 */
public class LoginException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected final String message;

    public LoginException(String message) {
        this.message = message;
    }

    public LoginException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
