package com.phoenixhell.authbackend.exception;

import com.phoenixhell.authbackend.utils.ExceptionCode;
import com.phoenixhell.authbackend.utils.R;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * AuthenticationException异常 也可以被全局异常捕获  不过不推荐
 *
 * @ControllerAdvice和@ExceptionHanlder组合拦截，不能拦截Filter中的异常
 * @ControllerAdvice只是对Controller做了加强，而Filter在Controller之前进行 controller中抛出的异常我们使用ControllerAdvice来处理：
 */


@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(value = MethodArgumentNotValidException.class)
    //TODO 捕获AUTH 异常

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
        return R.ok().put("error",name + " parameter is missing");
    }

    @ExceptionHandler(MyException.class)
    public R errorHandle(MyException myException) {
        return R.error(myException.getCode(), myException.getMessage());
    }
}
