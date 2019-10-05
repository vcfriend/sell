package com.imooc.xsell.service.impl;

import com.imooc.xsell.dataobject.ProductInfo;
import com.imooc.xsell.enums.ProductStatusEnum;
import com.imooc.xsell.repository.ProductInfoRepository;
import com.imooc.xsell.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 亚林
 * date 19/10/5,0005 14:32
 */
@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {

  ProductInfoRepository repository;

  @Override
  public ProductInfo findOne(String productId) {
    return repository.findOne(productId);
  }

  @Override
  public List<ProductInfo> findUpAll() {
    return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
  }

  @Override
  public Page<ProductInfo> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public ProductInfo save(ProductInfo productInfo) {
    return repository.save(productInfo);
  }
}
