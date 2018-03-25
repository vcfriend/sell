package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.repository.ProductInfoRepository;
import com.imooc.sell.service.ProductCategoryService;
import com.imooc.sell.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {
    @Autowired
    ProductInfoService productInfoService;

    @Test
    public void findOne() throws Exception {
        ProductInfo one = productInfoService.findOne("1");
        Assert.assertNotEquals(new Integer(1),one.getProductId());
    }

    @Test
    public void findAllByUp() throws Exception {
        List<ProductInfo> byProductStatus = productInfoService.findAllByUp();
        Assert.assertNotEquals(0, byProductStatus.size());
    }

    @Test
    public void findAll() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> all = productInfoService.findAll(pageRequest);
        System.out.println("总记录条数: "+all.getTotalElements());
        Assert.assertNotEquals(0,all.getTotalElements());
    }

    @Test
    @Transactional
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("111");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(3.5));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好吃的虾");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(11);

        ProductInfo save = productInfoService.save(productInfo);
        Assert.assertNotNull(save);
    }

}