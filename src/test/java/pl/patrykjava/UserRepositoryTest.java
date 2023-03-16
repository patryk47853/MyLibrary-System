package pl.patrykjava;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.UserRepository;

@DataJpaTest
@Rollback(value = false)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void createUserTest() {
        User user = User.builder()
                .username("patryk47853")
                .password("patryk47853")
                .email("patryk47853@gmail.com")
                .build();

        User newUser = userRepository.save(user);

        User existingUser = entityManager.find(User.class, newUser.getId());

        Assertions.assertThat(existingUser.getEmail()).isEqualTo(user.getEmail());
    }

}
