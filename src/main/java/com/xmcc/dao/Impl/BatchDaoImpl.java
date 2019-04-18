package com.xmcc.dao.Impl;

import com.xmcc.dao.BatchDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author 邓桥
 * @date 2019-04-17 14:37
 */
public class BatchDaoImpl<T> implements BatchDao<T> {
    @PersistenceContext
    protected EntityManager em;
    @Override
    public void batchInsert(List<T> list) {
         int size = list.size();
        for (int i = 0; i <size ; i++) {
            if(size%100==0||i==(size-1)){
                em.flush();
                em.clear();
            }
        }
    }
}
