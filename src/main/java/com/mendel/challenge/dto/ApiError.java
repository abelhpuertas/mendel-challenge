package com.mendel.challenge.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError {
    private HttpStatus status;
    private String msg;

    private ApiError(HttpStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    public static ApiError create(HttpStatus status, String msg) {
        return new ApiError(status, msg);
    }
}
