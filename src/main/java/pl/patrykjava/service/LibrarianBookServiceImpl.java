package pl.patrykjava.service;

import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.patrykjava.entity.Book;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibrarianBookServiceImpl implements LibrarianBookService {

    @Value("${google.books.api.key}")
    private String apiKey;

    @Override
    public List<Book> searchBooks(String query, Long startIndex) {
        try {
            Books books = new Books.Builder(
                    new com.google.api.client.http.javanet.NetHttpTransport(),
                    new com.google.api.client.json.jackson2.JacksonFactory(),
                    null)
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
                if (volume.getVolumeInfo().getAuthors() != null) {
                    book.setAuthors(volume.getVolumeInfo().getAuthors());
                }
                book.setPublishedDate(volume.getVolumeInfo().getPublishedDate());
                Integer pageCount = volume.getVolumeInfo().getPageCount();
                String pageCountValue = pageCount != null && pageCount != 0 ? pageCount.toString() : "no data";
                book.setPageCount(pageCountValue);
                book.setCategories(volume.getVolumeInfo().getCategories());

                myBooks.add(book);
            }

            return myBooks;
        } catch (Exception e) {
            System.err.println("Exception during API call: " + e);
            return null;
        }
    }
}
