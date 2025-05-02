package com.ann.spending.category;

import com.ann.spending.category.entity.BasicCategory;
import org.springframework.cache.interceptor.BasicOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<BasicCategory, Long> {
}
