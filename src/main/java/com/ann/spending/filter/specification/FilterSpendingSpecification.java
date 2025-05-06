package com.ann.spending.filter.specification;

import com.ann.spending.filter.dto.FilterSpendingRequest;
import com.ann.spending.spending.entity.Spending;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FilterSpendingSpecification implements Specification<Spending> {

    private final FilterSpendingRequest request;

    public FilterSpendingSpecification(FilterSpendingRequest request) {
        this.request = request;
    }

    @Override
    public Predicate toPredicate(Root<Spending> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (request.categoryId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("category").get("id"), request.categoryId()));
        }
        if (request.description() != null) {
            predicates.add(criteriaBuilder.like(root.get("description"), "%" + request.description() + "%"));
        }
        if (request.spentFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("spentAt"), request.spentFrom()));
        }
        if (request.spentTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("spentAt"), request.spentTo()));
        }
        if (request.maxAmount() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), request.maxAmount()));
        }
        if (request.minAmount() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), request.minAmount()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
