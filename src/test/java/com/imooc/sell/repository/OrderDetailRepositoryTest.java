package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("D111");
        orderDetail.setOrderId("O111");
        orderDetail.setProductIcon("http://xxxx.jpg");
        orderDetail.setProductId("1");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(3.5));
        orderDetail.setProductQuantity(20);

        OrderDetail save = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(save);
    }
    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> o111 = orderDetailRepository.findByOrderId("O111");
        Assert.assertNotEquals(0, o111.size());
    }

}