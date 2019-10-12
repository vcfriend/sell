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
  PARAM_ERROR(1, "参数不正确"),
  PRODUCT_NOT_EXIST(10, "商品不存在"),
  PRODUCT_STOCK_ERROR(11, "商品库存不正确"),
  ORDER_NOT_EXIST(12, "订单不存在"), 
  ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"), 
  ORDER_STATUS_ERROR(14, "订单状态不正确"), 
  ORDER_UPDATE_FAIL(15, "更新订单失败"), 
  ORDER_DETAIL_EMPTY(16, "订单详情为空"), 
  ORDER_PAY_STATUS_ERROR(17, "订单支付状态不正确"),
  CART_NOT_EMPTY(18, "购物车不能为空"),
  ORDER_OPEND_ID_ERROR(19, "订单的openid错误"), 
  WECHAT_MP_ERROR(20, "微信公众号方面错误信息");

  private Integer code;

  private String message;

  ResultEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}
