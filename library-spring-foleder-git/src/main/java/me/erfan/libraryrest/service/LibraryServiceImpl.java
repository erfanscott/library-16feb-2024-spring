package me.erfan.libraryrest.service;

import me.erfan.libraryrest.entity.Book;
import me.erfan.libraryrest.entity.libraryUser.Authority;
import me.erfan.libraryrest.entity.libraryUser.Authority;
import me.erfan.libraryrest.entity.libraryUser.LibraryUser;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Librarian;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Member;

import me.erfan.libraryrest.repository.BookRepository;
import me.erfan.libraryrest.repository.LibrarianRepository;
import me.erfan.libraryrest.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Member fetchMemberById(Long id) {
        Optional<Member> result = memberRepository.findById(id);
        if(result.isPresent())
            return result.get();
        /**
         * This should be revised
         * */
        throw null;
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
        book.setBorrowingDate(LocalDate.now());
        saveLibraryUser(member,Member.class);
    }

    @Override
    public void returnBook(Long memberId, Long bookId) {
        Member member = fetchMemberById(memberId);
        Book book = fetchBookById(bookId);
        member.removeBorrowedBook(book);
        book.setBorrowedBy(null);
        book.setBorrowingDate(null);
        saveLibraryUser(member,Member.class);
        saveBook(book);
    }


    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Book fetchBookById(Long id) {
        Optional<Book> result = bookRepository.findById(id);
        if(result.isPresent())
            return result.get();
        /**
         * This should be revised
         * */
        throw null;
    }


    public String delete(){
        return "s";
    }

}
