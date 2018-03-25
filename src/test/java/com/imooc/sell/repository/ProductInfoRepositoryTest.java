package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import utils.Info;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    ProductInfoRepository productInfoRepository;

    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> byProductStatus = productInfoRepository.findByProductStatus(0);
        Info.print(byProductStatus);
    }

    @Test
    public void findByOne() {
        ProductInfo one = productInfoRepository.findOne("1");
        Info.print(one);
    }




}