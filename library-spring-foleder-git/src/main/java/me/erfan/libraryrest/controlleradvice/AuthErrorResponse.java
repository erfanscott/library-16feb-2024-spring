package me.erfan.libraryrest.controlleradvice;

public class AuthErrorResponse {
    private String message;
    private short errorCode;

    public AuthErrorResponse(String message, short errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
