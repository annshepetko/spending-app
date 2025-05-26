package com.ann.spending.data;

import com.ann.spending.category.entity.Category;

public class CategoryServiceDataFactory {

    public static Category createCategory() {
        return new Category("tech");
    }

}
