package me.erfan.libraryrest.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import me.erfan.libraryrest.entity.Book;
import me.erfan.libraryrest.entity.enums.BookAvailability;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Member;
import me.erfan.libraryrest.repository.BookRepository;
import me.erfan.libraryrest.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {
    LibraryService libraryService;
    BookRepository bookRepository;

    @Autowired
    public BookRestController(LibraryService libraryService,BookRepository bookRepository) {
        this.libraryService = libraryService;
        this.bookRepository = bookRepository;
    }
    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable String bookId){
        Book requestedBook = libraryService.fetchBookById(Long.valueOf(bookId));
        return requestedBook;
    }

    @PostMapping("/")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        book.setId(null);

        Book savedBook = libraryService.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
    @PostMapping("/populate")
    public void addBooks(@RequestBody List<Book> books){

        for (Book book: books
             ) {
           libraryService.saveBook(book);
        }

    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable(required = false) String bookId){

        libraryService.deleteBook(Long.valueOf(bookId));
        return "successfully deleted";
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Book>> findBooks(@RequestParam String key,@RequestParam String page){


        List<Book> results = new ArrayList<>();
        int pageNumber = Integer.valueOf(page);

        if(key.equals("")){
            results = libraryService.fetchAllBooks(pageNumber);
            return new ResponseEntity<List<Book>>(results, HttpStatus.OK);
        }


        Long keyAsId;
        try {
            keyAsId = Long.valueOf(key);
        }catch (Exception e){
            keyAsId = null;
        }
        final Long longKey = keyAsId;

/**
 * payyyyyyyyyyyyyyyyyy attention to %%%%%%%
 * */
        Specification<Book> spec = (root, query,  criteriaBuilder) -> {
            return (criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%"+key.toLowerCase()+"%")
                    ,criteriaBuilder.like(criteriaBuilder.lower(root.get("authorName")),"%"+key.toLowerCase()+"%")
                    ,criteriaBuilder.equal(root.get("id"),longKey)));
        };



       results = libraryService.findSpecificBooks(spec,pageNumber);

        return new ResponseEntity<List<Book>>(results, HttpStatus.OK);
    }



    @RequestMapping(method = RequestMethod.GET,path = "/borrowed-by")
    public ResponseEntity<List<Book>> findBorrowedByMemberBooks(@RequestParam String key,@RequestParam String borrowedBy,@RequestParam String page){

        List<Book> results = new ArrayList<>();
        int pageNumber = Integer.valueOf(page);
        Long keyAsId;
        try {
            keyAsId = Long.valueOf(key);
        }catch (Exception e){
            keyAsId = null;
        }
        final Long longKey = keyAsId;

        if(key.equals("")){
//            Specification<Book> spec = (root, query,  criteriaBuilder) -> {
//                return (criteriaBuilder.or(
//                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%"+key.toLowerCase()+"%")
//                        ,criteriaBuilder.like(criteriaBuilder.lower(root.get("authorName")),"%"+key.toLowerCase()+"%")
//                        ,criteriaBuilder.equal(root.get("borrowedBy"),libraryService.fetchMemberById(Long.valueOf(borrowedBy)))
//                        ,criteriaBuilder.equal(root.get("id"),longKey)));
//            };
//            results = libraryService.fetchAllBooks(pageNumber);
//            return new ResponseEntity<List<Book>>(results, HttpStatus.OK);
        }

/**
 * payyyyyyyyyyyyyyyyyy attention to %%%%%%%
 * */
        Specification<Book> memberspec = (root, query,  criteriaBuilder) -> {
            return (criteriaBuilder.equal(root.get("borrowedBy"),libraryService.fetchMemberById(Long.valueOf(borrowedBy))));
        };
        Specification<Book> bookSpec = (root, query,  criteriaBuilder) -> {
            return (criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%"+key.toLowerCase()+"%")
                    ,criteriaBuilder.like(criteriaBuilder.lower(root.get("authorName")),"%"+key.toLowerCase()+"%")
                    ,criteriaBuilder.equal(root.get("id"),longKey)));
        };
        Specification<Book> spec = Specification.where(memberspec).and(bookSpec);

        results = libraryService.findSpecificBooks(spec,pageNumber);

        return new ResponseEntity<List<Book>>(results, HttpStatus.OK);
    }
}
