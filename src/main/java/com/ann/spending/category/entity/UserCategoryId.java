package com.ann.spending.category.entity;

import jakarta.persistence.Embeddable;
import org.springframework.boot.SpringApplication;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserCategoryId implements Serializable {

    private Long userId;
    private Long categoryId;

    public UserCategoryId() {
    }

    public UserCategoryId(Long userId, Long categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCategoryId that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, categoryId);
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
