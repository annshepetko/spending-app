package com.ann.spending.spending.entity;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Spending {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spending_id_generator")
    @SequenceGenerator(name = "spending_id_generator", allocationSize = 1)
    private Long id;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    private BigDecimal amount;

    public Spending(BigDecimal amount, LocalDateTime date) {

        this.amount = amount;
        this.date = date;
    }

    private String description;

    private LocalDateTime updatedAt;

    @Column(name = "spent_at", nullable = false)
    private LocalDateTime date;


    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setAmount(BigDecimal amount) {
        if (amount.longValue() < 0) {
            throw new RuntimeException("amount is less than 0");
        }
        this.amount = amount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category){
        this.category = category;
    }

}
