package com.xmcc.repository;

import com.xmcc.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    @Query(value = "select * from order_master where buyer_openId = ?",nativeQuery = true)
    List<OrderMaster> findbyOpenId(String openId);
    @Query(value = "select * from order_master where buyer_openId = ?1",countQuery = "select count(*) from order_master where buyer_openId = ?1",nativeQuery = true)
    Page<OrderMaster> findbyOpenIdByPage(String buyeropenId, Pageable pageable);

    @Query(value = "select * from order_master where buyer_openId = ?1 and order_id = ?2",nativeQuery = true)
    OrderMaster findbyOpenIdAndOrderId(String openid, String orderId);

    void deleteByOrderIdAndAndBuyerOpenid(String orderId,String openId);
}
