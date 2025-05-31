package com.ann.spending.data;

import com.ann.spending.category.entity.Category;
import com.ann.spending.category.view.AddCategoryRequest;

public class CategoryServiceDataFactory {

    public static Category createCategory() {
        return new Category("tech", "icon");
    }

    public static AddCategoryRequest createCategoryRequest() {
        return new AddCategoryRequest("tech", "icon");
    }
}
