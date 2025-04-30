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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
    @SequenceGenerator(name = "category_id_seq", allocationSize = 1)
    @Column(name = "category_id", nullable = false)
    private Long id;

    private String name;

    @ManyToOne
    private User user;

}
