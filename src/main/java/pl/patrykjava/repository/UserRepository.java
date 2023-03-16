package pl.patrykjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.patrykjava.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {


}
