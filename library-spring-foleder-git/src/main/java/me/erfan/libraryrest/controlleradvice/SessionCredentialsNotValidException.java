package me.erfan.libraryrest.controlleradvice;

public class SessionCredentialsNotValidException extends RuntimeException{
    public SessionCredentialsNotValidException() {
    }

    public SessionCredentialsNotValidException(String message) {
        super(message);
    }
}
