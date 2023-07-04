package pl.patrykjava;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import pl.patrykjava.entity.Role;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.RoleRepository;
import pl.patrykjava.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void createUser_whenSaved_thenGetOk() {
        //given
        User user = new User(
                "patryk47853",
                "patryk47853@test.com",
                "patryk47853",
                Timestamp.valueOf(LocalDateTime.now().plusHours(2L))
        );

        //when
        userRepository.save(user);
        User existingUser = userRepository.findUserById(user.getId());

        //then
        Assertions.assertThat(existingUser.getId()).isEqualTo(user.getId());
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void createUserAndAssignRole_whenGranted_thenGetOk() {
        //given
        User user = new User("patryk35874",
                "patryk35874@test.com",
                "patryk35874",
                Timestamp.valueOf(LocalDateTime.now().plusHours(2L)));

        //when
        Role userRole = roleRepository.findByName("USER");
        user.addRole(userRole);
        userRepository.save(user);

        User newUser = userRepository.findByUsername("patryk35874");

        //then (newly created User should have only one role)
        Assertions.assertThat(newUser.getRoles().size()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void assignRoleToUser_whenAssigned_thenGetOk() {
       //given
       String username = "patryk2136";
       Role readerRole = roleRepository.findByName("READER");

       //when
       User user = userRepository.findByUsername(username);

       user.addRole(readerRole);
       userRepository.save(user);

       //then
       Assertions.assertThat(user.getRoles()).contains(readerRole);
    }

    @Test
    @Order(4)
    public void givenEmail_whenFound_thenGetOk() {
        //given
        String email = "patryk47853@test.com";

        //when
        User user = userRepository.findByEmail(email);

        //then
        Assertions.assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    @Order(5)
    public void givenUsername_whenFound_thenGetOk() {
        //given
        String username = "patryk47853";

        //when
        User user = userRepository.findByUsername(username);

        //then
        Assertions.assertThat(user.getUsername()).isEqualTo(username);
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void givenEmail_whenUserDeleted_thenGetOk() {
        //given
        String firstEmail = "patryk47853@test.com";
        String secondEmail = "patryk35874@test.com";

        //when
        User firstUser = userRepository.findByEmail(firstEmail);
        userRepository.delete(firstUser);

        User secondUser = userRepository.findByEmail(secondEmail);
        userRepository.delete(secondUser);

        //then
        Assertions.assertThat(userRepository.findByEmail(firstEmail)).isNull();
        Assertions.assertThat(userRepository.findByEmail(secondEmail)).isNull();
    }
}
