package com.phoenixhell.authbackend.exception;

import com.phoenixhell.authbackend.utils.ExceptionCode;
import com.phoenixhell.authbackend.utils.R;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * controller中抛出的异常我们使用ControllerAdvice来处理：
 * @ControllerAdvice和@ExceptionHanlder组合拦截，不能拦截Filter中的异常
 * @ControllerAdvice只是对Controller做了加强，而Filter在Controller之前进行
 *
 */


@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     *  AccessDeniedException 在调用 Controller 之后抛出的，
     *  @ControllerAdvice 全局异常可以捕获
     */
    @ExceptionHandler(AccessDeniedException.class)
    public R handleAccessDeniedException(AccessDeniedException exception) {
        System.out.println("AccessDeniedException捕获到了异常  ： "+exception);
        return R.error(99999, exception.getMessage());
    }


    /**
     *  AuthenticationException异常是由 DispatcherServlet 后面的 Authentication Filter 在调用 Controller 方法之前抛出的，
     *  因此 @ControllerAdvice 无法捕获这些异常
     *  需要通过自定义 CustomAccessDeniedHandler  使用handlerExceptionResolver 传递异常到控制层捕获
     *
     */
    @ExceptionHandler(AuthenticationException.class)
    public R handleAuthenticationException(AuthenticationException exception) {
        System.out.println("AuthenticationException捕获到了异常"+exception);

        if (exception instanceof BadCredentialsException ) {
            //BadCredentialsException 的 message是自己定义的
            return R.error(ExceptionCode.LOGIN_EXCEPTION.getCode(), ExceptionCode.LOGIN_EXCEPTION.getMessage());
        }
        else if(exception  instanceof InsufficientAuthenticationException){
            return R.error(ExceptionCode.TOKEN_NOT_MATCH_EXCEPTION.getCode(), ExceptionCode.TOKEN_NOT_MATCH_EXCEPTION.getMessage());
        }
        return R.error(99999, exception.getMessage());
    }


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


    @ExceptionHandler(MyException.class)
    public R errorHandle(MyException myException) {
        System.out.println("MyException捕获到了异常");
        return R.error(myException.getCode(), myException.getMessage()
        );
    }

    //捕获其他所有异常
    //@ExceptionHandler(Exception.class)
    //public R errorHandle(Exception exception) {
    //    System.out.println("全局异常捕获到了异常");
    //    return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    //}
}
