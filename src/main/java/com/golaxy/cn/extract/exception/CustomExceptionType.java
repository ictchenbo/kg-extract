package com.golaxy.cn.extract.exception;

public enum CustomExceptionType {

    USER_INPUT_ERROR(400,"您输入的数据格式错误，或者没有权限！"),
    SYSTEM_ERROR(500,"系统出现异常，请联系管理员！"),
    OTHER_ERROR(999,"系统出现未知异常，请联系管理员！");

    private String desc;
    private int code;
    CustomExceptionType(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getDesc(){
        return desc;
    }
    public int getCode(){
        return code;
    }

}
