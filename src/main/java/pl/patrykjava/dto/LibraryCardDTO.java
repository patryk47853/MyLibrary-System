package pl.patrykjava.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

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
