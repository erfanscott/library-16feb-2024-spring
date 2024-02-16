package me.erfan.libraryrest.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import me.erfan.libraryrest.entity.Book;
import me.erfan.libraryrest.repository.BookRepository;
import me.erfan.libraryrest.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public List<Book> findBooks(@RequestParam String key){
        List<Book> results = new ArrayList<>();

        if(key=="")
            return results;

/**
 * payyyyyyyyyyyyyyyyyy attention to %%%%%%%
 * */
        Specification<Book> spec = (root, query,  criteriaBuilder) -> {
            return (criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%"+key.toLowerCase()+"%")
                    ,criteriaBuilder.like(criteriaBuilder.lower(root.get("authorName")),"%"+key.toLowerCase()+"%")));
        };



       results = bookRepository.findAll(spec);

        return results;
    }


}
