package com.ann.spending.spending;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.entity.BasicCategory;
import com.ann.spending.category.entity.CustomCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Spending {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spending_id_generator")
    @SequenceGenerator(name = "spending_id_generator", allocationSize = 1)
    private Long id;

    @ManyToOne
    private BasicCategory basicCategory;

    @ManyToOne
    private CustomCategory customCategory;

    @ManyToOne
    private User user;

    private BigDecimal amount;

    public void setAmount(BigDecimal amount) {
        if (amount.longValue() < 0){
            throw new RuntimeException("amount is less than 0");
        }
        this.amount = amount;
    }

    @Column(name = "spent_at", nullable = false)
    private LocalDateTime date;

    public void setBasicCategory(BasicCategory basicCategory) {
        this.basicCategory = basicCategory;
        basicCategory.getSpendings().add(this);
    }

    public void setCustomCategory(CustomCategory customCategory){
        this.customCategory = customCategory;
        customCategory.getSpending().add(this);
    }

}
