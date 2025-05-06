package com.ann.spending.search.spending.impl;

import com.ann.spending.filter.page.OrderPage;
import com.ann.spending.search.spending.interfaces.SearchingSpendingService;
import com.ann.spending.search.spending.specification.SpendingSearchSpecification;
import com.ann.spending.spending.entity.Spending;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultSpendingSearchService extends SearchingSpendingService<List<String>> {

    public DefaultSpendingSearchService(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Page<Spending> findByFilter(List<String> filter, OrderPage orderPage) {
        return super.findByFilter(filter, orderPage);
    }

    @Override
    protected Predicate buildPredicate(List<String> strings, CriteriaRequestBody criteriaRequestBody) {

        return buildSpecification(strings).toPredicate(
                criteriaRequestBody.root(),
                criteriaRequestBody.criteriaQuery(),
                criteriaRequestBody.criteriaBuilder()
        );
    }

    private static Specification<Spending> buildSpecification(List<String> strings) {
        return new SpendingSearchSpecification(strings);
    }

}
