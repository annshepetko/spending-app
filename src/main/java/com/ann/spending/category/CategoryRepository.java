package com.ann.spending.category;

import com.ann.spending.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.name IN :names")
    List<Category> findCategoriesByNameIn(@Param("names") List<String> names);
}
