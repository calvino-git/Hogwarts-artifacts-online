package com.packt.hogwartsartifactsonline.system.exception;

import com.packt.hogwartsartifactsonline.artifact.ArtifactNotFoundException;
import com.packt.hogwartsartifactsonline.system.Result;
import com.packt.hogwartsartifactsonline.system.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ArtifactNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleNotFoundException(ArtifactNotFoundException e) {
        return new Result(false, StatusCode.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<Map<String, Object>> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("object-name", error.getObjectName());
            errorDetails.put("field", error.getField());
            errorDetails.put("rejected-value", error.getRejectedValue());
            errorDetails.put("violation-constraint", error.getCode());
            errorDetails.put("message", error.getDefaultMessage());
            errors.add(errorDetails);
        });
        return new Result(false, StatusCode.INVALID_ARGUMENT, e.getMessage(), errors);
    }
}
