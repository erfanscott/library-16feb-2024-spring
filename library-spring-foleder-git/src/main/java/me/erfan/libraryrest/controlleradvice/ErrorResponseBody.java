package me.erfan.libraryrest.controlleradvice;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseBody {
    private String message;
    private int errorCode;
}
