package me.erfan.libraryrest.repository;

import me.erfan.libraryrest.entity.libraryUser.libraryusertypes.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;


public interface MemberRepository extends JpaRepository<Member,Long>, JpaSpecificationExecutor<Member> {
}
