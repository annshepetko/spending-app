package com.ann.spending.spending;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.spending.dto.SpendingDTO;
import com.ann.spending.spending.entity.Spending;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpendingRepository extends JpaRepository<Spending, Long> {

    @Query("""
                SELECT new com.ann.spending.spending.dto.SpendingDTO(
                    s.id,
                    s.description,
                    s.category.id,
                    s.date,
                    s.amount,
                    s.updatedAt
                )
                FROM Spending s
                where s.user.id = :userId
            """)
    Page<SpendingDTO> findAll(Pageable pageable, Long userId);

    @Query("select s from Spending s where s.user =:user")
    List<Spending> findAllUserSpending(User user);
}