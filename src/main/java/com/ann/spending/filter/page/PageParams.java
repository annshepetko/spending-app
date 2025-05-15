package com.ann.spending.filter.page;

import org.springframework.data.domain.Sort;

public class PageParams {

    private Integer pageCount = 0;
    private Integer pageSize = 5;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy;

    public PageParams(Integer pageCount, Integer pageSize, Sort.Direction sortDirection, String sortBy) {
        this.pageCount = pageCount;
        this.pageSize = pageSize;
        this.sortDirection = sortDirection;
        this.sortBy = sortBy;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
