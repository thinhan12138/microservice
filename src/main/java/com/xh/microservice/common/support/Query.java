package com.xh.microservice.common.support;

public class Query {
    private Integer current = 1;
    private Integer pageSize = 10;
    private String ascs;
    private String descs;

    public Query() {
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getAscs() {
        return ascs;
    }

    public void setAscs(String ascs) {
        this.ascs = ascs;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }
}
