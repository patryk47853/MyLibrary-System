package pl.patrykjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.patrykjava.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b from Book b where b.googleBooksId = ?1")
    Book findBookByGoogleBooksId(String googleBooksId);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1%")
    Book findBookByTitleContaining(String title);

    @Query("SELECT DISTINCT b FROM Book b JOIN FETCH b.authors WHERE b.title LIKE %?1%")
    List<Book> findBooksByTitleContaining(String title);

}
