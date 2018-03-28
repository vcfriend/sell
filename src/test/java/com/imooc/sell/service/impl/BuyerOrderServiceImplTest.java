package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.service.BuyerOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.rmi.runtime.Log;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BuyerOrderServiceImplTest {
    @Autowired
    BuyerOrderService buyerOrderService;
    private final String BUYER_OPENID = "wx111222";
    private final String ORDER_ID = "O111";

    @Test
    public void findOrderOne() throws Exception {
        OrderDTO orderOne = buyerOrderService.findOrderOne(BUYER_OPENID, ORDER_ID);
        log.info("orderOne={}", orderOne);
        Assert.assertNotNull(orderOne);
    }

    @Test
    public void cancelOrder() throws Exception {
        OrderDTO orderDTO = buyerOrderService.cancelOrder(BUYER_OPENID, ORDER_ID);
        log.info("orderDTO={}", orderDTO);
        Assert.assertNotNull(orderDTO);
    }

}