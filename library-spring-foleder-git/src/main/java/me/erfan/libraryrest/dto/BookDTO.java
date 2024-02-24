package me.erfan.libraryrest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDTO {
    private String name;
}
