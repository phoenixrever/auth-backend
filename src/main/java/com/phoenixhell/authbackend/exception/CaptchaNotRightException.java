package com.phoenixhell.authbackend.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义一个异常 交给
 */

public class CaptchaNotRightException extends AuthenticationException {

    public CaptchaNotRightException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CaptchaNotRightException(String msg) {
        super(msg);
    }
}
