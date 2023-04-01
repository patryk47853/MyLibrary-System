package pl.patrykjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.patrykjava.entity.LibraryCard;
import pl.patrykjava.entity.UsersRoles;


@Repository
public interface UsersRolesRepository extends JpaRepository<UsersRoles, Integer> {

}
