package com.imooc.xsell.repository;

import com.imooc.xsell.XsellApplicationTests;
import com.imooc.xsell.dataobject.ProductInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class ProductInfoRepositoryTest extends XsellApplicationTests {
  @Autowired
  ProductInfoRepository repository;

  @Test
  public void saveTest(){
    ProductInfo productInfo = new ProductInfo().setProductId("123456")
        .setProductName("皮蛋粥")
        .setProductPrice(new BigDecimal(3.5))
        .setProductStock(100)
        .setProductDescription("很好喝的粥")
        .setProductIcon("http://xxxx.jpg")
        .setProductStatus(0)
        .setCategoryType(22);
    ProductInfo save = repository.save(productInfo);
    System.out.println(save);
  }

  @Test
  public void findByProductStatus() {
    List<ProductInfo> productInfoList = repository.findByProductStatus(0);
    productInfoList.forEach(System.out::println);
  }
}