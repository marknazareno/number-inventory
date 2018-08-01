package com.mnazareno.numberinventory.web.model;

import java.util.List;

public class Response<T> {

    private List<T> results;

    private int size;

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Response(List<T> list) {
        this.results = list;
        this.size = list.size();
    }
}
