package com.ann.spending.category.entity;

import com.ann.spending.authorization.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class BasicCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "basic_category_id", nullable = false)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "basicCategories")
    private List<User> user = new ArrayList<>();

}
