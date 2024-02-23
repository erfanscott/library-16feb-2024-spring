package me.erfan.libraryrest.controller;

import jakarta.servlet.http.HttpSession;
import me.erfan.libraryrest.controlleradvice.RegisterationFailedException;
import me.erfan.libraryrest.controlleradvice.SessionCredentialsNotValidException;
import me.erfan.libraryrest.entity.libraryUser.LibraryUser;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Librarian;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Member;
import me.erfan.libraryrest.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class LibraryAuthController {

    private LibraryService libraryService;
    private SessionRegistry sessionRegistry;

    @Autowired
    public LibraryAuthController(LibraryService libraryService,SessionRegistry sessionRegistry) {
        this.libraryService = libraryService;
        this.sessionRegistry = sessionRegistry;
    }




    @GetMapping("/user")
    public LibraryUser getUser(Authentication authentication){

        String  username = authentication.getName();
        List<LibraryUser>  user = libraryService.findLibraryUserByEmail(username);
        if(user.size()>0)
            return user.get(0);
            else
                throw new SessionCredentialsNotValidException();
    }
    @PostMapping("/member/register")
    public ResponseEntity<Member> registerUser(@RequestBody Member member){

        try{
            Member dbMember = libraryService.registerMember(member);
            return new ResponseEntity<Member>(dbMember, HttpStatus.CREATED);
            /**
             * You should add some filters to catch exceptions fram sql
             * This ain't doing shit
             * */
        }catch (Exception e){
            System.out.println("exception was thrown");
            throw new RegisterationFailedException(e.getMessage());
        }

    }
    @PostMapping("/librarian/register")
    public ResponseEntity<Librarian> registerUser(@RequestBody Librarian librarian){

        try{
            Librarian dbLibrarian = libraryService.registerLibrarian(librarian);
            return new ResponseEntity<Librarian>(dbLibrarian, HttpStatus.CREATED);
            /**
             * You should add some filters to catch exceptions fram sql
             * This ain't doing shit
             * */
        }catch (Exception e){
            System.out.println("exception was thrown");
            throw new RegisterationFailedException(e.getMessage());
        }

    }

}
