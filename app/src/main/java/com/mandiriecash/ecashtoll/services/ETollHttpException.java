package com.mandiriecash.ecashtoll.services;

public class ETollHttpException extends Exception{
    int code;
    String body;

    public ETollHttpException(int code,String body){
        super(code+" "+body);
        this.code = code;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }
}
