package pl.patrykjava;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Order(1)
    public void createUserTest() {

        User user = new User();
        user.setUsername("patryk47853");
        user.setPassword("patryk47853");
        user.setEmail("patryk47853@test.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now().plusHours(1)));

        User newUser = userRepository.save(user);

        User existingUser = entityManager.find(User.class, newUser.getId());


        Assertions.assertThat(existingUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @Order(2)
    public void findUserByEmailTest() {

        String email = "patryk47853@test.com";

        User user = userRepository.findByEmail(email);

        Assertions.assertThat(user).isNotNull();
    }

}
