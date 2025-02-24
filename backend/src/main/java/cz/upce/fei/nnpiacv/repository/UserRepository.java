package cz.upce.fei.nnpiacv.repository;

import cz.upce.fei.nnpiacv.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

