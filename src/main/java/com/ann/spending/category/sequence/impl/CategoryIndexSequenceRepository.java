package com.ann.spending.category.sequence.impl;

import com.ann.spending.category.sequence.SequenceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryIndexSequenceRepository implements SequenceRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Long getNextSequenceValue() {
        return ((Number) entityManager
                .createNativeQuery("SELECT nextval('category_index_seq')")
                .getSingleResult()).longValue();
    }


    @Override
    public List<Long> getNextSequenceValues(int count) {
        return entityManager.createNativeQuery(
                        "SELECT nextval('category_index_seq') FROM generate_series(1, :count)")
                .setParameter("count", count)
                .getResultList()
                .stream()
                .map(val -> ((Number) val).longValue())
                .toList();
    }
}
