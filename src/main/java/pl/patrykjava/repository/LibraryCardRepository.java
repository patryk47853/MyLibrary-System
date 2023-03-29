package pl.patrykjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.patrykjava.entity.LibraryCard;


@Repository
public interface LibraryCardRepository extends JpaRepository<LibraryCard, Integer> {

    @Query("SELECT c from LibraryCard c where c.firstName = ?1")
    LibraryCard findByFirstName(String firstName);

}
