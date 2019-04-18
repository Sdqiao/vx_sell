package com.xmcc.service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmcc.common.*;
import com.xmcc.dto.*;
import com.xmcc.entity.OrderDetail;
import com.xmcc.entity.OrderMaster;
import com.xmcc.entity.Product;
import com.xmcc.exception.CustomException;
import com.xmcc.repository.OrderDetailRepository;
import com.xmcc.repository.OrderMasterRepository;
import com.xmcc.service.OrderDetailService;
import com.xmcc.service.OrderMasterService;
import com.xmcc.service.ProductService;
import com.xmcc.utils.BigDecimalUtil;
import com.xmcc.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 邓桥
 * @date 2019-04-17 14:50
 */
@Service
public class OrderMasterServiceImpl implements OrderMasterService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    @Transactional
    public ResultResponse insertOrder(OrderMasterDto orderMasterDto) {
        //先取出订单项
        List<OrderDetailDto> items = orderMasterDto.getItems();
        //创建一个集合来存储订单，方便后面进行批量插入
        List<OrderDetail> details = Lists.newArrayList();
        //先初始化订单的总价
        BigDecimal totleMoney = new BigDecimal("0");
        //根据商品的id查询商品的信息
        for (OrderDetailDto dto:items ) {
            ResultResponse<Product> resultResponse = productService.findById(dto.getProductId());
            if(resultResponse.getCode()== ResultEnums.FAIL.getCode()){
                //商品未查询到，抛出异常
                throw new CustomException(resultResponse.getMsg());
            }
            //获得查询的商品
            Product product = resultResponse.getData();
            //判断商品的库存是否充足
            if(product.getProductStock()<dto.getProductQuantity()){
                throw new CustomException(ProductEnums.PRODUCT_NOT_ENOUGH.getMsg());
            }
            OrderDetail orderDetail = OrderDetail.builder().detailId(IdUtils.createIdbyUUID()).productName(product.getProductName())
                    .productIcon(product.getProductIcon()).productQuantity(dto.getProductQuantity()).productPrice(product.getProductPrice()).productId(product.getProductId()).build();
            details.add(orderDetail);
            //减少商品的库存
            product.setProductStock(product.getProductStock()-dto.getProductQuantity());
            //修改数据库
            productService.update(product);
            //计算价格
            totleMoney  = BigDecimalUtil.add(totleMoney,BigDecimalUtil.multi(product.getProductPrice(),dto.getProductQuantity()));
        }
        //生成订单的id
        String orderId = IdUtils.createIdbyUUID();
        //生成订单信息
        OrderMaster orderMaster = OrderMaster.builder().orderId(orderId).orderAmount(totleMoney).orderStatus(OrderEnums.NEW.getCode())
                .buyerAddress(orderMasterDto.getAddress()).buyerName(orderMasterDto.getName()).buyerOpenid(orderMasterDto.getOpenid())
                .buyerPhone(orderMasterDto.getPhone()).payStatus(PayEnums.WAIT.getCode()).build();
        //将生成的订单id设置到订单项中
        List<OrderDetail> orderDetailList = details.stream().map(orderDetail -> {
            orderDetail.setOrderId(orderId);
            return orderDetail;
        }).collect(Collectors.toList());
        //插入订单项
        orderDetailService.batchInsert(orderDetailList);
        //插入订单
        orderMasterRepository.save(orderMaster);
        //按照前端的数据格式返回数据
        HashMap<String, String> map = Maps.newHashMap();
        //按照前台要求的数据结构传入
        map.put("orderId",orderId);
        return ResultResponse.success(map);
    }

    @Override
    public ResultResponse getOrderList(ListDto listDto) {
        //先根据openid查询是否存在订单
        List<OrderMaster> byId = orderMasterRepository.findbyOpenId(listDto.getBuyerOpenid());
        if(byId==null||byId.size()==0){
            return ResultResponse.fail(ResultEnums.ORDER_NOTEXIST.getCode()+":"+ResultEnums.ORDER_NOTEXIST.getMsg());
        }
        //分页查出orderMaster
        Pageable pageable = new PageRequest(listDto.getPage(),listDto.getSize());
        Page<OrderMaster> orderMasters = orderMasterRepository.findbyOpenIdByPage(listDto.getBuyerOpenid(),pageable);
        List<OrderMaster> orderMasterList = orderMasters.getContent();
        //将orderId取出来存放到一个集合中
        List<String> orderIdList = Lists.newArrayList();
        for (int i = 0; i <orderMasterList.size() ; i++) {
            orderIdList.add(orderMasterList.get(i).getOrderId());
        }
        //根据orderId查询orderdetail
        List<OrderDetail> detailsList = orderDetailRepository.findAllByOrderIdIn(orderIdList);
        if(detailsList==null||detailsList.size()==0){
            detailsList=null;
        }
        //将ordermaster转换成dto，存储到集合中，作为数据返回
        List<OrderListDto> listDtos = Lists.newArrayList();
        for (OrderMaster orderMaster:orderMasterList) {
            OrderListDto dto = OrderListDto.adapt(orderMaster);
            dto.setOrderDetailList(detailsList);
            listDtos.add(dto);
        }
        HashMap<String,Object> map = Maps.newHashMap();
        map.put("openId",listDto.getBuyerOpenid());
        map.put("Data",listDtos);
        return ResultResponse.success(map);
    }

    @Override
    public ResultResponse getOrderDetail(DetailDto detailDto) {
        //先根据openid和orderId查询订单信息
        OrderMaster orderMaster = orderMasterRepository.findbyOpenIdAndOrderId(detailDto.getBuyerOpenid(),detailDto.getOrderId());
        if(orderMaster==null){
            return ResultResponse.fail(ResultEnums.ORDER_NOTEXIST.getCode()+":"+ResultEnums.ORDER_NOTEXIST.getMsg());
        }
        //根据orderId查询订单项
        List<OrderDetail>  orderDetails= orderDetailRepository.findAllByOrderId(detailDto.getOrderId());
        //将ordermaster转换成dto,作为数据返回
        OrderListDto dto = OrderListDto.adapt(orderMaster);
        dto.setOrderDetailList(orderDetails);
        HashMap<String,Object> map = Maps.newHashMap();
        map.put("Data",dto);
        return ResultResponse.success(map);
    }
    @Transactional
    @Override
    public ResultResponse cancleOrder(DetailDto detailDto) {
        //先查询订单是否存在
        OrderMaster orderMaster = orderMasterRepository.findbyOpenIdAndOrderId(detailDto.getBuyerOpenid(), detailDto.getOrderId());
        if (orderMaster == null) {
            return ResultResponse.fail(ResultEnums.ORDER_NOTEXIST.getCode() + ":" + ResultEnums.ORDER_NOTEXIST.getMsg());
        }
        //根据openid和orderId删除订单信息
        orderMasterRepository.deleteByOrderIdAndAndBuyerOpenid(detailDto.getOrderId(),detailDto.getBuyerOpenid());
        return ResultResponse.success(null);
    }
}
