package com.xmcc.service;

import com.xmcc.common.ResultResponse;
import com.xmcc.dto.DetailDto;
import com.xmcc.dto.ListDto;
import com.xmcc.dto.OrderMasterDto;

/**
 * @author 邓桥
 * @date 2019-04-17 14:49
 */
public interface OrderMasterService {
    ResultResponse insertOrder(OrderMasterDto orderMasterDto);

    ResultResponse getOrderList(ListDto listDto);

    ResultResponse getOrderDetail(DetailDto detailDto);

    ResultResponse cancleOrder(DetailDto detailDto);
}
