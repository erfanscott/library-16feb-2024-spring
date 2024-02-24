package me.erfan.libraryrest.service;

import me.erfan.libraryrest.controlleradvice.UpdateUserFailedException;
import me.erfan.libraryrest.dto.LibraryUserDTO;
import me.erfan.libraryrest.entity.Book;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Librarian;
import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Member;
import me.erfan.libraryrest.entity.libraryUser.LibraryUser;
import org.springframework.data.jpa.domain.Specification;

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
    void updateLibraryUserProfile(Long id, LibraryUserDTO updatedFields,Class<?> clazz) throws UpdateUserFailedException;

    /**
     * MemberService
     * */
    List<Member> findSpecificMembers(Specification<Member> spec, int pageNumber);
    List<Member> fetchAllMembers(int page);
    List<Member> fetchAllMembers();
    Member fetchMemberById(Long id);
    void deleteMember(Long id);
    List<Book> getBorrowedBooks(Long memberId);
    void borrowBook(Long memberId, Long bookId);
    void returnBook(Long memberId, Long bookId);
    /**
     *BookService
     */
    Book saveBook(Book book);
    Book fetchBookById(Long id);
    List<Book> fetchAllBooks(int page);
    List<Book> findSpecificBooks(Specification<Book> spec, int pageNumber);


    void deleteBook(Long id);
}
