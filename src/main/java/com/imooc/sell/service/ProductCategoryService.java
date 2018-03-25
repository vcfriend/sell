package com.imooc.sell.service;

import com.imooc.sell.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目
 * @author 向亚林
 * 2018/3/25 15:47
 */
public interface ProductCategoryService {
    /**
     * 查询一个类目
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查询所有类目
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 查询指定类目
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 保存或修改类目
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);
}
