package pl.patrykjava.dto;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private int id;
    private String title;
    private List<String> authors;
    private String coverImageUrl;
    private String publisher;
    private String publishedDate;
    private String pageCount;
    private String googleBooksId;
    private Double averageRating;
    private List<String> categories;
    private String selfLink;
    private String description;

}