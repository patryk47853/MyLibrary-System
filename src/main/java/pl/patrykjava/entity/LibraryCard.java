package pl.patrykjava.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "library_card")
public class LibraryCard {


    public LibraryCard(String firstName, String lastName, String phoneNumber, String address, String postalCode, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="phone_number")
    private String phoneNumber;

//    @Column(name="birthday")
//    private Date birthday;

    @Column(name="address")
    private String address;

    @Column(name="postal")
    private String postalCode;

    @Column(name="city")
    private String city;

    @Column(name = "created_at")
    private Timestamp createdAt;

//    @OneToMany(mappedBy="user",
//            fetch = FetchType.LAZY,
//            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    private Set<Reserved> reservedBooks;
}
