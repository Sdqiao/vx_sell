package com.xmcc.service.Impl;

import com.xmcc.dao.Impl.BatchDaoImpl;
import com.xmcc.entity.OrderDetail;
import com.xmcc.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 邓桥
 * @date 2019-04-17 14:51
 */
@Service
public class OrderDetailServiceImpl extends BatchDaoImpl<OrderDetail> implements OrderDetailService {
    @Override
    @Transactional //增删改触发事务
    public void  batchInsert(List<OrderDetail> orderDetailList){
        super.batchInsert(orderDetailList);
    }
}
