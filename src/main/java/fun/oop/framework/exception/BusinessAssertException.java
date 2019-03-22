package fun.oop.framework.exception;


import fun.oop.framework.render.ResponseCode;

public class BusinessAssertException extends RuntimeException {

    private static final long serialVersionUID = -4224770594424495880L;
    private Integer errorCode = ResponseCode.ERROR;


    public BusinessAssertException(String message) {
        super(message);
    }

    public BusinessAssertException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }


    public BusinessAssertException() {
    }

    public BusinessAssertException(String message, Throwable e) {
        super(message, e);
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
