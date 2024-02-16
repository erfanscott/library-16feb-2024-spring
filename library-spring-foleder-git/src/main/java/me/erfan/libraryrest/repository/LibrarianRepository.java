package me.erfan.libraryrest.repository;

import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Librarian;
import org.springframework.data.repository.CrudRepository;


public interface LibrarianRepository extends CrudRepository<Librarian,Long> {
}
