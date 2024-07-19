package com.domjur.alltime.auth.exception;

import lombok.Data;

@Data
public class UserCustomException extends RuntimeException{

    private String errorCode;

    public UserCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
