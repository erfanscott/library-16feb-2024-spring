package me.erfan.libraryrest.repository;

import me.erfan.libraryrest.entity.libraryUser.LibraryUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LibraryUserRepository extends CrudRepository<LibraryUser,Long>
{
    List<LibraryUser> findByEmail(String email);
}
