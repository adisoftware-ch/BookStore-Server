package ch.adisoftware.bookstore.persistence.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import ch.adisoftware.bookstore.persistence.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
