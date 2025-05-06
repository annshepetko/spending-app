package com.ann.spending.spending.interfaces;

import com.ann.spending.spending.entity.Spending;

public interface SpendingDaoService {
    Spending findById(Long id);

    Spending save(Spending spending);

    void deleteById(Long id);
}
