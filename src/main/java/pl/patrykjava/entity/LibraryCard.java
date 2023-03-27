//package pl.patrykjava.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.Date;
//import java.util.Set;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//@Table(name = "library_cards")
//public class LibraryCard {
//
//    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
//    @Column(name="id")
//    private int id;
//
//    @Column(name="first_name")
//    private String firstName;
//
//    @Column(name="last_name")
//    private String lastName;
//
//    @Column(name="phone_number")
//    private String phoneNumber;
//
//    @Column(name="birthday")
//    private Date birthday;
//
//    @Column(name="address")
//    private String address;
//
//    @Column(name="postal")
//    private String postalCode;
//
//    @Column(name="city")
//    private String city;
//
//    @OneToMany(mappedBy="user",
//            fetch = FetchType.LAZY,
//            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    private Set<Reserved> reservedBooks;
//
//}
