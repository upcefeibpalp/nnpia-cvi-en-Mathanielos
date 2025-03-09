package cz.upce.fei.nnpiacv.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Email musí být platný")
    @NotNull(message = "Email je povinný")
    private String email;

    @Size(min = 6, message = "Heslo musí mít alespoň 6 znaků")
    @NotNull(message = "Heslo je povinné")
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference // Řídí serializaci směrem k `Profile`
    private Profile profile;

    @Version
    private Integer version;

    // Default constructor
    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
