package me.erfan.libraryrest.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.erfan.libraryrest.dto.LibraryUserDTO;
import me.erfan.libraryrest.entity.Book;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Librarian;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Member;
import me.erfan.libraryrest.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/librarians")
public class LibrariansRestController {
    private LibraryService libraryService;
    private SessionRegistry sessionRegistry;

    @Autowired
    public LibrariansRestController(LibraryService libraryService,SessionRegistry sessionRegistry) {
        this.libraryService = libraryService;
        this.sessionRegistry = sessionRegistry;
    }

    @GetMapping("/")
    public List<Member> getMembers(){
        return libraryService.fetchAllMembers();
    }

    @GetMapping("/members/{memberId}")
    public Member getMember(@PathVariable String memberId){
        Member requestedMember = libraryService.fetchMemberById(Long.valueOf(memberId));
        return requestedMember;
    }

    @PostMapping("/")
    public Member addMember(@RequestBody Member member){
        member.setId(null);
        Member savedMember = libraryService.saveLibraryUser(member,Member.class);
        return savedMember;
    }

    @GetMapping("/{memberId}/borrowed-books")
    public List<Book> getBorrowedBooks(@PathVariable String memberId){
        return libraryService.getBorrowedBooks(Long.valueOf(memberId));
    }
    @PostMapping("/{memberId}/borrow/{bookId}")
    public void borrowBook(@PathVariable String memberId,@PathVariable String bookId){
        libraryService.borrowBook(Long.valueOf(memberId),Long.valueOf(bookId));
    }

    @PostMapping("/{memberId}/return/{bookId}")
    public void returnBook(@PathVariable String memberId,@PathVariable String bookId){
        libraryService.returnBook(Long.valueOf(memberId),Long.valueOf(bookId));
    }

    @PutMapping("/{librarianId}")
    public ResponseEntity<String> updateLibrarian(@RequestBody LibraryUserDTO updatedFields,
                                                  @PathVariable String librarianId,
                                                  Authentication authentication,
                                                  HttpServletRequest request){
        System.out.println(librarianId+"////i was in put mapping");
        libraryService.updateLibraryUserProfile(Long.valueOf(librarianId),updatedFields, Librarian.class);
//        if(authentication.getName()!=updatedFields.getEmail()) {
//            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
//            System.out.println(details.getSessionId());
//            sessionRegistry.getSessionInformation(details.getSessionId()).expireNow();
//        }
        return new ResponseEntity<>("the user has been successfullyy updated",HttpStatus.OK);
    }

}

