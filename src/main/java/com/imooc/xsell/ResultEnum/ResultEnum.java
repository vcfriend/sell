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
  ORDER_NOT_EXIST(12, "订单不存在"), 
  ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"), 
  ORDER_STATUS_ERROR(14, "订单状态不正确"), 
  ORDER_UPDATE_FAIL(15, "更新订单失败"), 
  ORDER_DETAIL_EMPTY(16, "订单详情为空"), 
  ORDER_PAY_STATUS_ERROR(17, "订单支付状态不正确"),;

  private Integer code;

  private String message;

  ResultEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}
