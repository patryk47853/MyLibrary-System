package pl.patrykjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.patrykjava.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository <Author, Integer> {

    @Query("SELECT a from Author a where a.name = ?1")
    Author findByName(String name);

}
