package pl.patrykjava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @Column(name = "self_link")
    private String selfLink;

    @Column(name = "description")
    private String description;
}
