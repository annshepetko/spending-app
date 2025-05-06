package com.ann.spending.spending.service.impl.page;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.spending.SpendingRepository;
import com.ann.spending.spending.dto.SpendingDTO;
import com.ann.spending.spending.interfaces.SpendingPageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SpendingPageServiceImpl implements SpendingPageService {
    private final SpendingRepository spendingRepository;

    public SpendingPageServiceImpl(SpendingRepository spendingRepository) {
        this.spendingRepository = spendingRepository;

    }

    @Override
    public Page<SpendingDTO> findSpendingPage(Pageable pageable, User user) {
        return spendingRepository.findAll(pageable, user.getId());
    }
}
