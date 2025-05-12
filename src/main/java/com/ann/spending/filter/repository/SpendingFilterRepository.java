package com.ann.spending.filter.repository;

import com.ann.spending.filter.dto.SearchSpendingRequest;
import com.ann.spending.filter.interfaces.FilterRepository;
import com.ann.spending.filter.page.OrderPage;
import com.ann.spending.spending.entity.Spending;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SpendingFilterRepository implements FilterRepository<Spending, SearchSpendingRequest> {

    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public SpendingFilterRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Transactional
    @Override
    public Page<Spending> findByFilter(SearchSpendingRequest searchSpendingRequest, OrderPage orderPage) {

        CriteriaQuery<Spending> criteriaQuery = criteriaBuilder.createQuery(Spending.class);
        Root<Spending> root = criteriaQuery.from(Spending.class);
        Predicate predicate = buildPredicate(root, searchSpendingRequest);
        criteriaQuery.where(predicate);

        if (orderPage.getSortBy() != null) {
            setSorting(root, criteriaQuery, orderPage);
        }

        TypedQuery<Spending> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(orderPage.getPageCount() * orderPage.getPageSize());
        typedQuery.setMaxResults(orderPage.getPageSize());

        Pageable pageable = getPageable(orderPage);
        long foundedCount = getSpendingCount(searchSpendingRequest);

        return new PageImpl<>(typedQuery.getResultList(), pageable, foundedCount);
    }

    private Long getSpendingCount(SearchSpendingRequest request) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Spending> root = countQuery.from(Spending.class);
        Predicate predicate = buildPredicate(root, request);
        countQuery.select(criteriaBuilder.count(root)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Predicate buildPredicate(Root<Spending> root, SearchSpendingRequest request) {
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

    private void setSorting(Root<Spending> root, CriteriaQuery<Spending> criteriaQuery, OrderPage orderPage) {
        if (orderPage.getSortDirection() == Sort.Direction.ASC) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(orderPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(orderPage.getSortBy())));
        }
    }

    private Pageable getPageable(OrderPage orderPage) {
        Sort sort = Sort.by(orderPage.getSortDirection(), orderPage.getSortBy());
        return PageRequest.of(orderPage.getPageCount(), orderPage.getPageSize(), sort);
    }
}
