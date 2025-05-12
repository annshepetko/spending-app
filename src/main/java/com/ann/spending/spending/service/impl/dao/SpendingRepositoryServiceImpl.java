package com.ann.spending.spending.service.impl.dao;

import com.ann.spending.spending.SpendingRepository;
import com.ann.spending.spending.dto.SpendingDTO;
import com.ann.spending.spending.entity.Spending;
import com.ann.spending.spending.interfaces.SpendingDaoService;
import com.ann.spending.spending.interfaces.SpendingPageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SpendingRepositoryServiceImpl implements SpendingDaoService {

    private final SpendingRepository spendingRepository;

    public SpendingRepositoryServiceImpl(SpendingRepository spendingRepository) {
        this.spendingRepository = spendingRepository;
    }

    @Override
    public Spending findById(Long id) {
        return spendingRepository.findById(id).orElseThrow(() -> new RuntimeException("Spending is not found"));
    }

    @Override
    public Spending save(Spending spending) {
        return spendingRepository.save(spending);
    }

    @Override
    public void deleteById(Long id) {
        spendingRepository.deleteById(id);
    }


}
