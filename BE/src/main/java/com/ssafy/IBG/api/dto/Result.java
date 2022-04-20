package com.ssafy.IBG.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private int code;
    private T data;


    public Result(int code){
        this.code = code;
    }
}
