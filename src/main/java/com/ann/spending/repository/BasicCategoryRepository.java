package com.ann.spending.repository;

import com.ann.spending.category.entity.BasicCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicCategoryRepository extends JpaRepository<BasicCategory, Long> {
}