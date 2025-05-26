package com.ann.spending.category;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.entity.Category;
import com.ann.spending.category.entity.UserCategory;
import com.ann.spending.category.entity.UserCategoryId;
import com.ann.spending.category.view.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory, UserCategoryId> {


    @Query("SELECT new com.ann.spending.category.view.CategoryDTO(uc.category.id, uc.category.name, uc.index) from UserCategory uc WHERE uc.user.id = :userId ORDER BY uc.index ASC")
    List<CategoryDTO> findCategoryViewsByUserId(Long userId);

    @Query("select uc from UserCategory uc where uc.user = :user")
    List<UserCategory> findCategoriesByUser(User user);


}
