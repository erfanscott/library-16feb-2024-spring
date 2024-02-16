package me.erfan.libraryrest.entity.libraryUser.libraryusertypes;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import me.erfan.libraryrest.entity.libraryUser.LibraryUser;

import java.util.List;

@Entity
@Table(name = "librarian")
@Data
@NoArgsConstructor
@SuperBuilder
public class Librarian extends LibraryUser {

    @Column(name = "employee_id")
    //@NotNull(message = "employee id should not be null")
    private String employeeId;

}
