import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.patrykjava.entity.Book;
import pl.patrykjava.entity.User;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name="borrowed")
public class Borrowed {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="books_id")
    private Book book;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="users_id")
    private User user;

    @Column(name="dated")
    private Timestamp dated;

    @Column(name="due_date")
    private Date dueDate;
}