package com.golaxy.cn.extract.exception;

public class ExceptionResponse {


    private int code;
    private String message;
    private Object data;
    private ExceptionResponse(){}


    static ExceptionResponse error(CustomException e){

        ExceptionResponse resultBean  = new ExceptionResponse();
        resultBean.setCode(e.getCode());
        if(e.getMessage()!=null){
            resultBean.setMessage(e.getMessage());
        }else{
            resultBean.setMessage(CustomExceptionType.OTHER_ERROR.getDesc());
        }

        return resultBean;
    }

    public static ExceptionResponse error(CustomExceptionType type,String errorMessage){
        ExceptionResponse resultBean  = new ExceptionResponse();
        resultBean.setCode(type.getCode());
        resultBean.setMessage(errorMessage);
        return resultBean;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
