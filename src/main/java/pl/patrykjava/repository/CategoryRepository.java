package pl.patrykjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patrykjava.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
