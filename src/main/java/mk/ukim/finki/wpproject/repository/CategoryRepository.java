package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Category findByName(String name);

}
