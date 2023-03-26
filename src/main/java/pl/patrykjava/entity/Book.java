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
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "google_id")
    private String googleId;

    @Column(name="title")
    private String title;

    @Column(name="page_count")
    private int pageCount;

    @Column(name="description")
    private String description;

    private Set<BookCategory> categories;

    @Column(name="rating")
    private double rating;

    private Set<Author> authors;

    @Column(name="publisher")
    private String publisher;

    @Column(name="published_at")
    private String publishedAt;

    private Isbn isbn;

    @Column(name="image_link")
    private String imageLink;
}
