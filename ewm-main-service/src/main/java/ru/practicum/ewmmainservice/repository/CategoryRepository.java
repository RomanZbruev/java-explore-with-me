package ru.practicum.ewmmainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.model.Category;

import java.util.Collection;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findCategoryById(Integer id);

    @Query("select c " +
            "from Category c " +
            "where c.id in ?1 " +
            "group by c.id " +
            "order by c.id desc")
    List<Category> findCategoriesByIds(Collection<Integer> id);
}
