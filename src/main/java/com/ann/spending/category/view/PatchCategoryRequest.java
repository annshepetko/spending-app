package com.ann.spending.category.view;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PatchCategoryRequest(

        @Positive
        Long id,

        @NotEmpty(message = "Name is empty")
        @NotNull
        @Size(min = 1, max = 64, message = "Category name violation")
        String name) {
}
