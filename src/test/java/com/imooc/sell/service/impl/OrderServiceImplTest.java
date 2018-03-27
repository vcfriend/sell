package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.service.OrderService;
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
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    private final String BUYER_OPENID = "wx111222";
    private final String ORDER_ID = "O111";

    /**
     创建订单
     POST /sell/buyer/order/create
     API参数
     name: "张三"
     phone: "18868822111"
     address: "慕课网总部"
     openid: "ew3euwhd7sjw9diwkq" //用户的微信openid
     items: [{
     productId: "1423113435324",
     productQuantity: 2 //购买数量
     }]

     * @throws Exception
     */
    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("亚林");
        orderDTO.setBuyerAddress("龙潭路");
        orderDTO.setBuyerPhone("15011112222");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("2");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO orderDTO1 = orderService.create(orderDTO);
        log.info("[查询单个订单] result={}", orderDTO1);
        Assert.assertNotNull(orderDTO1);
    }

    @Test
    public void findOne() throws Exception {
        OrderDTO one = orderService.findOne(ORDER_ID);
        log.info("[查询单个订单 result={}]", one);
        Assert.assertEquals(ORDER_ID, one.getOrderId());
    }

    @Test
    public void findAllByOpentid() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<OrderDTO> orderDTOPage = orderService.findAllByOpentid(BUYER_OPENID, pageRequest);
        log.info("查询买家所有的订单: {} " + orderDTOPage.getContent());
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());

    }

    @Test
    public void cancel() throws Exception {
        OrderDTO one = orderService.findOne(ORDER_ID);
        OrderDTO orderDTO = orderService.cancel(one);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), orderDTO.getOrderStatus());
    }

    @Test
    public void finish() throws Exception {
    }

    @Test
    public void paid() throws Exception {
    }

}