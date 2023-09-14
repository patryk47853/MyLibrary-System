package pl.patrykjava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "books")
public class Book {

    @Column(name = "title")
    private String title;

    @ElementCollection
    @CollectionTable(name = "authors", joinColumns = @JoinColumn(name = "book_id"))
    private List<String> authors;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publishedDate")
    private String publishedDate;

    @Column(name = "pageCount")
    private String pageCount;

    @Column(name = "google_books_id")
    private String googleBooksId;

    @Column(name = "average_rating")
    private Double averageRating;

    @ElementCollection
    @CollectionTable(name = "categories", joinColumns = @JoinColumn(name = "book_id"))
    private List<String> categories;

    @Column(name = "selfLink")
    private String selfLink;

    @Column(name = "description")
    private String description;
}
