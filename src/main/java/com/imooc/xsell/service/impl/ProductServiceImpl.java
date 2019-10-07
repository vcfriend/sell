package com.imooc.xsell.service.impl;

import com.imooc.xsell.ResultEnum.ResultEnum;
import com.imooc.xsell.dataobject.ProductInfo;
import com.imooc.xsell.dto.CartDto;
import com.imooc.xsell.enums.ProductStatusEnum;
import com.imooc.xsell.exception.SellException;
import com.imooc.xsell.repository.ProductInfoRepository;
import com.imooc.xsell.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

  /**
   * 加库存
   *
   * @param cartDtoList 购物车列表
   */
  @Override
  public void increaseStock(List<CartDto> cartDtoList) {
    
  }

  /**
   * 减库存
   *
   * @param cartDtoList 购物车列表
   */
  @Override
  @Transactional
  public void decreaseStock(List<CartDto> cartDtoList) {
    for (CartDto cartDto : cartDtoList) {
      // 查询商品信息是否存在
      ProductInfo productInfo = repository.findOne(cartDto.getProductId());
      if (productInfo == null) {
        throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
      }
      // 商品库存是否大于销售数量
      Integer stock = productInfo.getProductStock() - cartDto.getProductQuantity();
      if ( stock < 0) {
        throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
      }
      
      productInfo.setProductStock(stock);
      
      repository.save(productInfo);
    }
  }
}
