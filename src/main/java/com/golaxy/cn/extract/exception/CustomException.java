package com.golaxy.cn.extract.exception;

public class CustomException extends RuntimeException{

    private int code;
    private String message;

    public CustomException(CustomExceptionType userInputError, String message){

        this.code = userInputError.getCode();
        this.message = message;
    }

    public CustomException(CustomExceptionType exceptionTypeEnum){
        this.code = exceptionTypeEnum.getCode();
    }

    public int getCode(){
        return code;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
