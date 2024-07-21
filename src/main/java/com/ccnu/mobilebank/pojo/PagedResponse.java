package com.ccnu.mobilebank.pojo;

import java.util.List;

public class PagedResponse<T> {
    private T data;
    private long total;

    public PagedResponse(T data, long total) {
        this.data = data;
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}

