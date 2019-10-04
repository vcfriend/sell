package com.imooc.xsell.service.impl;

import com.imooc.xsell.dataobject.ProductCategory;
import com.imooc.xsell.repository.ProductCategoryRepository;
import com.imooc.xsell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 亚林
 * @time 19/10/4,0004、20:22
 */
@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private ProductCategoryRepository repository;

  @Override
  public ProductCategory findOne(Integer categoryId) {
    return repository.findOne(categoryId);
  }

  @Override
  public List<ProductCategory> findAll() {
    return repository.findAll();
  }

  @Override
  public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
    return repository.findByCategoryTypeIn(categoryTypeList);
  }

  @Override
  public ProductCategory save(ProductCategory productCategory) {
    return repository.save(productCategory);
  }
}
