package com.hqd.calc.exception;

/**
 * 计算错误异常
 */
public class CalcException extends RuntimeException {
    public CalcException() {
        super();
    }

    public CalcException(String message) {
        super(message);
    }

    public CalcException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalcException(Throwable cause) {
        super(cause);
    }
}
