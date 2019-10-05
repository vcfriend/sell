package com.imooc.xsell.service;

import com.imooc.xsell.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品service
 *
 * @author 亚林
 * date 19/10/5,0005 14:26
 */
public interface ProductService {

  ProductInfo findOne(String productId);

  /**
   * 查询所有在架商品
   */
  List<ProductInfo> findUpAll();

  Page<ProductInfo> findAll(Pageable pageable);

  ProductInfo save(ProductInfo productInfo);

  //加库存

  //减库存
}
