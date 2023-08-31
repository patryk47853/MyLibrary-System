package pl.patrykjava.dto;

import java.util.List;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private String title;
    private List<String> authors;
    private String publishedDate;

    private String pageCount;
    private List<String> categories;
}