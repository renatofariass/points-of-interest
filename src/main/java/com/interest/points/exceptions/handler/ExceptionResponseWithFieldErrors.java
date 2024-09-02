package com.interest.points.exceptions.handler;

import java.util.Date;
import java.util.Map;

public class ExceptionResponseWithFieldErrors extends ExceptionResponse{
    private Map<String, String> fieldErrors;

    public ExceptionResponseWithFieldErrors(Date timestamp, String message, String details, Map<String, String> fieldErrors) {
        super(timestamp, message, details);
        this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
