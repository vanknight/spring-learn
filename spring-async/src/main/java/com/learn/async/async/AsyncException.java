package com.learn.async.async;

import lombok.Data;

@Data
public class AsyncException extends RuntimeException{
    public AsyncException(){super();}
    public AsyncException(String msg){
        super(msg);
    }
    public AsyncException(int code,String msg){
        super(msg);
        this.code = code;
    }
    private int code;
}
