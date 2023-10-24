package pl.patrykjava.service;

import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.patrykjava.dto.BookDTO;
import pl.patrykjava.entity.Book;
import pl.patrykjava.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibrarianServiceImpl implements LibrarianService {

    @Value("${google.books.api.key}")
    private String apiKey;

    private final BookService bookService;
    private final BookRepository bookRepository;

    public LibrarianServiceImpl(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDTO> searchBooks(String query, Long startIndex) {
        Books books = buildBooksClient();
        if (books == null) return null;

        try {
            Volumes volumes = books.volumes().list(query)
                    .setStartIndex(startIndex)
                    .setMaxResults(3L)
                    .execute();

            List<BookDTO> myBooks = new ArrayList<>();

            for (Volume volume : volumes.getItems()) {
                BookDTO bookDTO = bookService.displayBookInfoFromVolume(volume);
                myBooks.add(bookDTO);
            }

            return myBooks;
        } catch (Exception e) {
            System.err.println("Exception during API call: " + e);
            return null;
        }
    }

    @Override
    public BookDTO searchBookByGoogleBooksId(String googleBooksId) {
        Books books = buildBooksClient();
        if (books == null) return null;

        try {
            Volume volume = books.volumes().get(googleBooksId).execute();

            if (volume != null) {
                return bookService.displayBookInfoFromVolume(volume);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("Exception during API call: " + e);
            return null;
        }
    }


    private Books buildBooksClient() {
        try {
            return new Books.Builder(
                    new com.google.api.client.http.javanet.NetHttpTransport(),
                    new com.google.api.client.json.jackson2.JacksonFactory(),
                    null)
                    .setApplicationName("MyLibrary")
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer(apiKey))
                    .build();
        } catch (Exception e) {
            System.err.println("Exception while building Books client: " + e);
            return null;
        }
    }

    @Override
    public void addBookToLibrary(BookDTO bookDTO) {
        Book book = bookService.convertBookDtoToBookEntity(bookDTO);
        bookRepository.save(book);
    }
}