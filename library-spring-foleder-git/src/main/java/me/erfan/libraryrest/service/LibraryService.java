package me.erfan.libraryrest.service;

import me.erfan.libraryrest.entity.Book;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Librarian;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Member;
import me.erfan.libraryrest.entity.libraryUser.LibraryUser;

import java.util.List;

public interface LibraryService {
    /**
     * UserService
     * */
    <T> T saveLibraryUser(LibraryUser libraryUser, Class<T> clazz);
    Member registerMember(Member member);
    Librarian registerLibrarian(Librarian librarian);
    LibraryUser saveLibraryUser(LibraryUser libraryUser);
    List<LibraryUser> findLibraryUserByEmail(String email);


    /**
     * MemberService
     * */
    List<Member> fetchAllMembers();
    Member fetchMemberById(Long id);
    List<Book> getBorrowedBooks(Long memberId);
    void borrowBook(Long memberId, Long bookId);
    void returnBook(Long memberId, Long bookId);
    /**
     *BookService
     */
    void saveBook(Book book);
    Book fetchBookById(Long id);



}
