package pl.patrykjava;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import pl.patrykjava.entity.LibraryCard;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.LibraryCardRepository;

import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class LibraryCardRepositoryTest {

        @Autowired
        private LibraryCardRepository libraryCardRepository;

        @Test
        @Order(1)
        public void testCreateLibraryCard() {
            LibraryCard libraryCard = new LibraryCard();
            libraryCard.setFirstName("Patryk");
            libraryCard.setLastName("Testowy");
            libraryCard.setCity("Warsaw");
            libraryCard.setPostalCode("01-341");

            libraryCardRepository.save(libraryCard);

            List<LibraryCard> myCards = libraryCardRepository.findAll();


            Assertions.assertThat(myCards.size()).isEqualTo(1);
        }

//        @Test
//        @Order(2)
//        public void testDeleteLibraryCard() {
//            List<Role> roles = roleRepository.findAll();
//
//            for(Role role : roles) {
//                roleRepository.delete(role);
//            }
//
//            Assertions.assertThat(roles.size()).isEqualTo(0);
//        }
//    }

}
