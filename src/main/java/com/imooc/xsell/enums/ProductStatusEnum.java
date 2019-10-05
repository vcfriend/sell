package com.imooc.xsell.enums;

import lombok.Getter;

/**
 * @author 亚林
 * date 19/10/5,0005 14:37
 */
@Getter
public enum ProductStatusEnum {
  UP(0,"在架")
  ,DOWN(1,"下架")
  ;

  private Integer code;
  private String message;


  ProductStatusEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}
