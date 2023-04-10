package pl.patrykjava.service;

import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private Books booksClient;

    public List<Volume> searchForBooks(String query) throws IOException {
        List volumes = null;
        String title = "intitle:" + query;
        String author = "inauthor:" + query;
        String publisher = "inpublisher:" + query;

        // Search for books by title, author, or publisher
        Volumes volumesResponse = booksClient.volumes().list(title + "+" + author + "+" + publisher)
                .execute();
        if (volumesResponse != null && volumesResponse.getItems() != null) {
            volumes = volumesResponse.getItems();
        }

        return volumes;
    }
}
