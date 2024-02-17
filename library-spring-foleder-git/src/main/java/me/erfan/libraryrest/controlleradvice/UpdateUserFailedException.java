package me.erfan.libraryrest.controlleradvice;

public class UpdateUserFailedException extends RuntimeException{
    public UpdateUserFailedException() {
    }

    public UpdateUserFailedException(String message) {
        super(message);
    }
}
