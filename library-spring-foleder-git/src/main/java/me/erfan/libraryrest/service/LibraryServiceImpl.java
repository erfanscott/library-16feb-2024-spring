package me.erfan.libraryrest.service;

import me.erfan.libraryrest.controlleradvice.UpdateUserFailedException;
import me.erfan.libraryrest.dto.LibraryUserDTO;
import me.erfan.libraryrest.entity.Book;
import me.erfan.libraryrest.entity.enums.BookAvailability;
import me.erfan.libraryrest.entity.libraryUser.Authority;
import me.erfan.libraryrest.entity.libraryUser.Authority;
import me.erfan.libraryrest.entity.libraryUser.LibraryUser;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Librarian;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Member;

import me.erfan.libraryrest.repository.BookRepository;
import me.erfan.libraryrest.repository.LibrarianRepository;
import me.erfan.libraryrest.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private me.erfan.libraryrest.repository.LibraryUserRepository libraryUserRepository;
    private MemberRepository memberRepository;
    private LibrarianRepository librarianRepository;
    private BookRepository bookRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LibraryServiceImpl(me.erfan.libraryrest.repository.LibraryUserRepository libraryUserRepository,
                              MemberRepository memberRepository,
                              LibrarianRepository librarianRepository,
                              BookRepository bookRepository,
                              PasswordEncoder passwordEncoder) {
        this.libraryUserRepository = libraryUserRepository;
        this.memberRepository = memberRepository;
        this.librarianRepository = librarianRepository;
        this.bookRepository = bookRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *has some issues
     */
    @Override
    public <T> T saveLibraryUser(LibraryUser libraryUser, Class<T> clazz) {
        return (T) this.libraryUserRepository.save(libraryUser);
    }

    @Override
    public LibraryUser saveLibraryUser(LibraryUser libraryUser) {
        return (LibraryUser) this.libraryUserRepository.save(libraryUser);
    }

    @Override
    public List<LibraryUser> findLibraryUserByEmail(String email) {
        return libraryUserRepository.findByEmail(email);
    }

    @Override
    public void updateLibraryUserProfile(Long id, LibraryUserDTO updatedFields,Class<?> clazz) throws UpdateUserFailedException {
        System.out.println(id+"////i was in library service");
        if(clazz.equals(Member.class)){
            Optional<Member> result = memberRepository.findById(id);
            if(result.isPresent()) {
                Member user = result.get();

                if(updatedFields.getFirstName()!= null)
                    user.setFirstName(updatedFields.getFirstName());
                if(updatedFields.getLastName()!= null)
                    user.setLastName(updatedFields.getLastName());
                if(updatedFields.getEmail()!= null)
                    user.setEmail(updatedFields.getEmail());
                saveLibraryUser(user, Member.class);
            }else
                throw new UpdateUserFailedException();
        }else if(clazz.equals(Librarian.class)){

                Optional<Librarian> result = librarianRepository.findById(id);
                if(result.isPresent()) {
                    Librarian user = result.get();

                    if(updatedFields.getFirstName()!= null)
                        user.setFirstName(updatedFields.getFirstName());
                    if(updatedFields.getLastName()!= null)
                        user.setLastName(updatedFields.getLastName());
                    if(updatedFields.getEmail()!= null)
                        user.setEmail(updatedFields.getEmail());
                    saveLibraryUser(user, Librarian.class);
                }else
                    throw new UpdateUserFailedException();
        }else{
            throw new UpdateUserFailedException();
        }


    }

    @Override
    public Member registerMember(Member member) {
        member.setPwd(passwordEncoder.encode(member.getPwd()));
        member.setAuthorities(Collections.singleton(new Authority("ROLE_MEMBER",member)));
        return saveLibraryUser(member, Member.class);
    }

    @Override
    public Librarian registerLibrarian(Librarian librarian) {
        librarian.setPwd(passwordEncoder.encode(librarian.getPwd()));
        librarian.setAuthorities(Collections.singleton(new Authority("ROLE_LIBRARIAN",librarian)));
        return saveLibraryUser(librarian, Librarian.class);
    }


    @Override
    public List<Member> fetchAllMembers() {
       return (List<Member>) memberRepository.findAll();
    }

    @Override
    public List<Member> fetchAllMembers(int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Member> results =  memberRepository.findAll(pageable);
        return results.stream().toList();
    }

    @Override
    public List<Member> findSpecificMembers(Specification<Member> spec, int pageNumber) {
        Page<Member> results = memberRepository.findAll(spec,PageRequest.of(pageNumber,10));
        return results.stream().toList();
    }
    @Override
    public Member fetchMemberById(Long id) {
        Optional<Member> result = memberRepository.findById(id);
        if(result.isPresent())
            return result.get();
        /**
         * This should be revised
         * */
        throw new RuntimeException();
    }
    @Override
    public void deleteMember(Long id){
        Member member = fetchMemberById(id);
        List<Book>  borrowedBooks = member.getBorrowedBooks();
        for(Book book : borrowedBooks){
            book.setBorrowedBy(null);
            saveBook(book);
        }
        memberRepository.deleteById(id);
    }
    @Override
    public List<Book> getBorrowedBooks(Long memberId) {
      Member member = fetchMemberById(memberId);
      List<Book> borrowedBooks = member.getBorrowedBooks();
      return borrowedBooks;
    }
    @Override
    public void borrowBook(Long memberId, Long bookId) {
        Member member = fetchMemberById(memberId);
        Book book = fetchBookById(bookId);
        member.addBorrowedBook(book);
        book.setBorrowedBy(member);
        book.setAvailability(BookAvailability.BORROWED);
        book.setBorrowingDate(LocalDate.now());
        saveLibraryUser(member,Member.class);
    }

    @Override
    public void returnBook(Long memberId, Long bookId) {
        Member member = fetchMemberById(memberId);
        Book book = fetchBookById(bookId);
        member.removeBorrowedBook(book);
        book.setBorrowedBy(null);
        book.setAvailability(BookAvailability.AVAILABLE);
        book.setBorrowingDate(null);
        saveLibraryUser(member,Member.class);
        saveBook(book);
    }


    @Override
    public Book saveBook(Book book) {
      return bookRepository.save(book);
    }

    @Override
    public Book fetchBookById(Long id) {
        Optional<Book> result = bookRepository.findById(id);
        if(result.isPresent())
            return result.get();
        /**
         * This should be revised
         * */
        throw new RuntimeException();
    }

    @Override
    public List<Book> fetchAllBooks(int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Book> results =  bookRepository.findAll(pageable);
        return results.stream().toList();
    }

    @Override
    public List<Book> findSpecificBooks(Specification<Book> spec, int pageNumber) {
        Page<Book> results = bookRepository.findAll(spec,PageRequest.of(pageNumber,10));
        return results.stream().toList();
    }

    @Override
    public void deleteBook(Long id) {
       // Member member = book.getBorrowedBy();
        bookRepository.deleteById(id);
      //

    }


    public String delete(){
        return "s";
    }

}
