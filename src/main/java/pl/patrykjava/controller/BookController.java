package pl.patrykjava.controller;

import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;

import java.util.ArrayList;
import java.util.List;

import com.google.api.services.books.model.Volumes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.patrykjava.entity.Book;

@Controller
public class BookController {

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

    @GetMapping("/books")
    public String showSearchResults(@RequestParam("query") String query, @RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Long startIndex = page * 6L;
        List<Book> books = searchBooks(query, startIndex);
        int totalRecords = 60; // assuming there are 60 books for the given query

        model.addAttribute("books", books);
        model.addAttribute("query", query);
        model.addAttribute("totalPages", (int) Math.ceil(totalRecords / 6.0));
        model.addAttribute("currentPage", page);

        return "books";
    }

}
