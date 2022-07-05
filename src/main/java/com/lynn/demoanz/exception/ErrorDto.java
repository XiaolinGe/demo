package com.lynn.demoanz.exception;

import lombok.Data;

import java.util.List;

@Data
public class ErrorDto {
    private String message;
    private List<ErrorField> errorFields;

    public ErrorDto(String message) {
        this.message = message;
    }

    public ErrorDto(String message, List<ErrorField> errorFields) {
        this.message = message;
        this.errorFields = errorFields;
    }
}
