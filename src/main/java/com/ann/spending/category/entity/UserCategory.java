package com.ann.spending.category.entity;

import com.ann.spending.authorization.entity.User;
import jakarta.persistence.*;

@Entity
public class UserCategory {


    @EmbeddedId
    private UserCategoryId userCategoryId = new UserCategoryId();

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("categoryId")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private Long index;

    public UserCategory() {
    }

    public UserCategory(UserCategoryId userCategoryId,User user, Category category, Long index) {
        this.userCategoryId = userCategoryId;
        this.user = user;
        this.category = category;
        this.index = index;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

}
