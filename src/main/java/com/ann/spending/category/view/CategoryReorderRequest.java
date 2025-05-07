package com.ann.spending.category.view;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public class CategoryReorderRequest {

    @NotEmpty(message = "list is empty")
    @NotNull(message = "list is null")
    private List<CategoryDTO> categories;

    public CategoryReorderRequest(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }
}