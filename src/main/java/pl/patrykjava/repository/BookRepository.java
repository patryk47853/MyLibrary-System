package pl.patrykjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patrykjava.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
