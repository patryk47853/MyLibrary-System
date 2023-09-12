package pl.patrykjava.service;

import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.patrykjava.entity.Book;

import java.util.ArrayList;
import java.util.Collections;
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
                    .setApplicationName("MyLibrary")
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer(apiKey))
                    .build();

            Volumes volumes = books.volumes().list(query)
                    .setStartIndex(startIndex)
                    .setMaxResults(3L)
                    .execute();

            List<Book> myBooks = new ArrayList<>();

            for (Volume volume : volumes.getItems()) {
                Book book = createBookFromVolume(volume);
                myBooks.add(book);
            }

            return myBooks;
        } catch (Exception e) {
            System.err.println("Exception during API call: " + e);
            return null;
        }
    }

    @Override
    public Book createBookFromVolume(Volume volume) {
        Book book = new Book();

        String title = volume.getVolumeInfo().getTitle();
        book.setTitle(title != null ? title : "no data");

        List<String> authors = volume.getVolumeInfo().getAuthors();
        book.setAuthors(authors != null ? authors : Collections.singletonList("no data"));

        String publishedDate = volume.getVolumeInfo().getPublishedDate();
        book.setPublishedDate(publishedDate != null ? publishedDate : "no data");

        Integer pageCount = volume.getVolumeInfo().getPageCount();
        book.setPageCount(pageCount != null && pageCount != 0 ? pageCount.toString() : "no data");

        List<String> categories = volume.getVolumeInfo().getCategories();
        book.setCategories(categories != null ? categories : Collections.singletonList("no data"));

        String selfLink = volume.getSelfLink();
        book.setSelfLink(selfLink != null ? selfLink : "http://localhost:8080/home");

        String description = volume.getVolumeInfo().getDescription();
        book.setDescription(description != null ? description : "No description available.");

        return book;
    }
}