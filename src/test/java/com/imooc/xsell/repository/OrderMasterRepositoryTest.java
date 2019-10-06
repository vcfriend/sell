package com.imooc.xsell.repository;

import com.imooc.xsell.dataobject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

  private static final String OPEN_ID = "110110";
  @Autowired
  private OrderMasterRepository repository;

  @Test
  public void saveTest() {
    OrderMaster orderMaster = new OrderMaster();
    orderMaster.setOrderId("1234567");
    orderMaster.setBuyerName("师兄");
    orderMaster.setBuyerPhone("13311223344");
    orderMaster.setBuyerAddress("买家地址");
    orderMaster.setBuyerOpenid(OPEN_ID);
    orderMaster.setOrderAmount(new BigDecimal(2.5));

    OrderMaster save = repository.save(orderMaster);
    System.out.println(save);
  }

  @Test
  public void findByAll() {
    List<OrderMaster> all = repository.findAll();
    all.forEach(System.out::println);
  }

  @Test
  public void findByBuyerOpenid() {
    Page<OrderMaster> byBuyerOpenid = repository.findByBuyerOpenid(OPEN_ID, new PageRequest(0, 3));
    byBuyerOpenid.getContent().forEach(System.out::println);
  }
}