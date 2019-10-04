package com.imooc.xsell.repository;

import com.imooc.xsell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 亚林
 * @time 19/10/4,0004、16:37
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
  List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
