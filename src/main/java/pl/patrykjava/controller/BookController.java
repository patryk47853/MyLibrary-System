package pl.patrykjava.controller;

import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import java.util.List;

import com.google.api.services.books.model.Volumes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping("/search")
    public List<Volume> searchBooks(@RequestParam String query) {
        try {
            Books books = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(), new com.google.api.client.json.jackson2.JacksonFactory(), null)
                    .setApplicationName("MyLibrarySearch")
                    .setGoogleClientRequestInitializer(new BooksRequestInitializer("GOOGLE.API.KEY"))
                    .build();

            Volumes volumes = books.volumes().list(query).execute();

            return volumes.getItems();
        } catch (Exception e) {
            System.err.println("Exception during API call: " + e);
            return null;
        }
    }
}
