package pl.patrykjava;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import pl.patrykjava.entity.Role;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.RoleRepository;
import pl.patrykjava.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void createUserTest() {

        User user = new User();
        user.setUsername("patryk47853");
        user.setPassword("patryk47853");
        user.setEmail("patryk47853@test.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now().plusHours(1L)));

        User newUser = userRepository.save(user);

        User existingUser = entityManager.find(User.class, newUser.getId());


        Assertions.assertThat(existingUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void createUserWithRoleTest() {

        User user = new User();
        user.setUsername("patryk35874");
        user.setPassword("patryk35874");
        user.setEmail("patryk35874@test.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now().plusHours(1L)));

        Role roleUser = roleRepository.findByName("USER");
        user.addRole(roleUser);

        User theUser = userRepository.save(user);


        Assertions.assertThat(theUser.getRoles().size()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void findUserByEmailTest() {

        String email = "patryk47853@test.com";

        User user = userRepository.findByEmail(email);

        Assertions.assertThat(user.getUsername()).isEqualTo("patryk47853");
    }

    @Test
    @Order(4)
    public void findUserByUsernameTest() {

        String username = "patryk47853";

        User user = userRepository.findByUsername(username);

        Assertions.assertThat(user.getEmail()).isEqualTo("patryk47853@test.com");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteUserByEmailTest() {

        String email = "patryk47853@test.com";

        User user = userRepository.findByEmail(email);

        userRepository.delete(user);
    }

}
