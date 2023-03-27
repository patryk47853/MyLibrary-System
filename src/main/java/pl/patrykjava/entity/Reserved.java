//package pl.patrykjava.entity;
//
//import java.sql.Date;
//import java.sql.Timestamp;
//
//import jakarta.persistence.*;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//
//@Getter
//@Setter
//@EqualsAndHashCode
//@ToString
//@NoArgsConstructor
//@Entity
//@Table(name="reserved")
//public class Reserved {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private int id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "books_id")
//    private Book book;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "users_id")
//    private User user;
//
//    @Column(name = "booked")
//    private Timestamp booked;
//
//    @Column(name = "due_to")
//    private Date dueTo;
//
//}
