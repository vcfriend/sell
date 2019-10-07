package com.imooc.xsell.service.impl;

import com.imooc.xsell.dataobject.OrderDetail;
import com.imooc.xsell.dto.BuyerOrderDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
  }

  @Test
  public void findList() {
  }

  @Test
  public void cancel() {
  }

  @Test
  public void finish() {
  }

  @Test
  public void pdid() {
  }
}