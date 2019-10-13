package com.imooc.xsell.service.impl;

import com.imooc.xsell.dto.BuyerOrderDto;
import com.imooc.xsell.service.OrderService;
import com.imooc.xsell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

  @Autowired
  private PayService payService;

  @Autowired
  private OrderService orderService;

  @Test
  public void create() {
    BuyerOrderDto orderDTO = orderService.findOne("1570612448199218015");
    payService.create(orderDTO);
  }
}