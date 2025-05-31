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
    @Column(name = "category_id", nullable = false)
    private Long id;


    public Category(Long id) {
        this.id = id;
    }

    private String name;

    public Category(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCategory> userCategories = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Spending> spending = new ArrayList<>();

    @Column(name = "icon_name", nullable = false)
    private String iconName;

    public Category(String name, String iconName) {
        this.name = name;
        this.iconName = iconName;
    }
}
