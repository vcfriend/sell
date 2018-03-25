package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import utils.Info;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOneTest() {
        ProductCategory one = productCategoryRepository.findOne(1);
        Assert.assertEquals(new Integer(1),one.getCategoryId());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> productCategoryList = productCategoryRepository.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4));
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void findAllTest() {
        List<ProductCategory> all = productCategoryRepository.findAll();
        Assert.assertNotEquals(0,all.size());
    }

    @Test
//    @Transactional //测试完数据回滚
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory("热销榜", 1);
        ProductCategory save = productCategoryRepository.save(productCategory);
        Assert.assertNotNull(save);
    }


}