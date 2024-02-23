package me.erfan.libraryrest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import me.erfan.libraryrest.entity.enums.BookAvailability;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Member;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
/**Whhhhhhhhhhhhhhhhhhhhhhhhhhhhyyyyyyyyyyyyyyyyyyyyyyyy*/
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = "borrowedBy")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotEmpty(message = "name of the book is required")
    private String name;

    //@NotEmpty(message = "name of the book is required")
    private String authorName;

    @Enumerated(EnumType.STRING)
    private BookAvailability availability = BookAvailability.AVAILABLE;

   // @Temporal(TemporalType.DATE)
    private LocalDate borrowingDate;

    @JsonIgnoreProperties("borrowedBooks")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member borrowedBy;
}
