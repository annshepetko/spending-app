package com.ann.spending.spending.service;

import com.ann.spending.category.entity.Category;
import com.ann.spending.spending.dto.SpendingDTO;
import com.ann.spending.spending.entity.Spending;
import com.ann.spending.spending.dto.CreateSpendingBody;
import com.ann.spending.spending.interfaces.SpendingCrudService;
import com.ann.spending.spending.interfaces.SpendingDaoService;
import com.ann.spending.spending.mapper.SpendingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SpendingCrudServiceImpl implements SpendingCrudService {

    private final SpendingDaoService spendingDaoService;
    private final SpendingMapper spendingMapper;

    public SpendingCrudServiceImpl(
            SpendingDaoService spendingDaoService, SpendingMapper spendingMapper) {
        this.spendingDaoService = spendingDaoService;
        this.spendingMapper = spendingMapper;
    }

    @Override
    @Transactional
    public void createSpending(CreateSpendingBody createSpendingBody) {

        Spending spending = spendingMapper.mapToSpending(createSpendingBody);
        spendingDaoService.save(spending);
    }

    @Override
    @Transactional
    public void deleteSpending(Long id){
        spendingDaoService.deleteById(id);
    }


    @Override
    @Transactional
    public Spending update(SpendingDTO spendingDTO){

        Spending spending = spendingDaoService.findById(spendingDTO.id());
        spending.setAmount(spendingDTO.amount());
        spending.setUpdatedAt(LocalDateTime.now());
        spending.setDescription(spending.getDescription());
        spending.setCategory(new Category(spendingDTO.categoryId()));

        return spending;
    }

}
