package pl.patrykjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.patrykjava.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r from Role r where r.name = ?1")
    Role findByName(String name);

}
