package com.learning.cruddemo.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CustomErrorResponse {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final Date timestamp;
    private final String message;

    public CustomErrorResponse(Date timestamp, String message) {
        super();
        this.timestamp = timestamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

}
