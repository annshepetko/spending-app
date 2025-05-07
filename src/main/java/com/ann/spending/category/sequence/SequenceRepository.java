package com.ann.spending.category.sequence;

import java.util.List;

public interface SequenceRepository {
    Long getNextSequenceValue();
    List<Long> getNextSequenceValues(int count);
}
