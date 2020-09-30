package com.xh.microservice.common.wrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.stream.Collectors;

public interface BaseWrapper<T, E> {
    T wrapper(E e);

    default List<T> wrapperList(List<E> eList){
        return eList.stream().map(e -> wrapper(e)).collect(Collectors.toList());
    }

    default IPage<T> wrapperPage(IPage<E> ePage){
        IPage<T> tPage = new Page(ePage.getCurrent(), ePage.getSize(), ePage.getTotal());
        final List<T> records = wrapperList(ePage.getRecords());
        tPage.setRecords(records);
        return tPage;
    }
}
