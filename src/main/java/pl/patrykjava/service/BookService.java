package pl.patrykjava.service;

import com.google.api.services.books.model.Volume;
import pl.patrykjava.dto.BookDTO;
import pl.patrykjava.entity.Book;

import java.util.List;

public interface BookService {

    Book convertBookDtoToBookEntity(BookDTO bookDTO);

    BookDTO convertBookEntityToBookDto(Book book);

    BookDTO displayBookInfoFromVolume(Volume volume);

    List<Book> searchBooks(String query);

    List<Book> getPaginatedBooks(String query, int page, int booksPerPage);

    int getTotalRecords(String query, int booksPerPage);

}