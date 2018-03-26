package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    OrderMasterRepository orderMasterRepository;

    private final String OPENID = "wx111222";

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("O555");
        orderMaster.setBuyerName("亚林");
        orderMaster.setBuyerPhone("15011118888");
        orderMaster.setBuyerAddress("绿汀路");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderStatus(1);
        orderMaster.setPayStatus(1);
        orderMaster.setOrderAmount(new BigDecimal(3.5));

        OrderMaster save = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(save);
    }


    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest pageRequest = new PageRequest(1, 2);

        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(OPENID, pageRequest);
        Assert.assertNotEquals(0, orderMasterPage.getTotalElements());
    }

}