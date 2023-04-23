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

    private String title;
    private List<String> authors;
}
