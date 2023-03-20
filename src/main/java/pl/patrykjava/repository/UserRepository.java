package pl.patrykjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.patrykjava.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u from User u where u.email = ?1")
    User findByEmail(String email);

    @Query("SELECT u from User u where u.username = ?1")
    User findByUsername(String username);

}
