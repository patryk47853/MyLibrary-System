package pl.patrykjava.service;

import com.google.api.services.books.model.Volume;
import pl.patrykjava.dto.BookDTO;
import pl.patrykjava.entity.Book;

public interface BookService {

    Book convertBookDtoToBookEntity(BookDTO bookDTO);

    BookDTO convertBookEntityToBookDto(Book book);

    BookDTO displayBookInfoFromVolume(Volume volume);

}