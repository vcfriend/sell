package com.imooc.xsell.repository;

import com.imooc.xsell.dataobject.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

  @Autowired
  OrderDetailRepository repository;
  
  @Test
  public void saveTest(){
    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setDetailId("2222");
    orderDetail.setOrderId("2222");
    orderDetail.setProductIcon("http://xxxx.jpg");
    orderDetail.setProductId("2222");
    orderDetail.setProductName("皮蛋粥");
    orderDetail.setProductPrice(new BigDecimal(2.2));
    orderDetail.setProductQuantity(3);

    OrderDetail save = repository.save(orderDetail);
    System.out.println("save = " + save);
  }
  @Test
  public void findByOrderId() {
    List<OrderDetail> orderDetailList = repository.findByOrderId("1111");
    orderDetailList.forEach(System.out::println);
  }
  
}