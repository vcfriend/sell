package com.imooc.xsell.repository;

import com.imooc.xsell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 亚林
 * date 19/10/4
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
  List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
