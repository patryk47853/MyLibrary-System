package pl.patrykjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patrykjava.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository <Author, Integer> {

}
