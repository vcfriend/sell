package com.imooc.xsell.service.impl;

import com.imooc.xsell.XsellApplicationTests;
import com.imooc.xsell.dataobject.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CategoryServiceImplTest extends XsellApplicationTests {
  @Autowired
  private CategoryServiceImpl categoryService;

  @Test
  public void findOne() {
    ProductCategory one = categoryService.findOne(1);
    System.out.println(one);
  }

  @Test
  public void findAll() {
    List<ProductCategory> all = categoryService.findAll();
    all.forEach(productCategory -> System.out.println(productCategory));
  }

  @Test
  public void findByCategoryTypeIn() {
    List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(11, 22, 33));
    productCategoryList.forEach(productCategory -> System.out.println(productCategory));
  }

  @Test
  public void save() {
    ProductCategory productCategory = new ProductCategory().setCategoryType(44).setCategoryName("男生专享");
    ProductCategory save = categoryService.save(productCategory);
    System.out.println(save);
  }
}