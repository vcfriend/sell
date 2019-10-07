package com.imooc.xsell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 购物车
 *
 * @author 亚林
 * date 19/10/7,0007 16:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
  
  /** 商品id */
  private String productId;

  /** 商品数量 */
  private Integer productQuantity;
  
}
