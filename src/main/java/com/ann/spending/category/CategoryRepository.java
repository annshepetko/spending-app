package com.ann.spending.category;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.entity.Category;
import com.ann.spending.category.view.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.LongStream;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Query("SELECT new com.ann.spending.category.view.CategoryDTO(c.id, c.name, c.index) from Category c JOIN c.users us WHERE us.id = :userId ORDER BY c.index ASC")
    List<CategoryDTO> findCategoryViewsByUserId(Long userId);

    @Query("select c from Category c join c.users cu where cu.id = :user.id")
    List<Category> findCategoriesByUser(User user);

    @Query("SELECT c FROM Category c WHERE c.name IN :names")
    List<Category> findCategoriesByNameIn(@Param("names") List<String> names);
}
