package com.imooc.xsell.repository;

import com.imooc.xsell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author 亚林
 * date 19/10/5,0005、9:38
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
  List<ProductInfo> findByProductStatus(Integer ProductStatus);

}
