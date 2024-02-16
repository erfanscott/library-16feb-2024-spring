package me.erfan.libraryrest.entity.libraryUser;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "role_and_authority")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Authority(String name, LibraryUser libraryUser) {
        this.name = name;
        this.libraryUser = libraryUser;
    }

    @ManyToOne
    @JoinColumn(name = "library_user_id")
    private LibraryUser libraryUser;

}
