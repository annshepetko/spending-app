package com.ann.spending.filter.repository;

import com.ann.spending.filter.dto.FilterSpendingRequest;
import com.ann.spending.filter.page.OrderPage;
import com.ann.spending.search.spending.interfaces.SearchingSpendingService;
import com.ann.spending.filter.specification.FilterSpendingSpecification;
import com.ann.spending.spending.entity.Spending;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class SpendingFilterService extends SearchingSpendingService<FilterSpendingRequest> {


    public SpendingFilterService(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Page<Spending> findByFilter(FilterSpendingRequest filter, OrderPage orderPage) {
        return super.findByFilter(filter, orderPage);
    }

    @Override
    protected Predicate buildPredicate(FilterSpendingRequest filterSpendingRequest, CriteriaRequestBody criteriaRequestBody) {

        return buildSpecification(filterSpendingRequest).toPredicate(
                criteriaRequestBody.root(),
                criteriaRequestBody.criteriaQuery(),
                criteriaRequestBody.criteriaBuilder()

        );
    }


    private static Specification<Spending> buildSpecification(FilterSpendingRequest request) {
        return new FilterSpendingSpecification(request);
    }
}
