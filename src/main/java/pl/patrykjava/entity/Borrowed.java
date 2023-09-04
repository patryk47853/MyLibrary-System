//package pl.patrykjava.entity;
//
//import java.sql.Date;
//import java.sql.Timestamp;
//import java.util.List;
//
//import jakarta.persistence.*;
//
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//@Getter
//@Setter
//@EqualsAndHashCode
//@ToString
//@NoArgsConstructor
//@Entity
//@Table(name="borrowed")
//public class Borrowed {
//
//    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
//    @Column(name="id")
//    private int id;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<Borrowed> borrowedBooks;
//
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="users_id")
//    private User user;
//
//    @Column(name="dated")
//    private Timestamp dated;
//
//    @Column(name="due_date")
//    private Date dueDate;
//}