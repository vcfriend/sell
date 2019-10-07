package com.imooc.xsell.ResultEnum;

import lombok.Getter;

/**
 * 错误信息枚举
 *
 * @author 亚林
 * date 19/10/7,0007 10:49
 */
@Getter
public enum  ResultEnum {
  PRODUCT_NOT_EXIST(10, "商品不存在"),

  PRODUCT_STOCK_ERROR(11, "商品库存不正确"),
          ;

  private Integer code;

  private String message;

  ResultEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}
