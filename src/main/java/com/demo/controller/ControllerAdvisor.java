package com.demo.controller;

import com.demo.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {
    private final ApiResponse apiResponse;

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGenericException(Exception e){
        String message = e.getMessage() != null ? e.getMessage():"Bad Request";
        apiResponse.setMessage(e.getMessage());
        apiResponse.setData(null);

        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.BAD_REQUEST);
    }

}
