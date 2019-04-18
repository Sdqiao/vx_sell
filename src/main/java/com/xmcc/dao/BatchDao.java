package com.xmcc.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BatchDao<T> {
    void batchInsert(List<T> list);
}
