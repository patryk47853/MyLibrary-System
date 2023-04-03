package pl.patrykjava;

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
@Rollback(value = false)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Order(1)
    public void createRolesTest() {
        Role user = new Role("USER");
        Role librarian = new Role("LIBRARIAN");
        Role admin = new Role("ADMIN");

        roleRepository.saveAll(List.of(user, librarian, admin));

        List<Role> listRoles = roleRepository.findAll();

        Assertions.assertThat(listRoles.size()).isEqualTo(3);
    }

    @Test
    @Order(2)
    public void deleteRolesTest() {
        List<Role> roles = roleRepository.findAll();

        for(Role role : roles) {
            roleRepository.delete(role);
        }

        Assertions.assertThat(roles.size()).isEqualTo(0);
    }

    @Test
    @Order(3)
    public void findRoleTest() {

        Role shouldBeUser = roleRepository.getReferenceById(1);

        Role isUser = roleRepository.findByName("USER");

        Assertions.assertThat(isUser.getName()).isEqualTo(shouldBeUser.getName());
    }
}
