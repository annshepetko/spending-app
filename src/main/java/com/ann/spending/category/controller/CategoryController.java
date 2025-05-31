package com.ann.spending.category.controller;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.service.CategoryCrudService;
import com.ann.spending.category.view.AddCategoryRequest;
import com.ann.spending.category.view.CategoryReorderRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryCrudService categoryCrudService;

    public CategoryController(CategoryCrudService categoryCrudService) {
        this.categoryCrudService = categoryCrudService;

    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") Long id, @RequestAttribute("user") User user){
        categoryCrudService.deleteCategory(id, user.getId());
    }

    @PostMapping
    public void addCategory(

            @Valid
            @RequestBody AddCategoryRequest addCategoryRequest,
            @RequestAttribute("user") User user
    ){

        categoryCrudService.createCategory(addCategoryRequest, user);
    }

    @PatchMapping("/reorder")
    public void reorderCategories(
            @RequestAttribute("user") User user,
            @Valid @RequestBody CategoryReorderRequest categoryDTOS
    ){

        categoryCrudService.reorderCategories(user, categoryDTOS.getCategories());
    }

}
