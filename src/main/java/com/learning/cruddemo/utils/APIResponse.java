package com.learning.cruddemo.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
public class APIResponse<T> {
    private final String timestamp = LocalDateTime.now().toString();
    private T data;
    private HttpStatus status;

    public APIResponse(T data, HttpStatus status) {
        this.data = data;
        this.status = status;
    }

}
