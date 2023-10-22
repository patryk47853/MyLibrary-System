package pl.patrykjava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @ElementCollection
    @CollectionTable(name = "authors", joinColumns = @JoinColumn(name = "book_id"))
    private List<String> authors;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "published_date")
    private String publishedDate;

    @Column(name = "page_count")
    private String pageCount;

    @Column(name = "google_books_id")
    private String googleBooksId;

    @Column(name = "average_rating")
    private Double averageRating;

    @ElementCollection
    @CollectionTable(name = "categories", joinColumns = @JoinColumn(name = "book_id"))
    private List<String> categories;

    @Column(name = "self_link")
    private String selfLink;

    @Column(name = "description")
    private String description;
}
