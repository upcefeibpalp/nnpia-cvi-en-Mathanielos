package cz.upce.fei.nnpiacv.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @OneToOne(mappedBy = "user")
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
