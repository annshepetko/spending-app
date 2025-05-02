package com.ann.spending.category.entity;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.spending.Spending;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "customCategory", cascade = {CascadeType.ALL})
    private List<Spending> spending = new ArrayList<>();
}
