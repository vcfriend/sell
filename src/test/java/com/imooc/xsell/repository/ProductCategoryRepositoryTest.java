package com.imooc.xsell.repository;

import com.imooc.xsell.XsellApplicationTests;
import com.imooc.xsell.dataobject.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;


public class ProductCategoryRepositoryTest extends XsellApplicationTests {

  @Autowired
  ProductCategoryRepository repository;

  @Test
  public void findOneTest(){
    ProductCategory one = repository.findOne(1);
    System.out.println(one);
  }

  @Test
  public void saveTest() {
    ProductCategory save = repository.save(new ProductCategory().setCategoryName("男生最爱").setCategoryType(33));
    System.out.println(save);
  }

  @Test
  public void updateTest() {
    ProductCategory one = repository.findOne(2);
    one.setCategoryType(2222);
    ProductCategory save = repository.save(one);
    System.out.println(save);
  }

  @Test
  public void findByCategoryTypeIn() {
    List<Integer> list = Arrays.asList(11, 22, 33);
    List<ProductCategory> categoryList = repository.findByCategoryTypeIn(list);

    categoryList.forEach(System.out::println);
    categoryList.forEach(e -> System.out.println(e));
  }
}