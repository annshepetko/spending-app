package com.ann.spending.search.spending.specification;

import com.ann.spending.spending.entity.Spending;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpendingSearchSpecification implements Specification<Spending> {

    private final List<String> keyWords;

    public SpendingSearchSpecification(List<String> keyWords) {

        this.keyWords = keyWords;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        for (String keyword : keyWords) {
            String likePattern = "%" + keyword + "%";

            predicates.add(cb.like(cb.lower(root.get("description")), likePattern));
            predicates.add(cb.like(cb.lower(root.get("category").get("name")), likePattern));

        }

        return cb.or(predicates.toArray(new Predicate[0]));

    }

}
