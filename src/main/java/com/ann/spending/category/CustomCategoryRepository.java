package com.ann.spending.category;

import com.ann.spending.category.entity.CustomCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomCategoryRepository extends JpaRepository<CustomCategory, Long> {
}
