package pl.patrykjava.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibraryCardDTO {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private String postalCode;

    private String city;

    private Timestamp createdAt;
}
