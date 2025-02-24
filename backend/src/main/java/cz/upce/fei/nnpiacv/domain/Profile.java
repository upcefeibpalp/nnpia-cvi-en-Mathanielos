package cz.upce.fei.nnpiacv.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String bio;

    @OneToOne
    @JoinColumn(name = "user_id") // This column will link Profile to User
    private User user;

    public Profile() {
    }

    public Profile(String fullName, String bio, User user) {
        this.fullName = fullName;
        this.bio = bio;
        this.user = user;
    }
}
