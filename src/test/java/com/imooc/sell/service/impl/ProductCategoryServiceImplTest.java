package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.service.ProductCategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Test
    public void findOne() throws Exception {
        ProductCategory one = productCategoryService.findOne(1);
        Assert.assertEquals(new Integer(1),one.getCategoryId());
    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> all = productCategoryService.findAll();
        Assert.assertNotEquals(0,all.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> byCategoryTypeIn = productCategoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4));
        Assert.assertNotEquals(0,byCategoryTypeIn.size());
    }

    @Test
    @Transactional
    public void save() throws Exception {
        ProductCategory productCategory = new ProductCategory("主食", 12);
        ProductCategory save = productCategoryService.save(productCategory);
        Assert.assertNotNull(save);
    }

}