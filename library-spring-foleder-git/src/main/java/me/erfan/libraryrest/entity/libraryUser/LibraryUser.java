package me.erfan.libraryrest.entity.libraryUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import me.erfan.libraryrest.entity.enums.Gender;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "person_derived_objects_common_fields")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@SuperBuilder
@ToString(exclude = "roles")
public class LibraryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email",unique = true)
    //@NotEmpty(message = "email field is required")
    //@Email(message = "invalid e-mail address")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "pwd")
    private String pwd;

    @Column(name = "first_name")
    //@NotEmpty(message = "first name is required")
    private String firstName;

    @Column(name = "last_name")
    //@NotEmpty(message = "first name is required")
    private String lastName;

    @Column(name = "gender")
    //@NotNull(message = "gender must be specified")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonIgnoreProperties("libraryUser")
    @OneToMany(mappedBy = "libraryUser",
            cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Set<Authority> authorities;

        public String[] authoritiesStringArray() {
            List<String> authoritiesList = new ArrayList<>();


            for (Authority a :
                    this.authorities) {
                authoritiesList.add(a.getName()
                );
            }
            String[] authoritiesStringArray = new String[authoritiesList.size()];
            return authoritiesList.toArray(authoritiesStringArray);
        }



//    @EqualsAndHashCode.Exclude
//    @JsonIgnoreProperties("libraryUser")
//    @ManyToMany(mappedBy = "libraryUser",
//            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
//            fetch = FetchType.EAGER
//    )
//    protected Set<Role> roles;
//
//

//       String[] rolesListArr = new String[rolesList.size()];
//        rolesListArr =  rolesList.toArray(rolesListArr);
//        return rolesListArr;
//    }
}
