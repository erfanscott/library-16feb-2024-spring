package me.erfan.libraryrest.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.http.HttpResponse;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RegisterationFailedException.class)
    public ResponseEntity<AuthErrorResponse> duplicateEmailHandler(RegisterationFailedException exception){
        AuthErrorResponse responseBody = new AuthErrorResponse(exception.getMessage(),(short) 1);
        ResponseEntity<AuthErrorResponse> response = new ResponseEntity<>(responseBody,HttpStatus.BAD_REQUEST);
        return response;
    }

}
