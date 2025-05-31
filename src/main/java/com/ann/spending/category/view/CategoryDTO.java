package com.ann.spending.category.view;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CategoryDTO(

        @NotNull
        @Positive
        Long id,
        @Size(min = 2, max = 64, message = "Invalid name length")
        String name,
        Long index,
        String iconName

) {
}
