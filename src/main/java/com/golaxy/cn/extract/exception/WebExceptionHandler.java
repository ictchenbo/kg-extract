package com.golaxy.cn.extract.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ExceptionResponse customerException(CustomException e){

        return ExceptionResponse.error(e);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ExceptionResponse Exception(Exception e){

        return ExceptionResponse.error(new CustomException(CustomExceptionType.OTHER_ERROR));
    }
}
