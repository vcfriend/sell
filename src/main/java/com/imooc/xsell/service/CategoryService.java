package com.imooc.xsell.service;

import com.imooc.xsell.dataobject.ProductCategory;

import java.util.List;

/**
 * @author 亚林
 * @time 19/10/4,0004、20:16
 */
public interface CategoryService {

  ProductCategory findOne(Integer categoryId);

  List<ProductCategory> findAll();

  List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

  ProductCategory save(ProductCategory productCategory);

}
