package com.phoenixhell.authbackend.utils;

/**
 * @author phoenixhell
 * @since 2022/1/27 0027-下午 2:52
 */



public enum ExceptionCode {
    LOGIN_EXCEPTION(20000,"用户名或密码错误"),
    CAPTCHA_EXCEPTION(90001,"验证码错误"),
    TO_MANY_REQUEST_EXCEPTION(10002, "请求过于频繁"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    SMS_CODe_EXCEPTION(10001, "验证码获取频率太高请稍后再试"),
    USER_EXIST_EXCEPTION(15001, "用户名已经存在"),
    PHONE_EXIST_EXCEPTION(15002, "手机号已经存在"),
    TOKEN_NOT_MATCH_EXCEPTION(21001, "令牌校验失败"),
    FEIGN_EXCEPTION(22000, "feign远程服务调用失败"),
    //远程验证唯一性返回0 success
    UNIQUE_CHECK(0,"验证唯一性失败"),
    ACCESS_DENIED(20002,"权限不足");

    private final Integer code;
    private final String message;


    ExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

}
