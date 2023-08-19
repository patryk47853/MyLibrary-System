package pl.patrykjava;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import pl.patrykjava.dto.LibraryCardDTO;
import pl.patrykjava.entity.LibraryCard;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.LibraryCardRepository;
import pl.patrykjava.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(value = false)
public class LibraryCardRepositoryTest {

    @Autowired
    private LibraryCardRepository libraryCardRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @SneakyThrows
    public void givenLibraryCardCreated_whenLibraryCardAssignedToUser_thenGetOk() {
        //given
        LibraryCard libraryCard = new LibraryCard(
                "Jan",
                "Kowalski",
                "+48505404303",
                "Wall Street",
                "01-341",
                "Warsaw",
                Timestamp.valueOf(LocalDateTime.now().plusHours(2L))
        );

        User user = new User(
                "jankow505",
                "testing@example.com",
                "example"
        );

        //when
        libraryCard.setUser(user);

        userRepository.save(user);
        libraryCardRepository.save(libraryCard);

        Thread.sleep(1000);

        User expectedUser = userRepository.findByUsername("jankow505");
        LibraryCard expectedLibraryCard = libraryCardRepository.findByPhoneNumber("+48505404303");

        //then
        Assertions.assertThat(expectedUser).isNotNull();
        Assertions.assertThat(expectedLibraryCard).isNotNull();
    }

    @Test
    @Order(2)
    @SneakyThrows
    public void givenPhoneNumber_whenLibraryCardFound_thenCheckFullLibraryCardDetails() {
        //given
        LibraryCard foundCard = libraryCardRepository.findByPhoneNumber("+48505404303");

        Thread.sleep(1000);

        //then
        Assertions.assertThat(foundCard).isNotNull();
        Assertions.assertThat(foundCard.getFirstName()).isEqualTo("Jan");
        Assertions.assertThat(foundCard.getLastName()).isEqualTo("Kowalski");
        Assertions.assertThat(foundCard.getPhoneNumber()).isEqualTo("+48505404303");
        Assertions.assertThat(foundCard.getAddress()).isEqualTo("Wall Street");
        Assertions.assertThat(foundCard.getCity()).isEqualTo("Warsaw");
        Assertions.assertThat(foundCard.getPostalCode()).isEqualTo("01-341");
        Assertions.assertThat(foundCard.getCreatedAt()).isNotNull();
    }

    @Test
    @Order(3)
    @SneakyThrows
    public void givenPhoneNumber_whenLibraryCardDeleted_thenGetOk() {
        //given
        LibraryCard libraryCard = libraryCardRepository.findByPhoneNumber("+48505404303");

        //when
        libraryCardRepository.delete(libraryCard);
        List<LibraryCard> allCards = libraryCardRepository.findAll();

        Thread.sleep(1000);

        //then
        Assertions.assertThat(allCards).doesNotContain(libraryCard);
    }

    @SneakyThrows
    @Test
    @Order(4)
    public void givenExistingUserWhoOwnsLibraryCard_whenLibraryCardIsUpdatedByHim_thenGetOk() {
        //given
        User existingUser = userRepository.findByUsername("jankow505");
        LibraryCard libraryCard = existingUser.getLibraryCard();

        LibraryCardDTO libraryCardDTO = new LibraryCardDTO(
                "Jan", "Kowalski", "+48505404303", "Sample Street 1/4", "41-412", "Cracow", existingUser.getCreatedAt()
        );

        //when
        libraryCard.setFirstName(libraryCardDTO.getFirstName());
        libraryCard.setLastName(libraryCardDTO.getLastName());
        libraryCard.setPhoneNumber(libraryCardDTO.getPhoneNumber());
        libraryCard.setAddress(libraryCardDTO.getAddress());
        libraryCard.setPostalCode(libraryCardDTO.getPostalCode());
        libraryCard.setCity(libraryCardDTO.getCity());

        libraryCardRepository.save(libraryCard);

        existingUser.setLibraryCard(libraryCard);
        userRepository.save(existingUser);
        Thread.sleep(1000);

        //then
        Assertions.assertThat(existingUser.getLibraryCard()).isNotNull();
        Assertions.assertThat(existingUser.getLibraryCard().getCity()).isEqualTo("Cracow");
    }

    @Test
    @Order(4)
    @SneakyThrows
    public void deleteUser_whenUserIsDeleted_thenLibraryCardShouldBeDeletedToo() {
        //given
        User testedUser = userRepository.findByUsername("jankow505");

        //when
        userRepository.delete(testedUser);

        Thread.sleep(1000);

        //then
        Assertions.assertThat(userRepository.findByUsername("jankow505")).isNull();
        Assertions.assertThat(libraryCardRepository.findByPhoneNumber("+48505404303")).isNull();
    }
}
