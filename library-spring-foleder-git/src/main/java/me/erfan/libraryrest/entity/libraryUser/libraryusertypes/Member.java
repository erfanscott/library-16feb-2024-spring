package me.erfan.libraryrest.entity.libraryUser.libraryusertypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import me.erfan.libraryrest.entity.Book;
import me.erfan.libraryrest.entity.libraryUser.LibraryUser;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "member")
@Data
@NoArgsConstructor
@SuperBuilder
@ToString(exclude = "borrowedBooks")
public class Member extends LibraryUser {


    @JsonIgnoreProperties("borrowedBy")
    @OneToMany(mappedBy = "borrowedBy",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Book> borrowedBooks = new ArrayList<>();
    //removeIf("first"::equals);
    public void addBorrowedBook(Book book){
        borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }
}
