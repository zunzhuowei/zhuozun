package com.qs.game.aspect;

import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.enum0.Code;
import com.qs.game.exception.TokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.bind.ValidationException;

/**
 * Created by zun.wei on 2018/8/14 10:22.
 * Description: 全局异常处理切面
 * Description: 利用 @ControllerAdvice + @ExceptionHandler 组合处理Controller层RuntimeException异常
 */
@Slf4j
@ControllerAdvice   // 控制器增强
@ResponseBody
public class ExceptionAspect {


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public BaseResult handleValidationException(ValidationException e) {
        log.error("parameter_validation_exception", e);
        return BaseResult.getBuilder().setCode(Code.ERROR_1000).setMessage("parameter_validation_exception").setSuccess(false).build();
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("could_not_read_json...", e);
        return BaseResult.getBuilder().setCode(Code.ERROR_1000).setMessage("could_not_read_json").setSuccess(false).build();
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public BaseResult handleValidationException(MethodArgumentNotValidException e) {
        log.error("parameter_validation_exception...", e);
        return BaseResult.getBuilder().setCode(Code.ERROR_1000).setMessage("parameter_validation_exception")
                .setSuccess(false).build();
    }

    /**
     * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResult handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("request_method_not_supported...", e);
        return BaseResult.getBuilder().setCode(Code.ERROR_1000).setMessage("request_method_not_supported")
                .setSuccess(false).build();
    }

    /**
     * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public BaseResult handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("content_type_not_supported...", e);
        return BaseResult.getBuilder().setCode(Code.ERROR_1000).setMessage("content_type_not_supported")
                .setSuccess(false).build();
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TokenException.class)
    public BaseResult handleTokenException(Exception e) {
        log.error("Token is invaild...", e);
        return BaseResult.getBuilder().setCode(Code.ERROR_1000).setMessage("Token is invaild")
                .setSuccess(false).build();
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResult handleException(Exception e) {
        log.error("Internal Server Error...", e);
        return BaseResult.getBuilder().setCode(Code.ERROR_1000).setMessage("Internal Server Error")
                .setSuccess(false).build();
    }

}
