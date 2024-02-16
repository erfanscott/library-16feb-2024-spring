package me.erfan.libraryrest.controlleradvice;

public class RegisterationFailedException extends RuntimeException{
    public RegisterationFailedException() {
    }

    public RegisterationFailedException(String message) {
        super(message);
    }
}
