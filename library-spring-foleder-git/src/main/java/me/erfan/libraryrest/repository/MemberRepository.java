package me.erfan.libraryrest.repository;

import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Member;
import org.springframework.data.repository.CrudRepository;


public interface MemberRepository extends CrudRepository<Member,Long> {
}
