package pl.patrykjava.service;

import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pl.patrykjava.entity.Book;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibrarianBookService {

    @Value("${google.books.api.key}")
    private String apiKey;
    public List<Book> searchBooks(@RequestParam String query, Long startIndex) {
        try {
            Books books = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(), new com.google.api.client.json.jackson2.JacksonFactory(), null)
                    .setApplicationName("MyLibrarySearch")
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer(apiKey))
                    .build();

            Volumes volumes = books.volumes().list(query)
                    .setStartIndex(startIndex)
                    .setMaxResults(6L)
                    .execute();

            List<Book> myBooks = new ArrayList<>();

            for (Volume volume : volumes.getItems()) {
                Book book = new Book();
                book.setTitle(volume.getVolumeInfo().getTitle());
                book.setAuthors(volume.getVolumeInfo().getAuthors());

                myBooks.add(book);
            }

            return myBooks;
        } catch (Exception e) {
            System.err.println("Exception during API call: " + e);
            return null;
        }
    }
}