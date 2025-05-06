package com.ann.spending.spending.interfaces;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.spending.dto.SpendingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpendingPageService {

    Page<SpendingDTO> findSpendingPage(Pageable pageable, User user);
}
