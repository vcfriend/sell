package com.imooc.xsell.service.impl;

import com.imooc.xsell.dataobject.ProductInfo;
import com.imooc.xsell.enums.ProductStatusEnum;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class ProductServiceImplTest {
  @Autowired
  ProductServiceImpl productService;

  @Test
  public void findOne() {
    ProductInfo one = productService.findOne("123456");
    System.out.println(one);
  }

  @Test
  public void findUpAll() {
    List<ProductInfo> upAll = productService.findUpAll();
    upAll.forEach(System.out::println);
  }

  @Test
  public void findAll() {
    Page<ProductInfo> productInfoPage = productService.findAll(new PageRequest(0, 2));
    productInfoPage.getContent().forEach(System.out::println);
  }

  @Test
  public void save() {
    ProductInfo productInfo = new ProductInfo();
    productInfo.setProductId("123458");
    productInfo.setProductName("皮皮虾");
    productInfo.setProductPrice(new BigDecimal(3.2));
    productInfo.setProductStock(100);
    productInfo.setProductDescription("很好吃的虾");
    productInfo.setProductIcon("http://xxxxx.jpg");
    productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
    productInfo.setCategoryType(2);

    ProductInfo result = productService.save(productInfo);
    System.out.println(result);

  }
}