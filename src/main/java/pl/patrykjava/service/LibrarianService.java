package pl.patrykjava.service;

import org.springframework.web.bind.annotation.RequestParam;
import pl.patrykjava.dto.BookDTO;

import java.util.List;

public interface LibrarianService {

    List<BookDTO> searchBooks(@RequestParam String query, Long startIndex);

    BookDTO searchBookByGoogleBooksId(String googleBooksId);

    void addBookToLibrary(BookDTO bookDTO);
}
