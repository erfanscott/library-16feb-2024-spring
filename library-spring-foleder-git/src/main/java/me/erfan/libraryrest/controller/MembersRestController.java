package me.erfan.libraryrest.controller;

import me.erfan.libraryrest.dto.LibraryUserDTO;
import me.erfan.libraryrest.entity.Book;
import me.erfan.libraryrest.entity.libraryUser.Authority;
import me.erfan.libraryrest.entity.libraryUser.LibraryUser;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Member;
import me.erfan.libraryrest.service.LibraryService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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

    @GetMapping("/{memberId}")
    public Member getMember(@PathVariable String memberId){
        Member requestedMember = libraryService.fetchMemberById(Long.valueOf(memberId));
        return requestedMember;
    }

    /**
     *This has to be worked onnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
     *
     */
    @PostMapping("/")
    public Member addMember(@RequestBody Member member){
        member.setId(null);
        Member savedMember = libraryService.registerMember(member);
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

    @PutMapping("/{memberId}")
    public ResponseEntity<String> updateMember(@RequestBody LibraryUserDTO updatedFields,@PathVariable String memberId){
        System.out.println(memberId+"////i was in put mapping");
            libraryService.updateLibraryUserProfile(Long.valueOf(memberId),updatedFields, Member.class);
            return new ResponseEntity<>("the user has been successfullyy updated",HttpStatus.OK);
    }

    @PostMapping("/populate")
    public void addMembers(@RequestBody List<Member> members){

        for (Member member: members
        ) {
            libraryService.saveLibraryUser(member, Member.class);
        }

    }
    @DeleteMapping("/{memberId}")
    public String deleteBook(@PathVariable String memberId){
        libraryService.deleteMember(Long.valueOf(memberId));
        return "Successfully deleted";
    }

    @RequestMapping(method = RequestMethod.GET)

    public ResponseEntity<List<Member>> findMembers(@RequestParam String key,@RequestParam String page){


        List<Member> results = new ArrayList<>();
        int pageNumber = Integer.valueOf(page);

        if(key.equals("")){
            results = libraryService.fetchAllMembers(pageNumber);
            return new ResponseEntity<List<Member>>(results, HttpStatus.OK);
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
        Specification<Member> spec = (root, query, criteriaBuilder) -> {
            return (criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),"%"+key.toLowerCase()+"%")
                    ,criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),"%"+key.toLowerCase()+"%")
                    ,criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),"%"+key.toLowerCase()+"%")
                    ,criteriaBuilder.equal(root.get("id"),longKey)));
        };



        results = libraryService.findSpecificMembers(spec,pageNumber);

        return new ResponseEntity<List<Member>>(results, HttpStatus.OK);
    }

}
