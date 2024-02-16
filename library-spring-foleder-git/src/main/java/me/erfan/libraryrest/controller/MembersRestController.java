package me.erfan.libraryrest.controller;

import me.erfan.libraryrest.entity.Book;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Member;
import me.erfan.libraryrest.service.LibraryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MembersRestController {
    private LibraryService libraryService;

    public MembersRestController(LibraryService libraryService) {
        this.libraryService = libraryService;
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

}
