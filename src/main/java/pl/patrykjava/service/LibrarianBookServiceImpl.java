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
    public Book searchBookByGoogleBooksId(String googleBooksId) {
        try {
            Books books = new Books.Builder(
                    new com.google.api.client.http.javanet.NetHttpTransport(),
                    new com.google.api.client.json.jackson2.JacksonFactory(),
                    null)
                    .setApplicationName("MyLibrary")
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer(apiKey))
                    .build();

            // Use the Google Books ID to retrieve the book
            Volume volume = books.volumes().get(googleBooksId).execute();

            if (volume != null) {
                // Create a Book object from the Volume
                Book book = createBookFromVolume(volume);
                return book;
            } else {
                // No book found with the provided Google Books ID
                return null;
            }
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

        String coverImageUrl = volume.getVolumeInfo().getImageLinks().getMedium();
        book.setCoverImageUrl(coverImageUrl != null ? coverImageUrl : "https://example.com/default-cover-image.jpg");

        List<String> authors = volume.getVolumeInfo().getAuthors();
        book.setAuthors(authors != null ? authors : Collections.singletonList("no data"));

        String publisher = volume.getVolumeInfo().getPublisher();
        book.setPublisher(publisher != null ? publisher : "no data");

        String publishedDate = volume.getVolumeInfo().getPublishedDate();
        book.setPublishedDate(publishedDate != null ? publishedDate : "no data");

        Integer pageCount = volume.getVolumeInfo().getPageCount();
        book.setPageCount(pageCount != null && pageCount != 0 ? pageCount.toString() : "no data");

        String googleBooksId = volume.getId();
        book.setGoogleBooksId(googleBooksId != null ? googleBooksId : "");

        Double averageRating = volume.getVolumeInfo().getAverageRating();
        book.setAverageRating(averageRating != null ? averageRating : 5.0);

        List<String> categories = volume.getVolumeInfo().getCategories();
        book.setCategories(categories != null ? categories : Collections.singletonList("no data"));

        String description = volume.getVolumeInfo().getDescription();
        book.setDescription(description != null ? description : "No description available.");

        String selfLink = volume.getSelfLink();
        book.setSelfLink(selfLink != null ? selfLink : "http://localhost:8080/home");


        return book;
    }
}