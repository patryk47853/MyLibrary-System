package pl.patrykjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.patrykjava.entity.UserRole;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    @Query("SELECT ur from UserRole ur where ur.userId = ?1 AND ur.roleId = ?1")
    UserRole findBy(int userId, int roleId);

}
