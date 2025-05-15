package com.ann.spending.search.spending.interfaces;

import com.ann.spending.filter.page.PageParams;
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
    public Page<Spending> findByFilter(T filter, PageParams pageParams) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Spending> spendingCriteriaQuery = criteriaBuilder.createQuery(Spending.class);
        Root<Spending> root = spendingCriteriaQuery.from(Spending.class);

        CriteriaRequestBody criteriaRequestBody = new CriteriaRequestBody(root, criteriaBuilder, spendingCriteriaQuery);
        Predicate predicate = buildPredicate(filter, criteriaRequestBody);

        setSorting(criteriaRequestBody, pageParams);

        spendingCriteriaQuery.where(predicate);

        TypedQuery<Spending> typedQuery = getSpendingTypedQuery(pageParams, spendingCriteriaQuery);

        Pageable pageable = getPageable(pageParams);
        long foundedCount = getSpendingCount(filter);

        return buildPage(typedQuery, pageable, foundedCount);
    }

    protected abstract Predicate buildPredicate(T t, CriteriaRequestBody criteriaRequestBody);

    private void setSorting(CriteriaRequestBody criteriaRequest, PageParams pageParams) {

        CriteriaBuilder criteriaBuilder = criteriaRequest.criteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaRequest.criteriaQuery();
        Root root = criteriaRequest.root();

        if (pageParams.getSortDirection() == Sort.Direction.ASC) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(pageParams.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(pageParams.getSortBy())));
        }
    }


    private static PageImpl<Spending> buildPage(TypedQuery<Spending> typedQuery, Pageable pageable, long foundedCount) {
        return new PageImpl<>(typedQuery.getResultList(), pageable, foundedCount);
    }

    private TypedQuery<Spending> getSpendingTypedQuery(PageParams pageParams, CriteriaQuery<Spending> spendingCriteriaQuery) {

        TypedQuery<Spending> typedQuery = entityManager.createQuery(spendingCriteriaQuery);
        typedQuery.setFirstResult(pageParams.getPageCount() * pageParams.getPageSize());
        typedQuery.setMaxResults(pageParams.getPageSize());

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

    private Pageable getPageable(PageParams pageParams) {
        Sort sort = Sort.by(pageParams.getSortDirection(), pageParams.getSortBy());
        return PageRequest.of(pageParams.getPageCount(), pageParams.getPageSize(), sort);
    }

    public record CriteriaRequestBody<T>(
            Root<T> root,
            CriteriaBuilder criteriaBuilder,
            CriteriaQuery<T> criteriaQuery
    ) {
    }

}
