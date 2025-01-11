package com.packt.hogwartsartifactsonline.system.exception;

import com.packt.hogwartsartifactsonline.artifact.ArtifactNotFoundException;
import com.packt.hogwartsartifactsonline.system.Result;
import com.packt.hogwartsartifactsonline.system.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ArtifactNotFoundException.class)
    Result handleNotFoundException(ArtifactNotFoundException e) {
        return new Result(false, StatusCode.NOT_FOUND, e.getMessage());
    }
}
