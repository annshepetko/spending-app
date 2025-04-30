package com.ann.spending.spending;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.entity.BasicCategory;
import com.ann.spending.category.entity.CustomCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


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



}
