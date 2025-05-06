package com.ann.spending.spending.interfaces;

import com.ann.spending.spending.dto.CreateSpendingBody;
import com.ann.spending.spending.dto.SpendingDTO;
import com.ann.spending.spending.entity.Spending;

public interface SpendingCrudService {
    void createSpending(CreateSpendingBody createSpendingBody);
    void deleteSpending(Long id);
    Spending update(SpendingDTO spendingDTO);

}
