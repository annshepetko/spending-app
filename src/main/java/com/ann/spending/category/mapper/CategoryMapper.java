package com.ann.spending.category.mapper;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.entity.Category;
import com.ann.spending.category.entity.UserCategory;
import com.ann.spending.category.entity.UserCategoryId;
import com.ann.spending.category.sequence.SequenceRepository;
import com.ann.spending.category.view.CategoryDTO;
import com.ann.spending.category.view.PatchCategoryRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CategoryMapper {


    private final SequenceRepository sequenceRepository;

    public CategoryMapper(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    public List<UserCategory> mapToUserCategoryList(List<CategoryDTO> incomingDtos, User user){
        return incomingDtos.stream()
                .map((dto) -> new UserCategory(new UserCategoryId(user.getId(), dto.id()), user, new Category(dto.id()), dto.index()))
                .toList();
    }

    public List<UserCategory> buildUserCategories(List<Category> categories, User user) {
        List<Long> indexes = sequenceRepository.getNextSequenceValues(categories.size());

        return IntStream.range(0, categories.size())
                .mapToObj(i -> {
                    UserCategory userCategory = new UserCategory();
                    userCategory.setUser(user);
                    userCategory.setCategory(categories.get(i));
                    userCategory.setIndex(indexes.get(i));
                    return userCategory;
                })
                .collect(Collectors.toList());
    }


    public UserCategory buldUserCategory(Category category, User user){
        Long index = sequenceRepository.getNextSequenceValue();

        UserCategory userCategory = new UserCategory();

        userCategory.setUser(user);
        userCategory.setCategory(category);
        userCategory.setIndex(index);

        return userCategory;
    }

}
