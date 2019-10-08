package com.imooc.xsell.service.impl;

import com.imooc.xsell.dataobject.OrderDetail;
import com.imooc.xsell.dto.BuyerOrderDto;
import com.imooc.xsell.enums.OrderStatusEnum;
import com.imooc.xsell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

  @Autowired
  OrderServiceImpl orderService;

  private static final String BUYER_OPENID = "110110";
  private static final String ORDER_ID = "1570441329769962874";
  
  @Test
  public void create() {
    BuyerOrderDto buyerOrderDto = new BuyerOrderDto()
            .setBuyerName("廖师兄")
            .setBuyerAddress("地址111")
            .setBuyerPhone("13312341234")
            .setBuyerOpenid(BUYER_OPENID);
    
    
    //购物车
    ArrayList<OrderDetail> orderDetails = new ArrayList<>();
    orderDetails.add(new OrderDetail().setProductId("123456").setProductQuantity(1));
    orderDetails.add(new OrderDetail().setProductId("123457").setProductQuantity(1));

    buyerOrderDto.setOrderDetailList(orderDetails);

    BuyerOrderDto result = orderService.create(buyerOrderDto);
    log.info("[创建订单] result={}", result);
    Assert.assertNotNull(result);
  }

  @Test
  public void findOne() {
    BuyerOrderDto one = orderService.findOne(ORDER_ID);
    log.info("[查询单个订单]= " + one);
    Assert.assertEquals(ORDER_ID, one.getOrderId());
  }

  @Test
  public void findList() {
    Page<BuyerOrderDto> buyerOrderDtoPage = orderService.findList(BUYER_OPENID, new PageRequest(0, 2));
    buyerOrderDtoPage.getContent().forEach(System.out::println);
    Assert.assertNotEquals(0,buyerOrderDtoPage.getTotalElements());
  }

  @Test
  public void cancel() {
    BuyerOrderDto one = orderService.findOne(ORDER_ID);
    BuyerOrderDto cancel = orderService.cancel(one);
    Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),cancel.getOrderStatus());
  }

  @Test
  public void finish() {
    BuyerOrderDto one = orderService.findOne(ORDER_ID);
    BuyerOrderDto finish = orderService.finish(one);
    Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), finish.getOrderStatus());
  }

  @Test
  public void pdid() {
    BuyerOrderDto one = orderService.findOne(ORDER_ID);
    BuyerOrderDto pdid = orderService.pdid(one);
    Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), pdid.getPayStatus());
  }
}