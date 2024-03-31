package com.phoenixhell.authbackend.exception;

import com.phoenixhell.authbackend.utils.ExceptionCode;
import com.phoenixhell.authbackend.utils.R;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * AuthenticationException异常 全局捕获
 *
 * @ControllerAdvice和@ExceptionHanlder组合拦截，不能拦截Filter中的异常
 * @ControllerAdvice只是对Controller做了加强，而Filter在Controller之前进行 controller中抛出的异常我们使用ControllerAdvice来处理：
 */


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)

    public R handleValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        HashMap<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(item -> {
            errorMap.put(item.getField(), item.getDefaultMessage());
        });
        return R.error(ExceptionCode.VALID_EXCEPTION.getCode(), ExceptionCode.VALID_EXCEPTION.getMessage()).put("error", errorMap);
    }

    /**
     * 捕获参数缺少异常  不然返回404
     * 返回值code 要是0  这个要saunders正常返回 不然前端request 会捕获不是0的返回 会弹框
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        System.out.println(name + " parameter is missing");
        return R.ok().put("error", name + " parameter is missing");
    }


    @ExceptionHandler(AuthenticationException.class)
    public R errorHandle(AuthenticationException authenticationException) {
        System.out.println("AuthenticationException捕获到了异常");

        if (authenticationException instanceof BadCredentialsException ) {
            //BadCredentialsException 的 message是自己定义的
            return R.error(ExceptionCode.LOGIN_EXCEPTION.getCode(), ExceptionCode.LOGIN_EXCEPTION.getMessage());
        }
        return R.error(11111, authenticationException.getMessage());
    }

    @ExceptionHandler(MyException.class)
    public R errorHandle(MyException myException) {
        System.out.println("MyException捕获到了异常");
        return R.error(myException.getCode(), myException.getMessage()
        );
    }

    //捕获其他所有异常
    @ExceptionHandler(Exception.class)
    public R errorHandle(Exception exception) {
        System.out.println("全局异常捕获到了异常");
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }
}
