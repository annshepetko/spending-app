package com.ann.spending.spending.mapper;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.spending.dto.AddSpendingRequest;
import com.ann.spending.spending.dto.CreateSpendingBody;
import org.springframework.stereotype.Service;

@Service
public class CreationSpendingMapper {

    public CreateSpendingBody mapToSpendingBody(AddSpendingRequest addSpendingRequest, User user){

        return new CreateSpendingBody(
                addSpendingRequest.amount(),
                addSpendingRequest.categoryId(),
                addSpendingRequest.description(),
                user
        );
    }
}
