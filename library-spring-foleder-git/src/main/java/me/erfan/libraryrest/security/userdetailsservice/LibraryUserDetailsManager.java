package me.erfan.libraryrest.security.userdetailsservice;

import me.erfan.libraryrest.entity.libraryUser.LibraryUser;

import me.erfan.libraryrest.repository.LibraryUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryUserDetailsManager implements UserDetailsService {

  private LibraryUserRepository libraryUserRepository;

    public LibraryUserDetailsManager(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<LibraryUser> libraryUsers = libraryUserRepository.findByEmail(username);
        System.out.println(libraryUsers.size());
        if (libraryUsers.size() < 1) {
            throw new UsernameNotFoundException(username);
        }
        LibraryUser user = libraryUsers.get(0);
        return User.builder()
                .username(user.getEmail())
                .password(user.getPwd())
                .authorities(user.authoritiesStringArray())
                .build();
    }
}
