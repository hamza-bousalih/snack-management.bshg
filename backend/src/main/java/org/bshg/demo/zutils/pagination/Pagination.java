package org.bshg.demo.zutils.pagination;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class Pagination<T> {
    private List<T> data;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;

    public Pagination() {
    }

    public Pagination(Page<T> found) {
        this.data = found.stream().toList();
        this.page = found.getNumber();
        this.size = found.getSize();
        this.totalElements = found.getTotalElements();
        this.totalPages = found.getTotalPages();
        this.first = found.isFirst();
        this.last = found.isLast();
    }

    public <N> Pagination<N> convert(Function<List<T>, List<N>> converter) {
        var newPagination = new Pagination<N>();
        newPagination.data = converter.apply(data);
        newPagination.page = page;
        newPagination.size = size;
        newPagination.totalElements = totalElements;
        newPagination.totalPages = totalPages;
        newPagination.first = first;
        newPagination.last = last;
        return newPagination;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
