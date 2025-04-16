package com.ann.spending.category.entity;

import com.ann.spending.authorization.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CustomCategory {

    @Id
    @SequenceGenerator(name = "category_seq_gen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_gen")
    @Column(name = "id", nullable = false)
    private Long id;


    private String name;

    @ManyToOne
    private User user;
}
