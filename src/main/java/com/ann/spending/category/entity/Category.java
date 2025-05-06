package com.ann.spending.category.entity;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.spending.entity.Spending;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @SequenceGenerator(name = "category_seq_gen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_gen")
    @Column(name = "id", nullable = false)
    private Long id;


    public Category(Long id) {
        this.id = id;
    }

    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<User> users;


    private Integer index;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.ALL})
    private List<Spending> spending = new ArrayList<>();
}
