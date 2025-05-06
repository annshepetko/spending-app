package com.ann.spending.category.entity;

import com.ann.spending.authorization.entity.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class UserCategory {

    @EmbeddedId
    private UserCategoryId userCategoryId = new UserCategoryId();

    public UserCategoryId getUserCategoryId() {
        return userCategoryId;
    }

    @MapsId
    @ManyToOne
    private User user;

    @MapsId
    @ManyToOne
    private Category category;

    private Integer index;


    public void setUserCategoryId(UserCategoryId userCategoryId) {
        this.userCategoryId = userCategoryId;
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

}
