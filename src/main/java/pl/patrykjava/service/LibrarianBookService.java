package pl.patrykjava.service;

import org.springframework.web.bind.annotation.RequestParam;
import pl.patrykjava.entity.Book;

import java.util.List;

public interface LibrarianBookService {

    List<Book> searchBooks(@RequestParam String query, Long startIndex);
}
