package com.ann.spending.spending.mapper;

import com.ann.spending.category.entity.Category;
import com.ann.spending.spending.dto.SpendingDTO;
import com.ann.spending.spending.entity.Spending;
import com.ann.spending.spending.dto.CreateSpendingBody;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SpendingMapper {

    public Spending mapToSpending(CreateSpendingBody createSpendingBody){

        Spending spending =  Spending.builder()
                .user(createSpendingBody.user())
                .category(Category.builder()
                        .id(createSpendingBody.categoryId())
                        .build()
                )
                .description(createSpendingBody.description())
                .updatedAt(LocalDateTime.now())
                .date(LocalDateTime.now())
                .build();

        spending.setAmount(createSpendingBody.amount());
        createSpendingBody.user().getSpending().add(spending);
        return spending;
    }

    public SpendingDTO mapToDTO(Spending spending){
        return new SpendingDTO(
                spending.getId(),
                spending.getDescription(),
                spending.getCategory().getId(),
                spending.getDate(),
                spending.getAmount(),
                spending.getUpdatedAt()
        );
    }

}
