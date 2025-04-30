package com.ann.spending.category.service;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;


    public void initCategoriesForUser(User user){


        categoryRepository.save();
    }

}
