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
import pl.patrykjava.entity.Role;
import pl.patrykjava.repository.RoleRepository;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(value = false)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @SneakyThrows
    @Test
    @Order(1)
    public void createNewRoles_whenSaved_thenGetOk() {
        //given
        Role testRole = new Role("TEST");
        Role newRole = new Role("NEW");

        //when
        roleRepository.saveAll(List.of(testRole, newRole));

        //to ensure that the roles are saved and available for deletion in the subsequent test method
        Thread.sleep(1000);
        List<Role> listRoles = roleRepository.findAll();

        //then (there are [4] main roles: USER, READER, LIBRARIAN, ADMIN, so 4 + 2 = 6)
        Assertions.assertThat(listRoles.size()).isEqualTo(6);
    }

    @Test
    @Order(2)
    public void givenRolesNames_whenDeleted_thenGetOk() {
        //given
        Role testRole = roleRepository.findByName("TEST");
        Role newRole = roleRepository.findByName("NEW");

        //assert that the roles are found
        Assertions.assertThat(testRole).isNotNull();
        Assertions.assertThat(newRole).isNotNull();

        //when
        roleRepository.delete(testRole);
        roleRepository.delete(newRole);

        //then
        List<Role> existingRoles = roleRepository.findAll();
        Assertions.assertThat(existingRoles.size()).isEqualTo(4);
    }

    @Test
    @Order(3)
    public void givenRoleIdOne_whenIsEqualToUser_thenGetOk() {
        //given
        Role shouldBeUser = roleRepository.getReferenceById(1);
        Role isUser = roleRepository.findByName("USER");

        //then
        Assertions.assertThat(isUser.getName()).isEqualTo(shouldBeUser.getName());
    }
}
