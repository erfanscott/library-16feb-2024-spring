package me.erfan.libraryrest.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RegisterationFailedException.class)
    public ResponseEntity<ErrorResponseBody> duplicateEmailHandler(RegisterationFailedException exception){
        ErrorResponseBody responseBody = new ErrorResponseBody(exception.getMessage(),(short) 1);
        ResponseEntity<ErrorResponseBody> response = new ResponseEntity<>(responseBody,HttpStatus.BAD_REQUEST);
        return response;
    }
    @ExceptionHandler(UpdateUserFailedException.class)
    public ResponseEntity<ErrorResponseBody> updateUserFailedHandler(UpdateUserFailedException exception){
        return new ResponseEntity<ErrorResponseBody>(ErrorResponseBody.builder()
                .message("the user was not found").errorCode(HttpStatus.NOT_FOUND.value()).build(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SessionCredentialsNotValidException.class)
    public ResponseEntity<ErrorResponseBody> sessionCredentialsNotValidHandler(SessionCredentialsNotValidException exception){
        return new ResponseEntity<ErrorResponseBody>(ErrorResponseBody.builder()
                .message("the credentials associated with this session id are not valid anymore")
                .errorCode(HttpStatus.UNAUTHORIZED.value()).build(),HttpStatus.UNAUTHORIZED);    }

}
