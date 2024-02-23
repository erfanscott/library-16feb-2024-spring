package me.erfan.libraryrest.controller;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import me.erfan.libraryrest.entity.Book;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Member;
import me.erfan.libraryrest.entity.enums.BookAvailability;
import me.erfan.libraryrest.entity.enums.Gender;

import me.erfan.libraryrest.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class LibraryRestController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadDb(){
//       Book b3 = Book.builder().name("Poor Folk").authorName("Fyodor Mikhailovich Dostoevsky").availability(BookAvailability.AVAILABLE).build();
//        Book b2 = Book.builder().name("Notes from Underground").authorName("Fyodor Mikhailovich Dostoevsky").availability(BookAvailability.AVAILABLE).build();
//        Book b4 = Book.builder().name("The Idiot").authorName("Fyodor Mikhailovich Dostoevsky").availability(BookAvailability.AVAILABLE).build();
//        Book b5 = Book.builder().name("Crime and Punishment").authorName("Fyodor Mikhailovich Dostoevsky").availability(BookAvailability.AVAILABLE).build();
//        Book b6 = Book.builder().name("War and Peace").authorName("Leo Tolstoy").availability(BookAvailability.AVAILABLE).build();
//        Book b7 = Book.builder().name("Anna Karenina").authorName("Leo Tolstoy").availability(BookAvailability.AVAILABLE).build();
//        Book b8 = Book.builder().name("The Death of Ivan Ilyich").authorName("Leo Tolstoy").availability(BookAvailability.AVAILABLE).build();
//        Book b9 = Book.builder().name("Resurrection").authorName("Leo Tolstoy").availability(BookAvailability.AVAILABLE).build();
//        libraryService.saveBook(b2);libraryService.saveBook(b3);libraryService.saveBook(b4);libraryService.saveBook(b5);libraryService.saveBook(b6);
//        libraryService.saveBook(b7);libraryService.saveBook(b8);libraryService.saveBook(b9);


//        Role member = new Role(("MEMBER"));
//        Role librarian = new Role("LIBRARIAN");
//        Member m1 = Member.builder().email("member@gmail.com").firstName("mammad").lastName("hasani").borrowedBooks(Arrays.asList(b1)).gender(Gender.MALE)
//                .roles(Collections.singleton(member))
//                .pwd(passwordEncoder.encode("123"))
//                .build();
//        b1.setBorrowedBy(m1);
//       member.setLibraryUser(Collections.singleton(m1));
//        Member m2 = Member.builder().email("librarian@gmail.com").firstName("Ali").lastName("Mohammadi").borrowedBooks(Arrays.asList(b2)).gender(Gender.FEMALE)
//                .roles(Collections.singleton(librarian))
//                .pwd(passwordEncoder.encode("123"))
//                .build();
//        b2.setBorrowedBy(m2);
//        librarian.setLibraryUser(Collections.singleton(m2));
//       libraryService.savePerson(m1, Member.class);
//
//l     libraryService.savePerson(m2, Member.class);

    }




    private LibraryService libraryService;

    @Autowired
    public LibraryRestController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }


}
