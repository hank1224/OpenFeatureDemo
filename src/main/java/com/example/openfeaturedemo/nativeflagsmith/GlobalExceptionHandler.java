package com.example.openfeaturedemo.nativeflagsmith;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        // 這裡可以根據異常的具體信息返回更具體的錯誤響應
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Data integrity violation, possibly duplicate product code.");
    }
}
