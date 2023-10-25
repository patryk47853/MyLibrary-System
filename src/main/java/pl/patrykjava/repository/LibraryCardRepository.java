package pl.patrykjava.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.patrykjava.entity.LibraryCard;

@Repository
public interface LibraryCardRepository extends JpaRepository<LibraryCard, Integer> {

    @Query("SELECT c from LibraryCard c where c.phoneNumber = ?1")
    LibraryCard findByPhoneNumber(String phoneNumber);
    Page<LibraryCard> findByPhoneNumber(String phoneNumber, Pageable pageable);

}
