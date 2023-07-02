package pl.patrykjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.patrykjava.entity.UserToRoleMapping;


@Repository
public interface UserToRoleMappingRepository extends JpaRepository<UserToRoleMapping, Integer> {

    @Query("SELECT ur from UserToRoleMapping ur where ur.userId = ?1 AND ur.roleId = ?1")
    UserToRoleMapping findBy(int userId, int roleId);

}
