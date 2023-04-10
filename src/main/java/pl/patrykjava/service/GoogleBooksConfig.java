package pl.patrykjava.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleBooksConfig {

    @Bean
    public Books booksClient() throws GeneralSecurityException, IOException {
        return new Books.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                null)
                .setApplicationName("MyApplication")
                .setGoogleClientRequestInitializer(
                        new BooksRequestInitializer("{GOOGLE_BOOKS_API_KEY}"))
                .build();
    }
}
