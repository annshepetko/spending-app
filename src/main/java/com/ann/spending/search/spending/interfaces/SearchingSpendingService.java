package com.ann.spending.search.spending.interfaces;

import com.ann.spending.filter.page.OrderPage;
import com.ann.spending.spending.entity.Spending;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;

public abstract class SearchingSpendingService<T> implements SearchService<Spending, T> {

    private final EntityManager entityManager;

    protected SearchingSpendingService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Page<Spending> findByFilter(T filter, OrderPage orderPage) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Spending> spendingCriteriaQuery = criteriaBuilder.createQuery(Spending.class);
        Root<Spending> root = spendingCriteriaQuery.from(Spending.class);

        CriteriaRequestBody criteriaRequestBody = new CriteriaRequestBody(root, criteriaBuilder, spendingCriteriaQuery);
        Predicate predicate = buildPredicate(filter, criteriaRequestBody);

        setSorting(criteriaRequestBody, orderPage);

        spendingCriteriaQuery.where(predicate);

        TypedQuery<Spending> typedQuery = getSpendingTypedQuery(orderPage, spendingCriteriaQuery);

        Pageable pageable = getPageable(orderPage);
        long foundedCount = getSpendingCount(filter);

        return buildPage(typedQuery, pageable, foundedCount);
    }

    protected abstract Predicate buildPredicate(T t, CriteriaRequestBody criteriaRequestBody);

    private void setSorting(CriteriaRequestBody criteriaRequest, OrderPage orderPage) {

        CriteriaBuilder criteriaBuilder = criteriaRequest.criteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaRequest.criteriaQuery();
        Root root = criteriaRequest.root();

        if (orderPage.getSortDirection() == Sort.Direction.ASC) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(orderPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(orderPage.getSortBy())));
        }
    }


    private static PageImpl<Spending> buildPage(TypedQuery<Spending> typedQuery, Pageable pageable, long foundedCount) {
        return new PageImpl<>(typedQuery.getResultList(), pageable, foundedCount);
    }

    private TypedQuery<Spending> getSpendingTypedQuery(OrderPage orderPage, CriteriaQuery<Spending> spendingCriteriaQuery) {

        TypedQuery<Spending> typedQuery = entityManager.createQuery(spendingCriteriaQuery);
        typedQuery.setFirstResult(orderPage.getPageCount() * orderPage.getPageSize());
        typedQuery.setMaxResults(orderPage.getPageSize());

        return typedQuery;
    }

    private Long getSpendingCount(T filter) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Spending> root = countQuery.from(Spending.class);

        CriteriaRequestBody<Spending> criteriaRequestBody = new CriteriaRequestBody(root, criteriaBuilder, countQuery);
        Predicate predicate = buildPredicate(filter, criteriaRequestBody);

        countQuery.select(criteriaBuilder.count(root)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(OrderPage orderPage) {
        Sort sort = Sort.by(orderPage.getSortDirection(), orderPage.getSortBy());
        return PageRequest.of(orderPage.getPageCount(), orderPage.getPageSize(), sort);
    }

    public record CriteriaRequestBody<T>(
            Root<T> root,
            CriteriaBuilder criteriaBuilder,
            CriteriaQuery<T> criteriaQuery
    ) {
    }

}
