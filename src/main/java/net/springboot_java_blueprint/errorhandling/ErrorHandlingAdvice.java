package net.springboot_java_blueprint.errorhandling;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlingAdvice {

    @ExceptionHandler(ProblemDetailsException.class)
    public ResponseEntity<ProblemDetail> handle(ProblemDetailsException exception) {
        var problemDetail = exception.getProblemDetail();
        return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
    }
}
