package pl.patrykjava.dto;

import java.util.List;
import java.util.Set;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private int id;
    private String title;
    private Set<String> authors;
    private String coverImageUrl;
    private String publisher;
    private String publishedDate;
    private String pageCount;
    private String googleBooksId;
    private Double averageRating;
    private Set<String> categories;
    private String selfLink;
    private String description;

}